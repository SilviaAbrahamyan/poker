package com.egs.training.poker;

import com.egs.training.logger.Logger;
import com.egs.training.logger.LoggerFactory;
import com.egs.training.poker.exception.InvalidFileLineException;

import java.io.*;

public class Game {

    Logger logger = LoggerFactory.getLogger(Game.class.getName());

    private Player player1;
    private Player player2;

    private long dealtCount;

    public Game () {
        player1 = new Player();
        player2 = new Player();
    }

    public void process() {

        BufferedReader reader;
        InputStream input;

        try {
            input = Game.class.getClassLoader().getResourceAsStream("poker.txt");
            if (input == null) {
                throw new FileNotFoundException();
            }
            reader = new BufferedReader(new InputStreamReader(input));

            Dealt dealt;

            String line = reader.readLine();
            while (line != null) {

//                System.out.println(line);
                try {
                    dealt = new Dealt(line, player1, player2);
                    dealt.process(player1, player2);
                    dealtCount++;
                } catch (Exception e) {
                    logger.warn("Skipping invalid line: "+line);
                }

                // read next line
                line = reader.readLine();
            }
            reader.close();

        } catch (IOException ioe) {
            logger.error("Unable to read poker.txt file.", ioe);
        }

    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public long getDealtCount() {
        return dealtCount;
    }

    public void setDealtCount(long dealtCount) {
        this.dealtCount = dealtCount;
    }


    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Game.class.getName());
        logger.info(" --- Starting to analyze file --- ");

        Game game = new Game();
        game.process();

        String result = " --- Analyzing finished --- ";

        result += "\n Total dealts count: "+game.getDealtCount();
        result += "\n Player1 wins: "+game.getPlayer1().getWinCount();
        result += "\n Player2 wins: "+game.getPlayer2().getWinCount();
        result += "\n Draws: "+(game.getDealtCount() -
                (game.getPlayer1().getWinCount() + game.getPlayer2().getWinCount()));


        logger.info(result);

    }
}
