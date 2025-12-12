package ru.teamscore.shipments;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Коллекция позиций отгрузки, отвечающая за хранение состава документа
 * и выполнение всех расчетов (суммы, поиск, расчет скидок).
 */
public class ShipmentItemsCollection {
    private final List<ShipmentItem> items;
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    /**
     * Конструктор преобразует массивы параллельных данных в список типизированных объектов {@link ShipmentItem}.
     */
    public ShipmentItemsCollection(int itemsCount, String[] itemsId, String[] itemsArticle,
                                   String[] itemsTitle, double[] itemsQuantity, double[] itemsPrice) {
        this.items = new ArrayList<>(itemsCount);
        for (int i = 0; i < itemsCount; i++) {
            this.items.add(new ShipmentItem(
                    itemsId[i], itemsArticle[i], itemsTitle[i], itemsQuantity[i], itemsPrice[i]
            ));
        }
    }

    /**
     * Возвращает полный список позиций в документе.
     * @return Неизменяемый список позиций.
     */
    public List<ShipmentItem> getItems() {
        return items;
    }

    /**
     * Суммарная стоимость товаров в документе.
     */
    public double totalAmount() {
        return items.stream()
                .map(ShipmentItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .doubleValue();
    }

    /**
     * Стоимость товара с переданным id.
     */
    public double itemAmount(String id) {
        return items.stream()
                .filter(item -> item.id().equals(id))
                .findFirst()
                .map(ShipmentItem::getAmount)
                .orElse(BigDecimal.ZERO)
                .doubleValue();
    }

    /**
     * Суммарная стоимость товаров, попадающих в список промо-акции.
     * Используется для базовой логики и для перемещений.
     */
    public double promoSum(String[] promoArticles) {
        Set<String> promoSet = Set.of(promoArticles);

        return items.stream()
                .filter(item -> promoSet.contains(item.article()))
                .map(ShipmentItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .doubleValue();
    }

    /**
     * Суммарное количество товаров (в штуках) в документе.
     */
    public double getTotalQuantity() {
        return items.stream()
                .map(ShipmentItem::quantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .doubleValue();
    }

    /**
     * Вспомогательный метод для расчета суммы промо-товаров с дисконтом.
     * Для продаж: применяется скидка с обязательным округлением до копейки в большую сторону.
     * Если скидка меньше или равна 0, вызывается стандартный метод без скидки.
     * @param promoArticles Список артикулов, участвующих в промо.
     * @param discount Процент скидки (0-100).
     * @return Суммарная стоимость промо-товаров со скидкой.
     */
    protected double calculatePromoSumWithDiscount(String[] promoArticles, double discount) {
        if (discount <= 0) {
            return this.promoSum(promoArticles);
        }

        BigDecimal discountFactor = BigDecimal.valueOf(100 - discount).divide(HUNDRED, 4, RoundingMode.HALF_UP);
        Set<String> promoSet = Set.of(promoArticles);

        return items.stream()
                .filter(item -> promoSet.contains(item.article()))
                .map(item -> {
                    BigDecimal itemAmount = item.quantity().multiply(item.price());

                    BigDecimal discountedAmount = itemAmount.multiply(discountFactor);

                    return discountedAmount.setScale(2, RoundingMode.CEILING);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .doubleValue();
    }
}