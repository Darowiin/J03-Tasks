package ru.teamscore.shipments;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentItemsCollectionTest {

    @Test
    @DisplayName("totalAmount() - суммарная стоимость с округлением")
    void totalAmount_roundingPerItem() {
        ShipmentItemsCollection collection = new ShipmentItemsCollection(
                2,
                new String[]{"1", "2"},
                new String[]{"A1", "B2"},
                new String[]{"T1", "T2"},
                new double[]{2.0, 1.5},
                new double[]{10.0, 5.555}
        );

        assertEquals(28.33, collection.totalAmount(), 1e-9);
    }

    @Test
    @DisplayName("itemAmount() - поиск по id: найдено и не найдено")
    void itemAmount_foundById_and_notFound() {
        ShipmentItemsCollection collection = new ShipmentItemsCollection(
                1,
                new String[]{"123"},
                new String[]{"A1"},
                new String[]{"T"},
                new double[]{3.0},
                new double[]{2.5}
        );

        assertEquals(7.5, collection.itemAmount("123"), 1e-9);

        assertEquals(0.0, collection.itemAmount("apple"), 1e-9);
    }

    @Test
    @DisplayName("promoSum() - суммирование для всех товаров с округлением")
    void promoSum_matchesArticles() {
        ShipmentItemsCollection collection = new ShipmentItemsCollection(
                3,
                new String[]{"id1", "id2", "id3"},
                new String[]{"A1", "B2", "C3"},
                new String[]{"T1", "T2", "T3"},
                new double[]{1.0, 2.0, 3.0},
                new double[]{10.0, 5.555, 1.234}
        );

        String[] promo = new String[] {"A1", "C3"};
        assertEquals(13.70, collection.promoSum(promo), 1e-9);
    }

    @Test
    @DisplayName("calculatePromoSumWithDiscount() - применение скидки и округление в большую сторону")
    void calculatePromoSumWithDiscount_usesCeilingRound() {
        ShipmentItemsCollection collection = new ShipmentItemsCollection(
                1,
                new String[]{"id1"},
                new String[]{"A1"},
                new String[]{"T"},
                new double[]{1.5},
                new double[]{5.555}
        );

        double result = collection.calculatePromoSumWithDiscount(new String[] {"A1"}, 10.0);
        assertEquals(7.50, result, 1e-9);
    }

    @Test
    @DisplayName("calculatePromoSumWithDiscount() - при некорректном значении скидки возвращает результат без скидки")
    void calculatePromoSumWithIncorrectDiscount_returnsSumWithoutDiscount() {
        ShipmentItemsCollection collection = new ShipmentItemsCollection(
                1,
                new String[]{"id1"},
                new String[]{"A1"},
                new String[]{"T"},
                new double[]{1.5},
                new double[]{5.555}
        );

        double result = collection.calculatePromoSumWithDiscount(new String[] {"A1"}, -10.0);
        assertEquals(8.33, result, 1e-9);

        result = collection.calculatePromoSumWithDiscount(new String[] {"A1"}, 1000.0);
        assertEquals(0.00, result, 1e-9);
    }

    @Test
    @DisplayName("getTotalQuantity() - суммарное количество позиций")
    void getTotalQuantity_sumOfQuantities() {
        ShipmentItemsCollection collection = new ShipmentItemsCollection(
                3,
                new String[]{"id1", "id2", "id3"},
                new String[]{"A","B","C"},
                new String[]{"t1","t2","t3"},
                new double[]{1.0, 2.5, 3.5},
                new double[]{1.0, 1.0, 1.0}
        );

        assertEquals(7.0, collection.getTotalQuantity(), 1e-9);
    }
}