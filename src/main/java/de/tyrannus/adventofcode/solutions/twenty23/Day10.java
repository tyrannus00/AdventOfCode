package main.java.de.tyrannus.adventofcode.solutions.twenty23;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

import java.util.HashMap;
import java.util.Map;

public class Day10 extends Solution<Integer> {

    private static final byte MOVE_NORTH = -1;
    private static final byte MOVE_SOUTH = 1;
    private static final byte MOVE_WEST = -2;
    private static final byte MOVE_EAST = 2;

    private static final Map<Character, byte[]> pipeShapes = new HashMap<>();

    static {
        pipeShapes.put('|', new byte[]{MOVE_NORTH, MOVE_SOUTH});
        pipeShapes.put('-', new byte[]{MOVE_WEST, MOVE_EAST});
        pipeShapes.put('L', new byte[]{MOVE_NORTH, MOVE_EAST});
        pipeShapes.put('J', new byte[]{MOVE_NORTH, MOVE_WEST});
        pipeShapes.put('7', new byte[]{MOVE_WEST, MOVE_SOUTH});
        pipeShapes.put('F', new byte[]{MOVE_EAST, MOVE_SOUTH});
        pipeShapes.put('.', new byte[]{});
        pipeShapes.put('S', new byte[]{MOVE_NORTH, MOVE_SOUTH, MOVE_WEST, MOVE_EAST});
    }

    public Day10() {
        super(2023, 10);
    }

    @Override
    protected Integer partOne(String input) {
        var pipes = parsePipes(input);

        var lineLength = pipes.length;
        var startIndex = input.replace("\n", "").indexOf("S");

        var x = startIndex % lineLength;
        var y = startIndex / lineLength;
        byte lastMove = -1;

        var loopLength = 0;

        do {
            loopLength++;

            var move = getMove(pipes, x, y, lastMove);
            lastMove = move;

            switch (move) {
                case MOVE_NORTH -> y--;
                case MOVE_SOUTH -> y++;
                case MOVE_WEST -> x--;
                case MOVE_EAST -> x++;
            }
        } while (pipes[x][y] != 'S');

        return loopLength / 2;
    }

    @Override
    protected Integer partTwo(String input) {
        return null;
    }

    private byte getMove(char[][] pipes, int x, int y, byte lastMove) {
        var pipe = pipes[x][y];

        if (pipe == 'S') {
            if (y < pipes[x].length - 1 && contains(pipeShapes.get(pipes[x][y + 1]), MOVE_NORTH)) {
                 return MOVE_SOUTH;
            } else if (y > 0 && contains(pipeShapes.get(pipes[x][y - 1]), MOVE_SOUTH)) {
                return MOVE_NORTH;
            } else if (y < pipes.length - 1 && contains(pipeShapes.get(pipes[x + 1][y]), MOVE_WEST)) {
                return MOVE_EAST;
            } else {
                return MOVE_WEST;
            }
        }

        var moves = pipeShapes.get(pipe);

        for (var move : moves) {
            if (move * -1 == lastMove) {
                continue;
            }

            return move;
        }

        throw new IllegalStateException();
    }

    private boolean contains(byte[] moves, byte move) {
        for (var containedMove : moves) {
            if (containedMove == move) {
                return true;
            }
        }

        return false;
    }

    private char[][] parsePipes(String input) {
        var lines = inputToCharArrayList(input);
        var lineLength = lines.getFirst().length;

        var pipes = new char[lineLength][lines.size()];

        for (var y = 0; y < lines.size(); y++) {
            var line = lines.get(y);

            for (var x = 0; x < lineLength; x++) {
                var pipe = line[x];

                pipes[x][y] = pipe;
            }
        }

        return pipes;
    }
}
