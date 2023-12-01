package main.java.de.tyrannus.adventofcode.solutions;

import main.java.de.tyrannus.adventofcode.secrets.SessionCookie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class Solution {
    private final int year, day;

    protected Solution(int year, int day) {
        this.year = year;
        this.day = day;

        generateInput(year, day);
    }

    protected abstract int partOne(String input);

    protected abstract int partTwo(String input);

    public void doPartOne(int iterations) throws IOException {
        System.out.println("Executing Advent of Coding puzzle part one of December " + day + ", " + year + ".");

        execute(iterations, this::partOne);
    }

    public void doPartTwo(int iterations) throws IOException {
        System.out.println("Executing Advent of Coding puzzle part two of December " + day + ", " + year + ".");

        execute(iterations, this::partTwo);
    }

    private void execute(int iterations, SolutionRunnable e) throws IOException {
        var input = getInput();

        var startTimeNs = System.nanoTime();
        var output = 0;

        for (var i = 0; i < iterations; i++) {
            output = e.run(input);
        }

        var endTimeNs = System.nanoTime();

        System.out.println("The solution is: " + output);

        System.out.println("Average execution time over " + iterations + " iterations is " + ((endTimeNs - startTimeNs) / 1_000_000D / iterations) + "ms.");
    }

    private String getInput() throws IOException {
        try (var stream = new FileInputStream("src/main/resources/inputs/" + year + "/" + day + ".txt")) {
            return new String(stream.readAllBytes());
        }
    }

    public static void generateInput(int year, int day) {
        try {
            Files.createDirectories(Paths.get("src/main/resources/inputs/" + year));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var file = new File("src/main/resources/inputs/" + year + "/" + day + ".txt");

        if (file.exists()) {
            return;
        }

        var sessionCookie = SessionCookie.get();

        String result;

        try {
            var url = new URL("https://adventofcode.com/" + year + "/day/" + day + "/input");
            System.out.println("Downloading the new input from " + url + ".");

            var connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Cookie", "session=" + sessionCookie);

            result = new String(connection.getInputStream().readAllBytes());
        } catch (IOException e) {
            System.out.println("Downloading of input failed!");
            e.printStackTrace();
            return;
        }

        try (var writer = new FileWriter(file)) {
            writer.write(result);
            System.out.println("Downloading of input succeeded!");
        } catch (IOException e) {
            System.out.println("Downloading of input failed!");
            e.printStackTrace();
        }
    }

    private interface SolutionRunnable {
        int run(String input);
    }
}
