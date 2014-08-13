package com.kevin.ultimatet3;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GameActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    ImageView ivPlayerSymbol1, ivPlayerSymbol2, bCommit, bBack1;
    LinearLayout loGameTitle;
    GridView vGameBoard;
    ImageAdapter imageAdapter;

    TTGGame ttgGame;
    TTGGame.Dimensions dim;
    ImageView previousView;
    TTGGame.Dimensions previousDim;

    DisplayMetrics metrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initialize();
    }

    private void initialize() {
        metrics = getResources().getDisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        /*TTGGame.Dimensions tempDim;
        if(SPrefs.getInt(getApplicationContext(), "bx") == -256) {
            tempDim = null;
        } else {
            tempDim = new TTGGame.Dimensions(SPrefs.getInt(getApplicationContext(), "bx"), SPrefs.getInt(getApplicationContext(), "by"), SPrefs.getInt(getApplicationContext(), "cx"), SPrefs.getInt(getApplicationContext(), "cy"));
        }
        ttgGame = new TTGGame(SPrefs.get4dimIntArray(getApplicationContext(), "gameboard"), SPrefs.get2dimIntArray(getApplicationContext(), "subboard"), SPrefs.getBool(getApplicationContext(), "blueTurn"), SPrefs.getBool(getApplicationContext(), "firstTurn"), SPrefs.getBool(getApplicationContext(), "win"), SPrefs.getBool(getApplicationContext(), "blueWin"), tempDim);
        dim = null;
        previousView = null;

        System.out.println(SPrefs.getBool(getApplicationContext(), "blueTurn") + "");
        */
        ttgGame = new TTGGame();

        loGameTitle = (LinearLayout) findViewById(R.id.loGameTitle);
        ivPlayerSymbol1 = (ImageView) findViewById(R.id.ivPlayerSymbol1);
        ivPlayerSymbol2 = (ImageView) findViewById(R.id.ivPlayerSymbol2);
        vGameBoard = (GridView) findViewById(R.id.vGameboard);
        imageAdapter = new ImageAdapter(this, getGameboardDimensions());
        bCommit = (ImageView) findViewById(R.id.bCommit);
        bBack1 = (ImageView) findViewById(R.id.bBack1);

        vGameBoard.setNumColumns(9);
        vGameBoard.setBackgroundColor(Color.BLACK);
        vGameBoard.setVerticalSpacing(2);
        vGameBoard.setHorizontalSpacing(2);
        vGameBoard.setAdapter(imageAdapter);

        loGameTitle.getLayoutParams().height = metrics.heightPixels/11;

        ivPlayerSymbol1.setImageResource(R.drawable.o_square_o_win);
        ivPlayerSymbol2.setImageResource(R.drawable.x_symbol);

        bCommit.setOnClickListener(this);
        bBack1.setOnClickListener(this);
        vGameBoard.setOnItemClickListener(this);
    }

    public int getGameboardDimensions() {
        int gameboardHeight = metrics.heightPixels;
        int gameboardWidth = metrics.widthPixels;

        if(gameboardWidth > gameboardHeight) {
            return gameboardHeight / 9;
        } else {
            return gameboardWidth / 9;
        }
    }

    public void commitToSPrefs() {
        SPrefs.set4dimIntArray(this, "gameboard", ttgGame.gameboard);
        SPrefs.set2dimIntArray(this, "subboard", ttgGame.subboard);
        SPrefs.setBool(this, "blueTurn", ttgGame.blueTurn);
        SPrefs.setBool(this, "firstTurn", ttgGame.firstTurn);
        SPrefs.setBool(this, "win", ttgGame.win);
        SPrefs.setBool(this, "blueWin", ttgGame.blueWin);
        SPrefs.setInt(this, "bx", ttgGame.lastMove.bx);
        SPrefs.setInt(this, "by", ttgGame.lastMove.by);
        SPrefs.setInt(this, "cx", ttgGame.lastMove.cx);
        SPrefs.setInt(this, "cy", ttgGame.lastMove.cy);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bCommit:
                if(dim != null) {
                    int bx = dim.bx;
                    int by = dim.by;
                    int cx = dim.cx;
                    int cy = dim.cy;
                    dim = null;
                    previousView = null;
                    ttgGame.commit(bx, by, cx, cy);

                    commitToSPrefs();

                    imageAdapter.updateSquareImageIds(ttgGame.gameboard, ttgGame.lastMove, ttgGame.subboard);
                    vGameBoard.setAdapter(imageAdapter);

                    if(ttgGame.win) {
                        int symbol;
                        if(ttgGame.blueWin) {
                            symbol = R.drawable.o_symbol;
                        } else {
                            symbol = R.drawable.x_symbol;
                        }
                        Bundle bundle = new Bundle();
                        bundle.putInt("symbol", symbol);
                        Intent intent = new Intent(this, KevinWinsActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    if(ttgGame.blueTurn) {
                        ivPlayerSymbol1.setImageResource(R.drawable.o_square_o_win);
                        ivPlayerSymbol2.setImageResource(R.drawable.x_symbol);
                    } else {
                        ivPlayerSymbol1.setImageResource(R.drawable.o_symbol);
                        ivPlayerSymbol2.setImageResource(R.drawable.x_square_x_win);
                    }
                }
                break;
            case R.id.bBack1:
                Intent intent = new Intent(this, StartActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(ttgGame.firstTurn) {
            TTGGame.Dimensions tempDim = TTGGame.convertPosToCo(position);

            if(ttgGame.gameboard[tempDim.bx][tempDim.by][tempDim.cx][tempDim.cy] == 0) {
                if(previousView != null && (previousDim.bx == 1 && previousDim.by == 0 || previousDim.bx == 1 && previousDim.by == 2 || previousDim.bx == 0 && previousDim.by == 1 || previousDim.bx == 2 && previousDim.by == 1)) {
                    previousView.setImageResource(R.drawable.free_square_dark);
                } else if(previousView != null) {
                    previousView.setImageResource(R.drawable.free_square);
                }

                int imageId;
                if(ttgGame.blueTurn) {
                    imageId = R.drawable.o_square_clicked;
                } else {
                    imageId = R.drawable.x_square_clicked;
                }
                dim = TTGGame.convertPosToCo(position);

                ImageView imageView = (ImageView) view;
                imageView.setImageResource(imageId);

                previousView = imageView;
                previousDim = dim;
            }
        } else {
            TTGGame.Dimensions tempDim = TTGGame.convertPosToCo(position);

            if(tempDim.bx == ttgGame.lastMove.cx && tempDim.by == ttgGame.lastMove.cy) {
                if(ttgGame.gameboard[tempDim.bx][tempDim.by][tempDim.cx][tempDim.cy] == 0) {
                    if(previousView != null) {
                        previousView.setImageResource(R.drawable.highlight_square);
                    }

                    int imageId;
                    if(ttgGame.blueTurn) {
                        imageId = R.drawable.o_square_clicked;
                    } else {
                        imageId = R.drawable.x_square_clicked;
                    }
                    dim = TTGGame.convertPosToCo(position);

                    ImageView imageView = (ImageView) view;
                    imageView.setImageResource(imageId);

                    previousView = imageView;
                    previousDim = dim;
                }
            }
        }
    }
}
