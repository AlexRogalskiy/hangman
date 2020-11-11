package ru.hse.sc.hangman;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final List<String> PREDEFINED_WORDS = getPredefinedWords();

    private static List<String> getPredefinedWords() {
        return List.of(
                "весна",
                "университет",
                "ворона",
                "осень",
                "кофе",
                "сыр",
                "век",
                "корона",
                "тест",
                "подход",
                "задача",
                "решение",
                "рецензия",
                "музыка",
                "зима"
        );
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InteractiveGameSession interactiveGameSession = new InteractiveGameSession(
                PREDEFINED_WORDS, scanner, System.out, new Random());
        interactiveGameSession.playIndefinitely();
    }
}
