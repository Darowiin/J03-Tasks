package ru.teamscore.suppliers;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Конкретный поставщик - Дилер. Связан с одним производителем и имеет фиксированную наценку.
 */
@Getter
public class Dealer extends Supplier {
    private final Manufacturer manufacturer;
    private final BigDecimal markupPercentage;

    /**
     * @param tin ИНН дилера
     * @param name Название дилера
     * @param address Адрес дилера
     * @param manufacturer Производитель, чьи товары дилер перепродает
     * @param markupPercentage Фиксированная наценка в процентах (например, 15.0)
     */
    public Dealer(String tin, String name, String address, Manufacturer manufacturer, double markupPercentage) {
        super(tin, name, address);
        this.manufacturer = manufacturer;
        this.markupPercentage = BigDecimal.valueOf(markupPercentage).setScale(4, RoundingMode.HALF_UP);
    }
}