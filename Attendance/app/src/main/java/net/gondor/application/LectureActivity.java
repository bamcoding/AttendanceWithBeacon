package net.gondor.application;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import net.gondor.common.HttpClient;
import net.gondor.common.constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gondor.net.attendance.R;

/**
 * Created by 206-017 on 2016-12-12.
 */

public class LectureActivity extends Activity {
    private String userInfo = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_list);

        //리스뷰 참조.
        ListView listView = (ListView)findViewById(R.id.listview1) ;

        //로그인 정보를 가져온다.
        userInfo = getIntent().getStringExtra("userInfo");
        Toast.makeText(LectureActivity.this, userInfo, Toast.LENGTH_SHORT).show();

        //인트로 액티비티를 죽인다.
        IntroActivity introActivity = (IntroActivity)IntroActivity.introActivity;
        introActivity.finish();


        try {
            //로그인 정보를 json객체로 바꾼다.
            JSONObject json = new JSONObject(userInfo);
            final String jsonUserId = json.getString("userId");
            List<LectureItem> lectureItemList = new ArrayList<LectureItem>();
            LectureItem lectureItem = new LectureItem();

            lectureItem.setLectureTitle("hello");
            lectureItem.setLectureDesc("hellohellohello");
            lectureItem.setLectureTeacher("lee");
            lectureItem.setEnterTime("0900");//
            lectureItem.setExitTime("1800");
            lectureItem.setEnterBtn("Enter1");
            lectureItem.setExitBtn("Exit1");
            lectureItemList.add(lectureItem);

            LectureItemAdapter adapter = new LectureItemAdapter(lectureItemList, this);
            listView.setAdapter(adapter);

            //DB에서 리스트를 가져와서 하나씩 추가한다.
            new AsyncTask<String, Void, String>() {
                @Override
                protected String doInBackground(String... params) {
                    HttpClient.Builder builder = new HttpClient.Builder("POST", params[0]);
                    builder.addOrReplaceParameter("userId", jsonUserId);
                    HttpClient post = builder.create();
                    post.request();
                    Log.i("master","for lectures, sent a userId");
                    return post.getBody();
                }

                //콜백기능
                protected void onPostExecute(String s) {
                    if (s != null) {
                        try {
                            JSONArray jsonArray = new JSONArray(s);


                            Toast.makeText(LectureActivity.this, "Success !!!"+jsonArray.getJSONObject(0).get("userId"), Toast.LENGTH_SHORT).show();
                            Log.i("master", "callback : " + jsonArray.getJSONObject(0).get("userId"));



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        Toast.makeText(LectureActivity.this, "Fail...", Toast.LENGTH_SHORT).show();

                    }
                }
            }.execute(constants.GET_LECTURES_URL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
