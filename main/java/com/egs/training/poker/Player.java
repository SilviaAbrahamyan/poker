package com.egs.training.poker;

public class Player {

    private Hand hand;
    private long winCount;

    public Player() {
        hand = new Hand();
        winCount = 0;
    }

    @Override
    public String toString() {
        return hand.toString();
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void incrementWinCount(){
        winCount++;
    }

    public long getWinCount() {
        return winCount;
    }

    public void setWinCount(long winCount) {
        this.winCount = winCount;
    }
}
