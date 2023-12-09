package main.java.de.tyrannus.adventofcode.solutions.twenty23;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

public class Day09 extends Solution<Long> {

    public Day09() {
        super(2023, 9);
    }

    @Override
    protected Long partOne(String input) {
        var lines = inputToStringList(input);

        var total = 0L;

        for (var line : lines) {
            var split = line.split(" ");
            var numbers = new long[split.length];

            for (var i = 0; i < split.length; i++) {
                numbers[i] = Long.parseLong(split[i]);
            }

            var nummer = extrapolate(numbers, false);
            total += nummer;
        }

        return total;
    }

    @Override
    protected Long partTwo(String input) {
        var lines = inputToStringList(input);

        var total = 0L;

        for (var line : lines) {
            var split = line.split(" ");
            var numbers = new long[split.length];

            for (var i = 0; i < split.length; i++) {
                numbers[i] = Long.parseLong(split[i]);
            }

            var nummer = extrapolate(numbers, true);
            total += nummer;
        }

        return total;
    }

    private long extrapolate(long[] numbers, boolean beginning) {
        var number = numbers[beginning ? 0 : numbers.length - 1];
        var differences = new long[numbers.length - 1];
        var allZeros = true;

        for (var i = 0; i < numbers.length - 1; i++) {
            var difference = beginning ? (numbers[i] - numbers[i + 1]) : (numbers[i + 1] - numbers[i]);

            differences[i] = difference;

            if (difference != 0) {
                allZeros = false;
            }
        }

        if (allZeros) {
            return number;
        }

        return number + extrapolate(differences, beginning);
    }
}