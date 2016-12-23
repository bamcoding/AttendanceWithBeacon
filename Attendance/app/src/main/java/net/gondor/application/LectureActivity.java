package net.gondor.application;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
    private LectureItem lectureItem;
    private LectureItemAdapter lectureAdapter;
    private List<LectureItem> lectureItemList;
    private String userInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_list);
    }


    @Override
    protected void onResume() {
        super.onResume();

        //리스트뷰 참조.
        final ListView listView = (ListView)findViewById(R.id.listview1) ;
        lectureItemList = new ArrayList<LectureItem>();

        //로그인 정보를 가져온다.
        userInfo = getIntent().getStringExtra("userInfo");

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
                        Log.d("master","Sent a UserId, for Lectures");
                        return post.getBody();
                    }

                    //콜백기능
                    protected void onPostExecute(String s) {
                        Log.d("master","Callback String : "+ s);
                        if (s.equals("") || s.length() == 0 || s == null) {
                            Toast.makeText(LectureActivity.this, "your lecture is none", Toast.LENGTH_SHORT).show();
                            Log.i("master","Your Lecture is None.");
                        }
                        else {
                            Log.i("master","Get your LectureInfo");
                            JSONArray jsonArray = null;
                            try {
                                jsonArray = new JSONArray(s);
                                int countLectures = jsonArray.length();
                                Log.d("master","The Count Of Your Lectures : "+jsonArray.length()+"================");

                                for(int i = 0; i < countLectures; i++){
                                    Log.d("master", "- "+jsonArray.getJSONObject(i).getString("lectureName")+
                                            "("+jsonArray.getJSONObject(i).getJSONObject("instructor").getJSONObject("user").getString("userName")+"): "+
                                            jsonArray.getJSONObject(i).getString("lectureContent"));
                                    Log.d("master", "- "+jsonArray.getJSONObject(i).getString("beaconId")+"("+jsonArray.getJSONObject(i).getInt("beaconMajor")+
                                            ", "+jsonArray.getJSONObject(i).getInt("beaconMinor")+")");

                                    lectureItem = new LectureItem();
                                    lectureItem.setLectureId(jsonArray.getJSONObject(i).getString("id"));
                                    lectureItem.setLectureTitle(jsonArray.getJSONObject(i).getString("lectureName"));
                                    lectureItem.setLectureDesc(jsonArray.getJSONObject(i).getString("lectureContent"));
                                    lectureItem.setLectureTeacher(jsonArray.getJSONObject(i).getJSONObject("instructor").getJSONObject("user").getString("userName"));
                                    lectureItem.setBeaconId(jsonArray.getJSONObject(i).getString("beaconId"));
                                    lectureItem.setBeaconMajor(jsonArray.getJSONObject(i).getInt("beaconMajor"));
                                    lectureItem.setBeaconMinor(jsonArray.getJSONObject(i).getInt("beaconMinor"));
                                    lectureItem.setEnterTime("0900");
                                    lectureItem.setExitTime("1800");
                                    lectureItemList.add(lectureItem);

                                }
                                lectureAdapter = new LectureItemAdapter(lectureItemList, LectureActivity.this);
                                listView.setAdapter(lectureAdapter);

                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        LectureItem item = lectureAdapter.itemList.get(position);
                                        Toast.makeText(LectureActivity.this, "강의 이름 - "+item.getLectureTitle(), Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(LectureActivity.this, BeaconLaderActivity.class);
                                        //attentdance 정보를 넘겨준다.
                                        intent.putExtra("lectureId",item.getLectureId());
                                        intent.putExtra("beaconId",item.getBeaconId());
                                        intent.putExtra("beaconMajor", item.getBeaconMajor());
                                        intent.putExtra("beaconMinor", item.getBeaconMinor());
                                        intent.putExtra("userInfo", userInfo);
                                        startActivity(intent);
                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }.execute(constants.GET_LECTURES_URL);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 커스텀 아답터
     */
    private class LectureItemAdapter extends BaseAdapter {
        private Context context;
        private List<LectureItem> itemList = new ArrayList<LectureItem>();

        public LectureItemAdapter(List<LectureItem> lectureItemList, Context context) {
            this.itemList = lectureItemList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;

            // "listview_item" Layout을 inflate하여 convertView 참조 획득.
            if (convertView == null) {
                holder = new Holder();

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_lecture, parent, false);

                holder.lectureTitle = (TextView) convertView.findViewById(R.id.lectureTitle);
                holder.lectureDesc = (TextView) convertView.findViewById(R.id.lectureDesc);
                holder.lectureTeacher = (TextView) convertView.findViewById(R.id.lectureTeacher);
                holder.enterTime = (TextView) convertView.findViewById(R.id.enterTime);
                holder.exitTime = (TextView) convertView.findViewById(R.id.exitTime);

                convertView.setTag(holder);
            }
            else {
                holder = (Holder) convertView.getTag();
            }

            // 레이아웃에 있는 컴포넌트를 참조
            // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
            LectureItem mData = itemList.get(position);

            holder.lectureTitle.setText(mData.getLectureTitle());
            holder.lectureDesc.setText(mData.getLectureDesc());
            holder.lectureTeacher.setText(mData.getLectureTeacher());
            holder.enterTime.setText(mData.getEnterTime());
            holder.exitTime.setText(mData.getExitTime());

            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return itemList.get(position);
        }

        public class Holder {
            public TextView lectureTitle;
            public TextView lectureDesc;
            public TextView lectureTeacher;
            public TextView enterTime;
            public TextView exitTime;
        }
    }

}