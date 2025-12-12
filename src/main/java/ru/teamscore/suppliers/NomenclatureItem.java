package ru.teamscore.suppliers;

import java.math.BigDecimal;

/**
 * Товарная позиция в общей номенклатуре.
 *
 * @param article           Артикул
 * @param title             Наименование
 * @param manufacturerPrice Цена от производителя
 * @param supplier          Текущий поставщик товара
 */
public record NomenclatureItem(String article, String title, BigDecimal manufacturerPrice, Supplier supplier) {
    public NomenclatureItem(String article, String title, double manufacturerPrice, Supplier supplier) {
        this(article, title, BigDecimal.valueOf(manufacturerPrice), supplier);
    }
}