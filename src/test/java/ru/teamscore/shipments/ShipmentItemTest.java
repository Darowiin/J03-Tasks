package ru.teamscore.shipments;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentItemTest {

    @Test
    @DisplayName("getAmount() - округление к ближайшему целому до копейки")
    void getAmount_halfUpRounding() {
        ShipmentItem item = new ShipmentItem("id-1", "A1", "Title", 1.5, 5.555);

        BigDecimal expected = BigDecimal.valueOf(1.5)
                .multiply(BigDecimal.valueOf(5.555))
                .setScale(2, RoundingMode.HALF_UP);

        assertEquals(expected, item.getAmount());
    }
}