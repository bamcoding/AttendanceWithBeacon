package net.gondor.application.prepare;

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
    private EditText userId;
    private EditText password, password2;
    private EditText userName;
    private EditText birthday;
    private EditText gender;
    private EditText phoneNumber;
    private EditText address;
    private Button signupConfirm;
    private Button signupCancel;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        final UserVO userVO = new UserVO();
        userId = (EditText)findViewById(R.id.signupId);
        password = (EditText)findViewById(R.id.signupPassword);
        password2 = (EditText)findViewById(R.id.signupPassword2);
        userName = (EditText)findViewById(R.id.signupName);
        birthday = (EditText)findViewById(R.id.personalNumber);
        gender = (EditText)findViewById(R.id.sex);
        phoneNumber = (EditText)findViewById(R.id.phoneNumber);
        address = (EditText)findViewById(R.id.address);

        signupConfirm = (Button)findViewById(R.id.signupConfirm);
        signupConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userVO.setUserId(userId.getText().toString());
                userVO.setPassword(password.getText().toString());
                userVO.setUserName(userName.getText().toString());
                userVO.setBirthday(birthday.getText().toString());
                userVO.setGender(gender.getText().toString());
                userVO.setPhoneNumber(phoneNumber.getText().toString());
                userVO.setAddress(address.getText().toString());


                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        HttpClient.Builder builder = new HttpClient.Builder("POST", params[0]);
                        builder.addOrReplaceParameter("userId", userVO.getUserId());
                        builder.addOrReplaceParameter("password", userVO.getPassword());
                        builder.addOrReplaceParameter("userName", userVO.getUserName());
                        builder.addOrReplaceParameter("birthday", userVO.getBirthday());
                        builder.addOrReplaceParameter("gender", userVO.getGender());
                        builder.addOrReplaceParameter("phoneNumber", userVO.getPhoneNumber());
                        builder.addOrReplaceParameter("address", userVO.getAddress());

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
                }.execute(constants.SIGNUP_URL);
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
