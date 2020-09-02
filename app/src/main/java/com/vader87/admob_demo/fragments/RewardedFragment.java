package com.vader87.admob_demo.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.vader87.admob_demo.R;

import org.w3c.dom.Text;

public class RewardedFragment extends Fragment {

    private final String TAG = "RewardedFragment";

    private RewardedAd mRewardedAd = null;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rewarded, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onInit();

        Button btnLoad = (Button)getActivity().findViewById(R.id.btn_rewarded_load);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoad();
            }
        });

        Button btnShow = (Button)getActivity().findViewById(R.id.btn_rewarded_show);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShow();
            }
        });
    }

    private void onInit() {
        Log("onInit");
        mRewardedAd = new RewardedAd(getActivity(), getString(R.string.rewarded_ad_unit_id));
    }

    private void onLoad() {
        Log("onLoad");
        mRewardedAd.loadAd(new AdRequest.Builder().build(), new RewardedAdLoadCallback(){
            @Override
            public void onRewardedAdLoaded() {
                Log( "onRewardedAdLoaded");
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
                Log("onRewardedAdFailedToLoad " + adError.getMessage());
            }
        });
    }

    private void onShow() {
        Log.d(TAG, "onShow");
        if (mRewardedAd.isLoaded()) {
            mRewardedAd.show(getActivity(), new RewardedAdCallback() {
                @Override
                public void onRewardedAdOpened() {
                    Log( "onRewardedAdOpened");
                }

                @Override
                public void onRewardedAdClosed() {
                    Log("onRewardedAdClosed");
                }

                @Override
                public void onUserEarnedReward(@NonNull RewardItem reward) {
                    Log("onUserEarnedReward " + reward.getType() + " / " + reward.getAmount());
                }

                @Override
                public void onRewardedAdFailedToShow(AdError adError) {
                    Log("onRewardedAdFailedToShow " + adError.getMessage());
                }
            });
        } else {
            Log("onShow - Is Not Loaded");
        }
    }

    private void Log(String msg) {
        if (Looper.myLooper() == Looper.getMainLooper())
        {

        }
        else
        {
            Log.e(TAG, "Thread Exception");
        }

        Log.d(TAG, msg);
    }
}
