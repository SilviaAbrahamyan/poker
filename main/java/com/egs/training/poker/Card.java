package com.egs.training.poker;


import com.egs.training.logger.Logger;
import com.egs.training.logger.LoggerFactory;

public class Card implements Comparable<Card> {

    private Logger logger = LoggerFactory.getLogger(Card.class.getName());

    private CardRank rank;
    private Suit suit;

    public Card(CardRank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Card(char rankSymb, char suitVal) {
        try {
            this.rank = CardRank.getBySymbol(rankSymb);
            this.suit = Suit.getByValue(suitVal);
        } catch (IllegalArgumentException iae) {
            logger.warn("Incorrect card specified: "+rankSymb+""+suitVal);
            throw iae;
        }
    }

    @Override
    public int compareTo(Card card) {
        //returns a negative integer, zero, or a positive integer as its rank value
        //is less than, equal to, or greater than the specified object.
        return (this.getRank().getValue() - card.getRank().getValue());
    }

    //printing the user friendly information about the card
    @Override
    public String toString() {
        return " [" + this.rank.getSymbol() + "" + this.suit.getSymbol() + "] ";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Card.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Card other = (Card) obj;
        if ( (this.rank == null) ? (other.rank != null) : !(this.rank.equals(other.rank)) ) {
            return false;
        }
        if ( (this.suit == null) ? (other.suit != null) : !(this.suit.equals(other.suit)) ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = this.rank != null ? this.rank.hashCode() : 0;
        hash = hash + (this.suit != null ? this.suit.hashCode() : 0);
        return hash;
    }

    public CardRank getRank() {
        return rank;
    }

    public void setRank(CardRank rank) {
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }
}
