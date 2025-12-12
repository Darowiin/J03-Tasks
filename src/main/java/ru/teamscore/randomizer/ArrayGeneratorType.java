package ru.teamscore.randomizer;

import ru.teamscore.randomizer.generators.ArrayGenerator;
import ru.teamscore.randomizer.generators.BooleanArrayGenerator;
import ru.teamscore.randomizer.generators.DiceArrayGenerator;
import ru.teamscore.randomizer.generators.LicensePlateArrayGenerator;

import java.util.function.Supplier;

/**
 * Перечисление, представляющее доступные типы генераторов массивов.
 * Выступает в роли фабрики, инкапсулируя логику создания конкретных генераторов
 * с использованием {@link Supplier} для упрощения кода.
 */
public enum ArrayGeneratorType {

    /** Генератор случайных целых чисел от 1 до 6 (броски кубика). */
    DICE("dice", DiceArrayGenerator::new),

    /** Генератор случайных булевых значений (true/false). */
    BOOLEAN("boolean", BooleanArrayGenerator::new),

    /** Генератор случайных автомобильных номеров. */
    PLATE("plate", LicensePlateArrayGenerator::new);

    private final String userFriendlyName;
    private final Supplier<ArrayGenerator<?>> generatorSupplier;

    /**
     * Конструктор, принимающий удобное для пользователя имя и поставщика (Supplier)
     * для создания экземпляра нужного генератора.
     */
    ArrayGeneratorType(String userFriendlyName, Supplier<ArrayGenerator<?>> generatorSupplier) {
        this.userFriendlyName = userFriendlyName;
        this.generatorSupplier = generatorSupplier;
    }

    /**
     * Возвращает имя, которое пользователь вводит в консоли.
     */
    public String getUserFriendlyName() {
        return userFriendlyName;
    }

    /**
     * Создает конкретный экземпляр генератора, соответствующий данному типу,
     * используя делегирование {@link Supplier}.
     * @return Экземпляр ArrayGenerator.
     */
    public ArrayGenerator<?> createGenerator() {
        return generatorSupplier.get();
    }

    /**
     * Утилитарный метод для поиска элемента enum по имени, удобному для пользователя.
     * @param name Имя, введенное пользователем.
     * @return Соответствующий ArrayGeneratorType.
     * @throws IllegalArgumentException Если имя не найдено.
     */
    public static ArrayGeneratorType fromUserFriendlyName(String name) {
        for (ArrayGeneratorType type : values()) {
            if (type.userFriendlyName.equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Неизвестный тип генератора: " + name);
    }
}