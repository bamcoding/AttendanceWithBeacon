package net.gondor.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import net.gondor.application.prepare.SignIn;
import net.gondor.application.prepare.SignUp;

import gondor.net.attendance.R;

/**
 * Created by 206-017 on 2016-12-07.
 */

public class MainActivity extends AppCompatActivity{

    private Button beaconState;
    private Button login;
    private Button signUp;
    private Button popUpSignIn;
    private Button coverBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beaconState = (Button)findViewById(R.id.beaconState);
        login = (Button)findViewById(R.id.login);
        signUp = (Button)findViewById(R.id.signup);
        popUpSignIn = (Button)findViewById(R.id.testBtn);
        coverBtn = (Button)findViewById(R.id.coverBtn);


        beaconState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyBeaconFinder.class);
                startActivity(intent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignIn.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });

        popUpSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PopUpSignIn.class);
                startActivity(intent);
            }
        });

        coverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IntroActivity.class);
                startActivity(intent);
            }
        });
    }
}
