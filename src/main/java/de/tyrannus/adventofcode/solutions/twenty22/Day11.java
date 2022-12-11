package main.java.de.tyrannus.adventofcode.solutions.twenty22;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day11 extends Solution {
    public Day11() {
        super(2022, 11);
    }

    @Override
    protected int partOne(String input) {
        var lines = input.split("\n");

        var monkeyArray = new Monkey[8];

        for (var i = 0; i < lines.length; i += 7) {
            var line = lines[i];

            var testNumber = Integer.parseInt(lines[i + 3].split(" ")[5]);
            var monkeyNumbertrue = Integer.parseInt(lines[i + 4].split(" ")[9]);
            var monkeyNumberfalse = Integer.parseInt(lines[i + 5].split(" ")[9]);

            var operationLine = lines[i + 2].split(" ");
            Operation operation;
            int operationNumber;

            if (operationLine[6].equals("*")) {
                if (operationLine[7].equals("old")) {
                    operation = Operation.SQUARE;
                    operationNumber = 0;
                } else {
                    operation = Operation.PRODUCT;
                    operationNumber = Integer.parseInt(operationLine[7]);
                }
            } else {
                operation = Operation.SUM;
                operationNumber = Integer.parseInt(operationLine[7]);
            }

            var items = Arrays.stream(lines[i + 1].substring(18).split(", ")).mapToInt(Integer::parseInt).toArray();

            monkeyArray[i / 7] = new Monkey(testNumber, monkeyNumbertrue, monkeyNumberfalse, operation, operationNumber, items);
        }

        for (var round = 0; round < 20; round++) {
            for (var monkey : monkeyArray) {
                while (!monkey.inventory.isEmpty()) {
                    var item = monkey.inspection();
                    var passMonkey = monkey.testAndGetPassMonkey(item);

                    monkeyArray[passMonkey].inventory.add(item);
                }
            }
        }

        int first = 0, second = 0;

        for (var monkey : monkeyArray) {
            if (monkey.getTimesInspected() > first) {
                second = first;
                first = monkey.getTimesInspected();
            } else if (monkey.getTimesInspected() > second) {
                second = monkey.getTimesInspected();
            }
        }

        return first * second;
    }

    @Override
    protected int partTwo(String input) {
        return 0;
    }

    private static class Monkey {
        final int testNumber, monkeyNumberTrue, monkeyNumberFalse, operationNumber;
        final Operation operation;
        final List<Integer> inventory = new ArrayList<>();
        private int timesInspected;

        public Monkey(int testNumber, int monkeyNumberTrue, int monkeyNumberFalse, Operation operation, int operationNumber, int[] startingItems) {
            this.testNumber = testNumber;
            this.monkeyNumberTrue = monkeyNumberTrue;
            this.monkeyNumberFalse = monkeyNumberFalse;
            this.operation = operation;
            this.operationNumber = operationNumber;

            for (var i : startingItems) {
                inventory.add(i);
            }
        }

        int testAndGetPassMonkey(int value) {
            if (value % testNumber == 0) {
                return monkeyNumberTrue;
            }

            return monkeyNumberFalse;
        }

        int inspection() {
            timesInspected++;

            var oldValue = inventory.remove(0);
            var newValue = switch (operation) {
                case SUM -> oldValue + operationNumber;
                case PRODUCT -> oldValue * operationNumber;
                case SQUARE -> oldValue * oldValue;
            };

            return newValue / 3;
        }

        public int getTimesInspected() {
            return timesInspected;
        }
    }

    private enum Operation {
        PRODUCT,
        SUM,
        SQUARE
    }

}
