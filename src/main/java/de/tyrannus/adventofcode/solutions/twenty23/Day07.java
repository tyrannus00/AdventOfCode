package main.java.de.tyrannus.adventofcode.solutions.twenty23;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Day07 extends Solution<Integer> {

    public Day07() {
        super(2023, 7);
    }

    private static final String STRENGTHS_ONE = "23456789TJQKA";
    private static final String STRENGTHS_TWO = "J23456789TQKA";

    private static final Byte FIVE_OF_A_KIND = 6;
    private static final Byte FOUR_OF_A_KIND = 5;
    private static final Byte FULL_HOUSE = 4;
    private static final Byte THREE_OF_A_KIND = 3;
    private static final Byte TWO_PAIR = 2;
    private static final Byte ONE_PAIR = 1;
    private static final Byte HIGH_CARD = 0;

    @Override
    protected Integer partOne(String input) {
        var lines = inputToStringList(input);

        var hands = new ArrayList<Hand>();

        for (var line : lines) {
            hands.add(Hand.parse(line, true));
        }

        hands.sort(Comparator.comparingInt(hand -> hand.numericValue));
        hands.sort(Comparator.comparingInt(hand -> hand.type));

        var winnings = 0;

        for (var i = 0; i < hands.size(); i++) {
            var hand = hands.get(i);
            winnings += hand.bid * (i + 1);
        }

        return winnings;
    }

    @Override
    protected Integer partTwo(String input) {
        var lines = inputToStringList(input);

        var hands = new ArrayList<Hand>();

        for (var line : lines) {
            hands.add(Hand.parse(line, false));
        }

        hands.sort(Comparator.comparingInt(hand -> hand.numericValue));
        hands.sort(Comparator.comparingInt(hand -> hand.type));

        var winnings = 0;

        for (var i = 0; i < hands.size(); i++) {
            var hand = hands.get(i);
            winnings += hand.bid * (i + 1);
        }

        return winnings;
    }

    private static class Hand {
        static final int HAND_SIZE = 5;

        final int bid;
        final int numericValue;
        final byte type;

        public Hand(String cards, int bid, boolean partOne) {
            this.bid = bid;

            var totalValue = 0;
            var map = new HashMap<Character, Integer>();

            for (var i = 0; i < HAND_SIZE; i++) {
                var c = cards.charAt(i);

                var value = (partOne ? STRENGTHS_ONE : STRENGTHS_TWO).indexOf(c);
                totalValue += (int) (value * Math.pow(STRENGTHS_ONE.length(), HAND_SIZE - i));

                map.put(c, map.getOrDefault(c, 0) + 1);
            }

            this.numericValue = totalValue;

            if (partOne) {
                type = switch (map.size()) {
                    case 1 -> FIVE_OF_A_KIND;
                    case 2 -> map.containsValue(4) ? FOUR_OF_A_KIND : FULL_HOUSE;
                    case 3 -> map.containsValue(3) ? THREE_OF_A_KIND : TWO_PAIR;
                    case 4 -> ONE_PAIR;
                    default -> HIGH_CARD;
                };
            } else {
                if (map.size() == 1 || map.size() == 2 && map.containsKey('J')) {
                    type = FIVE_OF_A_KIND;
                } else if (map.containsValue(4) && !map.containsKey('J') || map.containsValue(3) && map.getOrDefault('J', 0) == 1 || map.size() == 3 && map.containsValue(1) && map.getOrDefault('J', 0) == 2) {
                    type = FOUR_OF_A_KIND;
                } else if (map.size() == 2 || map.size() == 3 && map.getOrDefault('J', 0) == 1) {
                    type = FULL_HOUSE;
                } else if (map.containsValue(3) || map.size() == 4 && map.containsKey('J')) {
                    type = THREE_OF_A_KIND;
                } else if (map.size() == 3 && map.containsValue(2) && !map.containsKey('J')) {
                    type = TWO_PAIR;
                } else if (map.size() == 4 && !map.containsKey('J') || map.size() == 5 && map.containsKey('J')) {
                    type = ONE_PAIR;
                } else {
                    type = HIGH_CARD;
                }
            }
        }

        static Hand parse(String line, boolean partOne) {
            var split = line.split(" ");

            return new Hand(split[0], Integer.parseInt(split[1]), partOne);
        }
    }
}
