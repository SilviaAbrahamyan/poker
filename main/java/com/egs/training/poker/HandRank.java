package com.egs.training.poker;

public enum HandRank {

    HIGH_CARD("High Card", 0),
    ONE_PAIR("One Pair", 100),
    TWO_PAIRS("Two Pairs", 200),
    THREE_OF_A_KIND("Three of a Kind", 300),
    STRAIGHT("Straight", 400),
    FLUSH("Flush", 500),
    FULL_HOUSE("Full House", 600),
    FOUR_OF_A_KIND("Four of a Kind", 700),
    STRAIGHT_FLUSH("Straight Flush", 800),
    ROYAL_FLUSH("Royal Flush", 900);

    private String name;
    private int value;

    HandRank(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public static HandRank getByName(final String name) throws IllegalArgumentException {
        for (HandRank rank : values()) {
            if (rank.name.equals(name)) {
                return rank;
            }
        }
        throw new IllegalArgumentException("Hand rank with name " + name + " not found");
    }

}
