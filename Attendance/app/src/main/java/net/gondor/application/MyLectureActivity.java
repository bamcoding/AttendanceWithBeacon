package net.gondor.application;

import android.app.Activity;
import android.os.Bundle;

import gondor.net.attendance.R;

/**
 * Created by 206-017 on 2016-12-12.
 */

public class MyLectureActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylecture_activity);

        IntroActivity introActivity = (IntroActivity)IntroActivity.introActivity;
        introActivity.finish();

    }
}
