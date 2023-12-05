package main.java.de.tyrannus.adventofcode.solutions.twenty23;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.List;

public class Day05 extends Solution {

    public Day05() {
        super(2023, 5);
    }

    @Override
    protected int partOne(String input) {
        var lines = inputToList(input);

        var seeds = parseSeeds(lines);
        var maps = parseMaps(lines);

        var smallestLocation = Long.MAX_VALUE;

        for (var seed : seeds) {
            var previousValue = seed;

            for (var map : maps) {
                previousValue = map.getDestination(previousValue);
            }

            if (previousValue < smallestLocation) {
                smallestLocation = previousValue;
            }
        }

        System.out.println(smallestLocation);
        return 0;
    }

    @Override
    protected int partTwo(String input) {
        return 0;
    }

    private List<Long> parseSeeds(List<String> lines) {
        var seeds = new ArrayList<Long>();

        // Parsing seeds
        var split = lines.getFirst().substring(7).split(" ");

        for (var num : split) {
            seeds.add(Long.parseLong(num));
        }

        return seeds;
    }

    private List<Map> parseMaps(List<String> lines) {
        var maps = new ArrayList<Map>(7);

        // Parsing maps
        for (var i = 2; i < lines.size(); i++) {
            var line = lines.get(i);

            if (line.contains("map:")) {
                maps.add(new Map());
            } else if (!line.isBlank()) {
                maps.getLast().subMaps.add(SubMap.parse(line));
            }
        }

        return maps;
    }

    private static class Map {
        final List<SubMap> subMaps = new ArrayList<>();

        long getDestination(long source) {
            for (var subMap : subMaps) {
                if (subMap.contains(source)) {
                    return subMap.getDestinationOf(source);
                }
            }

            return source;
        }
    }

    private static class SubMap {
        final long sourceRangeStart, sourceRangeEnd, destinationOffset;

        SubMap(long sourceRangeStart, long destinationRangeStart, long rangeLength) {
            this.sourceRangeStart = sourceRangeStart;
            this.sourceRangeEnd = sourceRangeStart + rangeLength;
            this.destinationOffset = destinationRangeStart - sourceRangeStart;
        }

        static SubMap parse(String line) {
            var split = line.split(" ");
            long sourceRangeStart = 0;
            long destinationRangeStart = 0;
            long rangeLength = 0;

            for (var i = 0; i < 3; i++) {
                switch (i) {
                    case 0 -> destinationRangeStart = Long.parseLong(split[i]);
                    case 1 -> sourceRangeStart = Long.parseLong(split[i]);
                    default -> rangeLength = Long.parseLong(split[i]);
                }
            }

            return new SubMap(sourceRangeStart, destinationRangeStart, rangeLength);
        }

        boolean contains(long source) {
            return source >= sourceRangeStart && source < sourceRangeEnd;
        }

        long getDestinationOf(long source) {
            return source + destinationOffset;
        }
    }
}
