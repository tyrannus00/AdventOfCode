package main.java.de.tyrannus.adventofcode.solutions.twenty22;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

import java.util.HashSet;

public class Day9 extends Solution {
    public Day9() {
        super(2022, 9);
    }

    @Override
    protected int partOne(String input) {
        var lines = input.split("\n");

        var headPos = new int[2];
        var tailPos = new int[2];

        var tailTouchedPositions = new HashSet<Pos>();
        tailTouchedPositions.add(new Pos(tailPos));

        for (var line : lines) {
            var direction = line.charAt(0);
            var steps = Integer.parseInt(line.substring(2));

            for (var step = 0; step < steps; step++) {
                switch (direction) {
                    case 'R' -> headPos[0]++;
                    case 'L' -> headPos[0]--;
                    case 'U' -> headPos[1]++;
                    case 'D' -> headPos[1]--;
                }

                if (notTouching(tailPos, headPos)) {
                    tailPos[0] += clampToAbs1(headPos[0] - tailPos[0]);
                    tailPos[1] += clampToAbs1(headPos[1] - tailPos[1]);
                }

                tailTouchedPositions.add(new Pos(tailPos[0], tailPos[1]));
            }
        }

        return tailTouchedPositions.size();
    }

    @Override
    protected int partTwo(String input) {
        var lines = input.split("\n");

        var tailTouchedPositions = new HashSet<Pos>();
        tailTouchedPositions.add(new Pos(0, 0));

        var rope = new int[10][2];  //0 is head, 9 is tail

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

                for (var i = 1; i < 10; i++) {
                    var pieceAhead = rope[i - 1];
                    var piece = rope[i];

                    if (!notTouching(piece, pieceAhead)) {
                        continue;
                    }

                    piece[0] += clampToAbs1(pieceAhead[0] - piece[0]);
                    piece[1] += clampToAbs1(pieceAhead[1] - piece[1]);
                }

                tailTouchedPositions.add(new Pos(rope[9][0], rope[9][1]));
            }
        }

        return tailTouchedPositions.size();
    }

    private boolean notTouching(int[] tail, int[] head) {
        return Math.abs(head[0] - tail[0]) > 1 || Math.abs(head[1] - tail[1]) > 1;
    }

    private int clampToAbs1(int value) {
        if (value < -1) {
            return -1;
        } else if (value > 1) {
            return 1;
        }

        return value;
    }

    private void printPositions(HashSet<Pos> tailTouchedPositions, int[] headPos) {
        int minX = -11, minY = -5, maxX = 14, maxY = 15;

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

                var pos = new Pos(x, y);

                if (tailTouchedPositions.contains(pos)) {
                    System.out.print('#');
                } else {
                    System.out.print('.');
                }
            }
            System.out.println();
        }
    }

    private record Pos(int x, int y) {
        Pos(int[] array) {
            this(array[0], array[1]);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pos pos)) return false;

            if (x != pos.x) return false;
            return y == pos.y;
        }

        @Override
        public int hashCode() {
            var result = x;
            result = 31 * result + y;
            return result;
        }
    }
}
