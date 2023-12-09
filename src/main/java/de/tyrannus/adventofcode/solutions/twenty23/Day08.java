package main.java.de.tyrannus.adventofcode.solutions.twenty23;

import main.java.de.tyrannus.adventofcode.solutions.Solution;
import org.graalvm.collections.Pair;

import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;

public class Day08 extends Solution<Long> {

    public Day08() {
        super(2023, 8);
    }

    @Override
    protected Long partOne(String input) {
        var lines = inputToStringList(input);

        var map = new HashMap<String, Pair<String, String>>();

        for (var i = 2; i < lines.size(); i++) {
            var split = lines.get(i).split(" ");

            var key = split[0];
            var left = split[2].substring(1, 4);
            var right = split[3].substring(0, 3);

            map.put(key, Pair.create(left, right));
        }

        var instructions = lines.getFirst();
        var pos = "AAA";
        var steps = 0;
        var i = 0;

        while (!pos.equals("ZZZ")) {
            var options = map.get(pos);
            var instruction = instructions.charAt(i);

            pos = instruction == 'L' ? options.getLeft() : options.getRight();

            steps++;
            i++;

            if (i == instructions.length()) {
                i = 0;
            }
        }


        return (long) steps;
    }

    @Override
    protected Long partTwo(String input) {
        var lines = inputToStringList(input);

        var map = new HashMap<String, Pair<String, String>>();

        for (var i = 2; i < lines.size(); i++) {
            var split = lines.get(i).split(" ");

            var key = split[0];
            var left = split[2].substring(1, 4);
            var right = split[3].substring(0, 3);

            map.put(key, Pair.create(left, right));
        }

        var currentNodes = new ArrayList<Node>();

        for (var pos : map.keySet()) {
            if (pos.endsWith("A")) {
                currentNodes.add(new Node(pos));
            }
        }

        var steps = 0L;
        var i = 0;

        var instructions = lines.getFirst();

        loop:
        while (true) {
            var instruction = instructions.charAt(i);

            steps++;
            i++;

            if (i == instructions.length()) {
                i = 0;
            }

            for (Node node : currentNodes) {
                var options = map.get(node.pos);

                node.pos = instruction == 'L' ? options.getLeft() : options.getRight();

                if (node.stepsTillZ == 0 && node.pos.endsWith("Z")) {
                    node.stepsTillZ = steps;
                }
            }

            for (var node : currentNodes) {
                if (node.stepsTillZ == 0) {
                    continue loop;
                }
            }

            var totalSteps = currentNodes.getFirst().stepsTillZ;

            for (var n = 1; n < currentNodes.size(); n++) {
                var node = currentNodes.get(n);
                totalSteps = lcm(totalSteps, node.stepsTillZ);
            }

            return totalSteps;
        }
    }

    /**
     * Calculates the least common multiple.
     * This algorithm was taken from Baeldung.
     */
    private static long lcm(long a, long b) {
        if (a == 0 || b == 0) {
            return 0;
        }

        var absA = Math.abs(a);
        var absB = Math.abs(b);

        var absHigherNumber = Math.max(absA, absB);
        var absLowerNumber = Math.min(absA, absB);

        var lcm = absHigherNumber;

        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }

        return lcm;
    }

    private static class Node {
        String pos;
        long stepsTillZ = 0;

        public Node(String pos) {
            this.pos = pos;
        }
    }
}
