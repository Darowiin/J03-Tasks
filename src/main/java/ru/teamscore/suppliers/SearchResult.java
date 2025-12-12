package ru.teamscore.suppliers;

import java.math.BigDecimal;

/**
 * Структура данных для возврата результатов поиска с итоговой ценой и информацией о поставщике.
 * @param article Артикул
 * @param title Название
 * @param finalPrice Итоговая цена (с наценкой, если есть)
 * @param supplierName Название поставщика (Дилер или Производитель)
 * @param supplierAddress Адрес поставщика
 * @param realManufacturerName Название реального производителя
 */
public record SearchResult(
        String article,
        String title,
        BigDecimal finalPrice,
        String supplierName,
        String supplierAddress,
        String realManufacturerName
) {}