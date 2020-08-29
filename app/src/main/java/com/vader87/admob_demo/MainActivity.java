package com.vader87.admob_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.vader87.admob_demo.fragments.BannerFragment;
import com.vader87.admob_demo.fragments.InterstitialFragment;
import com.vader87.admob_demo.fragments.RewardedFragment;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private FragmentManager mFragmentManager = null;
    private FragmentTransaction mFragmentTransaction = null;
    private Fragment mCurrentFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onRequest();
        onInit();

        mFragmentManager = getSupportFragmentManager();
    }

    public void onClick(View view) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.btn_banner:
                BannerFragment bannerFragment = new BannerFragment();
                mFragmentTransaction.replace(R.id.frameLayout, bannerFragment).commitAllowingStateLoss();
                break;
            case R.id.btn_interstitial:
                InterstitialFragment interstitialFragment = new InterstitialFragment();
                mFragmentTransaction.replace(R.id.frameLayout, interstitialFragment).commitAllowingStateLoss();
                break;
            case R.id.btn_rewarded:
                RewardedFragment rewardedFragment = new RewardedFragment();
                mFragmentTransaction.replace(R.id.frameLayout, rewardedFragment).commitAllowingStateLoss();
                break;
            case R.id.btn_native:
                Log.w(TAG, "onClick Not Ready Native");
                break;
            default:
                Log.e(TAG, "onClick Error : Unkown Id");
                break;
        }
    }


    private void onRequest() {
        List<String> testDeviceIds = Arrays.asList(getString(R.string.test_device_id));
        RequestConfiguration configuration = new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);
    }

    private void onInit() {
        Log.d("MainActivity", "onInit");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.d("MainActivity", "onInitializationComplete");
            }
        });
    }
}
