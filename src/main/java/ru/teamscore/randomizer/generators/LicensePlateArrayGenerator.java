package ru.teamscore.randomizer.generators;

import java.util.Random;

/**
 * Генератор массива случайных автомобильных номеров.
 */
public class LicensePlateArrayGenerator implements ArrayGenerator<String> {
    private static final Random RANDOM = new Random();
    private static final char[] LETTERS = "АВЕКМНОРСТУХ".toCharArray();

    @Override
    public String[] generate(int length) {
        if (length < 0) throw new IllegalArgumentException("Длина массива не может быть отрицательной.");

        String[] array = new String[length];
        for (int i = 0; i < length; i++) {
            array[i] = generateSinglePlate();
        }
        return array;
    }

    private String generateSinglePlate() {
        StringBuilder sb = new StringBuilder();

        sb.append(LETTERS[RANDOM.nextInt(LETTERS.length)]);

        for (int i = 0; i < 3; i++) {
            sb.append(RANDOM.nextInt(10));
        }

        sb.append(LETTERS[RANDOM.nextInt(LETTERS.length)]);
        sb.append(LETTERS[RANDOM.nextInt(LETTERS.length)]);

        int region = RANDOM.nextInt(99) + 1;
        sb.append(String.format("%02d", region));

        return sb.toString();
    }
}