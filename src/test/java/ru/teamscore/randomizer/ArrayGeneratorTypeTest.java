package ru.teamscore.randomizer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.teamscore.randomizer.generators.ArrayGenerator;
import ru.teamscore.randomizer.generators.BooleanArrayGenerator;
import ru.teamscore.randomizer.generators.DiceArrayGenerator;
import ru.teamscore.randomizer.generators.LicensePlateArrayGenerator;

import static org.junit.jupiter.api.Assertions.*;

class ArrayGeneratorTypeTest {

    @Test
    @DisplayName("createGenerator() - DICE должен создавать DiceArrayGenerator")
    void createGenerator_dice() {
        ArrayGenerator<?> generator = ArrayGeneratorType.DICE.createGenerator();
        assertInstanceOf(DiceArrayGenerator.class, generator);
    }

    @Test
    @DisplayName("createGenerator() - BOOLEAN должен создавать BooleanArrayGenerator")
    void createGenerator_boolean() {
        ArrayGenerator<?> generator = ArrayGeneratorType.BOOLEAN.createGenerator();
        assertInstanceOf(BooleanArrayGenerator.class, generator);
    }

    @Test
    @DisplayName("createGenerator() - PLATE должен создавать LicensePlateArrayGenerator")
    void createGenerator_plate() {
        ArrayGenerator<?> generator = ArrayGeneratorType.PLATE.createGenerator();
        assertInstanceOf(LicensePlateArrayGenerator.class, generator);
    }

    @Test
    @DisplayName("fromUserFriendlyName() - должен находить тип по имени")
    void fromUserFriendlyName_success() {
        assertEquals(ArrayGeneratorType.DICE, ArrayGeneratorType.fromUserFriendlyName("dice"));
        assertEquals(ArrayGeneratorType.BOOLEAN, ArrayGeneratorType.fromUserFriendlyName("BOOLEAN"));
        assertEquals(ArrayGeneratorType.PLATE, ArrayGeneratorType.fromUserFriendlyName("Plate"));
    }

    @Test
    @DisplayName("fromUserFriendlyName() - должен выбросить исключение для неизвестного имени")
    void fromUserFriendlyName_unknown() {
        assertThrows(IllegalArgumentException.class, () -> ArrayGeneratorType.fromUserFriendlyName("unknown_type"));
    }
}