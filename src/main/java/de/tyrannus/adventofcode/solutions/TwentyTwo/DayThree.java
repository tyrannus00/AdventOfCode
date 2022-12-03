package main.java.de.tyrannus.adventofcode.solutions.TwentyTwo;

import main.java.de.tyrannus.adventofcode.Solution;

public class DayThree extends Solution {

    private final String priorities = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public DayThree() {
        super(2022, 3);
    }

    @Override
    protected int partOne(String input) {

        var split = input.split("\n");

        var totalValue = 0;

        for (var rucksack : split) {
            totalValue += priorities.indexOf(findCommonChar(rucksack)) + 1;
        }

        return totalValue;
    }

    @Override
    protected int partTwo(String input) {

        var split = input.split("\n");

        var totalValue = 0;

        for (var i = 2; i < split.length; i += 3) {
            totalValue += priorities.indexOf(findCommonChar(split[i], split[i - 1], split[i - 2])) + 1;
        }

        return totalValue;
    }

    private char findCommonChar(String rucksack) {
        var length = rucksack.length();
        var lengthHalf = length / 2;

        for (var i = 0; i < lengthHalf; i++) {
            var firstChar = rucksack.charAt(i);

            for (var j = lengthHalf; j < length; j++) {
                var secondChar = rucksack.charAt(j);

                if (firstChar == secondChar) {
                    return firstChar;
                }
            }
        }

        throw new RuntimeException();
    }

    private char findCommonChar(String sack1, String sack2, String sack3) {
        var chars1 = sack1.toCharArray();
        var chars2 = sack2.toCharArray();
        var chars3 = sack3.toCharArray();

        for (var char1 : chars1) {
            for (var char2 : chars2) {
                if (char1 == char2) {
                    for (var char3 : chars3) {
                        if (char2 == char3) {
                            return char3;
                        }
                    }
                }
            }
        }


        throw new RuntimeException();
    }
}
