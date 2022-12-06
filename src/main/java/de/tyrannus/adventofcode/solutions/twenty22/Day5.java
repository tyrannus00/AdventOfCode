package main.java.de.tyrannus.adventofcode.solutions.twenty22;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.List;

public class Day5 extends Solution {
    public Day5() {
        super(2022, 5);
    }

    @Override
    protected int partOne(String input) {
        var lines = input.split("\n");

        List<List<Character>> rows = new ArrayList<>(9);

        for (var i = 0; i < 9; i++) {
            rows.add(new ArrayList<>());
        }

        fillRowsInitially(rows, lines);

        for (var i = 10; i < lines.length; i++) {
            applyProcedure(rows, lines[i]);
        }

        printResult(rows);

        return 0;
    }

    @Override
    protected int partTwo(String input) {
        var lines = input.split("\n");

        List<List<Character>> rows = new ArrayList<>(9);

        for (var i = 0; i < 9; i++) {
            rows.add(new ArrayList<>());
        }

        fillRowsInitially(rows, lines);

        for (var i = 10; i < lines.length; i++) {
            applyFastProcedure(rows, lines[i]);
        }

        printResult(rows);

        return 0;
    }

    private void applyFastProcedure(List<List<Character>> rows, String instruction) {
        var split = instruction.split(" ");

        if (split.length < 6) {
            return;
        }

        var quantity = Integer.parseInt(split[1]);
        var from = Integer.parseInt(split[3]) - 1;
        var to = Integer.parseInt(split[5]) - 1;

        var sub = rows.get(from).subList(0, quantity);

        rows.get(to).addAll(0, sub);

        sub.clear();
    }

    private void applyProcedure(List<List<Character>> rows, String instruction) {
        var split = instruction.split(" ");

        if (split.length < 6) {
            return;
        }

        var quantity = Integer.parseInt(split[1]);
        var from = Integer.parseInt(split[3]) - 1;
        var to = Integer.parseInt(split[5]) - 1;

        for (var i = 0; i < quantity; i++) {
            rows.get(to).add(0, rows.get(from).remove(0));
        }
    }

    private void fillRowsInitially(List<List<Character>> rows, String[] lines) {
        for (var i = 0; i < 8; i++) {
            var line = lines[i];

            for (var j = 0; j < 9; j++) {
                var character = line.charAt(j * 4 + 1);

                if (Character.isLetter(character)) {
                    rows.get(j).add(character);
                }
            }
        }
    }

    private void printResult(List<List<Character>> rows) {
        var builder = new StringBuilder();

        for (var row : rows) {
            builder.append(row.get(0));
        }

        System.out.println(builder);
    }
}
