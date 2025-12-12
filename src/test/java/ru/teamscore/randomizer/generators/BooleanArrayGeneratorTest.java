package ru.teamscore.randomizer.generators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BooleanArrayGeneratorTest {

    private final BooleanArrayGenerator generator = new BooleanArrayGenerator();

    @Test
    @DisplayName("generate() - должен сгенерировать массив заданной длины")
    void generate_validLength() {
        int length = 15;
        Boolean[] result = generator.generate(length);
        assertNotNull(result);
        assertEquals(length, result.length);
    }

    @Test
    @DisplayName("generate() - все элементы должны быть булевыми")
    void generate_allElementsAreBoolean() {
        Boolean[] result = generator.generate(10);
        for (Boolean value : result) {
            assertNotNull(value);
        }
    }

    @Test
    @DisplayName("generate() - должен выбросить исключение при отрицательной длине")
    void generate_negativeLength_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> generator.generate(-1));
    }
}