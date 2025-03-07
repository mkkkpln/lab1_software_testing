package com.example.lab1.story;

import com.example.lab1.story.exception.InsufficientPrivilegesForShoutingException;
import com.example.lab1.story.exception.SelfShoutingException;
import com.example.lab1.story.exception.ShoutException;
import com.example.lab1.story.model.Mood;
import com.example.lab1.story.model.NoiseLevel;
import com.example.lab1.story.model.Person;
import com.example.lab1.story.model.PersonType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StoryTest {

    @Test
    @DisplayName("Крик на одного человека: только GUARD может кричать")
    void testShoutAtPersonOnlyGuardCanShout() {
        Person prisoner = new Person("Заключенный", PersonType.PRISONER);
        Person anotherPrisoner = new Person("Другой заключенный", PersonType.PRISONER);

        assertThrows(InsufficientPrivilegesForShoutingException.class, () ->
                prisoner.shoutAtPerson(anotherPrisoner, NoiseLevel.QUITE));
    }

    @Test
    @DisplayName("Крик на одного человека: нельзя кричать на самого себя")
    void testShoutAtPersonSelfShouting() {
        Person guard = new Person("Ворчун", PersonType.GUARD);

        assertThrows(SelfShoutingException.class, () ->
                guard.shoutAtPerson(guard, NoiseLevel.QUITE));
    }

    @Test
    @DisplayName("Крик на одного человека: только PRISONER может быть объектом крика")
    void testShoutAtPersonOnlyPrisonerCanBeBullied() {
        Person guard = new Person("Ворчун", PersonType.GUARD);
        Person anotherGuard = new Person("Другой ворчун", PersonType.GUARD);

        assertThrows(InsufficientPrivilegesForShoutingException.class, () ->
                guard.shoutAtPerson(anotherGuard, NoiseLevel.QUITE));
    }

    @Test
    @DisplayName("Крик на одного человека: bullied не может быть null")
    void testShoutAtPersonBulliedIsNull() {
        Person guard = new Person("Ворчун", PersonType.GUARD);

        assertThrows(IllegalArgumentException.class, () ->
                guard.shoutAtPerson(null, NoiseLevel.QUITE));
    }

    @Test
    @DisplayName("Крик на одного человека: корректное изменение уровня стресса и настроения")
    void testShoutAtPerson() throws ShoutException {
        Person guard = new Person("Ворчун", PersonType.GUARD);
        Person prisoner = new Person("Бедняга", PersonType.PRISONER);

        guard.setStressLevel(20); // Изначально HAPPY
        prisoner.setStressLevel(20); // Изначально HAPPY


        guard.shoutAtPerson(prisoner, NoiseLevel.QUITE);

        assertEquals(5, guard.getStressLevel()); // 20 - 15 = 5
        assertEquals(35, prisoner.getStressLevel()); // 20 + 15 = 35

        // Проверка настроения
        assertEquals(Mood.HAPPY, guard.changeState(0)); // 5 -> HAPPY
        assertEquals(Mood.CALM, prisoner.changeState(0)); // 35 -> CALM
    }

    @Test
    @DisplayName("Крик на группу людей: корректное изменение уровня стресса и настроения")
    void testShoutAtGroup() throws ShoutException {
        Person guard = new Person("Ворчун", PersonType.GUARD);
        Person prisoner1 = new Person("Бедняга 1", PersonType.PRISONER);
        Person prisoner2 = new Person("Бедняга 2", PersonType.PRISONER);
        List<Person> group = List.of(prisoner1, prisoner2);

        guard.setStressLevel(20); // Изначально HAPPY
        prisoner1.setStressLevel(20); // Изначально HAPPY
        prisoner2.setStressLevel(20); // Изначально HAPPY

        guard.shoutAtGroup(group, NoiseLevel.QUITE);

        // Проверка уровня стресса и настроения у охранника
        assertEquals(5, guard.getStressLevel()); // 20 - 15 = 5
        assertEquals(Mood.HAPPY, guard.changeState(0)); // 5 -> HAPPY

        // Проверка уровня стресса и настроения у первого заключенного
        assertEquals(35, prisoner1.getStressLevel()); // 20 + 15 = 35
        assertEquals(Mood.CALM, prisoner1.changeState(0)); // 35 -> CALM

        // Проверка уровня стресса и настроения у второго заключенного
        assertEquals(35, prisoner2.getStressLevel()); // 20 + 15 = 35
        assertEquals(Mood.CALM, prisoner2.changeState(0)); // 35 -> CALM
    }

    @Test
    @DisplayName("Крик на группу людей: только GUARD может кричать")
    void testShoutAtGroupOnlyGuardCanShout() {
        Person prisoner = new Person("Заключенный", PersonType.PRISONER);
        List<Person> group = List.of(new Person("Другой заключенный", PersonType.PRISONER));

        assertThrows(InsufficientPrivilegesForShoutingException.class, () ->
                prisoner.shoutAtGroup(group, NoiseLevel.QUITE));
    }

    @Test
    @DisplayName("Крик на группу людей: все в группе должны быть PRISONER")
    void testShoutAtGroupOnlyPrisonersCanBeBullied() {
        Person guard = new Person("Ворчун", PersonType.GUARD);
        Person prisoner = new Person("Бедняга", PersonType.PRISONER);
        Person anotherGuard = new Person("Другой охранник", PersonType.GUARD);
        List<Person> group = List.of(prisoner, anotherGuard);

        assertThrows(InsufficientPrivilegesForShoutingException.class, () ->
                guard.shoutAtGroup(group, NoiseLevel.QUITE));
    }

    @Test
    @DisplayName("Крик на группу людей: группа не может быть null")
    void testShoutAtGroupGroupIsNull() {
        Person guard = new Person("Ворчун", PersonType.GUARD);

        assertThrows(IllegalArgumentException.class, () ->
                guard.shoutAtGroup(null, NoiseLevel.QUITE));
    }

    @Test
    @DisplayName("Крик на группу людей: группа не может содержать null")
    void testShoutAtGroupGroupContainsNull() {
        Person guard = new Person("Ворчун", PersonType.GUARD);
        List<Person> group = new ArrayList<>();
        group.add(new Person("Бедняга", PersonType.PRISONER));
        group.add(null);

        assertThrows(IllegalArgumentException.class, () ->
                guard.shoutAtGroup(group, NoiseLevel.QUITE));
    }

    @Test
    @DisplayName("Крик на группу людей: изменение настроения при граничных значениях стресса")
    void testShoutAtGroupBoundaryStressLevels() throws ShoutException {
        Person guard = new Person("Ворчун", PersonType.GUARD);
        Person prisoner1 = new Person("Бедняга 1", PersonType.PRISONER);
        Person prisoner2 = new Person("Бедняга 2", PersonType.PRISONER);
        List<Person> group = List.of(prisoner1, prisoner2);

        // Начальные уровни стресса
        guard.setStressLevel(90); // Изначально HORRIFIED
        prisoner1.setStressLevel(29); // Граница между HAPPY и CALM
        prisoner2.setStressLevel(59); // Граница между CALM и NERVOUS

        guard.shoutAtGroup(group, NoiseLevel.QUITE);

        // Проверка уровня стресса и настроения у охранника
        assertEquals(75, guard.getStressLevel()); // 90 - 15 = 75
        assertEquals(Mood.NERVOUS, guard.changeState(0)); // 75 -> NERVOUS

        // Проверка уровня стресса и настроения у первого заключенного
        assertEquals(44, prisoner1.getStressLevel()); // 29 + 15 = 44
        assertEquals(Mood.CALM, prisoner1.changeState(0)); // 44 -> CALM

        // Проверка уровня стресса и настроения у второго заключенного
        assertEquals(74, prisoner2.getStressLevel()); // 59 + 15 = 74
        assertEquals(Mood.NERVOUS, prisoner2.changeState(0)); // 74 -> NERVOUS
    }

}
