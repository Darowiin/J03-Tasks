package ru.teamscore.suppliers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class DealerTest {
    private final Manufacturer manA = new Manufacturer("111", "Man A", "Addr A");

    @Test
    @DisplayName("Конструктор должен корректно инициализировать Дилера и округлить наценку")
    void constructor_initializesCorrectly() {
        Dealer dealer = new Dealer("222", "Dealer X", "Addr X", manA, 12.34567);

        assertEquals("222", dealer.getTin());
        assertEquals("Dealer X", dealer.getName());
        assertEquals(manA, dealer.getManufacturer());

        BigDecimal expectedMarkup = BigDecimal.valueOf(12.3457);
        assertEquals(0, expectedMarkup.compareTo(dealer.getMarkupPercentage()));
        assertEquals(4, dealer.getMarkupPercentage().scale(), "Масштаб наценки должен быть 4");
    }

    @Test
    @DisplayName("Наценка 0.0 должна быть сохранена с нужным масштабом")
    void constructor_zeroMarkup() {
        Dealer dealer = new Dealer("222", "Dealer X", "Addr X", manA, 0.0);
        BigDecimal expectedMarkup = BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP);
        assertEquals(0, expectedMarkup.compareTo(dealer.getMarkupPercentage()));
    }
}