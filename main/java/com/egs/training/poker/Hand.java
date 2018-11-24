package com.egs.training.poker;

import com.egs.training.logger.Logger;
import com.egs.training.logger.LoggerFactory;
import com.egs.training.poker.exception.HandIsFullException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Hand implements Comparable<Hand> {

    Logger logger = LoggerFactory.getLogger(Game.class.getName());

    private Card[] cards = null;
    private Card[] combination = null;


    public Hand() {
        cards = new Card[5];
    }

    public Hand(Card[] cards) {
        this.cards = cards;
    }

    public Card addCard(Card card) throws HandIsFullException {
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] == null) {
                cards[i] = card;
                return card;
            }
        }
        throw new HandIsFullException();
    }

    public void clear() {
        cards = new Card[5];
    }

    @Override
    public int compareTo(Hand hand) {
        //returns a negative integer, zero, or a positive integer as its rank value
        //is less than, equal to, or greater than the specified object.
        return (this.findHandValue() - hand.findHandValue());
    }

    //printing cards in hand
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(" {");
        for (Card card : cards) {
            result.append(card.toString());
        }
        result.append("} ");

        return result.toString();
    }

    public int findHandValue() {

        CardRank cardRank = null;

        combination = findRoyalFlashCards();
        if (combination != null) {
            logger.trace("ROYAL_FLUSH");
            return HandRank.ROYAL_FLUSH.getValue();
        }

        combination = findStrightFlashCards();
        if (combination != null) {
            logger.trace("STRAIGHT_FLUSH");
            cardRank = findHighestCardRank(combination);
            return HandRank.STRAIGHT_FLUSH.getValue() + cardRank.getValue();
        }

        combination = findFourOfKindCards();
        if (combination != null) {
            logger.trace("FOUR_OF_A_KIND");
            cardRank = findHighestCardRank(combination);
            return HandRank.FOUR_OF_A_KIND.getValue() + cardRank.getValue();
        }

        // @TODO in this case max card finding will be changed.
        combination = findFullHouseCards();
        if (combination != null) {
            logger.trace("FULL_HOUSE");
            cardRank = findHighestCardRank(combination);
            return HandRank.FULL_HOUSE.getValue() + cardRank.getValue();
        }

        combination = findFlushCards();
        if (combination != null) {
            logger.trace("FLUSH");
            cardRank = findHighestCardRank(combination);
            return HandRank.FLUSH.getValue() + cardRank.getValue();
        }

        combination = findStraightCards();
        if (combination != null) {
            logger.trace("STRAIGHT");
            cardRank = findHighestCardRank(combination);
            return HandRank.STRAIGHT.getValue() + cardRank.getValue();
        }

        combination = findThreeOfKindCards();
        if (combination != null) {
            logger.trace("THREE_OF_A_KIND");
            cardRank = findHighestCardRank(combination);
            return HandRank.THREE_OF_A_KIND.getValue() + cardRank.getValue();
        }

        combination = findTwoPairsCards();
        if (combination != null) {
            logger.trace("TWO_PAIRS");
            cardRank = findHighestCardRank(combination);
            return HandRank.TWO_PAIRS.getValue() + cardRank.getValue();
        }

        combination = findOnePairCards(this.cards);
        if (combination != null) {
            logger.trace("ONE_PAIR");
            cardRank = findHighestCardRank(combination);
            return HandRank.ONE_PAIR.getValue() + cardRank.getValue();
        }

        cardRank = findHighestCardRank(cards);
        return HandRank.HIGH_CARD.getValue() + cardRank.getValue();
    }

    private CardRank findHighestCardRank(Card[] cards) {
        Arrays.sort(cards);
        return cards[cards.length - 1].getRank();
    }

    private Card[] findRoyalFlashCards() {
        Card[] combination;
        for (Suit suit : Suit.values()) {
            combination = findRoyalFlashCards(suit);
            if (combination != null) {
                return combination;
            }
        }
        return null;
    }

    private Card[] findRoyalFlashCards(Suit s) {
        List<Card> list = new ArrayList<>();
        for (Card card : cards) {
            if (card.getSuit() == s) {
                list.add(card);
            }
        }
        if (list.contains(new Card(CardRank.TEN, s)) &&
                list.contains(new Card(CardRank.JACK, s)) &&
                list.contains(new Card(CardRank.QUEEN, s)) &&
                list.contains(new Card(CardRank.KING, s)) &&
                list.contains(new Card(CardRank.ACE, s))) {
            return list.toArray(new Card[0]);
        }

        return null;
    }

    private Card[] findStrightFlashCards() {
        Card[] combination;
        for (Suit suit : Suit.values()) {
            combination = findStrightFlashCards(suit);
            if (combination != null) {
                return combination;
            }
        }
        return null;
    }

    private Card[] findStrightFlashCards(Suit s) {
        List<Card> list = new ArrayList<>();
        for (Card card : cards) {
            if (card.getSuit() == s) {
                list.add(card);
            }
        }

        Collections.sort(list);

        if (list.size() < 5) {
            return null;
        }
        for (int i = 0; i < 4; i++) {
            if (list.get(i).getRank().getValue() != list.get(i + 1).getRank().getValue() - 1) {
                return null;
            }
        }

        return list.toArray(new Card[0]);
    }

    private Card[] findFourOfKindCards() {
        Card[] combination;
        for (CardRank rank : CardRank.values()) {
            combination = findFourOfKindCards(rank);
            if (combination != null) {
                return combination;
            }
        }
        return null;
    }

    private Card[] findFourOfKindCards(CardRank r) {
        List<Card> list = new ArrayList<>();
        for (Card card : cards) {
            if (card.getRank() == r) {
                list.add(card);
            }
        }

        Collections.sort(list);

        if (list.size() < 4) {
            return null;
        }

        return list.toArray(new Card[0]);
    }

    private Card[]   findFullHouseCards() {
        Card[] threeKind = findThreeOfKindCards();
        if (threeKind == null) {
            return null;
        }
        Card[] remainderCards = getReminder(threeKind);
        Card[] pair = findOnePairCards(remainderCards);
        if (pair == null) {
            return null;
        }

        return threeKind;
    }

    private Card[] findFlushCards() {
        Card[] combination;
        for (Suit suit : Suit.values()) {
            combination = findFlushCards(suit);
            if (combination != null) {
                return combination;
            }
        }
        return null;
    }

    private Card[] findFlushCards(Suit s) {
        List<Card> list = new ArrayList<>();
        for (Card card : cards) {
            if (card.getSuit() == s) {
                list.add(card);
            }
        }
        Collections.sort(list);

        if (list.size() < 5) {
            return null;
        }
        return list.toArray(new Card[0]);
    }

    private Card[] findStraightCards() {
        for (int i = 0; i < 4; i++) {
            if (cards[i].getRank().getValue() != cards[i + 1].getRank().getValue() - 1) {
                return null;
            }
        }

        return cards;
    }

    private Card[] findThreeOfKindCards() {
        Card[] combination;
        for (CardRank rank : CardRank.values()) {
            combination = findThreeOfKindCards(rank);
            if (combination != null) {
                return combination;
            }
        }
        return null;
    }

    private Card[] findThreeOfKindCards(CardRank r) {
        List<Card> list = new ArrayList<>();
        for (Card card : cards) {
            if (card.getRank() == r) {
                list.add(card);
            }
        }

        Collections.sort(list);

        if (list.size() < 3) {
            return null;
        }

        return list.toArray(new Card[0]);
    }

    private Card[] findTwoPairsCards() {
        Card[] firstPair = findOnePairCards(cards);
        if (firstPair == null) {
            return null;
        }
        Card[] remainderCards = getReminder(firstPair);
        Card[] secondPair = findOnePairCards(remainderCards);
        if (secondPair == null) {
            return null;
        }

        if (firstPair[0].compareTo(secondPair[0]) > 0) {
            return firstPair;
        } else {
            return secondPair;
        }
    }

    private Card[] findOnePairCards(Card[] cards) {
        Card[] combination;
        for (CardRank rank : CardRank.values()) {
            combination = findOnePairCards(rank, cards);
            if (combination != null) {
                return combination;
            }
        }
        return null;
    }

    private Card[] findOnePairCards(CardRank r, Card[] cards) {
        List<Card> list = new ArrayList<>();
        for (Card card : cards) {
            if (card.getRank() == r) {
                list.add(card);
            }
        }

        Collections.sort(list);

        if (list.size() < 2) {
            return null;
        }

        return list.toArray(new Card[0]);
    }

    private Card[] getReminder(Card[] combination) {
        List<Card> reminder = new ArrayList<>();

        boolean found;
        for (Card card : cards) {
            found = false;
            for (Card combCard : combination) {
                if (combCard.equals(card)) {
                    found = true;
                }
            }
            if (!found) {
                reminder.add(card);
            }
        }

        return reminder.toArray(new Card[0]);
    }


}
