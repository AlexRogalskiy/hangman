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
        Random random = new Random();

        Scanner scanner = new Scanner(System.in);
        //noinspection InfiniteLoopStatement
        while (true) {
            int i = random.nextInt(PREDEFINED_WORDS.size());
            String randomWord = PREDEFINED_WORDS.get(i);
            Hangman hangman = new Hangman(randomWord);
            InteractiveGameSession interactiveGameSession = new InteractiveGameSession(hangman, scanner, System.out);
            interactiveGameSession.start();
        }
    }
}
