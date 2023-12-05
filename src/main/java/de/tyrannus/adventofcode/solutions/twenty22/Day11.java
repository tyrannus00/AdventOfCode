package main.java.de.tyrannus.adventofcode.solutions.twenty22;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day11 extends Solution<Integer>{
    public Day11() {
        super(2022, 11);
    }

    @Override
    public Integer partOne(String input) {
        var monkeyArray = new Monkey[8];

        initMonkeys(monkeyArray, input.split("\n"));

        for (var round = 0; round < 20; round++) {
            for (var monkey : monkeyArray) {
                while (!monkey.inventory.isEmpty()) {
                    var item = monkey.inspection(true);
                    var passMonkey = monkey.testAndGetPassMonkey(item);

                    monkeyArray[passMonkey].inventory.add(item);
                }
            }
        }

        long first = 0, second = 0;

        for (var monkey : monkeyArray) {
            if (monkey.getTimesInspected() > first) {
                second = first;
                first = monkey.getTimesInspected();
            } else if (monkey.getTimesInspected() > second) {
                second = monkey.getTimesInspected();
            }
        }

        return (int) (first * second);
    }

    @Override
    public Integer partTwo(String input) {
        var monkeyArray = new Monkey[8];

        initMonkeys(monkeyArray, input.split("\n"));

        for (var round = 0; round < 10_000; round++) {
            for (var monkey : monkeyArray) {
                while (!monkey.inventory.isEmpty()) {
                    var item = monkey.inspection(false);
                    var passMonkey = monkey.testAndGetPassMonkey(item);

                    monkeyArray[passMonkey].inventory.add(item);
                }
            }

            if (round == 999 || round == 1999 || round == 2999 || round == 3999 || round == 4999 || round == 5999 || round == 6999 || round == 7999 || round == 8999 || round == 9999) {
                round = round;
            }
        }

        long first = 0, second = 0;

        for (var monkey : monkeyArray) {
            if (monkey.getTimesInspected() > first) {
                second = first;
                first = monkey.getTimesInspected();
            } else if (monkey.getTimesInspected() > second) {
                second = monkey.getTimesInspected();
            }
        }

        System.out.println("long: " + (first * second));

        return 0;
    }

    private void initMonkeys(Monkey[] monkeyArray, String[] lines) {
        for (var i = 0; i < lines.length; i += 7) {
            var testNumber = Long.parseLong(lines[i + 3].split(" ")[5]);
            var monkeyNumbertrue = Integer.parseInt(lines[i + 4].split(" ")[9]);
            var monkeyNumberfalse = Integer.parseInt(lines[i + 5].split(" ")[9]);

            var operationLine = lines[i + 2].split(" ");
            Operation operation;
            long operationNumber;

            if (operationLine[6].equals("*")) {
                if (operationLine[7].equals("old")) {
                    operation = Operation.SQUARE;
                    operationNumber = 0;
                } else {
                    operation = Operation.PRODUCT;
                    operationNumber = Long.parseLong(operationLine[7]);
                }
            } else {
                operation = Operation.SUM;
                operationNumber = Long.parseLong(operationLine[7]);
            }

            var items = Arrays.stream(lines[i + 1].substring(18).split(", ")).mapToLong(Long::parseLong).toArray();

            monkeyArray[i / 7] = new Monkey(testNumber, monkeyNumbertrue, monkeyNumberfalse, operation, operationNumber, items);
        }
    }

    private static class Monkey {
        final long testNumber, operationNumber;
        final int monkeyNumberTrue, monkeyNumberFalse;
        final Operation operation;
        final List<Long> inventory = new ArrayList<>();
        private long timesInspected;

        public Monkey(long testNumber, int monkeyNumberTrue, int monkeyNumberFalse, Operation operation, long operationNumber, long[] startingItems) {
            this.testNumber = testNumber;
            this.monkeyNumberTrue = monkeyNumberTrue;
            this.monkeyNumberFalse = monkeyNumberFalse;
            this.operation = operation;
            this.operationNumber = operationNumber;

            for (var i : startingItems) {
                inventory.add(i);
            }
        }

        int testAndGetPassMonkey(double value) {
            if (value % testNumber == 0) {
                return monkeyNumberTrue;
            }

            return monkeyNumberFalse;
        }

        long inspection(boolean relieve) {
            timesInspected++;

            long oldValue = inventory.remove(0);
            var newValue = switch (operation) {
                case SUM -> oldValue + operationNumber;
                case PRODUCT -> oldValue * operationNumber;
                case SQUARE -> oldValue * oldValue;
            };

            if (relieve) {
                return newValue / 3L;
            }

            return newValue % 9_699_690;
        }

        public long getTimesInspected() {
            return timesInspected;
        }
    }

    private enum Operation {
        PRODUCT,
        SUM,
        SQUARE
    }

}
