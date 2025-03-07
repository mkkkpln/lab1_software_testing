package com.example.lab1.math;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;



class MathUtilsTest {

    MathUtils mathUtils = new MathUtils();

    @Test
    @DisplayName("tg(0) должен быть равен 0")
    void testTangentOfZero() {
        double result = mathUtils.tg(0, 10);
        assertEquals(0.0, result, 0.01);
    }

    @ParameterizedTest
    @MethodSource("provideCommonAngles")
    @DisplayName("Проверка tg(x) для стандартных углов")
    void testTangentOfCommonAngles(double x, double expected) {
        double result = mathUtils.tg(x, 10);
        assertEquals(expected, result, 0.01, "Ожидаемое значение tg(" + x + ") = " + expected);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Math.PI})
    @DisplayName("Проверка исключений для недопустимых входных данных")
    void testInvalidInput(double x) {
        assertThrows(IllegalArgumentException.class, () -> mathUtils.tg(x, 10),
                "tg(" + x + ") должен выбрасывать исключение");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    @DisplayName("Проверка исключений для недопустимых значений count")
    void testInvalidCounts(int count) {
        assertThrows(IllegalArgumentException.class, () -> mathUtils.tg(1, count),
                "tg(1) с count = " + count + " должен выбрасывать исключение");
    }

    private static Stream<Arguments> provideCommonAngles() {
        return Stream.of(
                Arguments.of(-Math.PI / 3, Math.tan(-Math.PI / 3)), // tg(-π/3) = -√3 ≈ -1.732
                Arguments.of(-Math.PI / 4, Math.tan(-Math.PI / 4)), // tg(-π/4) = -1
                Arguments.of(-Math.PI / 6, Math.tan(-Math.PI / 6)), // tg(-π/6) = -1/√3 ≈ -0.577
                Arguments.of(0.0, Math.tan(0.0)), // tg(0) = 0
                Arguments.of(Math.PI / 6, Math.tan(Math.PI / 6)), // tg(π/6) = 1/√3 ≈ 0.577
                Arguments.of(Math.PI / 4, Math.tan(Math.PI / 4)), // tg(π/4) = 1
                Arguments.of(Math.PI / 3, Math.tan(Math.PI / 3)) // tg(π/3) = √3 ≈ 1.732
        );
    }
}