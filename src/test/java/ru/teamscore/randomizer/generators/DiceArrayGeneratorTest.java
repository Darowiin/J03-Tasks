package ru.teamscore.randomizer.generators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceArrayGeneratorTest {

    private final DiceArrayGenerator generator = new DiceArrayGenerator();

    @Test
    @DisplayName("generate() - должен сгенерировать массив заданной длины")
    void generate_validLength() {
        int length = 20;
        Integer[] result = generator.generate(length);
        assertNotNull(result);
        assertEquals(length, result.length);
    }

    @Test
    @DisplayName("generate() - все элементы должны быть в диапазоне от 1 до 6")
    void generate_allElementsInRange() {
        int length = 100;
        Integer[] result = generator.generate(length);

        for (Integer value : result) {
            assertTrue(value >= 1 && value <= 6, "Значение кубика должно быть от 1 до 6");
        }
    }

    @Test
    @DisplayName("generate() - должен выбросить исключение при отрицательной длине")
    void generate_negativeLength_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> generator.generate(-5));
    }

    @Test
    @DisplayName("generate() - должен вернуть пустой массив при длине 0")
    void generate_zeroLength_returnsEmptyArray() {
        Integer[] result = generator.generate(0);
        assertNotNull(result);
        assertEquals(0, result.length);
    }
}