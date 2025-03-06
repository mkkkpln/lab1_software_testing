package com.example.lab1.math;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class MathUtilsTest {

    @Autowired
    private MathUtils mathUtils;

    // Источник данных для теста tg(x)
    static Stream<Arguments> tgTestData() {
        return Stream.of(
                Arguments.of(0, 0), // tg(0) = 0
                Arguments.of(Math.PI, 0), // tg(π) = 0
                Arguments.of(-Math.PI, 0), // tg(-π) = 0
                Arguments.of(Math.PI / 4, 1), // tg(π/4) = 1
                Arguments.of(-Math.PI / 4, -1), // tg(-π/4) = -1
                Arguments.of(5 * Math.PI / 4, 1), // tg(5π/4) = 1
                Arguments.of(-5 * Math.PI / 4, -1), // tg(-5π/4) = -1
                Arguments.of(Math.PI / 6, 0.5773502691896257), // tg(π/6) ≈ 0.577
                Arguments.of(-Math.PI / 6, -0.5773502691896257), // tg(-π/6) ≈ -0.577
                Arguments.of(Math.PI / 3, 1.7320508075688774), // tg(π/3) ≈ 1.732
                Arguments.of(-Math.PI / 3, -1.7320508075688774), // tg(-π/3) ≈ -1.732
                Arguments.of(2 * Math.PI / 3, -1.7320508075688774), // tg(2π/3) ≈ -1.732
                Arguments.of(-2 * Math.PI / 3, 1.7320508075688774) // tg(-2π/3) ≈ 1.732
        );
    }

    // Источник данных для теста tg(x) в точках, где функция не определена
    static Stream<Arguments> tgUndefinedTestData() {
        return Stream.of(
                Arguments.of(Math.PI / 2), // tg(π/2) не определен
                Arguments.of(-Math.PI / 2), // tg(-π/2) не определен
                Arguments.of(3 * Math.PI / 2) // tg(3π/2) не определен
        );
    }

    // Источник данных для теста tg(x) вблизи точек, где функция не определена
    static Stream<Arguments> tgNearUndefinedPointsTestData() {
        return Stream.of(
                Arguments.of(Math.PI / 2 - 1e-2), // x = π/2 - 0.01
                Arguments.of(Math.PI / 2 + 1e-2), // x = π/2 + 0.01
                Arguments.of(-Math.PI / 2 - 1e-2), // x = -π/2 - 0.01
                Arguments.of(-Math.PI / 2 + 1e-2) // x = -π/2 + 0.01
        );
    }

    @ParameterizedTest
    @MethodSource("tgTestData")
    @DisplayName("Проверка корректности вычисления tg(x) для различных значений x")
    void testTg(double x, double expected) {
        double result = mathUtils.tg(x);
        assertEquals(expected, result, mathUtils.getDelta(),
                String.format("Ошибка для x = %f (в радианах). Ожидалось: %f, Получено: %f", x, expected, result));
    }

    @ParameterizedTest
    @MethodSource("tgUndefinedTestData")
    @DisplayName("Проверка выброса исключения для tg(x) в точках, где функция не определена")
    void testTgUndefined(double x) {
        assertThrows(IllegalArgumentException.class, () -> mathUtils.tg(x),
                String.format("Ожидалось исключение для x = %f (в радианах), так как tg(x) не определена в этой точке", x));
    }

    @ParameterizedTest
    @MethodSource("tgNearUndefinedPointsTestData")
    @DisplayName("Проверка поведения tg(x) вблизи точек, где функция не определена")
    void testTgNearUndefinedPoints(double x) {
        double result = mathUtils.tg(x);
        double expected = Math.tan(x); // Сравниваем с Math.tan для проверки точности
        assertEquals(expected, result, 0.01, // Допустимая погрешность для больших значений
                String.format("Ошибка для x = %f (в радианах). Ожидалось: %f, Получено: %f", x, expected, result));
    }

    @Test
    @DisplayName("Проверка, что tg(0) = 0")
    void testTgZero() {
        assertEquals(0, mathUtils.tg(0), mathUtils.getDelta(),
                "Ошибка для x = 0. Ожидалось: 0, Получено: " + mathUtils.tg(0));
    }

    @Test
    @DisplayName("Проверка точности вычисления tg(π/4), которое должно быть равно 1")
    void testTgPrecision() {
        double x = Math.PI / 4;
        double result = mathUtils.tg(x);
        assertEquals(1.0, result, mathUtils.getDelta(),
                String.format("Ошибка точности для x = π/4. Ожидалось: 1.0, Получено: %f", result));
    }

    @Test
    @DisplayName("Сравнение результатов MathUtils.tg(x) с Math.tan(x) для набора тестовых точек")
    void testTgComparisonWithMathTan() {
        double[] testPoints = {0, Math.PI / 6, Math.PI / 4, Math.PI / 3, Math.PI / 2 - 1e-2, Math.PI / 2 + 1e-2};
        for (double x : testPoints) {
            double expected = Math.tan(x);
            double result = mathUtils.tg(x);
            assertEquals(expected, result, 1e-2,
                    String.format("Ошибка для x = %f (в радианах). Ожидалось: %f, Получено: %f", x, expected, result));
        }
    }
}