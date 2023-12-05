package main.java.de.tyrannus.adventofcode.solutions.twenty22;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

import java.util.Arrays;

public class Day02 extends Solution<Integer> {

    public Day02() {
        super(2022, 2);
    }

    @Override
    public Integer partOne(String input) {
        var matchups = Arrays.stream(input.split("\n")).map(s -> {
            var chars = s.toCharArray();

            return new Matchup(chars[0], chars[2]);
        }).toList();

        var score = 0;
        for (var match : matchups) {
            score += match.score();
        }

        return score;
    }

    @Override
    public Integer partTwo(String input) {
        var matchups = Arrays.stream(input.split("\n")).map(s -> new Matchup(s.toCharArray())).toList();

        var score = 0;
        for (var match : matchups) {
            score += match.score();
        }

        return score;
    }

    interface Material {
        int getValue();

        int compare(Material enemy);

        Material react(char letter);
    }

    static class Matchup {
        final Material enemy, you;

        Matchup(char enemy, char you) {
            this.enemy = get(enemy);
            this.you = get(you);
        }

        Matchup(char[] chars) {
            this.enemy = get(chars[0]);
            this.you = this.enemy.react(chars[2]);
        }

        static Material get(char letter) {
            return switch (letter) {
                case 'A', 'X' -> new Rock();
                case 'B', 'Y' -> new Paper();
                default -> new Scissors();
            };
        }

        int score() {
            return you.getValue() + you.compare(enemy);
        }
    }

    static class Rock implements Material {
        @Override
        public int getValue() {
            return 1;
        }

        @Override
        public int compare(Material enemy) {
            if (enemy instanceof Scissors) {
                return 6;
            } else if (enemy instanceof Rock) {
                return 3;
            } else {
                return 0;
            }
        }

        @Override
        public Material react(char letter) {
            if (letter == 'X') {
                return new Scissors();
            } else if (letter == 'Y') {
                return new Rock();
            } else {
                return new Paper();
            }
        }
    }

    static class Paper implements Material {
        @Override
        public int getValue() {
            return 2;
        }

        @Override
        public int compare(Material enemy) {
            if (enemy instanceof Rock) {
                return 6;
            } else if (enemy instanceof Paper) {
                return 3;
            } else {
                return 0;
            }
        }

        @Override
        public Material react(char letter) {
            if (letter == 'X') {
                return new Rock();
            } else if (letter == 'Y') {
                return new Paper();
            } else {
                return new Scissors();
            }
        }
    }

    static class Scissors implements Material {
        @Override
        public int getValue() {
            return 3;
        }

        @Override
        public int compare(Material enemy) {
            if (enemy instanceof Paper) {
                return 6;
            } else if (enemy instanceof Scissors) {
                return 3;
            } else {
                return 0;
            }
        }

        @Override
        public Material react(char letter) {
            if (letter == 'X') {
                return new Paper();
            } else if (letter == 'Y') {
                return new Scissors();
            } else {
                return new Rock();
            }
        }
    }
}