package main.java.de.tyrannus.adventofcode;

import main.java.de.tyrannus.adventofcode.solutions.TwentyTwo.DayFour;
import main.java.de.tyrannus.adventofcode.solutions.TwentyTwo.DayOne;
import main.java.de.tyrannus.adventofcode.solutions.TwentyTwo.DayThree;
import main.java.de.tyrannus.adventofcode.solutions.TwentyTwo.DayTwo;

public class AdventOfCode {

    public static void main(String[] args) {
        var dayOne = new DayOne();
        var dayTwo = new DayTwo();
        var dayThree = new DayThree();
        var dayFour = new DayFour();

        dayFour.doPartTwo(1);
    }
}
