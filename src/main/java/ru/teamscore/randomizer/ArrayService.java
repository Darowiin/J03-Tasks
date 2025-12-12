package ru.teamscore.randomizer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.teamscore.randomizer.generators.ArrayGenerator;

/**
 * Сервис, который создает выбранный генератор и возвращает
 * сгенерированный массив в виде JSON-строки.
 */
public class ArrayService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final ArrayGenerator<?> generator;

    /**
     * Инициализирует сервис, создавая выбранный генератор.
     * @param generatorType Тип генератора (элемент enum).
     */
    public ArrayService(ArrayGeneratorType generatorType) {
        this.generator = generatorType.createGenerator();
        System.out.println("Сервис инициализирован. Используется генератор: " + generator.getClass().getSimpleName());
    }

    /**
     * Генерирует массив заданной длины и возвращает его в виде JSON-строки.
     * @param length Длина массива.
     * @return Массив в формате JSON.
     * @throws JsonProcessingException Если произошла ошибка при сериализации в JSON.
     */
    public String generateArrayAsJson(int length) throws JsonProcessingException {
        Object[] array = generator.generate(length);

        return OBJECT_MAPPER.writeValueAsString(array);
    }
}