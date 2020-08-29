package com.vader87.admob_demo.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.vader87.admob_demo.R;

public class RewardedFragment extends Fragment {

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
        mRewardedAd = new RewardedAd(getActivity(), getString(R.string.rewarded_ad_unit_id));
    }

    private void onLoad() {
        mRewardedAd.loadAd(new AdRequest.Builder().build(), new RewardedAdLoadCallback(){
            @Override
            public void onRewardedAdLoaded() {
                Log.d("MainActivity", "onRewardedAdLoaded");
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
                Log.d("MainActivity", "onRewardedAdFailedToLoad " + adError.getMessage());
            }
        });
    }

    private void onShow() {
        if (mRewardedAd.isLoaded()) {
            mRewardedAd.show(getActivity(), new RewardedAdCallback() {
                @Override
                public void onRewardedAdOpened() {
                    Log.d("MainActivity", "onRewardedAdOpened");
                }

                @Override
                public void onRewardedAdClosed() {
                    Log.d("MainActivity", "onRewardedAdClosed");
                }

                @Override
                public void onUserEarnedReward(@NonNull RewardItem reward) {
                    Log.d("MainActivity", "onUserEarnedReward " + reward.getType() + " / " + reward.getAmount());
                }

                @Override
                public void onRewardedAdFailedToShow(AdError adError) {
                    Log.d("MainActivity", "onRewardedAdFailedToShow " + adError.getMessage());
                }
            });
        }
    }
}
