package main.java.de.tyrannus.adventofcode.solutions.twenty22;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

import java.util.HashSet;

public class Day09 extends Solution<Integer>{
    public Day09() {
        super(2022, 9);
    }

    @Override
    public Integer partOne(String input) {
        return simulate(2, input.split("\n"));
    }

    @Override
    public Integer partTwo(String input) {
        return simulate(10, input.split("\n"));
    }

    private int simulate(int ropeLength, String[] lines) {
        var rope = new int[ropeLength][2];
        var tailTouchedPositions = new HashSet<Pos>();

        tailTouchedPositions.add(new Pos(0, 0));

        for (var line : lines) {
            var direction = line.charAt(0);
            var steps = Integer.parseInt(line.substring(2));

            for (var step = 0; step < steps; step++) {
                switch (direction) {
                    case 'R' -> rope[0][0]++;
                    case 'L' -> rope[0][0]--;
                    case 'U' -> rope[0][1]++;
                    case 'D' -> rope[0][1]--;
                }

                for (var i = 1; i < ropeLength; i++) {
                    var pieceAhead = rope[i - 1];
                    var piece = rope[i];

                    if (Math.abs(pieceAhead[0] - piece[0]) > 1 || Math.abs(pieceAhead[1] - piece[1]) > 1) {
                        piece[0] += clampToAbs1(pieceAhead[0] - piece[0]);
                        piece[1] += clampToAbs1(pieceAhead[1] - piece[1]);
                    }
                }

                tailTouchedPositions.add(new Pos(rope[ropeLength - 1]));
            }
        }

        return tailTouchedPositions.size();
    }

    private int clampToAbs1(int value) {
        if (value < -1) {
            return -1;
        } else if (value > 1) {
            return 1;
        }

        return value;
    }

    private record Pos(int x, int y) {
        Pos(int[] array) {
            this(array[0], array[1]);
        }
    }

    private void printPositions(HashSet<Pos> tailTouchedPositions, int[] headPos) {
        int minX = 0, minY = 0, maxX = 0, maxY = 0;

        for (var pos : tailTouchedPositions) {
            if (pos.x() < minX) {
                minX = pos.x();
            } else if (pos.x() > maxX) {
                maxX = pos.x();
            }

            if (pos.y() < minY) {
                minY = pos.y();
            } else if (pos.y() > maxY) {
                maxY = pos.y();
            }
        }

        for (var y = maxY; y >= minY; y--) {
            for (var x = minX; x <= maxX; x++) {
                if (x == 0 && y == 0) {
                    System.out.print('s');
                    continue;
                }

                if (x == headPos[0] && y == headPos[1]) {
                    System.out.print('H');
                    continue;
                }

                if (tailTouchedPositions.contains(new Pos(x, y))) {
                    System.out.print('#');
                } else {
                    System.out.print('.');
                }
            }
            System.out.println();
        }
    }
}
