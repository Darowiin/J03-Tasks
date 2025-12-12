package ru.teamscore.shipments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MoveShipmentDocumentTest {

    private ShipmentItemsCollection itemsCollection;
    private final String[] promoArticles = {"PROMO_ART"};
    private final Date documentDate = new Date();

    @BeforeEach
    void setUp() {
        itemsCollection = new ShipmentItemsCollection(
                2,
                new String[]{"id1", "id2"},
                new String[]{"REG_ART", "PROMO_ART"},
                new String[]{"T1", "T2"},
                new double[]{1.0, 2.0},
                new double[]{5.00, 3.333}
        );
    }

    @Test
    @DisplayName("isInternalMovement() - true для одинаковых владельцев")
    void isInternalMovement_internal() {
        MoveShipmentDocument doc = new MoveShipmentDocument(
                "M1", documentDate, "WarehouseA", "OwnerX", itemsCollection, "WarehouseB", "OwnerX"
        );
        assertTrue(doc.isInternalMovement(), "Перемещение должно быть внутренним, если владельцы совпадают.");
    }

    @Test
    @DisplayName("isInternalMovement() - false для разных владельцев")
    void isInternalMovement_external() {
        MoveShipmentDocument doc = new MoveShipmentDocument(
                "M2", documentDate, "WarehouseA", "OwnerX", itemsCollection, "WarehouseB", "OwnerY"
        );
        assertFalse(doc.isInternalMovement(), "Перемещение должно быть внешним, если владельцы разные.");
    }

    @Test
    @DisplayName("totalAmount() - делегирование расчета общей суммы")
    void totalAmount_delegates() {
        MoveShipmentDocument doc = new MoveShipmentDocument(
                "M3", documentDate, "W1", "O1", itemsCollection, "W2", "O1"
        );
        assertEquals(11.67, doc.totalAmount(), 1e-9);
    }

    @Test
    @DisplayName("itemAmount() - делегирование расчета суммы позиции")
    void itemAmount_delegates() {
        MoveShipmentDocument doc = new MoveShipmentDocument(
                "M4", documentDate, "W1", "O1", itemsCollection, "W2", "O1"
        );
        assertEquals(6.67, doc.itemAmount("id2"), 1e-9);
    }

    @Test
    @DisplayName("promoSum(String[]) - делегирование расчета промо-суммы без скидки")
    void promoSum_withoutDiscount_delegates() {
        MoveShipmentDocument doc = new MoveShipmentDocument(
                "M5", documentDate, "W1", "O1", itemsCollection, "W2", "O1"
        );
        assertEquals(6.67, doc.promoSum(promoArticles), 1e-9);
    }
}