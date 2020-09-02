package com.vader87.admob_demo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.vader87.admob_demo.R;
import com.vader87.locatlayout.LogcatLayout;

public class InterstitialFragment extends Fragment {

    private final String TAG = "InterstitialFragment";

    private InterstitialAd mInterstitialAd = null;
    private LogcatLayout mLogcatLayout = null;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_interstitial, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLogcatLayout = (LogcatLayout)getActivity().findViewById(R.id.log_view_interstitial);

        onInit();

        Button btnLoad = (Button)getActivity().findViewById(R.id.btn_interstitial_load);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoad();
            }
        });

        Button btnShow = (Button)getActivity().findViewById(R.id.btn_intersitial_show);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShow();
            }
        });
    }

    private void onInit() {
        Log("onInit");
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                Log("onAdClicked");
            }

            @Override
            public void onAdClosed() {
                Log("onAdClosed");
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Log("onAdFailedToLoad " + adError.getMessage());
            }

            @Override
            public void onAdImpression() {
                Log("onAdImpression");
            }

            @Override
            public void onAdLeftApplication() {
                Log("onAdLeftApplication");
            }

            @Override
            public void onAdLoaded() {
                Log( "onAdLoaded");
            }

            @Override
            public void onAdOpened() {
                Log("onAdOpened");
            }
        });
    }

    private void onLoad() {
        Log( "onLoad");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    // Make all calls to the Mobile Ads SDK on the main thread.
    // java.lang.IllegalStateException: setAdListener must be called on the main UI thread.
    private void onLoadFromThread() {
        Log( "onLoadFromThread");
        new Thread(new Runnable() {
            @Override
            public void run() {
                onLoad();
            }
        }).start();
    }

    private void onShow() {
        Log( "onShow");

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log("onShow - Is Not Loaded");
        }

    }

    private void Log(String msg) {
        mLogcatLayout.d(TAG, msg);
    }
}
