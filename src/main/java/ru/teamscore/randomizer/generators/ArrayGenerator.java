package ru.teamscore.randomizer.generators;

/**
 * Интерфейс для генерации массива заданного типа и длины.
 * @param <T> Тип элементов, которые будут содержаться в массиве.
 */
public interface ArrayGenerator<T> {

    /**
     * Генерирует массив элементов.
     * @param length Желаемая длина генерируемого массива.
     * @return Новый массив, заполненный элементами типа T.
     */
    T[] generate(int length);
}