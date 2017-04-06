package com.excy.excy.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.excy.excy.R;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        /* Set up excy link view  */
        TextView excyLinkTV = (TextView) findViewById(R.id.tvLink);
        excyLinkTV.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
