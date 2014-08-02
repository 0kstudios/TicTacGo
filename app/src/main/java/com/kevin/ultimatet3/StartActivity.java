package com.kevin.ultimatet3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;


public class StartActivity extends Activity implements View.OnClickListener {
    ImageView ivLogo, bStart;

    DisplayMetrics metrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initialize();

    }

    private void initialize() {
        metrics = getResources().getDisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        ivLogo = (ImageView) findViewById(R.id.ivLogo);
        bStart = (ImageView) findViewById(R.id.bStart);

        bStart.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bStart:
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                break;
            /*case R.id.bRestart:
                SPrefs.set4dimIntArray(getApplicationContext(), "gameboard", new int[3][3][3][3]);
                SPrefs.set2dimIntArray(getApplicationContext(), "subboard", new int[3][3]);
                SPrefs.setBool(getApplicationContext(), "blueTurn", true);
                SPrefs.setBool(getApplicationContext(), "firstTurn", true);
                SPrefs.setBool(getApplicationContext(), "win", false);
                SPrefs.setBool(getApplicationContext(), "blueWin", true);
                SPrefs.setInt(getApplicationContext(), "bx", -256);
                SPrefs.setInt(getApplicationContext(), "by", -256);
                SPrefs.setInt(getApplicationContext(), "cx", -256);
                SPrefs.setInt(getApplicationContext(), "cy", -256);
                break;
                */
        }
    }
}
