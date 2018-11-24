package com.egs.training.poker.util;

import com.egs.training.poker.Card;
import com.egs.training.poker.exception.InvalidFileLineException;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    public static char[][] fileLineToChars(String line) {
        line = line.replaceAll("\\s{2,}", " ").trim();
        String[] cardStrs = line.split("\\s+");

        //char[][] result = new char[10][2];
        List<char[]> reslutList = new ArrayList<>();

        for (int i = 0; i < cardStrs.length; i++) {
            char[] item = new char[2];
            item[0] = cardStrs[i].charAt(0);
            item[1] = cardStrs[i].charAt(1);

            reslutList.add(item);
        }

        return reslutList.toArray(new char[2][0]);
    }

}
