package net.gondor.application;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;

import net.gondor.application.prepare.BeaconFinder;
import net.gondor.common.HttpClient;
import net.gondor.common.constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import gondor.net.attendance.R;

/**
 * Created by owner on 2016-12-16.
 */

public class BeaconLaderActivity extends Activity{
    private BeaconManager beaconManager;
    private Region region;

    private TextView nowDate;
    private TextView tvId;
    private TextView valueState;

    private String beaconId;
    private int beaconMajor;
    private int beaconMinor;
    private String lectureId;
    private String userInfo;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_lader);

        Log.d("master","BEACON Activity");
        beaconId = getIntent().getStringExtra("beaconId");
        Log.d("master","- beaconId : "+beaconId );
        beaconMajor = getIntent().getIntExtra("beaconMajor",0);
        Log.d("master","- beaconMajor : "+beaconMajor);
        beaconMinor = getIntent().getIntExtra("beaconMinor",0);
        lectureId = getIntent().getStringExtra("lectureId");
        Log.d("master","- lectureId : "+lectureId);
        userInfo = getIntent().getStringExtra("userInfo");
        Log.d("master","- userInfo : "+userInfo);

        JSONObject jsonUser = null;
        try {
            jsonUser = new JSONObject(userInfo);
            userId = (String) jsonUser.get("userId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("master","- userId : "+userId);

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
                    valueState.setText(currentTime + "");

                    //현재 시간
                    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA);
                    String currentDate = formatDate.format(new Date());
                    nowDate.setText(currentDate);

                    if(myBeaconValue>-80){

                    new AsyncTask<String, Void, String>() {
                        @Override
                        protected String doInBackground(String... params) {
                            HttpClient.Builder builder = new HttpClient.Builder("POST", params[0]);
                            builder.addOrReplaceParameter("userId", userId);
                            builder.addOrReplaceParameter("lectureId", lectureId);
                            HttpClient post = builder.create();
                            post.request();
                            return post.getBody();
                        }

                        //콜백기능
                        protected void onPostExecute(String s) {
                            if (s.equals("true")) {
                                Toast.makeText(BeaconLaderActivity.this, "비콘을 인식하였습니다.", Toast.LENGTH_SHORT).show();
                                Log.i("master", "Access to AttendanceList : TRUE");
                                finish();
                            } else {
                                Toast.makeText(BeaconLaderActivity.this, "비콘을 인식에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                Log.i("master", "Access to AttendanceList : FAIL");
                                finish();
                            }

                        }
                    }.execute(constants.CHECK_ATTENDANCE_URL);

                }

                }
            }
        });
        //비콘을 특정합니다.
        region = new Region("ranged region",
                UUID.fromString(beaconId), beaconMajor, beaconMinor);
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
