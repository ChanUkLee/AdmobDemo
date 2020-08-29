package com.vader87.admob_demo.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.vader87.admob_demo.R;

public class InterstitialFragment extends Fragment {

    private InterstitialAd mInterstitialAd = null;

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
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.d("MainActivity", "onAdLoaded");            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Log.e("MainActivity", "onAdFailedToLoad " + adError.getMessage());
            }
        });
    }

    private void onLoad() {
        Log.d("MainActivity", "onLoad");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    // java.lang.IllegalStateException: setAdListener must be called on the main UI thread.
    private void onLoadFromThread() {
        Log.d("MainActivity", "onLoadFromThread");
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
