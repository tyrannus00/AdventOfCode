package main.java.de.tyrannus.adventofcode.solutions.twenty23;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.Arrays;

public class Day04 extends Solution {

    public Day04() {
        super(2023, 4);
    }

    @Override
    protected int partOne(String input) {
        var lines = inputToList(input);

        var totalPoints = 0;

        for (var line : lines) {
            var split = line.replace("  ", " ").split(" \\| ");
            var winningNumbers = split[0].substring(split[0].indexOf(":") + 2);
            var winningInts = new ArrayList<Integer>();

            var winningNumbersSplit = winningNumbers.split(" ");
            for (var num : winningNumbersSplit) {
                winningInts.add(Integer.parseInt(num));
            }

            var yourNumbers = split[1].split(" ");

            var points = 0.5;

            for (var number : yourNumbers) {
                var asInt = Integer.parseInt(number);

                if (winningInts.contains(asInt)) {
                    points *= 2;
                }
            }

            totalPoints += (int) points;
        }

        return totalPoints;
    }

    @Override
    protected int partTwo(String input) {
        var lines = inputToList(input);
        var differentCards = lines.size();

        var originalCardsWinAmounts = new int[differentCards];

        for (var i = 0; i < differentCards; i++) {
            var split = lines.get(i).replace("  ", " ").split(" \\| ");

            var winningNumbersSplit = split[0].substring(split[0].indexOf(":") + 2).split(" ");
            var winningInts = new ArrayList<Integer>();

            for (var num : winningNumbersSplit) {
                winningInts.add(Integer.parseInt(num));
            }

            var yourNumbers = split[1].split(" ");

            for (var number : yourNumbers) {
                if (winningInts.contains(Integer.parseInt(number))) {
                    originalCardsWinAmounts[i] += 1;
                }
            }
        }

        var cardAmounts = new int[differentCards];
        Arrays.fill(cardAmounts, 1);  // Start values

        var totalAmount = 0;

        for (var card = 0; card < differentCards; card++) {
            var amount = cardAmounts[card];
            totalAmount += amount;

            var wins = originalCardsWinAmounts[card];

            for (var j = 0; j < wins; j++) {
                cardAmounts[card + j + 1] += amount;
            }
        }

        return totalAmount;
    }

    /**
     * This is an alternative algorithm to solve part 2.
     * Warning, it is really, really, really slow!
     * 1_219_270 ms runtime.
     */
    protected int guh(String input) {
        var lines = inputToList(input);

        var originalCardsWinAmounts = new int[lines.size()];

        for (var i = 0; i < lines.size(); i++) {
            var split = lines.get(i).replace("  ", " ").split(" \\| ");

            var winningNumbersSplit = split[0].substring(split[0].indexOf(":") + 2).split(" ");
            var winningInts = new ArrayList<Integer>();

            for (var num : winningNumbersSplit) {
                winningInts.add(Integer.parseInt(num));
            }

            var yourNumbers = split[1].split(" ");

            for (var number : yourNumbers) {
                if (winningInts.contains(Integer.parseInt(number))) {
                    originalCardsWinAmounts[i] += 1;
                }
            }
        }

        return getAnInt(originalCardsWinAmounts);
    }

    private static int getAnInt(int[] originalCardsWinAmounts) {
        var cards = new ArrayList<Integer>(originalCardsWinAmounts.length);
        var amount = 0;

        // fill original cards
        for (var i = 0; i < originalCardsWinAmounts.length; i++) {
            cards.add(i);
        }

        while (!cards.isEmpty()) {
            amount++;

            int card = cards.remove(0);
            var wins = originalCardsWinAmounts[card];

            for (var j = 0; j < wins; j++) {
                cards.add(card + j + 1);
            }
        }

        return amount;
    }
}
