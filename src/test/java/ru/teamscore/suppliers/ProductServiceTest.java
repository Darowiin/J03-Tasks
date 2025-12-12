package ru.teamscore.suppliers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private Manufacturer manA;
    private Dealer dealerX;
    private ProductService service;

    @BeforeEach
    void setUp() {
        manA = new Manufacturer("1111", "Alfa Manufacture", "Addr A");
        // Dealer X продает товары ManA с наценкой 15.0%
        dealerX = new Dealer("3333", "Dealer X", "Addr X", manA, 15.0);

        // 1. Товар от Производителя А (Цена 100.00. Итог: 100.00)
        NomenclatureItem itemManA = new NomenclatureItem("ART001", "Aluminium Profile L-Shape", 100.00, manA);

        // 2. Товар от Дилера X (Цена 200.00. Итог: 200.00 * 1.15 = 230.00)
        NomenclatureItem itemDealerXSimple = new NomenclatureItem("ART002", "Dealer Item T-Shape", 200.00, dealerX);

        // 3. Товар от Дилера X (Цена 3.3333. Итог: 3.3333 * 1.15 = 3.833295 -> 3.83)
        NomenclatureItem itemDealerXRounding = new NomenclatureItem("ART003", "Fancy Product C (Small)", 3.3333, dealerX);

        List<NomenclatureItem> nomenclature = List.of(itemManA, itemDealerXSimple, itemDealerXRounding);
        service = new ProductService(nomenclature);
    }

    @Test
    @DisplayName("Поиск: Полное совпадение по Артикулу")
    void searchProducts_matchByArticle_full() {
        List<SearchResult> results = service.searchProducts("ART002");

        assertEquals(1, results.size());
        assertEquals("Dealer Item T-Shape", results.get(0).title());
    }

    @Test
    @DisplayName("Поиск: Частичное совпадение по Названию (case insensitive)")
    void searchProducts_matchByTitle_partialAndCaseInsensitive() {
        List<SearchResult> results = service.searchProducts("Profile");

        assertEquals(1, results.size());
        assertEquals("ART001", results.get(0).article());
    }

    @Test
    @DisplayName("Поиск: Частичное совпадение по Названию, возвращающее несколько результатов")
    void searchProducts_matchByTitle_multipleResults() {
        List<SearchResult> results = service.searchProducts("Shape");

        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(r -> r.article().equals("ART001")));
        assertTrue(results.stream().anyMatch(r -> r.article().equals("ART002")));
    }

    @Test
    @DisplayName("Поиск: Пустой или null запрос возвращает пустой список")
    void searchProducts_nullOrBlankQuery() {
        assertTrue(service.searchProducts(null).isEmpty());
        assertTrue(service.searchProducts("").isEmpty());
    }

    @Test
    @DisplayName("Цена и данные: Товар от Производителя")
    void searchProducts_manufacturerItem_correctPriceAndData() {
        List<SearchResult> results = service.searchProducts("ART001");
        SearchResult result = results.get(0);

        assertEquals(0, BigDecimal.valueOf(100.00).compareTo(result.finalPrice()), "Цена должна быть 100.00");

        assertEquals(manA.getName(), result.supplierName());
        assertEquals(manA.getName(), result.realManufacturerName());
        assertEquals(manA.getAddress(), result.supplierAddress());
    }

    @Test
    @DisplayName("Цена и данные: Товар от Дилера (простой расчет)")
    void searchProducts_dealerItem_simplePrice() {
        List<SearchResult> results = service.searchProducts("ART002");
        SearchResult result = results.get(0);

        assertEquals(0, BigDecimal.valueOf(230.00).compareTo(result.finalPrice()), "Цена должна быть 230.00");

        assertEquals(dealerX.getName(), result.supplierName());
        assertEquals(manA.getName(), result.realManufacturerName());
        assertEquals(dealerX.getAddress(), result.supplierAddress());
    }

    @Test
    @DisplayName("Цена и данные: Товар от Дилера (проверка округления HALF_UP)")
    void searchProducts_dealerItem_roundingCheck() {
        List<SearchResult> results = service.searchProducts("ART003"); // Товар с округлением
        SearchResult result = results.get(0);

        assertEquals(0, BigDecimal.valueOf(3.83).compareTo(result.finalPrice()), "Цена должна быть 3.83");
    }
}