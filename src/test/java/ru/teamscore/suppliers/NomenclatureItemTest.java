package ru.teamscore.suppliers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class NomenclatureItemTest {
    private final Manufacturer manA = new Manufacturer("111", "Man A", "Addr A");

    @Test
    @DisplayName("Перегруженный конструктор должен корректно преобразовать double цену в BigDecimal")
    void constructor_doubleToBigDecimal() {
        NomenclatureItem item = new NomenclatureItem("ART1", "Test Item", 99.9999, manA);

        assertEquals("ART1", item.article());

        BigDecimal expectedPrice = BigDecimal.valueOf(99.9999);
        assertEquals(0, expectedPrice.compareTo(item.manufacturerPrice()),
                "BigDecimal должен быть равен исходному double");
    }

    @Test
    @DisplayName("getManufacturerPrice должен возвращать точный BigDecimal")
    void getManufacturerPrice_returnsCorrectValue() {
        NomenclatureItem item = new NomenclatureItem("ART2", "Test Item 2", 123.45, manA);
        assertEquals(123.45, item.manufacturerPrice().doubleValue());
    }
}