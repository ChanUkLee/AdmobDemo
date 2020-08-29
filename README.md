# AdmobDemo

For Admob Simple Test
*Is the interstitial loadAd really being called in the main thread?*

## Dependencies

com.google.android.gms:play-services-ads:19.3.0

## Comment

#############################
Presetting for Android Studio
#############################

1. Uncheck Compact Middle Package
2. Change Package Name By Refactor => com.***.***
3. Change applicationId in build.gradle from App
4. Check package in AndroidManifest.xml

#################
Setting for Admob
#################

res/values/strings.xml
- test_device_id <= [Add your test device](https://developers.google.com/admob/android/test-ads#add_your_test_device)
- admob_app_id <= Your Admob App Id
- interstitial_ad_unit_id <= Your Interstitial Ad Unit Id or [Test Ad Unit Id](https://developers.google.com/admob/android/test-ads#sample_ad_units)

https://developers.google.com/admob/android/quick-start?hl=en
