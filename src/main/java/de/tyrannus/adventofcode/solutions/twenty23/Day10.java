package main.java.de.tyrannus.adventofcode.solutions.twenty23;

import main.java.de.tyrannus.adventofcode.solutions.Solution;
import org.graalvm.collections.Pair;

import java.util.*;

public class Day10 extends Solution<Integer> {

    private static final byte MOVE_NORTH = -1;
    private static final byte MOVE_SOUTH = 1;
    private static final byte MOVE_WEST = -2;
    private static final byte MOVE_EAST = 2;

    private static final byte NORTH_WEST = 0;
    private static final byte NORTH_EAST = 1;
    private static final byte SOUTH_WEST = 2;
    private static final byte SOUTH_EAST = 3;

    private static final Vec[] OFFSETS = new Vec[4];
    private static final Map<Character, byte[]> pipeShapes = new HashMap<>();

    static {    // 414 too high
        pipeShapes.put('|', new byte[]{MOVE_NORTH, MOVE_SOUTH});
        pipeShapes.put('-', new byte[]{MOVE_WEST, MOVE_EAST});
        pipeShapes.put('L', new byte[]{MOVE_NORTH, MOVE_EAST});
        pipeShapes.put('J', new byte[]{MOVE_NORTH, MOVE_WEST});
        pipeShapes.put('7', new byte[]{MOVE_WEST, MOVE_SOUTH});
        pipeShapes.put('F', new byte[]{MOVE_EAST, MOVE_SOUTH});
        pipeShapes.put('.', new byte[]{});
        pipeShapes.put('S', /*new byte[]{MOVE_EAST, MOVE_SOUTH});*/ new byte[]{MOVE_NORTH, MOVE_SOUTH, MOVE_WEST, MOVE_EAST});

        OFFSETS[0] = new Vec(1, 0);
        OFFSETS[1] = new Vec(-1, 0);
        OFFSETS[2] = new Vec(0, 1);
        OFFSETS[3] = new Vec(0, -1);
    }

    public Day10() {
        super(2023, 10);
    }

    public void test(String input, List<Tile> correctTiles) {
        var pipes = parsePipes(input);
        var tiles = new Tile[pipes.length][pipes[0].length];

        fillTiles(tiles, pipes);
        mapMainLoop(tiles, pipes, input);

        var enclosedTiles = 0;

        for (var x = 0; x < pipes.length; x++) {
            var row = pipes[x];
            for (var y = 0; y < row.length; y++) {
                var tile = tiles[x][y];

                if (tile.mainLoop || tile.outside || tile.inside) {
                    continue;
                }

                var area = new ArrayList<Tile>();
                queueNeighbours(tile, area, tiles, (byte) -1);

                var outside = false;
                var enclosed = 0;

                for (var areaTile : area) {
                    if (areaTile.outside && !areaTile.mainLoop) {
                        outside = true;
                        break;
                    }

                    if (!areaTile.mainLoop) {
                        enclosed++;
                    }
                }

                if (outside) {
                    for (var areaTile : area) {
                        if (!areaTile.mainLoop) {
                            areaTile.inside = false;
                            areaTile.outside = true;
                        }
                    }
                } else {
                    enclosedTiles += enclosed;

                    for (var areaTile : area) {
                        if (!areaTile.mainLoop) {
                            areaTile.inside = true;
                        }
                    }
                }

                for (var e : tiles) {
                    for (var f : e) {
                        f.indexed0 = 0;
                        f.indexed1 = 0;
                        f.indexed2 = 0;
                        f.indexed3 = 0;
                        f.simpleIndex = 0;
                    }
                }
            }
        }

        // Printing the result
        for (var y = 0; y < tiles[0].length; y++) {
            for (var x = 0; x < tiles.length; x++) {
                var tile = tiles[x][y];
                System.out.print(tile.mainLoop ? tile.pipe : (tile.inside ? 'i' : (tile.outside ? 'O' : '.')));
            }
            System.out.println();
        }

        for (var e : tiles) {
            for (var tile : e) {
                if (!tile.inside || correctTiles.contains(tile)) {
                    continue;
                }

                System.out.println(tile);
            }
        }

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
        var pipes = parsePipes(input);
        var tiles = new Tile[pipes.length][pipes[0].length];

        fillTiles(tiles, pipes);
        mapMainLoop(tiles, pipes, input);

        for (var x = 0; x < pipes.length; x++) {
            var row = pipes[x];
            for (var y = 0; y < row.length; y++) {
                var tile = tiles[x][y];

                if (tile.mainLoop || tile.inside || tile.outside) {
                    continue;
                }

                var area = new ArrayList<Tile>();
                queueNeighboursIterative(tile, area, tiles);

                var outside = false;

                for (var areaTile : area) {
                    if (areaTile.outside && !areaTile.mainLoop) {
                        outside = true;
                        break;
                    }
                }

                if (outside) {
                    for (var areaTile : area) {
                        if (!areaTile.mainLoop) {
                            areaTile.inside = false;
                            areaTile.outside = true;
                        }
                    }
                } else {
                    for (var areaTile : area) {
                        if (!areaTile.mainLoop) {
                            areaTile.inside = true;
                        }
                    }
                }

                for (var e : tiles) {
                    for (var f : e) {
                        f.indexed0 = 0;
                        f.indexed1 = 0;
                        f.indexed2 = 0;
                        f.indexed3 = 0;
                        f.simpleIndex = 0;
                    }
                }
            }
        }

        // Printing the result
//        for (var y = 0; y < tiles[0].length; y++) {
//            for (var x = 0; x < tiles.length; x++) {
//                var tile = tiles[x][y];
//                System.out.print(tile.mainLoop ? tile.pipe : (tile.inside ? 'i' : (tile.outside ? 'O' : '.')));
//            }
//            System.out.println();
//        }

        var count = 0;
        for (var e : tiles) {
            for (var f : e) {
                if (f.inside) {
                    count++;
                }
            }
        }

        return count;
    }

    private void queueNeighbours(Tile tile, Collection<Tile> area, Tile[][] tiles, byte pos) {
        if (!tile.mainLoop) {
            tile.simpleIndex++;
        }

        for (var offset : OFFSETS) {
            var neighbour = getNeighbourSafe(tile.x, tile.y, offset, tiles);

            if (neighbour == null) {
                continue;
            }

            if (tile.mainLoop) {
                var moves = tile.moves;

                if (offset.y < 0) { // Going north
                    if (pos == SOUTH_EAST && contains(moves, MOVE_EAST) || pos == SOUTH_WEST && contains(moves, MOVE_WEST)) {
                        continue;
                    }
                } else if (offset.y > 0) {  // Going south
                    if (pos == NORTH_EAST && contains(moves, MOVE_EAST) || pos == NORTH_WEST && contains(moves, MOVE_WEST)) {
                        continue;
                    }
                } else if (offset.x < 0) {  // Going west
                    if (pos == NORTH_EAST && contains(moves, MOVE_NORTH) || pos == SOUTH_EAST && contains(moves, MOVE_SOUTH)) {
                        continue;
                    }
                } else {    // Going east
                    if (pos == NORTH_WEST && contains(moves, MOVE_NORTH) || pos == SOUTH_WEST && contains(moves, MOVE_SOUTH)) {
                        continue;
                    }
                }
            }

            if (neighbour.outside && !neighbour.mainLoop) {
                for (var areaTile : area) {
                    if (!areaTile.mainLoop) {
                        areaTile.outside = true;
                    }
                }
                return;
            }

            if (neighbour.indexed(pos)) {
                continue;
            }

            if (neighbour.mainLoop) {
                var moves = neighbour.moves;

                if (tile.mainLoop) {
                    var isWest = pos == 0 || pos == 2;
                    var isNorth = pos == 0 || pos == 1;

                    if (offset.y < 0) { // Going north
                        pos = isWest ? (contains(moves, MOVE_WEST) ? SOUTH_WEST : NORTH_WEST) : (contains(moves, MOVE_EAST) ? SOUTH_EAST : NORTH_EAST);
                    } else if (offset.y > 0) {  // Going south
                        pos = isWest ? (contains(moves, MOVE_WEST) ? NORTH_WEST : SOUTH_WEST) : (contains(moves, MOVE_EAST) ? NORTH_EAST : SOUTH_EAST);
                    } else if (offset.x < 0) {  // Going west
                        pos = isNorth ? (contains(moves, MOVE_NORTH) ? NORTH_EAST : NORTH_WEST) : (contains(moves, MOVE_SOUTH) ? SOUTH_EAST : SOUTH_WEST);
                    } else {    // Going east
                        pos = isNorth ? (contains(moves, MOVE_NORTH) ? NORTH_WEST : NORTH_EAST) : (contains(moves, MOVE_SOUTH) ? SOUTH_WEST : SOUTH_EAST);
                    }
                } else {
                    if (offset.y < 0) { // Going north
                        pos = contains(moves, MOVE_WEST) ? (contains(moves, MOVE_EAST) ? SOUTH_EAST : NORTH_EAST) : NORTH_WEST;
                    } else if (offset.y > 0) {  // Going south
                        pos = contains(moves, MOVE_WEST) ? (contains(moves, MOVE_EAST) ? NORTH_EAST : SOUTH_EAST) : SOUTH_WEST;
                    } else if (offset.x < 0) {  // Going west
                        pos = contains(moves, MOVE_NORTH) ? (contains(moves, MOVE_SOUTH) ? SOUTH_EAST : SOUTH_WEST) : NORTH_WEST;
                    } else {    // Going east
                        pos = contains(moves, MOVE_NORTH) ? (contains(moves, MOVE_SOUTH) ? SOUTH_WEST : SOUTH_EAST) : NORTH_EAST;
                    }
                }

                if (pos == 0) {
                    neighbour.indexed0++;
                } else if (pos == 1) {
                    neighbour.indexed1++;
                } else if (pos == 2) {
                    neighbour.indexed2++;
                } else {
                    neighbour.indexed3++;
                }
            }

//            if (tile.x == 6 && tile.y == 7) {
//                tile.outside = tile.outside;
//            }

            try {
                queueNeighbours(neighbour, area, tiles, pos);
            } catch (StackOverflowError ignored) {
                return;
            }
        }
    }

    private void queueNeighboursIterative(Tile start, Collection<Tile> area, Tile[][] tiles) {
        Queue<Pair<Tile, Pair<Byte, Tile>>> queue = new LinkedList<>();
        queue.add(Pair.create(start, Pair.create((byte) -1, null)));

        while (!queue.isEmpty()) {
            var pair = queue.poll();
            var pair2 = pair.getRight();

            var tile = pair.getLeft();
            var pos = pair2.getLeft();
            var previous = pair2.getRight();

            area.add(tile);

            if (!tile.mainLoop) {
                tile.simpleIndex++;
            }

            for (var offset : OFFSETS) {
                var neighbour = getNeighbourSafe(tile.x, tile.y, offset, tiles);

                if (neighbour == null || neighbour == previous) {
                    continue;
                }

                if (tile.mainLoop) {
                    var moves = tile.moves;

                    if (offset.y < 0) { // Going north
                        if (pos == SOUTH_EAST && contains(moves, MOVE_EAST) || pos == SOUTH_WEST && contains(moves, MOVE_WEST)) {
                            continue;
                        }
                    } else if (offset.y > 0) {  // Going south
                        if (pos == NORTH_EAST && contains(moves, MOVE_EAST) || pos == NORTH_WEST && contains(moves, MOVE_WEST)) {
                            continue;
                        }
                    } else if (offset.x < 0) {  // Going west
                        if (pos == NORTH_EAST && contains(moves, MOVE_NORTH) || pos == SOUTH_EAST && contains(moves, MOVE_SOUTH)) {
                            continue;
                        }
                    } else {    // Going east
                        if (pos == NORTH_WEST && contains(moves, MOVE_NORTH) || pos == SOUTH_WEST && contains(moves, MOVE_SOUTH)) {
                            continue;
                        }
                    }
                }

                if (neighbour.outside && !neighbour.mainLoop) {
                    for (var areaTile : area) {
                        if (!areaTile.mainLoop) {
                            areaTile.outside = true;
                        }
                    }
                    return;
                }

                if (neighbour.indexed(pos)) {
                    continue;
                }

                byte neighbourPos = -1;

                if (neighbour.mainLoop) {
                    var moves = neighbour.moves;

                    if (tile.mainLoop) {
                        var isWest = pos == 0 || pos == 2;
                        var isNorth = pos == 0 || pos == 1;

                        if (offset.y < 0) { // Going north
                            neighbourPos = isWest ? (contains(moves, MOVE_WEST) ? SOUTH_WEST : NORTH_WEST) : (contains(moves, MOVE_EAST) ? SOUTH_EAST : NORTH_EAST);
                        } else if (offset.y > 0) {  // Going south
                            neighbourPos = isWest ? (contains(moves, MOVE_WEST) ? NORTH_WEST : SOUTH_WEST) : (contains(moves, MOVE_EAST) ? NORTH_EAST : SOUTH_EAST);
                        } else if (offset.x < 0) {  // Going west
                            neighbourPos = isNorth ? (contains(moves, MOVE_NORTH) ? NORTH_EAST : NORTH_WEST) : (contains(moves, MOVE_SOUTH) ? SOUTH_EAST : SOUTH_WEST);
                        } else {    // Going east
                            neighbourPos = isNorth ? (contains(moves, MOVE_NORTH) ? NORTH_WEST : NORTH_EAST) : (contains(moves, MOVE_SOUTH) ? SOUTH_WEST : SOUTH_EAST);
                        }
                    } else {
                        if (offset.y < 0) { // Going north
                            neighbourPos = contains(moves, MOVE_WEST) ? (contains(moves, MOVE_EAST) ? SOUTH_EAST : NORTH_EAST) : NORTH_WEST;
                        } else if (offset.y > 0) {  // Going south
                            neighbourPos = contains(moves, MOVE_WEST) ? (contains(moves, MOVE_EAST) ? NORTH_EAST : SOUTH_EAST) : SOUTH_WEST;
                        } else if (offset.x < 0) {  // Going west
                            neighbourPos = contains(moves, MOVE_NORTH) ? (contains(moves, MOVE_SOUTH) ? SOUTH_EAST : SOUTH_WEST) : NORTH_WEST;
                        } else {    // Going east
                            neighbourPos = contains(moves, MOVE_NORTH) ? (contains(moves, MOVE_SOUTH) ? SOUTH_WEST : SOUTH_EAST) : NORTH_EAST;
                        }
                    }

                    if (neighbourPos == 0) {
                        neighbour.indexed0++;
                    } else if (neighbourPos == 1) {
                        neighbour.indexed1++;
                    } else if (neighbourPos == 2) {
                        neighbour.indexed2++;
                    } else {
                        neighbour.indexed3++;
                    }
                }

                queue.add(Pair.create(neighbour, Pair.create(neighbourPos, tile)));
            }
        }
    }

    private Tile getNeighbourSafe(int x, int y, Vec offset, Tile[][] tiles) {
        var newX = x + offset.x;
        var newY = y + offset.y;

        if (newX < 0 || newX == tiles.length || newY < 0 || newY == tiles[0].length) {
            return null;
        }

        return tiles[newX][newY];
    }

    private void fillTiles(Tile[][] tiles, char[][] pipes) {
        for (var x = 0; x < pipes.length; x++) {
            var row = pipes[x];
            for (var y = 0; y < row.length; y++) {
                var tile = new Tile(row[y], x, y);
                tile.outside = x == 0 || y == 0 || x == pipes.length - 1 || y == row.length - 1;

                tiles[x][y] = tile;
            }
        }
    }

    private void mapMainLoop(Tile[][] tiles, char[][] pipes, String input) {
        var lineLength = pipes.length;
        var startIndex = input.replace("\n", "").indexOf("S");

        var x = startIndex % lineLength;
        var y = startIndex / lineLength;
        byte lastMove = -1;

        do {
            tiles[x][y].mainLoop = true;

            var move = getMove(pipes, x, y, lastMove);
            lastMove = move;

            switch (move) {
                case MOVE_NORTH -> y--;
                case MOVE_SOUTH -> y++;
                case MOVE_WEST -> x--;
                case MOVE_EAST -> x++;
            }
        } while (pipes[x][y] != 'S');
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

    public static class Tile {
        final char pipe;
        final int x, y;
        final byte[] moves;
        boolean mainLoop, outside, inside;
        int indexed0, indexed1, indexed2, indexed3, simpleIndex;

        public Tile(char pipe, int x, int y) {
            this.pipe = pipe;
            this.x = x;
            this.y = y;
            this.moves = pipeShapes.get(pipe);
        }

        boolean indexed(byte pos) {
            var simpleTolerance = 0;
            var tolerance = 0;

            if (!mainLoop || pos == -1) {
                return simpleIndex > simpleTolerance;
            }

            if (pos == 0) {
                return indexed0 > tolerance;
            } else if (pos == 1) {
                return indexed1 > tolerance;
            } else if (pos == 2) {
                return indexed2 > tolerance;
            } else if (pos == 3) {
                return indexed3 > tolerance;
            } else {
                return simpleIndex > simpleTolerance;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Tile tile)) return false;

            if (x != tile.x) return false;
            return y == tile.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }

        @Override
        public String toString() {
            return "Tile{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    private record Vec(int x, int y) {}
}
