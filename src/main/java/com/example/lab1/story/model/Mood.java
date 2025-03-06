package com.example.lab1.story.model;

public enum Mood {

    HAPPY(0),
    CALM(30),
    NERVOUS(60),
    HORRIFIED(90);

    final int level;

    Mood(int level) {
        this.level = level;
    }
}