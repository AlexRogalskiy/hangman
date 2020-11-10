package ru.hse.sc.hangman;

import java.util.HashSet;
import java.util.Set;

public class Hangman {
    private static final int MAX_ATTEMPTS = 7;

    private final String secretWord;
    private final Set<Character> usedLetters = new HashSet<>();
    private int attemptsLeft = MAX_ATTEMPTS;

    public Hangman(String secretWord) {
        throwIfNotValid(secretWord);

        this.secretWord = secretWord.toLowerCase();
    }

    private static void throwIfNotValid(String secretWord) {
        if (secretWord == null) {
            throw new IllegalArgumentException("secretWord == null");
        }

        if (secretWord.isBlank()) {
            throw new IllegalArgumentException("secretWord is blank");
        }

        for (int i = 0; i < secretWord.length(); i++) {
            if (!Character.isLetter(secretWord.charAt(i))) {
                throw new IllegalArgumentException("secretWord must contain only letters. secretWord = " + secretWord);
            }
        }
    }


    public boolean isLost() {
        return attemptsLeft == 0;
    }

    public String getMaskedWord() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < secretWord.length(); i++) {
            char c = secretWord.charAt(i);
            if (usedLetters.contains(c)) {
                result.append(c);
            } else {
                result.append("*");
            }
        }
        return result.toString();
    }

    public boolean guessLetter(char c) {
        char normalizedLetter = Character.toLowerCase(c);
        boolean letterNotUsed = usedLetters.add(normalizedLetter);
        boolean hasGuessed = secretWord.indexOf(normalizedLetter) != -1;
        if (!hasGuessed && letterNotUsed) {
            attemptsLeft--;
        }
        return hasGuessed;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public boolean isEnded() {
        return isLost() || isWon();
    }

    public boolean isWon() {
        for (int i = 0; i < secretWord.length(); i++) {
            if (!usedLetters.contains(secretWord.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public String getSecretWord() {
        return secretWord;
    }
}
