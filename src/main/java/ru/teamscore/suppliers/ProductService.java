package ru.teamscore.suppliers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервисный класс для выполнения поиска и расчета итоговой цены товаров.
 */
public class ProductService {
    private final List<NomenclatureItem> nomenclature;
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    public ProductService(List<NomenclatureItem> nomenclature) {
        this.nomenclature = nomenclature;
    }

    /**
     * Выполняет поиск товаров по артикулу (полное совпадение) или названию (частичное совпадение).
     *
     * @param query Строка для поиска (артикул или часть названия).
     * @return Список структурированных результатов поиска.
     */
    public List<SearchResult> searchProducts(String query) {
        if (query == null || query.isBlank()) {
            return List.of();
        }

        final String lowerCaseQuery = query.toLowerCase();

        return nomenclature.stream()
                .filter(item ->
                        item.article().equals(query) ||
                                item.title().toLowerCase().contains(lowerCaseQuery)
                )
                .map(this::buildSearchResult)
                .collect(Collectors.toList());
    }

    /**
     * Вспомогательный метод для расчета итоговой цены и агрегации данных о поставщике.
     */
    private SearchResult buildSearchResult(NomenclatureItem item) {
        Supplier supplier = item.supplier();
        BigDecimal finalPrice;
        Manufacturer realManufacturer;

        if (supplier instanceof Dealer dealer) {
            BigDecimal markupFactor = dealer.getMarkupPercentage().divide(HUNDRED, 4, RoundingMode.HALF_UP);

            BigDecimal totalFactor = BigDecimal.ONE.add(markupFactor);

            finalPrice = item.manufacturerPrice().multiply(totalFactor)
                    .setScale(2, RoundingMode.HALF_UP);

            realManufacturer = dealer.getManufacturer();
        } else if (supplier instanceof Manufacturer manufacturer) {
            finalPrice = item.manufacturerPrice().setScale(2, RoundingMode.HALF_UP);

            realManufacturer = manufacturer;
        } else {
            finalPrice = item.manufacturerPrice().setScale(2, RoundingMode.HALF_UP);
            realManufacturer = null;
        }

        return new SearchResult(
                item.article(),
                item.title(),
                finalPrice,
                supplier.getName(),
                supplier.getAddress(),
                realManufacturer != null ? realManufacturer.getName() : "Неизвестен"
        );
    }
}