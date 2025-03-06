package com.example.lab1.story.model;

import java.util.Objects;

public class Person {
    private final String name;

    private int stressLevel;

    private Mood mood;
    private final PersonType type;

    public Person(String name, PersonType type) {
        this.name = name;
        this.type = type;
    }

    public Mood changeState(int delta) {
        int newLvl = stressLevel + delta;
        if (newLvl < 0) {
            newLvl = 0;
        } else if (newLvl > 100) {
            newLvl = 100;
        }
        stressLevel = newLvl;
        mood = updateMood();
        return mood;
    }

    private Mood updateMood() {
        if (stressLevel >= 0 && stressLevel < 30) {
            return Mood.HAPPY;
        } else if (stressLevel >= 30 && stressLevel < 60) {
            return Mood.CALM;
        } else if (stressLevel >= 60 && stressLevel < 90) {
            return Mood.NERVOUS;
        } else {
            return Mood.HORRIFIED;
        }
    }

    public int getStressLevel() {
        return stressLevel;
    }

    public PersonType getType() {
        return type;
    }

    public void setStressLevel(int stressLevel) {
        this.stressLevel = stressLevel;
    }

    public Mood getMood() {
        return mood;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name) && type == person.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
