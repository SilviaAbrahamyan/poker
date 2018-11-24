package com.egs.training.poker;

public enum CardRank {

    TWO ("2", 2, '2'),
    THREE ("3", 3, '3'),
    FOUR ("4", 4, '4'),
    FIVE ("5", 5, '5'),
    DIX ("6", 6, '6'),
    SEVEN ("7", 7, '7'),
    EIGHT ("8", 8, '8'),
    NINE ("9", 9, '9'),
    TEN ("10", 10, 'T'),
    JACK ("jack", 11, 'J'),
    QUEEN ("queen", 12, 'Q'),
    KING ("king", 13, 'K'),
    ACE ("ace", 14, 'A');

    private String name;
    private int value;
    private char symbol;

    CardRank(String name, int value, char symbol) {
        this.name = name;
        this.value = value;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public char getSymbol() {
        return symbol;
    }

    public static CardRank getByName(final String name) throws IllegalArgumentException {
        for (CardRank rank : values()) {
            if (rank.name.equals(name)) {
                return rank;
            }
        }
        throw new IllegalArgumentException("Card rank with name " + name + " not found");
    }

    public static CardRank getBySymbol(final char symbol) throws IllegalArgumentException {
        for (CardRank rank : values()) {
            if (rank.symbol == symbol) {
                return rank;
            }
        }
        throw new IllegalArgumentException("Card rank with symbol " + symbol + " not found");
    }

}
