package main.java.de.tyrannus.adventofcode.solutions.twenty22;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

public class Day08 extends Solution<Integer> {

    public Day08() {
        super(2022, 8);
    }

    @Override
    public Integer partOne(String input) {
        var lines = input.split("\n");
        var rowLength = lines[0].length();
        var treeArray = new boolean[lines.length][rowLength];

        var visibleTrees = 0;

        for (var i = 0; i < lines.length; i++) {
            var row = lines[i].toCharArray();

            var highestInRow = -1;
            var lastVisibleTreeIndex = -1;

            for (var j = 0; j < rowLength; j++) {
                var height = row[j];

                if (height > highestInRow) {
                    highestInRow = height;
                    lastVisibleTreeIndex = j;

                    visibleTrees++;
                    treeArray[i][j] = true;    //true means visible and false is not visible.
                }

                if (height == 9) {
                    break;
                }
            }

            highestInRow = -1;

            for (var j = rowLength - 1; j > lastVisibleTreeIndex; j--) {
                var height = row[j];

                if (height > highestInRow) {
                    highestInRow = height;

                    if (!treeArray[i][j]) {    //true means visible and false is not visible.
                        visibleTrees++;

                        treeArray[i][j] = true;
                    }
                }

                if (height == 9) {
                    break;
                }
            }
        }

        for (var j = 0; j < rowLength; j++) {
            var highestInColumn = -1;
            var lastVisibleTreeIndex = -1;

            for (var i = 0; i < lines.length; i++) {
                var height = lines[i].charAt(j);

                if (height > highestInColumn) {
                    highestInColumn = height;
                    lastVisibleTreeIndex = i;

                    if (!treeArray[i][j]) {    //true means visible and false is not visible.
                        visibleTrees++;

                        treeArray[i][j] = true;
                    }
                }

                if (height == 9) {
                    break;
                }
            }

            highestInColumn = -1;

            for (var i = lines.length - 1; i > lastVisibleTreeIndex; i--) {
                var height = lines[i].charAt(j);

                if (height > highestInColumn) {
                    highestInColumn = height;

                    if (!treeArray[i][j]) {    //true means visible and false is not visible.
                        visibleTrees++;

                        treeArray[i][j] = true;
                    }
                }

                if (height == 9) {
                    break;
                }
            }
        }

        return visibleTrees;
    }

    @Override
    public Integer partTwo(String input) {
        var lines = input.split("\n");
        var rowLength = lines[0].length();

        var topScore = 0;

        for (var x = 0; x < lines.length; x++) {
            var row = lines[x].toCharArray();

            for (var y = 0; y < rowLength; y++) {
                var houseHeight = row[y];

                //check left
                var leftScore = 0;

                for (var j = y - 1; j >= 0; j--) {
                    leftScore++;

                    if (row[j] >= houseHeight) {
                        break;
                    }
                }

                //check right
                var rightScore = 0;

                for (var j = y + 1; j < rowLength; j++) {
                    rightScore++;

                    if (row[j] >= houseHeight) {
                        break;
                    }
                }

                //check up
                var upScore = 0;

                for (var i = x - 1; i >= 0; i--) {
                    upScore++;

                    if (lines[i].charAt(y) >= houseHeight) {
                        break;
                    }
                }

                //check down
                var downScore = 0;

                for (var i = x + 1; i < lines.length; i++) {
                    downScore++;

                    if (lines[i].charAt(y) >= houseHeight) {
                        break;
                    }
                }

                var total = leftScore * rightScore * upScore * downScore;

                if (total > topScore) {
                    topScore = total;
                }
            }
        }

        return topScore;
    }
}
