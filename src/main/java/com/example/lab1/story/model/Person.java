package com.example.lab1.story.model;

import com.example.lab1.story.exception.InsufficientPrivilegesForShoutingException;
import com.example.lab1.story.exception.SelfShoutingException;
import com.example.lab1.story.exception.ShoutException;

import java.util.List;
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

    public void shoutAtGroup(List<Person> groupOfBullied, NoiseLevel noiseLevel) throws ShoutException {
        if (groupOfBullied == null) {
            throw new IllegalArgumentException("Bullied is null!");
        }

        if (this.type != PersonType.GUARD) {
            throw new InsufficientPrivilegesForShoutingException("Only Guard can shout!");
        }

        for (Person bullied : groupOfBullied) {
            bully(bullied, noiseLevel);
        }

        this.changeState(-noiseLevel.stressDamage());
    }

    public void shoutAtPerson(Person bullied, NoiseLevel noiseLevel) throws ShoutException {
        if (this.type != PersonType.GUARD) {
            throw new InsufficientPrivilegesForShoutingException("Only Guard can bully!");
        }

        bully(bullied, noiseLevel);

        this.changeState(-noiseLevel.stressDamage());
    }

    private void bully(Person bullied, NoiseLevel noiseLevel) throws ShoutException {

        if (bullied == null) {
            throw new IllegalArgumentException("Bullied is null!");
        }

        if (bullied.equals(this)) {
            throw new SelfShoutingException("Person can't shout at himself");
        }

        if (bullied.type != PersonType.PRISONER) {
            throw new InsufficientPrivilegesForShoutingException("Only Prisoner can be bullied!");
        }

        bullied.changeState(noiseLevel.stressDamage());
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
