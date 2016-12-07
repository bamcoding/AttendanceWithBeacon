package net.gondor.application;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.gondor.common.HttpClient;
import net.gondor.common.constants;
import net.gondor.vo.UserVO;

import gondor.net.attendance.R;

/**
 * Created by 206-017 on 2016-12-01.
 */

public class SignUp extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        final EditText userId, password, password2, name;
        Button signupConfirm, signupCancel;

        userId = (EditText)findViewById(R.id.signupId);
        password = (EditText)findViewById(R.id.signupPassword);
        password2 = (EditText)findViewById(R.id.signupPassword2);
        name = (EditText)findViewById(R.id.signupName);

        signupConfirm = (Button)findViewById(R.id.signupConfirm);
        signupConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final UserVO userVO = new UserVO();
                userVO.setUserId(userId.getText().toString());
                userVO.setPassword(password.getText().toString());

                new AsyncTask<String, Void, String>() {

                    @Override
                    protected String doInBackground(String... params){
                        HttpClient.Builder builder = new HttpClient.Builder("POST", params[0]);
                        builder.addOrReplaceParameter("userId", userVO.getUserId());
                        builder.addOrReplaceParameter("password", userVO.getPassword());
                        HttpClient post = builder.create();
                        post.request();
                        Log.d("UserVO", post.getHttpStatusCode() + "");
                        Log.d("UserVO", post.getBody());
                        return post.getBody();
                    }

                    //콜백기능
                    protected void onPostExecute(String s) {
                        Toast.makeText(SignUp.this, "SignUp Success !!!", Toast.LENGTH_SHORT).show();
                    }
                }.execute(constants.SIGNUP_URL,"userId","password");
                finish();
            }
        });

        signupCancel = (Button)findViewById(R.id.signupCancel);
        signupCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
