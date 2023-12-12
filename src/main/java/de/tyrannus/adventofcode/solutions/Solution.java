package main.java.de.tyrannus.adventofcode.solutions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.MissingFormatArgumentException;

public abstract class Solution<T> {

    private static final String PATH = "adventofcode";
    private static final String INPUTS_PATH = PATH + "/inputs/";
    private static final String COOKIE_PATH = PATH + "/sessioncookie.txt";

    private final int year, day;

    private final List<Test> tests1 = new ArrayList<>();
    private final List<Test> tests2 = new ArrayList<>();

    public Solution(int year, int day) {
        this.year = year;
        this.day = day;

        generateInput(year, day);
    }

    /**
     *
     * @param part For which part the test is. Uses either the partOne or partTwo method.
     * @param input The test puzzle input in text block format (""" ... """).
     * @param expectedValue The value the part method should return.
     */
    protected void registerTest(int part, String input, T expectedValue) {
        (part == 1 ? tests1 : tests2).add(new Test<>(input, expectedValue));
    }

    public void runTests(int part) {
        var tests = part == 1 ? tests1 : tests2;

        var testAmount = tests.size();

        if (testAmount == 0) {
            System.out.println("No tests specified!");
            return;
        }

        var passed = 0;
        for (var test : tests) {
            var result = part == 1 ? partOne(test.input) : partTwo(test.input);
            if (result == test.expectedValue) {
                passed++;
            } else {
                System.out.println("Test " + test + " failed with value: " + result + ", expected was " + test.expectedValue);
            }
        }

        System.out.println(passed + "/" + testAmount + " tests have passed successfully.");
    }

    public static void generateInput(int year, int day) {
        try {
            Files.createDirectories(Paths.get(INPUTS_PATH + year));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        var inputFile = new File(INPUTS_PATH + year + "/" + day + ".txt");

        if (inputFile.exists()) {
            return;
        }

        String result;

        try {
            var url = URI.create("https://adventofcode.com/" + year + "/day/" + day + "/input").toURL();
            System.out.println("Downloading the new input from " + url + ".");

            var connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Cookie", "session=" + getSessionCookie());

            if (connection.getResponseCode() == 500) {
                throw new IllegalArgumentException("Invalid session cookie!");
            }

            result = new String(connection.getInputStream().readAllBytes());
        } catch (IOException e) {
            throw new IllegalStateException("Download of input failed for unknown reasons!");
        }

        try (var writer = new FileWriter(inputFile)) {
            writer.write(result);
            System.out.println("Download of input succeeded!");
        } catch (IOException e) {
            throw new IllegalStateException("Writing of input to file failed for unknown reasons!");
        }
    }

    private static String getSessionCookie() {
        try (var stream = new FileInputStream(COOKIE_PATH)) {
            return new String(stream.readAllBytes());
        } catch (IOException e) {
            throw new MissingFormatArgumentException("You need to create a file called \"sessioncookie.txt\" in the " + PATH + " folder, and insert your session cookie!");
        }
    }

    protected abstract T partOne(String input);

    protected abstract T partTwo(String input);

    public static List<String> inputToStringList(String input) {
        return Arrays.stream(input.split("\n")).toList();
    }

    protected static List<char[]> inputToCharArrayList(String input) {
        return Arrays.stream(input.split("\n")).map(String::toCharArray).toList();
    }

    public void execute(int part, int iterations) throws IOException {
        System.out.println("Executing Advent of Coding puzzle part " + part + " of December " + day + ", " + year + ".");

        SolutionCalculator<T> solutionCalculator = part == 1 ? this::partOne : this::partTwo;
        var input = getInput();
        T output = null;

        var startTimeNs = System.nanoTime();

        for (var i = 0; i < iterations; i++) {
            output = solutionCalculator.calculate(input);
        }

        var endTimeNs = System.nanoTime();

        System.out.println("The solution is: " + output);
        System.out.println("Average execution time over " + iterations + " iterations is " + ((endTimeNs - startTimeNs) / 1_000_000D / iterations) + "ms.");
    }

    public String getInput() throws IOException {
        try (var stream = new FileInputStream(INPUTS_PATH + year + "/" + day + ".txt")) {
            return new String(stream.readAllBytes());
        }
    }

    private interface SolutionCalculator<T> {
        T calculate(String input);
    }

    private record Test<T>(String input, T expectedValue) {}
}
