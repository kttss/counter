package com.kttss.counter.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kttss.counter.R;
import com.kttss.counter.checker.Counter;
import com.kttss.counter.checker.ServiceFactory;
import com.kttss.counter.util.Globals;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class CounterActivity extends AppCompatActivity{

    private TextView txtValue;
    private Button btnPrevious;
    private Button btnReset;
    private Button btnNext;
    private Counter counter;



    private AdView adView;


    private AdRequest adRequest;
    private InterstitialAd mInterstitialAd;
    private int nbrTry=0;
    private int adsCount=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        int id=getIntent().getExtras().getInt("ID");
        if(id>0){
            counter= ServiceFactory.counterService.load(id);
            setTitle(counter.getName());
        }else {
            finish();
        }
        txtValue=(TextView)findViewById(R.id.txt_value);
        btnPrevious=(Button)findViewById(R.id.btn_previous);
        btnReset=(Button)findViewById(R.id.btn_reset);
        btnNext=(Button)findViewById(R.id.btn_next);


        //Setting events
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            previous();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter.setValue(0d);
                txtValue.setText(String.valueOf(counter.getValue()));
                ServiceFactory.counterService.update(counter);
            }
        });

        txtValue.setText(((Double)counter.getValue()).intValue()+"");

        adView=(AdView)findViewById(R.id.adView);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(Globals.AD_MOB_INTERSTITIAL);
        if(Globals.WITH_ADS)
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        adRequest= new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        CountDownTimer countDownTimer=new CountDownTimer(Long.MAX_VALUE, 5000) {
            public void onTick(long millisUntilFinished) {
                fillAds();
            }
            public void onFinish() {
                start();
            }
        }.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_VOLUME_DOWN){
            previous();
            return true;
        }else if(keyCode==KeyEvent.KEYCODE_VOLUME_UP){
            next();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void next(){
        counter.setValue(counter.getValue()+counter.getLap());
        txtValue.setText(String.valueOf(((Double)counter.getValue()).intValue()));
        ServiceFactory.counterService.update(counter);
    }
    private void previous(){
        counter.setValue(counter.getValue()-counter.getLap());
        txtValue.setText(String.valueOf(((Double)counter.getValue()).intValue()));
        ServiceFactory.counterService.update(counter);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            return false;
        }else{
            return true;
        }
    }
    private void fillAds(){
        if(isNetworkAvailable() && Globals.WITH_ADS){
            adView.setVisibility(View.VISIBLE);
            if(nbrTry>(8*adsCount)){
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    nbrTry=-1;
                    adsCount++;
                }
            }
            nbrTry++;
        }else{
            adView.setVisibility(View.GONE);
        }
    }


}
