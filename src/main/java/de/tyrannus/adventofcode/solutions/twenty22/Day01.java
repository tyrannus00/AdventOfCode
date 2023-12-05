package main.java.de.tyrannus.adventofcode.solutions.twenty22;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

import java.util.Arrays;

public class Day01 extends Solution<Integer> {

    public Day01() {
        super(2022, 1);
    }

    @Override
    public Integer partOne(String input) {
        var calorieArray = Arrays.stream(input.split("\n")).mapToInt(s -> {
            if (s.isEmpty()) return 0;
            return Integer.parseInt(s);
        }).toArray();

        var topCals = 0;
        var currentCals = 0;

        for (var calories : calorieArray) {
            if (calories == 0) {
                if (currentCals > topCals) {
                    topCals = currentCals;
                }
                currentCals = 0;
            } else {
                currentCals += calories;
            }
        }

        return topCals;
    }

    @Override
    public Integer partTwo(String input) {
        var integerLines = Arrays.stream(input.split("\n")).mapToInt(s -> {
            if (s.isEmpty()) return 0;
            return Integer.parseInt(s);
        }).toArray();

        var topThree = new int[3];
        var currentCalories = 0;

        for (var lineCalories : integerLines) {
            if (lineCalories == 0) {
                if (currentCalories > topThree[0]) {
                    if (currentCalories > topThree[1]) {
                        if (currentCalories > topThree[2]) {
                            topThree[0] = topThree[1];
                            topThree[1] = topThree[2];
                            topThree[2] = currentCalories;
                        } else {
                            topThree[0] = topThree[1];
                            topThree[1] = currentCalories;
                        }
                    } else {
                        topThree[0] = currentCalories;
                    }
                }

                currentCalories = 0;
            } else {
                currentCalories += lineCalories;
            }
        }

        return topThree[0] + topThree[1] + topThree[2];
    }
}