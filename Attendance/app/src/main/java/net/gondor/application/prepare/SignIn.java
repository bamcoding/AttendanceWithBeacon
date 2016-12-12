package net.gondor.application.prepare;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.gondor.common.HttpClient;
import net.gondor.common.constants;
import net.gondor.vo.UserVO;

import gondor.net.attendance.R;

public class SignIn extends AppCompatActivity {
    private UserVO userVO = new UserVO();
    private TextView stateView;
    private EditText id, password;
    private Button logIn, signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        //필수 입력값
        id = (EditText)findViewById(R.id.input_userId);
        password = (EditText)findViewById(R.id.input_userPassword);
        //버튼
        logIn = (Button)findViewById(R.id.loginBtn);
        signUp = (Button)findViewById(R.id.signupBtn);
        //값표시
        stateView = (TextView)findViewById(R.id.loginState);

        //버튼 이벤트 구현
        logIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //클릭하면 상태를 바꾼다.
                String userId = id.getText().toString();
                String userPassword = password.getText().toString();

                if(userId == null || userId.length() == 0 || userPassword == null || userPassword.length() == 0){
                    Toast.makeText(SignIn.this, "Please enter the required input value correctly.", Toast.LENGTH_SHORT).show();
                }else {
                    userVO.setUserId(userId);
                    userVO.setPassword(userPassword);
                    stateView.setText("clicked button");

                    new AsyncTask<String, Void, String>() {
                        @Override
                        protected String doInBackground(String... params) {
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
                            Toast.makeText(SignIn.this, "Login Success !!!", Toast.LENGTH_SHORT).show();
                            stateView.setText(s);
                        }

                    }.execute(constants.LOGIN_URL);
                }
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
            }
        });
    }
}
