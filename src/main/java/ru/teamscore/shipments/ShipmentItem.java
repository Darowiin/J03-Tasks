package ru.teamscore.shipments;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Отдельная позиция в документе отгрузки.
 *
 * @param id       GUID товара
 * @param article  Артикул товара
 * @param title    Название товара
 * @param quantity Количество шт. товара
 * @param price    Цена товара
 */
public record ShipmentItem(String id, String article, String title, BigDecimal quantity, BigDecimal price) {

    /**
     * Конструктор, позволяющий создавать позицию с использованием примитивных типов double.
     * Значения автоматически преобразуются в BigDecimal.
     *
     * @param id GUID товара.
     * @param article Артикул товара.
     * @param title Название товара.
     * @param quantity Количество шт. товара.
     * @param price Цена товара.
     */
    public ShipmentItem(String id, String article, String title, double quantity, double price) {
        this(id, article, title, BigDecimal.valueOf(quantity), BigDecimal.valueOf(price));
    }

    /**
     * Возвращает стоимость данной позиции (количество * цена), округленную до копейки.
     */
    public BigDecimal getAmount() {
        return quantity.multiply(price)
                .setScale(2, RoundingMode.HALF_UP);
    }
}