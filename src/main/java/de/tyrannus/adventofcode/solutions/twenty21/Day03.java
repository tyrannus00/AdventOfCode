package main.java.de.tyrannus.adventofcode.solutions.twenty21;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.Arrays;

public class Day03 extends Solution<Integer>{
    public Day03() {
        super(2021, 3);
    }

    @Override
    public Integer partOne(String input) {

        var split = input.split("\n");

        var ones = new int[12];

        for (var s : split) {
            var bitArray = s.toCharArray();

            for (var j = 0; j < 12; j++) {
                if (bitArray[j] == '1') {
                    ones[j]++;
                }
            }
        }

        var builder = new StringBuilder();

        for (var i = 0; i < 12; i++) {
            if (ones[i] > split.length / 2) {
                builder.append('1');
            } else {
                builder.append('0');
            }
        }

        var gamma = Integer.parseInt(builder.toString(), 2);
        var epsilon = ~gamma & 0xFFF;

        return gamma * epsilon;
    }

    @Override
    public Integer partTwo(String input) {

        var charsList = new ArrayList<char[]>();

        Arrays.stream(input.split("\n")).forEach(s -> charsList.add(s.toCharArray()));

        var string = cope(charsList, 0).get(0);


        return 0;
    }

    private ArrayList<char[]> cope(ArrayList<char[]> chars, int depth) {
        if (chars.size() == 1) {
            return chars;
        }

        var ones = 0;

        for (var charas : chars) {
            if (charas[depth] == '1') {
                ones++;
            }
        }

        var one = ones >= chars.size() / 2;

        chars.removeIf(c -> c[depth] == (one ? '0' : '1'));

        return cope(chars, depth + 1);
    }

}
