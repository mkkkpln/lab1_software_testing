package com.example.lab1.story;

import com.example.lab1.story.exception.ShoutException;
import com.example.lab1.story.model.Mood;
import com.example.lab1.story.model.NoiseLevel;
import com.example.lab1.story.model.Person;
import com.example.lab1.story.model.PersonType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShoutEventHandlerTest {

    @Test
    @DisplayName("Обработка события с null аргументами")
    void testHandleNullEvent() {
        ShoutEventHandler handler = new ShoutEventHandler();

        assertThrows(IllegalStateException.class, () -> handler.handle(null));
    }

    @Test
    @DisplayName("Обработка события с null bullier")
    void testHandleNullBullier() {
        ShoutEventHandler handler = new ShoutEventHandler();
        Person bullied = new Person("Ворчун", PersonType.GUARD);
        ShoutEvent event = new ShoutEvent(null, bullied, NoiseLevel.QUITE);

        assertThrows(IllegalArgumentException.class, () -> handler.handle(event));
    }

    @Test
    @DisplayName("Обработка события с null bullied")
    void testHandleNullBullied() {
        ShoutEventHandler handler = new ShoutEventHandler();
        Person bullier = new Person("", PersonType.GUARD);
        ShoutEvent event = new ShoutEvent(bullier, null, NoiseLevel.QUITE);

        assertThrows(IllegalArgumentException.class, () -> handler.handle(event));
    }

    @Test
    @DisplayName("Обработка события с null noiseLevel")
    void testHandleNullNoiseLevel() {
        ShoutEventHandler handler = new ShoutEventHandler();
        Person bullier = new Person("", PersonType.GUARD);
        Person bullied = new Person("", PersonType.GUARD);
        ShoutEvent event = new ShoutEvent(bullier, bullied, null);

        assertThrows(IllegalArgumentException.class, () -> handler.handle(event));
    }

    @Test
    @DisplayName("Обработка события: крик на самого себя")
    void testHandleShoutAtSelf() {
        ShoutEventHandler handler = new ShoutEventHandler();
        Person person = new Person("Ворчун", PersonType.GUARD);
        ShoutEvent event = new ShoutEvent(person, person, NoiseLevel.QUITE);

        assertThrows(ShoutException.class, () -> handler.handle(event));
    }

    @Test
    @DisplayName("Обработка события: только GUARD может кричать")
    void testHandleOnlyGuardCanShout() {
        ShoutEventHandler handler = new ShoutEventHandler();
        Person bullier = new Person("Бедняга-1", PersonType.PRISONER);
        Person bullied = new Person("Бедняга-2", PersonType.PRISONER);
        ShoutEvent event = new ShoutEvent(bullier, bullied, NoiseLevel.QUITE);

        assertThrows(ShoutException.class, () -> handler.handle(event));
    }

    @Test
    @DisplayName("Обработка события: только PRISONER может быть объектом крика")
    void testHandleOnlyPrisonerCanBeBullied() {
        ShoutEventHandler handler = new ShoutEventHandler();
        Person bullier = new Person("Ворчун-1", PersonType.GUARD);
        Person bullied = new Person("Ворчун-2", PersonType.GUARD);
        ShoutEvent event = new ShoutEvent(bullier, bullied, NoiseLevel.QUITE);

        assertThrows(ShoutException.class, () -> handler.handle(event));
    }

    @Test
    @DisplayName("Обработка события: изменение уровня стресса у bullier и bullied")
    void testHandleStressLevelChange() throws ShoutException {
        ShoutEventHandler handler = new ShoutEventHandler();


        Person bullier = new Person("Ворчун", PersonType.GUARD);
        Person bullied = new Person("Бедняга", PersonType.PRISONER);

        bullier.setStressLevel(50);
        bullied.setStressLevel(50);

        ShoutEvent event = new ShoutEvent(bullier, bullied, NoiseLevel.OK);

        handler.handle(event);

        assertEquals(15, bullier.getStressLevel()); // 50 - 35 = 15
        assertEquals(85, bullied.getStressLevel()); // 50 + 35 = 85
    }

    @Test
    @DisplayName("Обработка события: изменение настроения у bullier и bullied (HAPPY -> CALM)")
    void testHandleMoodChangeHappyToCalm() throws ShoutException {
        ShoutEventHandler handler = new ShoutEventHandler();

        Person bullier = new Person("Ворчун", PersonType.GUARD);
        Person bullied = new Person("Бедняга", PersonType.PRISONER);

        bullier.setStressLevel(20); // Изначально HAPPY
        bullied.setStressLevel(20); // Изначально HAPPY

        ShoutEvent event = new ShoutEvent(bullier, bullied, NoiseLevel.QUITE);

        handler.handle(event);

        // Проверка уровня стресса
        assertEquals(5, bullier.getStressLevel()); // 20 - 15 = 5
        assertEquals(35, bullied.getStressLevel()); // 20 + 15 = 35

        // Проверка настроения
        assertEquals(Mood.HAPPY, bullier.getMood()); // 5 -> HAPPY
        assertEquals(Mood.CALM, bullied.getMood()); // 35 -> CALM
    }

    @Test
    @DisplayName("Обработка события: изменение настроения у bullier и bullied (CALM -> NERVOUS)")
    void testHandleMoodChangeCalmToNervous() throws ShoutException {
        ShoutEventHandler handler = new ShoutEventHandler();

        Person bullier = new Person("Ворчун", PersonType.GUARD);
        Person bullied = new Person("Бедняга", PersonType.PRISONER);

        bullier.setStressLevel(40); // Изначально CALM
        bullied.setStressLevel(40); // Изначально CALM

        ShoutEvent event = new ShoutEvent(bullier, bullied, NoiseLevel.OK);

        handler.handle(event);

        // Проверка уровня стресса
        assertEquals(5, bullier.getStressLevel()); // 40 - 35 = 5
        assertEquals(75, bullied.getStressLevel()); // 40 + 35 = 75

        // Проверка настроения
        assertEquals(Mood.HAPPY, bullier.getMood()); // 5 -> HAPPY
        assertEquals(Mood.NERVOUS, bullied.getMood()); // 75 -> NERVOUS
    }

    @Test
    @DisplayName("Обработка события: изменение настроения у bullier и bullied (NERVOUS -> HORRIFIED)")
    void testHandleMoodChangeNervousToHorrified() throws ShoutException {
        ShoutEventHandler handler = new ShoutEventHandler();

        Person bullier = new Person("Ворчун", PersonType.GUARD);
        Person bullied = new Person("Бедняга", PersonType.PRISONER);

        bullier.setStressLevel(50); // Изначально NERVOUS
        bullied.setStressLevel(70); // Изначально NERVOUS

        ShoutEvent event = new ShoutEvent(bullier, bullied, NoiseLevel.HORRIBLE);

        handler.handle(event);

        // Проверка уровня стресса
        assertEquals(0, bullier.getStressLevel()); // 70 - 70 = 0 (не может быть меньше 0)
        assertEquals(100, bullied.getStressLevel()); // 70 + 70 = 140 -> ограничено 100

        // Проверка настроения
        assertEquals(Mood.HAPPY, bullier.getMood()); // 0 -> HAPPY
        assertEquals(Mood.HORRIFIED, bullied.getMood()); // 100 -> HORRIFIED
    }

    @Test
    @DisplayName("Обработка события: изменение настроения у bullier и bullied (граничные значения)")
    void testHandleMoodChangeBoundaryValues() throws ShoutException {
        ShoutEventHandler handler = new ShoutEventHandler();

        Person bullier = new Person("Ворчун", PersonType.GUARD);
        Person bullied = new Person("Бедняга", PersonType.PRISONER);

        bullier.setStressLevel(29); // Граница между HAPPY и CALM
        bullied.setStressLevel(29); // Граница между HAPPY и CALM

        ShoutEvent event = new ShoutEvent(bullier, bullied, NoiseLevel.QUITE);

        handler.handle(event);

        // Проверка уровня стресса
        assertEquals(14, bullier.getStressLevel()); // 29 - 15 = 14
        assertEquals(44, bullied.getStressLevel()); // 29 + 15 = 44

        // Проверка настроения
        assertEquals(Mood.HAPPY, bullier.getMood()); // 14 -> HAPPY
        assertEquals(Mood.CALM, bullied.getMood()); // 44 -> CALM
    }
}