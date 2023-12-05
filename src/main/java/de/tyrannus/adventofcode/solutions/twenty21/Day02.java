package main.java.de.tyrannus.adventofcode.solutions.twenty21;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

public class Day02 extends Solution<Integer>{
    public Day02() {
        super(2021, 2);
    }

    @Override
    public Integer partOne(String input) {

        var x = 0;
        var y = 0;

        for (var string : input.split("\n")) {
            var split = string.split(" ");

            var amount = Integer.parseInt(split[1]);

            switch (split[0]) {
                case "forward" -> x += amount;
                case "down" -> y += amount;
                case "up" -> y -= amount;
            }
        }

        return x * y;
    }

    @Override
    public Integer partTwo(String input) {

        var x = 0;
        var y = 0;
        var aim = 0;

        for (var string : input.split("\n")) {
            var split = string.split(" ");

            var amount = Integer.parseInt(split[1]);

            switch (split[0]) {
                case "down" -> aim += amount;
                case "up" -> aim -= amount;
                case "forward" -> {
                    x += amount;
                    y += aim * amount;
                }
            }
        }

        return x * y;
    }
}
