package com.excy.excy.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.excy.excy.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn =(Button) findViewById(R.id.btnLogin);

        loginBtn.getBackground().setColorFilter(
                getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PlayActivity.class);
                startActivity(intent);
            }
        });

        Button signUpBtn = (Button) findViewById(R.id.btnSignUp);

        signUpBtn.getBackground().setColorFilter(
                getResources().getColor(R.color.colorSignUpBlue), PorterDuff.Mode.MULTIPLY);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
