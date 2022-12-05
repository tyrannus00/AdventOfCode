package main.java.de.tyrannus.adventofcode.solutions.TwentyTwo;

import main.java.de.tyrannus.adventofcode.Solution;

import java.util.ArrayList;
import java.util.List;

public class DayFive extends Solution {

    private final List<List<Character>> rows;

    public DayFive() {
        super(2022, 5);

        rows = new ArrayList<>(9);

        for (var i = 0; i < 9; i++) {
            rows.add(new ArrayList<>());
        }
    }

    @Override
    protected int partOne(String input) {
        var lines = input.split("\n");

        fillRowsInitially(lines);

        for (var i = 10; i < lines.length; i++) {
            applyProcedure(lines[i]);
        }

        printResult();

        return 0;
    }

    @Override
    protected int partTwo(String input) {
        var lines = input.split("\n");

        fillRowsInitially(lines);

        for (var i = 10; i < lines.length; i++) {
            applyFastProcedure(lines[i]);
        }

        printResult();

        return 0;
    }

    private void applyFastProcedure(String instruction) {
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

    private void applyProcedure(String instruction) {
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

    private void fillRowsInitially(String[] lines) {
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

    private void printResult() {
        var builder = new StringBuilder();

        for (var row : rows) {
            builder.append(row.get(0));
        }

        System.out.println(builder);
    }
}
