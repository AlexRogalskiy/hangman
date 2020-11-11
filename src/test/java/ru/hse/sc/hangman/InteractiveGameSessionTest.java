package ru.hse.sc.hangman;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InteractiveGameSessionTest {

    private final Hangman hangman = new Hangman("sofa");
    private final ByteArrayOutputStream outputBytes = new ByteArrayOutputStream();
    private final PrintStream output = new PrintStream(outputBytes);

    @Test
    void whenGameStartedMaskedWordIsPrinted() {
        InteractiveGameSession interactiveGameSession = newGame("");

        interactiveGameSession.start();

        assertTrue(getOutput().startsWith("****"));
    }

    @Test
    void whenLetterGuessedMaskedWordIsPrintedAsWellAsAttemptsLeft() {
        InteractiveGameSession interactiveGameSession = newGame("s\n");

        interactiveGameSession.start();

        assertTrue(getOutput().endsWith("s*** Attempts left: 7. Used letters: s\n"), "Unexpected output " + getOutput());
    }

    @Test
    void gameSuccessfullyWon() {
        InteractiveGameSession session = newGame("s\no\nf\na");

        session.start();

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

        session.start();

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

    private String getOutput() {
        return outputBytes.toString(StandardCharsets.UTF_8);
    }

    private InteractiveGameSession newGame(String input) {
        return new InteractiveGameSession(hangman, new Scanner(input), output);
    }
}
