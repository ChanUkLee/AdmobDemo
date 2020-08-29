package com.admob.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;

    private long sTime = 0L;
    private long eTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnInit = (Button)findViewById(R.id.btn_init);
        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sTime = System.nanoTime();
                onInit();
            }
        });

        Button btnLoad = (Button)findViewById(R.id.btn_load);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sTime = System.nanoTime();
                onLoad();
                //onLoadFromThread();
            }
        });

        Button btnShow = (Button)findViewById(R.id.btn_show);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShow();
            }
        });
        btnShow.setEnabled(false);

        onRequest();
    }

    private void onRequest() {
        List<String> testDeviceIds = Arrays.asList("[YOUR TEST DEVICE ID]");
        RequestConfiguration configuration = new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);
    }

    private void onInit() {
        Log.d("MainActivity", "onInit");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.d("MainActivity", "onInitializationComplete");
                onLoad();
            }
        });
    }

    private void onNewInterstitial() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                eTime = System.nanoTime();
                Log.d("MainActivity", "onAdLoaded " + (eTime - sTime) / 0.000001 + "ms");

                Button btnShow = (Button)findViewById(R.id.btn_show);
                btnShow.setEnabled(true);
            }

            @Override
            public void onAdFailedToLoad(LoadAdError error) {
                Log.e("MainActivity", "onAdFailedToLoad " + error.getMessage());
            }
        });
    }


    private void onLoad() {
        Log.d("MainActivity", "onLoad");
        onNewInterstitial();
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    // java.lang.IllegalStateException: setAdListener must be called on the main UI thread.
    private void onLoadFromThread() {
        Log.d("MainActivity", "onLoadFromThread");
        onNewInterstitial();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        }).start();
    }

    private void onShow() {
        Log.d("MainActivity", "onShow");

        if (mInterstitialAd.isLoaded())
            mInterstitialAd.show();
    }
}
