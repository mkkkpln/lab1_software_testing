package com.example.lab1.story;

import com.example.lab1.story.exception.ShoutException;
import com.example.lab1.story.model.Person;
import com.example.lab1.story.model.PersonType;

public class ShoutEventHandler {

    public void handle(ShoutEvent shoutEvent) throws ShoutException {
        if (shoutEvent == null) {
            throw new IllegalStateException("Event can't be null");
        }

        if (shoutEvent.bullier() == null || shoutEvent.bullied() == null || shoutEvent.noiseLevel() == null) {
            throw new IllegalArgumentException("Event's arguments can't be null");
        }

        if (shoutEvent.bullier().equals(shoutEvent.bullied())) {
            throw new ShoutException("Person can't shout at himself");
        }

        if (!shoutEvent.bullier().getType().equals(PersonType.GUARD)) {
            throw new ShoutException("Only Guard can bully!");
        }

        if (!shoutEvent.bullied().getType().equals(PersonType.PRISONER)) {
            throw new ShoutException("Only Prisoner can be bullied!");
        }

        // изменяем настроение тому кто кричит
        Person bullier = shoutEvent.bullier();
        Person bullied = shoutEvent.bullied();

        bullier.changeState(-shoutEvent.noiseLevel().stressDamage());
        bullied.changeState(shoutEvent.noiseLevel().stressDamage());
    }

}
