package net.gondor.application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;

import net.gondor.common.constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import gondor.net.attendance.R;

public class BeaconFinder extends AppCompatActivity {

    private BeaconManager beaconManager;
    private Region region;

    private TextView nowDate;
    private TextView tvId;
    private TextView valueState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_beacon);

        tvId = (TextView) findViewById(R.id.tvId);
        nowDate = (TextView) findViewById(R.id.nowDate);
        valueState = (TextView) findViewById(R.id.valueState);

        beaconManager = new BeaconManager(this);

        // add this below:
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    Beacon nearestBeacon = list.get(0);
                    Log.d("Airport", "Nearest places: " + nearestBeacon.getRssi());
                    int myBeaconValue = nearestBeacon.getRssi();
                    tvId.setText(myBeaconValue + "");

                    SimpleDateFormat formatTime = new SimpleDateFormat("HHmmss");
                    String time = formatTime.format(new Date());
                    int currentTime = Integer.parseInt(time);

                    valueState.setText(currentTime+"");
                    //체크 포인트
                    if(myBeaconValue >= -80){
                        if(constants.START_TIME >= currentTime){
                            Toast.makeText(BeaconFinder.this, "Attendance Complete !!!",Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else if(constants.FINISH_TIME <= currentTime){
                            Toast.makeText(BeaconFinder.this, "Please go home !!!",Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else {
                            Toast.makeText(BeaconFinder.this, "You are too late !!!",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                    //현재 시간
                    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA);
                    String currentDate = formatDate.format(new Date());
                    nowDate.setText(currentDate);
                }
            }
        });

        region = new Region("ranged region",
                UUID.fromString(constants.ROOM_204), null, null);
                //흰 비콘 아이디
    }

    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);

            }
        });
    }

    @Override
    protected void onPause() {
        beaconManager.stopRanging(region);
        super.onPause();
    }
}
