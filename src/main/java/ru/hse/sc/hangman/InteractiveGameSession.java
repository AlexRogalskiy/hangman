package ru.hse.sc.hangman;

import java.io.PrintStream;
import java.util.Scanner;

public class InteractiveGameSession {
    private final Hangman hangman;
    private final Scanner scanner;
    private final PrintStream output;

    public InteractiveGameSession(Hangman hangman, Scanner scanner, PrintStream output) {
        if (hangman == null) {
            throw new IllegalArgumentException("hangman == null");
        }
        if (scanner == null) {
            throw new IllegalArgumentException("scanner == null");
        }
        if (output == null) {
            throw new IllegalArgumentException("output == null");
        }
        this.hangman = hangman;
        this.scanner = scanner;

        this.output = output;
    }

    public void start() {
        output.println(hangman.getMaskedWord());
        output.flush();
        while (scanner.hasNext()) {
            String next = scanner.next();
            hangman.guessLetter(next.charAt(0));
            output.println(hangman.getMaskedWord() + " Attempts left: " + hangman.getAttemptsLeft() + ". Used letters: " + joinLetters());
            if (hangman.isEnded()) {
                endGame();
                return;
            }
            output.flush();
        }
    }

    private String joinLetters() {
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;
        for (Character letter : hangman.getUsedLetters()) {
            if (!first) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(letter);
            first = false;
        }
        return stringBuilder.toString();
    }

    private void endGame() {
        if (hangman.isWon()) {
            output.println("Congratulation, you won the game. The secret word: \"" + hangman.getMaskedWord() + "\"");
        } else {
            output.println("You have lost the game. The secret word: \"" + hangman.getSecretWord() + "\"");
        }
        output.flush();
    }
}
