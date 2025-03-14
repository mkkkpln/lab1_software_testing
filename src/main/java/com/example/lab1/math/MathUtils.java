package com.example.lab1.math;


public class MathUtils {

    /**
     * Вычисляет тангенс угла x с использованием ряда Тейлора.
     *
     * @param x -угол в радианах, для которого вычисляется тангенс
     * @param count количество членов ряда Тейлора для вычисления
     * @throws IllegalArgumentException если x не является конечным числом,
     *                                  если count не является положительным целым числом,
     *                                  или если |x| > π/2
     */
    public double tg(double x, int count) {
        if (Double.isNaN(x) || Double.isInfinite(x)) {
            throw new IllegalArgumentException("Input must be a finite number");
        }
        if (count <= 0) {
            throw new IllegalArgumentException("Terms must be a positive integer");
        }
        if (Math.abs(x) > Math.PI / 2) {
            throw new IllegalArgumentException("x must be smaller than PI/2");
        }

        double result = 0;

        for (int i = 1; i <= count; i++) {
            // Получаем число Бернулли для текущего члена ряда
            double bernoulli = getBernoulli(2 * i);

            // Вычисляем числитель
            double numerator = Math.pow(-1, i - 1) * Math.pow(2, 2 * i) * (Math.pow(2, 2 * i) - 1) * bernoulli;

            // Вычисляем знаменатель
            double denominator = factorial(2 * i);

            result += numerator / denominator * Math.pow(x, 2 * i - 1);
        }

        return result;
    }

    public static long factorial(int n) {
        if (n == 0) {
            return 1;
        }
        return n * factorial(n - 1);
    }

    private static double getBernoulli(int n) {
        if (n == 0) {
            return 1.0;
        }

        double result = 0;

        for (int k = 0; k < n; k++) {
            result += binomialCoefficient(n + 1, k) * getBernoulli(k);
        }

        return -result / (n + 1);
    }


    private static long binomialCoefficient(int n, int k) {
        if (k == 0) {
            return 1; // C(n, 0) = 1
        }

        // Вычисляем числитель и знаменатель биномиального коэффициента
        long numerator = factorial(n);
        long denominator = factorial(k) * factorial(n - k);

        return numerator / denominator;
    }
}