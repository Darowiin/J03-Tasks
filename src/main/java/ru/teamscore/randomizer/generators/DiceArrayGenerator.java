package ru.teamscore.randomizer.generators;

import java.util.Random;

/**
 * Генератор массива случайных целых чисел, имитирующих броски кубика (от 1 до 6).
 */
public class DiceArrayGenerator implements ArrayGenerator<Integer> {
    private static final Random RANDOM = new Random();

    @Override
    public Integer[] generate(int length) {
        if (length < 0) throw new IllegalArgumentException("Длина массива не может быть отрицательной.");

        Integer[] array = new Integer[length];
        for (int i = 0; i < length; i++) {
            array[i] = RANDOM.nextInt(6) + 1;
        }
        return array;
    }
}