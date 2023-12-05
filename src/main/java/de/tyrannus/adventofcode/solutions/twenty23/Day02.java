package main.java.de.tyrannus.adventofcode.solutions.twenty23;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

import java.util.Arrays;

public class Day02 extends Solution<Integer>{

    public Day02() {
        super(2023, 2);
    }

    @Override
    public Integer partOne(String input) {
        var lines = Arrays.stream(input.split("\n")).toList();

        var validGameIdsSum = 0;

        lines:
        for (var line : lines) {
            var lineSplit = line.split(" ", 3);

            var sets = lineSplit[2].split("; ");

            for (var set : sets) {
                var colorsWithAmounts = set.split(", ");

                for (var colorWithAmount : colorsWithAmounts) {
                    var colorWithAmountSplit = colorWithAmount.split(" ");

                    var color = colorWithAmountSplit[1];
                    var amount = Integer.parseInt(colorWithAmountSplit[0]);

                    var max = switch (color) {
                        case "red" -> 12;
                        case "green" -> 13;
                        case "blue" -> 14;
                        default -> throw new IllegalArgumentException(color);
                    };

                    if (amount > max) {
                        continue lines;
                    }
                }
            }

            var gameIdWithColon = lineSplit[1];
            var gameId = Integer.parseInt(gameIdWithColon.substring(0, gameIdWithColon.length() - 1));

            validGameIdsSum += gameId;

        }

        return validGameIdsSum;
    }

    @Override
    public Integer partTwo(String input) {
        var lines = Arrays.stream(input.split("\n")).toList();

        var sumOfPowers = 0;

        for (var game : lines) {
            var lineSplit = game.split(" ", 3);

            var sets = lineSplit[2].split("; ");
            var greatestRed = 0;
            var greatestGreen = 0;
            var greatestBlue = 0;

            for (var set : sets) {
                var colorsWithAmounts = set.split(", ");

                for (var colorWithAmount : colorsWithAmounts) {
                    var colorWithAmountSplit = colorWithAmount.split(" ");

                    var color = colorWithAmountSplit[1];
                    var amount = Integer.parseInt(colorWithAmountSplit[0]);

                    switch (color) {
                        case "red" -> greatestRed = Math.max(greatestRed, amount);
                        case "green" -> greatestGreen = Math.max(greatestGreen, amount);
                        case "blue" -> greatestBlue = Math.max(greatestBlue, amount);
                        default -> throw new IllegalArgumentException(color);
                    }
                }
            }

            sumOfPowers += greatestRed * greatestGreen * greatestBlue;
        }

        return sumOfPowers;
    }
}
