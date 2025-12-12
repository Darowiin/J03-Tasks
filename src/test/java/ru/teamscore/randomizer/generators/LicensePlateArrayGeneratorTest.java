package ru.teamscore.randomizer.generators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LicensePlateArrayGeneratorTest {

    private final LicensePlateArrayGenerator generator = new LicensePlateArrayGenerator();
    // Паттерн: [Буква][3 Цифры][2 Буквы][2 Цифры (Регион)]
    // Допустимые буквы: АВЕКМНОРСТУХ
    private static final String PLATE_REGEX = "^[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2}$";

    @Test
    @DisplayName("generate() - должен сгенерировать массив заданной длины")
    void generate_validLength() {
        int length = 5;
        String[] result = generator.generate(length);
        assertNotNull(result);
        assertEquals(length, result.length);
    }

    @Test
    @DisplayName("generate() - должен выбросить исключение при отрицательной длине")
    void generate_negativeLength_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> generator.generate(-10));
    }

    @Test
    @DisplayName("generate() - все номера должны соответствовать формату X999XXRR")
    void generate_allPlatesMatchPattern() {
        int length = 50;
        String[] result = generator.generate(length);

        for (String plate : result) {
            assertNotNull(plate);
            assertTrue(plate.matches(PLATE_REGEX), "Номер '" + plate + "' не соответствует формату X999XXRR");
            assertEquals(8, plate.length(), "Длина номера должна быть 8 символов");
        }
    }
}