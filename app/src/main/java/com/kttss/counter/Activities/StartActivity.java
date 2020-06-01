package com.kttss.counter.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.kttss.counter.R;
import com.kttss.counter.util.Cryptor;
import com.kttss.counter.util.Globals;

import io.sentry.Sentry;
import io.sentry.android.AndroidSentryClientFactory;

public class StartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Sentry.init(Cryptor.decrypt(Globals.SENTRY_KEY_CRYPTED));
        Sentry.init(new AndroidSentryClientFactory(this.getApplicationContext()));
    }


    @Override
    protected void onStart() {
        super.onStart();
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(StartActivity.this,MainActivity.class);
                startActivity(intent);
            }
        },1500);
    }
}
