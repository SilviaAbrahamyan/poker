package com.egs.training.poker;

public enum Suit {

    CLUBS ("clubs", 'C', '♣'),
    DIAMONDS ("diamonds", 'D', '♦'),
    HEARTS ("hearts", 'H', '♥'),
    SPADES ("spades", 'S', '♠');

    private String name;
    private char value;
    private char symbol;

    Suit(String name, char value, char symbol) {
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

    public static Suit getByName(final String name) {
        for (Suit suit : values()) {
            if (suit.name.equals(name)) {
                return suit;
            }
        }
        throw new IllegalArgumentException("Suit with name " + name + " not found");
    }

    public static Suit getByValue(final char value) {
        for (Suit suit : values()) {
            if (suit.value == value) {
                return suit;
            }
        }
        throw new IllegalArgumentException("Suit with value " + value + " not found");
    }
}
