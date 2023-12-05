package main.java.de.tyrannus.adventofcode.solutions.twenty23;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

public class Day01 extends Solution<Integer> {

    private final String[] numbers = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

    public Day01() {
        super(2023, 1);
    }

    @Override
    public Integer partOne(String input) {
        var lines = inputToCharArrayList(input);

        var sum = 0;
        int character;
        char[] line;

        lines:
        for (var i = 0; i < lines.size(); i++) {
            line = lines.get(i);

            for (var j = 0; j < line.length; j++) {
                character = line[j];

                if (character < 58) {
                    sum += (character + 16 & 31) * 10;

                    for (var k = line.length - 1; k >= 0; k--) {
                        character = line[k];

                        if (character < 58) {
                            sum += character + 16 & 31;
                            continue lines;
                        }
                    }
                }
            }
        }

        return sum;
    }

    @Override
    public Integer partTwo(String input) {
        var lines = inputToCharArrayList(input);

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

        if (startChar < 58) {
            return startChar;
        }

        var string = new String(line);

        for (var i = 0; i < 9; i++) {
            var number = numbers[i];
            if (string.startsWith(number, index)) {
                return Integer.toString(i + 1).charAt(0);
            }
        }

        return '-';
    }
}
