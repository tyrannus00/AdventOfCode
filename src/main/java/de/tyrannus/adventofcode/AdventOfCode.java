package main.java.de.tyrannus.adventofcode;

import main.java.de.tyrannus.adventofcode.solutions.Solution;
import main.java.de.tyrannus.adventofcode.solutions.twenty23.*;

import java.io.IOException;

public class AdventOfCode {

    public static void main(String[] args) throws IOException {
//        Solution.generateInput(2023, 10);
//
//        var tiles = Day10A.execute(Solution.inputToStringList(new Day10A().getInput()));
//
//        new Day10().test(new Day10A().getInput(), tiles);
        var day = new Day10();

        day.runTests(2);

        day.execute(2, 1);
    }
}