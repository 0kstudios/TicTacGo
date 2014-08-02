package com.kevin.ultimatet3;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by student on 7/28/2014.
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    public int[][][][] squareImageIds;
    private int squareWidth;
    private static final int[] imageIds = {
            R.drawable.free_square,
            R.drawable.free_square_dark,
            R.drawable.o_square,
            R.drawable.o_square_dark,
            R.drawable.x_square,
            R.drawable.x_square_dark,
            R.drawable.free_square_o_win,
            R.drawable.free_square_o_win_dark,
            R.drawable.o_square_o_win,
            R.drawable.o_square_o_win_dark,
            R.drawable.x_square_o_win,
            R.drawable.x_square_o_win_dark,
            R.drawable.free_square_x_win,
            R.drawable.free_square_x_win_dark,
            R.drawable.o_square_x_win,
            R.drawable.o_square_x_win_dark,
            R.drawable.x_square_x_win,
            R.drawable.x_square_x_win_dark,
            R.drawable.highlight_square,
            R.drawable.highlight_square
    };

    public ImageAdapter(Context c, int w) {
        context = c;
        squareWidth = w;
        squareImageIds = new int[3][3][3][3];
    }

    @Override
    public int getCount() {
        return 81;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(squareWidth - 2, squareWidth - 2));
        } else {
            imageView = (ImageView) convertView;
        }

        TTGGame.Dimensions dim = TTGGame.convertPosToCo(position);

        int imageId = squareImageIds[dim.bx][dim.by][dim.cx][dim.cy];

        if(dim.bx == 1 && dim.by == 0 || dim.bx == 1 && dim.by == 2 || dim.bx == 0 && dim.by == 1 || dim.bx == 2 && dim.by == 1) {
            imageId++;
        }

        imageView.setImageResource(imageIds[imageId]);

        return imageView;
    }

    public void updateSquareImageIds(int[][][][] gameboard, TTGGame.Dimensions lastMove, int[][] subboard) {
        for(int i = 0; i < gameboard.length; i++) {
            for(int j = 0; j < gameboard[i].length; j++) {
                for(int k = 0; k < gameboard[i][j].length; k++) {
                    for(int l = 0; l < gameboard[i][j][k].length; l++) {
                        int gameboardSquare = gameboard[i][j][k][l];
                        int subboardSquare = subboard[i][j];
                        if(subboardSquare > 0) {
                            if(subboardSquare == 1){
                                gameboardSquare += 3;
                            } else {
                                gameboardSquare += 6;
                            }
                        }

                        if(gameboardSquare == 0 && (i == lastMove.cx && j == lastMove.cy)) {
                            gameboardSquare = 9;
                        }

                        squareImageIds[i][j][k][l] = gameboardSquare * 2;
                    }
                }
            }
        }
    }

}
