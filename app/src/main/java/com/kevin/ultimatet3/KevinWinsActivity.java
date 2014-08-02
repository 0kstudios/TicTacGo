package com.kevin.ultimatet3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kevin.ultimatet3.R;

public class KevinWinsActivity extends Activity implements View.OnClickListener {
    TextView tvWinName;
    ImageView ivWinSymbol;
    ImageView bBack2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kevin_wins);

        initialize();
    }

    private void initialize() {
        tvWinName = (TextView) findViewById(R.id.tvWinName);
        ivWinSymbol = (ImageView) findViewById(R.id.ivWinSymbol);
        bBack2 = (ImageView) findViewById(R.id.bBack2);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        ivWinSymbol.setImageResource(bundle.getInt("symbol"));

        bBack2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bBack2:
                Intent intent = new Intent(this, StartActivity.class);
                startActivity(intent);
                break;
        }
    }
}
