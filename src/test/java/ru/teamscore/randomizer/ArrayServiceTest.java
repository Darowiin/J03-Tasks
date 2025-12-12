package ru.teamscore.randomizer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayServiceTest {

    @Test
    @DisplayName("Конструктор ArrayService должен корректно инициализировать генератор")
    void constructor_initializesGenerator() {
        ArrayService service = assertDoesNotThrow(() -> new ArrayService(ArrayGeneratorType.DICE));
        assertNotNull(service);
    }

    @Test
    @DisplayName("generateArrayAsJson() - должен вернуть корректную JSON-строку массива")
    void generateArrayAsJson_validJsonStructure() throws JsonProcessingException {
        ArrayService service = new ArrayService(ArrayGeneratorType.DICE);
        int length = 3;

        String json = service.generateArrayAsJson(length);

        assertTrue(json.startsWith("[") && json.endsWith("]"));

        long commaCount = json.chars().filter(ch -> ch == ',').count();
        assertEquals(length - 1, commaCount);
    }

    @Test
    @DisplayName("generateArrayAsJson() - должен корректно обрабатывать длину 0")
    void generateArrayAsJson_zeroLength() throws JsonProcessingException {
        ArrayService service = new ArrayService(ArrayGeneratorType.BOOLEAN);
        String json = service.generateArrayAsJson(0);

        assertEquals("[]", json);
    }

    @Test
    @DisplayName("generateArrayAsJson() - должен корректно экранировать строковые элементы")
    void generateArrayAsJson_stringElementsAreQuoted() throws JsonProcessingException {
        ArrayService service = new ArrayService(ArrayGeneratorType.PLATE);

        String json = service.generateArrayAsJson(1);

        assertTrue(json.startsWith("[\""));
        assertTrue(json.endsWith("\"]"));
    }
}