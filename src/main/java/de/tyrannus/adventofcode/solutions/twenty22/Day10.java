package main.java.de.tyrannus.adventofcode.solutions.twenty22;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

public class Day10 extends Solution {
    public Day10() {
        super(2022, 10);
    }

    @Override
    protected int partOne(String input) {
        var lines = input.split("\n");

        var values = new int[6];

        var currentX = 1;
        var instructionsLine = 0;
        var executionTimeLeft = 0;

        for (var cycle = 1; cycle <= 220; cycle++) {
            //Start

            if (executionTimeLeft == 0) {
                var instructionToExecute = lines[instructionsLine];

                if (instructionToExecute.equals("noop")) {
                    executionTimeLeft = 1;
                } else {
                    executionTimeLeft = 2;
                }
            }



            //During

            executionTimeLeft--;

            if ((cycle + 20) % 40 == 0) {
                values[(cycle + 20) / 40 - 1] = currentX * cycle;
            }



            //End

            if (executionTimeLeft == 0) {
                var instructionToExecute = lines[instructionsLine];

                if (instructionToExecute.startsWith("addx")) {
                    currentX += Integer.parseInt(instructionToExecute.substring(5));
                }

                instructionsLine++;
            }
        }

        return values[0] + values[1] + values[2] + values[3] + values[4] + values[5];
    }

    @Override
    protected int partTwo(String input) {
        var lines = input.split("\n");

        var currentX = 1;
        var instructionsLine = 0;
        var executionTimeLeft = 0;

        for (var cycle = 1; cycle <= 240; cycle++) {
            //Start

            if (executionTimeLeft == 0) {
                var instructionToExecute = lines[instructionsLine];

                if (instructionToExecute.equals("noop")) {
                    executionTimeLeft = 1;
                } else {
                    executionTimeLeft = 2;
                }
            }



            //During

            executionTimeLeft--;

            var currentRenderPos = cycle - 1 - ((cycle - 1) / 40) * 40;
            var dif = currentX - currentRenderPos;

            if (dif <= 1 && dif >= -1) {
                System.out.print('#');
            } else {
                System.out.print('.');
            }

            if (cycle % 40 == 0) {
                System.out.println();
            }



            //End

            if (executionTimeLeft == 0) {
                var instructionToExecute = lines[instructionsLine];

                if (instructionToExecute.startsWith("addx")) {
                    currentX += Integer.parseInt(instructionToExecute.substring(5));
                }

                instructionsLine++;
            }
        }

        return 0;
    }
}
