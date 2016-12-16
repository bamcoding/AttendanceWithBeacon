package net.gondor.application;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import net.gondor.application.listView.LectureItem;
import net.gondor.application.listView.LectureItemAdapter;
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
    private List<LectureItem> lectureItemList = new ArrayList<LectureItem>();
    private LectureItem lectureItem = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_list);

        //리스트뷰 참조.
        final ListView listView = (ListView)findViewById(R.id.listview1) ;

        //로그인 정보를 가져온다.
        String userInfo = userInfo = getIntent().getStringExtra("userInfo");

        if(userInfo.length() == 0 || userInfo.equals("") || userInfo == null ){
            Log.d("master","Fail to get a userInfo on Lecture_Activity");
        }else{
            Log.d("master","Success to get a userInfo on Lecture_Activity");
                //인트로 액티비티를 죽인다.
            IntroActivity introActivity = (IntroActivity)IntroActivity.introActivity;
            introActivity.finish();

            try {
                //로그인 정보를 json객체로 바꾼다.
                JSONObject json = new JSONObject(userInfo);
                final String jsonUserId = json.getString("userId");

                //DB에서 리스트를 가져와서 하나씩 추가한다.
                new AsyncTask<String, Void, String>() {
                    //Controller로 userId를 보낸다.
                    @Override
                    protected String doInBackground(String... params) {
                        HttpClient.Builder builder = new HttpClient.Builder("POST", params[0]);
                        builder.addOrReplaceParameter("userId", jsonUserId);
                        HttpClient post = builder.create();
                        post.request();
                        Log.d("master","Sent a UserId, For Lectures ");
                        return post.getBody();
                    }

                    //콜백기능
                    protected void onPostExecute(String s) {
                        Log.d("master","Lecture_Callback Works"+ s);
                        if (s.equals("") || s.length() == 0 || s == null) {
                            Toast.makeText(LectureActivity.this, "your lecture is none", Toast.LENGTH_SHORT).show();
                            Log.i("master","your lecture is none.");
                        }
                        else {
                            Log.i("master","get your lecture info.");
                            JSONArray jsonArray = null;
                            try {
                                jsonArray = new JSONArray(s);
                                int countLectures = jsonArray.length();
                                Log.d("master","the count of your lectures : "+jsonArray.length());

                                for(int i = 0; i < countLectures; i++){
                                    Log.d("master", jsonArray.getJSONObject(i).getString("lectureName"));
                                    Log.d("master", jsonArray.getJSONObject(i).getString("lectureContent"));
                                    Log.d("master", jsonArray.getJSONObject(i).getJSONObject("instructor").getJSONObject("user").getString("userName"));
                                    lectureItem = new LectureItem();
                                    lectureItem.setLectureTitle(jsonArray.getJSONObject(i).getString("lectureName"));
                                    lectureItem.setLectureDesc(jsonArray.getJSONObject(i).getString("lectureContent"));
                                    lectureItem.setLectureTeacher(jsonArray.getJSONObject(i).getJSONObject("instructor").getJSONObject("user").getString("userName"));
                                    lectureItem.setEnterTime("0900");//
                                    lectureItem.setExitTime("1800");
                                    lectureItemList.add(lectureItem);
                                }
                                LectureItemAdapter adapter = new LectureItemAdapter(lectureItemList, LectureActivity.this);
                                listView.setAdapter(adapter);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }.execute(constants.GET_LECTURES_URL);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("master","item is clicked");
                    Toast.makeText(LectureActivity.this, "담당 비콘 인식을 시작합니다.", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}