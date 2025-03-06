package com.example.lab1.story.model;

public enum NoiseLevel {
    QUITE(15),
    OK(35),
    HORRIBLE(70);

    int stressDamage;

    NoiseLevel(int stressDamage) {
        this.stressDamage = stressDamage;
    }

    public int stressDamage() {
        return stressDamage;
    }
}
