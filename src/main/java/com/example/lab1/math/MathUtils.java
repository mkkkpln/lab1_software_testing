package com.example.lab1.math;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MathUtils {

    private final double delta;

    public MathUtils(@Value("${math.utils.delta:1e-10}") double delta) {
        this.delta = delta;
    }

    public double tg(double x) {
        if (Math.abs(x % (Math.PI / 2)) < delta && Math.abs(x % Math.PI) > delta) {
            throw new IllegalArgumentException("tg(x) is undefined for x = π/2 + πk, where k is an integer");
        }

        double sinX = sin(x);
        double cosX = cos(x);

        return sinX / cosX;
    }

    private double sin(double x) {
        double result = 0;
        double term = x;
        int n = 1;

        while (Math.abs(term) > delta) {
            result += term;
            term = -term * x * x / ((2 * n) * (2 * n + 1));
            n++;
        }

        return result;
    }

    private double cos(double x) {
        double result = 0;
        double term = 1;
        int n = 1;

        while (Math.abs(term) > delta) {
            result += term;
            term = -term * x * x / ((2 * n - 1) * (2 * n));
            n++;
        }

        return result;
    }

    public double getDelta() {
        return delta;
    }
}