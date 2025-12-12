package ru.teamscore.randomizer.generators;

import java.util.Random;

/**
 * Генератор массива случайных булевых значений (true или false).
 */
public class BooleanArrayGenerator implements ArrayGenerator<Boolean> {
    private static final Random RANDOM = new Random();

    @Override
    public Boolean[] generate(int length) {
        if (length < 0) throw new IllegalArgumentException("Длина массива не может быть отрицательной.");

        Boolean[] array = new Boolean[length];
        for (int i = 0; i < length; i++) {
            array[i] = RANDOM.nextBoolean();
        }
        return array;
    }
}