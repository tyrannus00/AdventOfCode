package main.java.de.tyrannus.adventofcode.solutions.twenty23;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.List;

public class Day05 extends Solution<Long> {

    public Day05() {
        super(2023, 5);
    }

    @Override
    protected Long partOne(String input) {
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

        return smallestLocation;
    }

    @Override
    protected Long partTwo(String input) {
        var lines = inputToList(input);

        var maps = parseMaps(lines);

        var smallestLocation = Long.MAX_VALUE;

        var numbers = lines.getFirst().substring(7).split(" ");

        for (var i = 0; i < numbers.length - 1; i += 2) {
            var number = Long.parseLong(numbers[i]);

            var rangeLength = Long.parseLong(numbers[i + 1]);

            for (var seed = number; seed < number + rangeLength; seed++) {
                var previousValue = seed;

                for (var map : maps) {
                    previousValue = map.getDestination(previousValue);
                }

                if (previousValue < smallestLocation) {
                    smallestLocation = previousValue;
                }
            }
        }

        return smallestLocation;
    }

    private List<Long> parseSeeds(List<String> lines) {
        var seeds = new ArrayList<Long>();

        var numbers = lines.getFirst().substring(7).split(" ");

        for (var number : numbers) {
            seeds.add(Long.parseLong(number));
        }


        return seeds;
    }

    private List<Map> parseMaps(List<String> lines) {
        var maps = new ArrayList<Map>(7);

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
