package main.java.de.tyrannus.adventofcode.solutions.twenty23;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

import java.util.Arrays;

public class Day01 extends Solution {

    public Day01() {
        super(2023, 1);
    }

    @Override
    protected int partOne(String input) {
        var lines = Arrays.stream(input.split("\n")).map(String::toCharArray).toList();

        var sum = 0;

        for (var line : lines) {
            var firstNumber = '-';

            for (var c : line) {
                if (Character.isDigit(c)) {
                    firstNumber = c;
                    break;
                }
            }

            var lastNumber = '-';

            for (var i = line.length - 1; i >= 0; i--) {
                var c = line[i];

                if (Character.isDigit(c)) {
                    lastNumber = c;
                    break;
                }
            }

            sum += Integer.parseInt("" + firstNumber + lastNumber);
        }

        return sum;
    }

    private final String[] numbers = new String[]{"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

    @Override
    protected int partTwo(String input) {
        var lines = Arrays.stream(input.split("\n")).map(String::toCharArray).toList();

        var sum = 0;

        for (var line : lines) {
            var firstNumber = '-';

            for (var i = 0; i < line.length; i++) {
                var digit = getDigit(i, line);

                if (digit != '-') {
                    firstNumber = digit;
                    break;
                }
            }

            var lastNumber = '-';

            for (var i = line.length - 1; i >= 0; i--) {
                var digit = getDigit(i, line);

                if (digit != '-') {
                    lastNumber = digit;
                    break;
                }
            }

            sum += Integer.parseInt("" + firstNumber + lastNumber);
        }

        return sum;
    }

    private char getDigit(int index, char[] line) {
        var startChar = line[index];

        if (Character.isDigit(startChar)) {
            return startChar;
        }

        var string = new String(line);

        for (var i = 0; i < 10; i++) {
            var number = numbers[i];
            if (string.startsWith(number, index)) {
                return Integer.toString(i).charAt(0);
            }
        }

        return '-';
    }
}
