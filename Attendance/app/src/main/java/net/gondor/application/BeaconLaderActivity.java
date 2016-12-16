package net.gondor.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import gondor.net.attendance.R;

/**
 * Created by owner on 2016-12-16.
 */

public class BeaconLaderActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_lader);

        String beaconId = getIntent().getStringExtra("beaconId");

    }
}
