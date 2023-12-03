package main.java.de.tyrannus.adventofcode.solutions.twenty23;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

import java.util.Arrays;
import java.util.List;

public class Day03 extends Solution {

    public Day03() {
        super(2023, 3);
    }

    @Override
    protected int partOne(String input) {
        var lines = Arrays.stream(input.split("\n")).toList();

        var sum = 0;

        for (var i = 0; i < lines.size(); i++) {
            var line = lines.get(i);
            var chars = line.toCharArray();

            for (var j = 0; j < chars.length; j++) {
                var c = chars[j];

                if (Character.isDigit(c)) {
                    for (var k = j; k < chars.length; k++) {
                        var otherC = chars[k];
                        var lineEnd = k == chars.length - 1;

                        if (lineEnd || !Character.isDigit(otherC)) {
                            if (nextToSymbol(i, line, lines, j, k)) {
                                var number = Integer.parseInt(line.substring(j, k + (lineEnd && Character.isDigit(otherC) ? 1 : 0)));

                                sum += number;
                            }
                            j = k;
                            break;
                        }
                    }
                }
            }
        }

        return sum;
    }

    @Override
    protected int partTwo(String input) {
        var lines = Arrays.stream(input.split("\n")).toList();

        int[][] map = new int[lines.size()][lines.get(0).length()];

        for (var lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
            var line = lines.get(lineIndex);
            var chars = line.toCharArray();

            for (var charIndex = 0; charIndex < chars.length; charIndex++) {
                var c = chars[charIndex];

                if (Character.isDigit(c)) {
                    for (var k = charIndex; k < chars.length; k++) {
                        var otherC = chars[k];
                        var lineEnd = k == chars.length - 1;

                        if (lineEnd || !Character.isDigit(otherC)) {
                            var stringEnd = k + (lineEnd && Character.isDigit(otherC) ? 1 : 0);
                            map[lineIndex][charIndex] = stringEnd - charIndex;

                            charIndex = k;
                            break;
                        }
                    }
                }
            }
        }

        var sum = 0;

        for (var i = 0; i < lines.size(); i++) {
            var line = lines.get(i);
            var chars = line.toCharArray();

            stars:
            for (var starIndex = 0; starIndex < chars.length; starIndex++) {
                var c = chars[starIndex];

                if (c == '*') {
                    var numberOne = 0;
                    var numberTwo = 0;

                    var mapInLine = map[i];

                    for (var k = 0; k < mapInLine.length; k++) {
                        var length = mapInLine[k];

                        if (length == 0) {
                            continue;
                        }

                        if (isStarAdjacent(starIndex, k, length)) {
                            var number = Integer.parseInt(line.substring(k, k + length));

                            if (numberOne == 0 && numberTwo == 0) {
                                numberOne = number;
                            } else if (numberOne != 0 && numberTwo == 0) {
                                numberTwo = number;
                            } else {
                                continue stars;
                            }
                        }
                    }

                    if (i > 0) {
                        var mapBefore = map[i - 1];
                        var lineBefore = lines.get(i - 1);

                        for (var k = 0; k < mapBefore.length; k++) {
                            var length = mapBefore[k];

                            if (length == 0) {
                                continue;
                            }

                            if (isStarAdjacent(starIndex, k, length)) {
                                var number = Integer.parseInt(lineBefore.substring(k, k + length));

                                if (numberOne == 0 && numberTwo == 0) {
                                    numberOne = number;
                                } else if (numberOne != 0 && numberTwo == 0) {
                                    numberTwo = number;
                                } else {
                                    continue stars;
                                }
                            }
                        }
                    }

                    if (i < lines.size() - 1) {
                        var mapAfter = map[i + 1];
                        var lineAfter = lines.get(i + 1);

                        for (var k = 0; k < mapAfter.length; k++) {
                            var length = mapAfter[k];

                            if (length == 0) {
                                continue;
                            }

                            if (isStarAdjacent(starIndex, k, length)) {
                                var number = Integer.parseInt(lineAfter.substring(k, k + length));

                                if (numberOne == 0 && numberTwo == 0) {
                                    numberOne = number;
                                } else if (numberOne != 0 && numberTwo == 0) {
                                    numberTwo = number;
                                } else {
                                    continue stars;
                                }
                            }
                        }
                    }

                    var gearRatio = numberOne * numberTwo;

                    sum += gearRatio;
                }
            }
        }

        return sum;
    }

    private boolean isStarAdjacent(int starIndex, int numberStartIndex, int numberLength) {
        return numberStartIndex == starIndex + 1 || numberStartIndex + numberLength == starIndex || starIndex >= numberStartIndex && starIndex < numberStartIndex + numberLength;
    }

    private boolean nextToSymbol(int lineNumber, String line, List<String> lines, int firstDigitIndex, int lastDigitIndex) {
        var firstCheckIndex = Math.max(0, firstDigitIndex - 1);
        var lastCheckIndex = Math.min(line.length(), lastDigitIndex + 1);

        if (containsSymbol(line.substring(firstCheckIndex, lastCheckIndex))) {
            return true;
        }

        if (lineNumber > 0) {
            if (containsSymbol(lines.get(lineNumber - 1).substring(firstCheckIndex, lastCheckIndex))) {
                return true;
            }
        }

        if (lineNumber < lines.size() - 1) {
            if (containsSymbol(lines.get(lineNumber + 1).substring(firstCheckIndex, lastCheckIndex))) {
                return true;
            }
        }

        return false;
    }

    private boolean containsSymbol(String string) {
        for (var c : string.toCharArray()) {
            if (c != '.' && !Character.isDigit(c)) {
                return true;
            }
        }

        return false;
    }
}
