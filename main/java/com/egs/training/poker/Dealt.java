package com.egs.training.poker;

import com.egs.training.logger.Logger;
import com.egs.training.logger.LoggerFactory;
import com.egs.training.poker.exception.DuplicateCardException;
import com.egs.training.poker.exception.InvalidFileLineException;
import com.egs.training.poker.util.StringUtil;

import java.util.Arrays;

public class Dealt {

    private Logger logger = LoggerFactory.getLogger(Dealt.class.getName());

    public Dealt(String line, Player player1, Player player2) throws InvalidFileLineException {
        char[][] cards = null;
//        try {
            logger.trace("Processing line: "+line);
            cards = StringUtil.fileLineToChars(line);
            if(cards.length != 10) {
                logger.warn("There is invalid line in file: "+line);
                throw new InvalidFileLineException();
            }
//        } catch (Exception e) {
//            logger.warn("There is invalid line in file: "+line);
//        }

        player1.getHand().clear();
        player2.getHand().clear();

        Card[] cardsSet = new Card[10];
        Card card;

        for (int i = 0; i < 5; i++) {
            card = new Card(cards[i][0], cards[i][1]);
            cardsSet[i] = card;
            player1.getHand().addCard( card );
            card = new Card(cards[5+i][0], cards[5+i][1]);
            cardsSet[5+i] = card;
            player2.getHand().addCard( card );
        }

        validate(cardsSet);

    }

    public void validate(Card[] cards) throws InvalidFileLineException {
        Arrays.sort(cards);

        if (cards[0].equals(cards[1])) {
            throw new DuplicateCardException(cards[0].toString());
        }

        if (cards[cards.length-2].equals(cards[cards.length-1])) {
            throw new DuplicateCardException(cards[cards.length-2].toString());
        }

        for (int i = 1; i < cards.length - 1; i++) {
            if (cards[i].equals(cards[i+1])) {
                throw new DuplicateCardException(cards[i].toString());
            }
        }
    }

    public void process(Player player1, Player player2) {

        String dealtStr = player1.getHand()+" "+player2.getHand();

        int comparing = player1.getHand().compareTo(player2.getHand());

        if (comparing > 0) {
            player1.incrementWinCount();
            logger.info( dealtStr + " player1 ("+player1.getHand().findHandValue()+" vs "+
                    player2.getHand().findHandValue()+")" );
        } else if (comparing < 0) {
            player2.incrementWinCount();
            logger.info( dealtStr + " player2 ("+player1.getHand().findHandValue()+" vs "+
                    player2.getHand().findHandValue()+")" );
        } else {
            logger.info(dealtStr + " none ("+player1.getHand().findHandValue()+" vs "+
            player2.getHand().findHandValue()+")");
        }

    }

}
