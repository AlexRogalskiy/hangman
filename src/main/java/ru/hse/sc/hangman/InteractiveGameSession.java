package ru.hse.sc.hangman;

import java.io.PrintStream;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class InteractiveGameSession {
    private final List<String> words;
    private final Scanner scanner;
    private final PrintStream output;
    private final Random random;

    public InteractiveGameSession(List<String> words, Scanner scanner, PrintStream output, Random random) {
        if (words == null) {
            throw new IllegalArgumentException("words == null");
        }
        if (words.isEmpty()) {
            throw new IllegalArgumentException("words is empty");
        }
        if (scanner == null) {
            throw new IllegalArgumentException("scanner == null");
        }
        if (output == null) {
            throw new IllegalArgumentException("output == null");
        }
        if (random == null) {
            throw new IllegalArgumentException("random == null");
        }
        this.words = words;
        this.scanner = scanner;
        this.random = random;
        this.output = output;
    }

    public void playIndefinitely() {
        Hangman hangman;
        do {
            hangman = playNewGame();
        } while (wantsToPlay(hangman));
    }

    private boolean wantsToPlay(Hangman hangman) {
        if (hangman.isEnded()) {
            output.println("Play again? (y/n)");
        }
        return scanner.hasNext() && scanner.next().startsWith("y");
    }

    private Hangman playNewGame() {
        Hangman hangman = new Hangman(randomWord());
        output.println(hangman.getMaskedWord());
        while (scanner.hasNext()) {
            String enteredString = scanner.next();
            if (enteredString.length() != 1) {
                output.println("Please enter one letter");
                continue;
            }

            hangman.guessLetter(enteredString.charAt(0));
            output.println(hangman.getMaskedWord() +
                    " Attempts left: " + hangman.getAttemptsLeft() +
                    ". Used letters: " + joinLetters(hangman.getUsedLetters()));
            if (hangman.isEnded()) {
                endGame(hangman);
                return hangman;
            }
        }
        return hangman;
    }

    private String randomWord() {
        int i = random.nextInt(words.size());
        return words.get(i);
    }

    private String joinLetters(Set<Character> letters) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;
        for (Character letter : letters) {
            if (!first) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(letter);
            first = false;
        }
        return stringBuilder.toString();
    }

    private void endGame(Hangman hangman) {
        if (hangman.isWon()) {
            output.println("Congratulation, you won the game. The secret word: \"" + hangman.getMaskedWord() + "\"");
        } else {
            output.println("You have lost the game. The secret word: \"" + hangman.getSecretWord() + "\"");
        }
        output.flush();
    }
}
