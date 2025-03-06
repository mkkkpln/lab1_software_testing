package com.example.lab1.story;

import com.example.lab1.story.model.Person;
import com.example.lab1.story.model.NoiseLevel;

public record ShoutEvent(
        Person bullier, // кто кричит
        Person bullied, // на кого кричат
        NoiseLevel noiseLevel
){}
