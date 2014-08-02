package com.kevin.ultimatet3;

/**
 * Created by student on 7/28/2014.
 */
public class TTGGame {
    public int[][][][] gameboard;
    public int[][] subboard;
    public boolean blueTurn;
    public boolean firstTurn;
    public boolean win;
    public boolean blueWin;
    public Dimensions lastMove;

    public static class Dimensions {
        int bx;
        int by;
        int cx;
        int cy;

        Dimensions(int w1, int h1, int w2, int h2) {
            bx = w1;
            by = h1;
            cx = w2;
            cy = h2;
        }
    }

    public TTGGame() {
        gameboard = new int[3][3][3][3];
        subboard = new int[3][3];
        blueTurn = true;
        firstTurn = true;
        win = false;
        lastMove = null;
    }

    public TTGGame(int[][][][] g, int[][] s, boolean bT, boolean fT, boolean w, boolean bW, Dimensions lM) {
        gameboard = g;
        subboard = s;
        blueTurn = bT;
        firstTurn = fT;
        win = w;
        blueWin = bW;
        lastMove = lM;
    }

    public void commit(int bx, int by, int cx, int cy) {
        if(firstTurn) {
            firstTurn = false;
        }

        if(blueTurn) {
            blueTurn = false;
            gameboard[bx][by][cx][cy] = 1;

        } else {
            blueTurn = true;
            gameboard[bx][by][cx][cy] = 2;

        }

        if(subboard[bx][by] == 0) {
            int score = checkForWin(gameboard[bx][by]);

            if(score != -256) {
                subboard[bx][by] = score;

                int gameScore = checkForWin(subboard);

                if(gameScore != -256) {
                    win = true;
                    if(gameScore == 1) {
                        blueWin = true;
                    } else {
                        blueWin = false;
                    }
                }
            }
        }


        lastMove = new Dimensions(bx, by, cx, cy);

        int sum = 0;

        for(int i = 0; i < gameboard[cx][cy].length; i++) {
            for(int j = 0; j < gameboard[cx][cy][i].length; j++) {
                if(gameboard[cx][cy][i][j] > 0) {
                    sum++;
                }
            }
        }

        if(sum > 8) {
            firstTurn = true;
        }
    }

    private int checkForWin(int[][] board) {
        for(int i = 0; i < 3; i++) {
            int firstSquare = board[i][0];
            if(firstSquare > 0 && board[i][1] == firstSquare && board[i][2] == firstSquare) {
                return firstSquare;
            }
        }
        for(int j = 0; j < 3; j++) {
            int firstSquare = board[0][j];
            if(firstSquare > 0 && board[1][j] == firstSquare && board[2][j] == firstSquare) {
                return firstSquare;
            }
        }
        {
            int firstSquare = board[0][0];
            if(firstSquare > 0 && board[1][1] == firstSquare && board[2][2] == firstSquare) {
                return firstSquare;
            }
        }
        {
            int firstSquare = board[2][0];
            if(firstSquare > 0 && board[1][1] == firstSquare && board[0][2] == firstSquare) {
                return firstSquare;
            }
        }
        return -256;
    }

    public static Dimensions convertPosToCo(int pos) {
        int posx = pos % 9;
        int posy = pos / 9;
        int bx = posx / 3;
        int by = posy / 3;
        int cx = posx % 3;
        int cy = posy % 3;

        return new Dimensions(bx, by, cx, cy);
    }

}
