package ru.hse.sc.hangman;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InteractiveGameSessionTest {

    private final ByteArrayOutputStream outputBytes = new ByteArrayOutputStream();
    private final PrintStream output = new PrintStream(outputBytes);

    @Test
    void whenGameStartedMaskedWordIsPrinted() {
        InteractiveGameSession interactiveGameSession = newGame("");

        interactiveGameSession.playIndefinitely();

        assertTrue(getOutput().startsWith("****"));
    }

    @Test
    void whenLetterGuessedMaskedWordIsPrintedAsWellAsAttemptsLeft() {
        InteractiveGameSession interactiveGameSession = newGame("s\n");

        interactiveGameSession.playIndefinitely();

        assertTrue(getOutput().endsWith("s*** Attempts left: 7. Used letters: s\n"), "Unexpected output " + getOutput());
    }

    @Test
    void gameSuccessfullyWon() {
        InteractiveGameSession session = newGame("s\no\nf\na");

        session.playIndefinitely();

        //language=TEXT
        String expectedOutput = "****\n" +
                "s*** Attempts left: 7. Used letters: s\n" +
                "so** Attempts left: 7. Used letters: s, o\n" +
                "sof* Attempts left: 7. Used letters: s, o, f\n" +
                "sofa Attempts left: 7. Used letters: s, o, f, a\n" +
                "Congratulation, you won the game. The secret word: \"sofa\"\n";

        assertEquals(expectedOutput, getOutput());
    }

    @Test
    void gameSuccessfullyLost() {
        //language=TEXT
        InteractiveGameSession session = newGame("z\n" +
                "x\n" +
                "c\n" +
                "q\n" +
                "e\n" +
                "r\n" +
                "i");

        session.playIndefinitely();

        //language=TEXT
        String expectedOutput = "****\n" +
                "**** Attempts left: 6. Used letters: z\n" +
                "**** Attempts left: 5. Used letters: z, x\n" +
                "**** Attempts left: 4. Used letters: z, x, c\n" +
                "**** Attempts left: 3. Used letters: z, x, c, q\n" +
                "**** Attempts left: 2. Used letters: z, x, c, q, e\n" +
                "**** Attempts left: 1. Used letters: z, x, c, q, e, r\n" +
                "**** Attempts left: 0. Used letters: z, x, c, q, e, r, i\n" +
                "You have lost the game" +
                ". The secret word: \"sofa\"\n";

        assertEquals(expectedOutput, getOutput());
    }

    @Test
    void gameIsRestarted() {
        //language=TEXT
        InteractiveGameSession session = newGame("s\n" +
                "o\n" +
                "f\n" +
                "a\n" +
                "s\n" +
                "o\n" +
                "f\n" +
                "a");

        session.playIndefinitely();

        //language=TEXT
        String expectedOutput =
                "****\n" +
                "s*** Attempts left: 7. Used letters: s\n" +
                "so** Attempts left: 7. Used letters: s, o\n" +
                "sof* Attempts left: 7. Used letters: s, o, f\n" +
                "sofa Attempts left: 7. Used letters: s, o, f, a\n" +
                "Congratulation, you won the game. The secret word: \"sofa\"\n" +
                "****\n" +
                "s*** Attempts left: 7. Used letters: s\n" +
                "so** Attempts left: 7. Used letters: s, o\n" +
                "sof* Attempts left: 7. Used letters: s, o, f\n" +
                "sofa Attempts left: 7. Used letters: s, o, f, a\n" +
                "Congratulation, you won the game. The secret word: \"sofa\"\n";

        assertEquals(expectedOutput, getOutput());
    }

    private String getOutput() {
        return outputBytes.toString(StandardCharsets.UTF_8);
    }

    private InteractiveGameSession newGame(String input) {
        return new InteractiveGameSession(List.of("sofa"), new Scanner(input), output, ThreadLocalRandom.current());
    }
}
