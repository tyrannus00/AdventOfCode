package main.java.de.tyrannus.adventofcode.solutions.twenty22;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

import java.util.HashSet;

public class Day06 extends Solution<Integer>{
    public Day06() {
        super(2022, 6);
    }

//    @Override
//    public Integer partOne(String input) {
//        var list = input.chars().mapToObj(c -> (char) c).toList();
//        var set = new HashSet<Character>(4);
//
//        for (var i = 3; i < list.size(); i++) {
//            set.addAll(list.subList(i - 3, i + 1));
//
//            if (set.size() == 4) {  //Sets can't contain duplicates
//                return i + 1;
//            }
//
//            set.clear();
//        }
//
//        throw new RuntimeException();
//    } //0.1645209ms


    @Override
    public Integer partOne(String input) {
        var charArray = input.toCharArray();

        for (var i = 3; i < charArray.length; i++) {
            var charI = charArray[i];
            var charI1 = charArray[i - 1];

            if (charI == charI1) {
                i += 2;
                continue;
            }

            var charI2 = charArray[i - 2];
            var charI3 = charArray[i - 3];

            if (charI == charI2 || charI == charI3) {
                continue;
            }

            if (charI1 == charI2 || charI1 == charI3) {
                continue;
            }

            if (charI2 == charI3) {
                continue;
            }

            return i + 1;
        }

        throw new RuntimeException();
    }

    @Override
    public Integer partTwo(String input) {
        var list = input.chars().mapToObj(c -> (char) c).toList();
        var set = new HashSet<Character>(14);

        //Starting at 1099 + 10, because 1099 was the first index with at least 4 unique characters in a row, as we calculated in part one.
        for (var i = 1109; i < list.size(); i++) {
            set.addAll(list.subList(i - 13, i + 1));

            if (set.size() == 14) {  //Sets can't contain duplicates
                return i + 1;
            }

            set.clear();
        }

        throw new RuntimeException();
    }
}
