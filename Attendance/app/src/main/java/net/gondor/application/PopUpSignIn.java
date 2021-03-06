package net.gondor.application;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.gondor.common.HttpClient;
import net.gondor.common.constants;

import org.json.JSONException;
import org.json.JSONObject;

import gondor.net.attendance.R;

/**
 * Created by 206-017 on 2016-12-12.
 */

public class PopUpSignIn extends Activity{
    private EditText userId, userPassword;
    private Button signBtn;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.signin_popup);

        userId = (EditText)findViewById(R.id.signIn_userId);
        userPassword = (EditText)findViewById(R.id.signIn_userPassword);
        signBtn = (Button)findViewById(R.id.signIn_btn);

        signBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String userId2 = userId.getText().toString();
                final String userPassword2 = userPassword.getText().toString();

                Log.i("master","Login Button has been Clicked");

                //null이면 메시지 출력
                if(userId2 == null || userId2.length() == 0 || userPassword2 == null || userPassword2.length() == 0){
                   Toast.makeText(PopUpSignIn.this, "Please enter the required input value correctly.", Toast.LENGTH_SHORT).show();
                }else {
                    new AsyncTask<String, Void, String>() {
                        @Override
                        protected String doInBackground(String... params) {
                            HttpClient.Builder builder = new HttpClient.Builder("POST", params[0]);
                            builder.addOrReplaceParameter("userId", userId2);
                            builder.addOrReplaceParameter("userPassword", userPassword2);
                            HttpClient post = builder.create();
                            post.request();
                            Log.d("master","userId : "+ userId2 +", userPassword : "+ userPassword2);
                            return post.getBody();
                        }

                        //콜백기능
                        protected void onPostExecute(String s) {
                            Log.d("master", "Callback String : " + s);

                            if (s.equals("") || s==null || s.length()==0){
                                Log.i("master","Login Fail.");
                                Toast.makeText(PopUpSignIn.this, "Login Fail...", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Log.i("master","Login Success.");
                                Toast.makeText(PopUpSignIn.this, "Login Success !!!", Toast.LENGTH_SHORT).show();

                                //액티비티를 전환하면서 데이터를 전달한다.
                                Intent intent = new Intent(PopUpSignIn.this, LectureActivity.class);
                                intent.putExtra("userInfo",s);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }.execute(constants.LOGIN_URL);
               }
            }
        });
    }
}


