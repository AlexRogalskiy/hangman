package ru.hse.sc.hangman;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HangmanTest {

    @ParameterizedTest
    @MethodSource("invalidSecretWords")
    void gameCannotBeCreatedWithInvalidSecretWord(String word) {
        assertThrows(IllegalArgumentException.class, () -> new Hangman(word));
    }

    private static Stream<Arguments> invalidSecretWords() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of(""),
                Arguments.of("    "),
                Arguments.of("@#!#$@!#(*&$!")
        );
    }

    @Test
    void whenGameCreatedItIsNotLost() {
        Hangman hangman = new Hangman("sofa");

        assertFalse(hangman.isLost());
    }

    @Test
    void wordSuccessfullyMasked() {
        Hangman hangman = new Hangman("sofa");
        String maskedWord = hangman.getMaskedWord();

        assertEquals("****", maskedWord);
    }

    @Test
    void wordSuccessfullyMaskedEvenIfWordIsLongEnough() {
        Hangman hangman = new Hangman("delirium");
        String maskedWord = hangman.getMaskedWord();

        assertEquals("********", maskedWord);
    }

    @Test
    void oneCanGuessLetters() {
        Hangman hangman = new Hangman("sofa");

        assertTrue(hangman.guessLetter('s'));
    }

    @Test
    void guessLetterReturnsFalseIfSecretWordDoesNotContainIt() {
        Hangman hangman = new Hangman("sofa");

        assertFalse(hangman.guessLetter('z'));
    }

    @Test
    void gameIsNotLostWhenPlayerWins() {
        Hangman hangman = new Hangman("sofa");

        hangman.guessLetter('s');
        hangman.guessLetter('o');
        hangman.guessLetter('f');
        hangman.guessLetter('a');

        assertFalse(hangman.isLost());
    }

    @Test
    void gameIsLostWhenAllAttemptsToGuessItFailed() {
        Hangman hangman = new Hangman("sofa");

        String missingLetters = "zxywhde";
        for (int i = 0; i < missingLetters.length(); i++) {
            hangman.guessLetter(missingLetters.charAt(i));
        }

        assertTrue(hangman.isLost());
    }

    @Test
    void maskedWordIsDisplayedCorrectly() {
        Hangman hangman = new Hangman("sofa");

        hangman.guessLetter('s');
        assertEquals("s***", hangman.getMaskedWord());
        hangman.guessLetter('o');
        assertEquals("so**", hangman.getMaskedWord());
        hangman.guessLetter('a');
        assertEquals("so*a", hangman.getMaskedWord());
        hangman.guessLetter('f');
        assertEquals("sofa", hangman.getMaskedWord());
    }

    @Test
    void lettersAreCaseInsensitive() {
        Hangman hangman = new Hangman("SoFa");

        hangman.guessLetter('s');
        assertEquals("s***", hangman.getMaskedWord());
        hangman.guessLetter('O');
        assertEquals("so**", hangman.getMaskedWord());
        hangman.guessLetter('F');
        assertEquals("sof*", hangman.getMaskedWord());
        hangman.guessLetter('a');
        assertEquals("sofa", hangman.getMaskedWord());
    }

    @Test
    void initiallyThereAreSevenAttempts() {
        Hangman hangman = new Hangman("sofa");
        assertEquals(7, hangman.getAttemptsLeft());
    }

    @Test
    void guessingSameLatterDoesNotAffectAttemptCount() {
        Hangman hangman = new Hangman("sofa");
        for (int i = 0; i < 10; i++) {
            hangman.guessLetter('z');
            assertEquals(6, hangman.getAttemptsLeft());
        }
    }

    @Test
    void wordSuccessfullyUnmaskedIfItContainsSameLetters() {
        Hangman hangman = new Hangman("Delirium");
        hangman.guessLetter('i');

        assertEquals("***i*i**", hangman.getMaskedWord());
    }

    @Test
    void gameWon() {
        Hangman hangman = new Hangman("sofa");
        hangman.guessLetter('s');
        hangman.guessLetter('o');
        hangman.guessLetter('f');
        hangman.guessLetter('a');

        assertTrue(hangman.isEnded());
        assertTrue(hangman.isWon());
    }

    @Test
    void gameIsNotWonInitially() {
        Hangman hangman = new Hangman("sofa");

        assertFalse(hangman.isWon());
    }

    @Test
    void gameIsNotEndedInitially() {
        Hangman hangman = new Hangman("sofa");

        assertFalse(hangman.isEnded());
    }

    @Test
    void russianWordsAreSupported() {
        Hangman hangman = new Hangman("берЕг");
        assertTrue(hangman.guessLetter('б'));
        assertTrue(hangman.guessLetter('р'));
        assertTrue(hangman.guessLetter('е'));
        assertTrue(hangman.guessLetter('г'));
        assertTrue(hangman.isEnded());
        assertTrue(hangman.isWon());
    }
}
