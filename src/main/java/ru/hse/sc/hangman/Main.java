package ru.hse.sc.hangman;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static final List<String> PREDEFINED_WORDS = ("весна\n" +
            "Офис\n" +
            "Университет\n" +
            "своп\n" +
            "ворона\n" +
            "таблица\n" +
            "кофе\n" +
            "чернила").lines().collect(Collectors.toUnmodifiableList());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InteractiveGameSession interactiveGameSession = new InteractiveGameSession(
                PREDEFINED_WORDS, scanner, System.out, new Random());
        interactiveGameSession.playIndefinitely();
    }
}
