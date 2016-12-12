package net.gondor.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import gondor.net.attendance.R;

/**
 * Created by 206-017 on 2016-12-12.
 */

public class IntroActivity extends Activity{

    TextView explain;
    public static Activity introActivity;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);

        introActivity = IntroActivity.this;
        explain = (TextView)findViewById(R.id.cover_explain);
        explain.setText("Poject Ver0.1");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(IntroActivity.this, PopUpSignIn.class);
                startActivity(intent);
            }
        }, 2000);
    }
}

