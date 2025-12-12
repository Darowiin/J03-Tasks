package ru.teamscore.shipments;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SaleShipmentDocumentTest {

    @Test
    @DisplayName("isWholesale() - определение оптовой продажи: по сумме и по отдельной позиции")
    void isWholesale_saleBySumAndBySingleItem() {
        var itemsCol1 = new ShipmentItemsCollection(
                2,
                new String[]{"id1", "id2"},
                new String[]{"A1", "B2"},
                new String[]{"t1", "t2"},
                new double[]{3.0, 2.0},
                new double[]{1.0, 1.0}
        );

        SaleShipmentDocument doc1 = new SaleShipmentDocument("d1", new Date(), "s", "owner", itemsCol1, "customer");
        assertTrue(doc1.isWholesale(5.0));

        var itemsCol2 = new ShipmentItemsCollection(
                2,
                new String[]{"id1", "id2"},
                new String[]{"A1", "B2"},
                new String[]{"t1", "t2"},
                new double[]{6.0, 1.0},
                new double[]{1.0, 1.0}
        );

        SaleShipmentDocument doc2 = new SaleShipmentDocument("d2", new Date(), "s", "owner", itemsCol2, "customer");
        assertTrue(doc2.isWholesale(5.0));
    }

    @Test
    @DisplayName("promoSum(discount) - делегирование расчёта скидки коллекции")
    void promoSum_withDiscount_delegatesToCollection() {
        var itemsCol = new ShipmentItemsCollection(
                2,
                new String[]{"id1", "id2"},
                new String[]{"A1", "PROMO"},
                new String[]{"t1", "t2"},
                new double[]{1.5, 1.0},
                new double[]{5.555, 3.333}
        );

        SaleShipmentDocument doc = new SaleShipmentDocument("d", new Date(), "s", "owner", itemsCol, "cust");

        double promo = doc.promoSum(new String[] {"PROMO"}, 10.0);
        assertEquals(3.00, promo, 1e-9);
    }

    @Test
    @DisplayName("totalAmount() - делегирование к коллекции через ShipmentDocument")
    void totalAmount_delegation_throughShipmentDocument() {
        var itemsCol = new ShipmentItemsCollection(
                1,
                new String[]{"id1"},
                new String[]{"A1"},
                new String[]{"t1"},
                new double[]{2.0},
                new double[]{10.0}
        );

        SaleShipmentDocument doc = new SaleShipmentDocument("d", new java.util.Date(), "s", "owner", itemsCol, "cust");
        assertEquals(20.00, doc.totalAmount(), 1e-9);
    }
}