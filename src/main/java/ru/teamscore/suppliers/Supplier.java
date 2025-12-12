package ru.teamscore.suppliers;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Абстрактный базовый класс для всех поставщиков (производителей и дилеров).
 */
@Getter
@AllArgsConstructor
public abstract class Supplier {
    protected final String tin; // ИНН
    protected final String name;
    protected final String address;
}