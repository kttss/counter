package com.kttss.counter.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.kttss.counter.R;
import com.kttss.counter.adapters.CounterAdapter;
import com.kttss.counter.checker.ConfigurationService;
import com.kttss.counter.checker.Counter;
import com.kttss.counter.checker.CounterService;
import com.kttss.counter.checker.ServiceFactory;
import com.kttss.counter.util.Globals;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private AdView adView;


    private AdRequest adRequest;
    private InterstitialAd mInterstitialAd;
    private int nbrTry=0;
    private int adsCount=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ServiceFactory.configService=new ConfigurationService(this);
        ServiceFactory.counterService=new CounterService(this);
        MobileAds.initialize(this, Globals.AD_MOB_APP_ID);


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CounterEditor counterEditor=new CounterEditor();
                counterEditor.setContext(MainActivity.this);
                counterEditor.show(getFragmentManager(),"Add counter");

            }
        });
        /*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        */

        listView=(ListView)findViewById(R.id.list_view);
        adView=(AdView)findViewById(R.id.adView);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(Globals.AD_MOB_INTERSTITIAL);
        if(Globals.WITH_ADS)
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        adRequest= new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        fillListView();
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }
    }

    public void fillListView(){
        List<Counter> counterList=ServiceFactory.counterService.loadAll();
        CounterAdapter adapter=new CounterAdapter(counterList,this);
        listView.setAdapter(adapter);
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

