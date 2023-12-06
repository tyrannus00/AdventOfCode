package main.java.de.tyrannus.adventofcode.solutions.twenty23;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.List;

public class Day06 extends Solution<Integer> {

    public Day06() {
        super(2023, 6);
    }

    @Override
    protected Integer partOne(String input) {
        var inputs = inputToCharArrayList(input);

        var times = parse(inputs, 0);
        var distances = parse(inputs, 1);

        var margins = 1;

        for (var i = 0; i < times.size(); i++) {
            var time = times.get(i);
            var bestDistance = distances.get(i);

            var marginOfError = 0;

            for (var duration = 1; duration < time; duration++) {
                if (duration * (time - duration) > bestDistance) {
                    marginOfError++;
                }
            }

            margins *= marginOfError;
        }

        return margins;
    }

    @Override
    protected Integer partTwo(String input) {
        var inputs = inputToStringList(input);

        var time = parse(inputs.get(0));
        var bestDistance = parse(inputs.get(1));

        var marginOfError = 0;

        for (var duration = 1; duration < time; duration++) {
            if (duration * (time - duration) > bestDistance) {
                marginOfError++;
            }
        }

        return marginOfError;
    }

    private long parse(String line) {
        var start = line.indexOf(":") + 1;
        return Long.parseLong(line.substring(start).replace(" ", ""));
    }

    private List<Integer> parse(List<char[]> inputs, int line) {
        var listToParseTo = new ArrayList<Integer>(4);
        var chars = inputs.get(line);

        for (var i = 0; i < chars.length; i++) {
            var c = chars[i];

            if (Character.isDigit(c)) {
                var numberEnd = i + 1;

                for (var j = numberEnd; j <= chars.length; j++) {
                    if (j == chars.length || !Character.isDigit(chars[j])) {
                        numberEnd = j;
                        break;
                    }
                }

                var builder = new StringBuilder();

                for (var k = i; k < numberEnd; k++) {
                    builder.append(chars[k]);
                }

                listToParseTo.add(Integer.parseInt(builder.toString()));
                i = numberEnd - 1;
            }
        }

        return listToParseTo;
    }
}