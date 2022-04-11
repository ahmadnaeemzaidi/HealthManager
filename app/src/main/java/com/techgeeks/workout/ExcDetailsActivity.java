package com.techgeeks.workout;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.nativead.NativeAd;
import com.huawei.hms.ads.nativead.NativeAdLoader;
import com.techgeeks.workout.database.DatabaseOperations;
import com.techgeeks.workout.utils.NativeViewFactory;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import org.json.JSONException;
import org.json.JSONObject;

import kr.pe.burt.android.lib.faimageview.FAImageView;

public class ExcDetailsActivity extends AppCompatActivity {
    public int Dayvalue;
    public LinearLayout adView;
    public boolean adloaded = false;
    public FAImageView animImageFull;
    public DatabaseOperations databaseOperations;
    public String day;
    public int editCycle;
    public boolean editedValue = false;
    public int exercisepos;
    public int k;
    public TextView l;
    public TextView m;
    public SharedPreferences mSharedPreferences;
    public Context n;
    public LinearLayout nativeAdContainer;
    public NumberPicker number_picker;
    public LayoutInflater o;
    public int p;
    public int q;
    public int r = 0;
    public int[] s = {R.array.day1_cycles, R.array.day2_cycles, R.array.day3_cycles, R.array.day4_cycles, R.array.day5_cycles, R.array.day6_cycles, R.array.day7_cycles, R.array.day8_cycles, R.array.day9_cycles, R.array.day10_cycles, R.array.day11_cycles, R.array.day12_cycles, R.array.day13_cycles, R.array.day14_cycles, R.array.day15_cycles, R.array.day16_cycles, R.array.day17_cycles, R.array.day18_cycles, R.array.day19_cycles, R.array.day20_cycles, R.array.day21_cycles, R.array.day22_cycles, R.array.day23_cycles, R.array.day24_cycles, R.array.day25_cycles, R.array.day26_cycles, R.array.day27_cycles, R.array.day28_cycles, R.array.day29_cycles, R.array.day30_cycles};


    public String createJsonArray(String str) {
        String valueOf = null;
        int i = 0;
        JSONObject jSONObject = null;
        if (str != null) {
            try {
                jSONObject = new JSONObject(str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        int[] intArray = getResources().getIntArray(this.s[this.Dayvalue - 1]);
        JSONObject jSONObject2 = new JSONObject();
        int i2 = 0;
        for (int i3 : intArray) {
            try {
                if (this.exercisepos == i2) {
                    valueOf = String.valueOf(this.exercisepos);
                    i = this.editCycle;
                } else if (jSONObject == null || !jSONObject.has(String.valueOf(i2))) {
                    jSONObject2.put(String.valueOf(i2), i3);
                    i2++;
                } else {
                    valueOf = String.valueOf(i2);
                    i = jSONObject.getInt(String.valueOf(i2));
                }
                jSONObject2.put(valueOf, i);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            i2++;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("json str: ");
        sb.append(jSONObject2.toString());
        Log.e("TAG", sb.toString());
        return jSONObject2.toString();
    }

    private void getScreenHeightWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.p = displayMetrics.heightPixels;
        this.q = displayMetrics.widthPixels;
    }

    public void b() {
        this.nativeAdContainer = (LinearLayout) findViewById(R.id.nativeAdContainer);
//        this.o = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        this.admobAdsObject = new AdmobAds(this.n, this.nativeAdContainer, getString(R.string.g_native));
//        this.admobAdsObject.refreshAd();
        NativeAdLoader.Builder builder = new NativeAdLoader.Builder(this, getResources().getString(R.string.huawei_native_add));
        builder.setNativeAdLoadedListener(new NativeAd.NativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(NativeAd nativeAd) {
                // Called when an ad is loaded successfully.
                Log.d("TAGNative", "Native ad createType");
                final View nativeView = createNativeView(nativeAd, nativeAdContainer);
                nativeAdContainer.removeAllViews();
                nativeAdContainer.addView(nativeView);

            }
        }).setAdListener(new AdListener() {
            @Override
            public void onAdFailed(int errorCode) {
                // Called when an ad fails to be loaded.
                Log.d("TAGNative", "Native ad fail "+errorCode);

            }
        });
        NativeAdLoader nativeAdLoader = builder.build();
        nativeAdLoader.loadAd(new AdParam.Builder().build());
    }

    private View createNativeView(NativeAd nativeAd, ViewGroup parentView) {
        int createType = nativeAd.getCreativeType();
        Log.d("TAGNative", "Native ad createType is " + createType);
        if (createType == 2 || createType == 102) {
            // Large image
            return NativeViewFactory.createImageOnlyAdView(nativeAd, parentView);
        } else if (createType == 3 || createType == 6) {
            // Large image with text or video with text
            return NativeViewFactory.createMediumAdView(nativeAd, parentView);
        } else if (createType == 103 || createType == 106) {
            // Large image with text or Video with text, using AppDownloadButton template.
            return NativeViewFactory.createAppDownloadButtonAdView(nativeAd, parentView);
        } else if (createType == 7 || createType == 107) {
            // Small image with text-
            return NativeViewFactory.createSmallImageAdView(nativeAd, parentView);
        } else if (createType == 8 || createType == 108) {
            // Three small images with text
            return NativeViewFactory.createThreeImagesAdView(nativeAd, parentView);
        } else {
            // Undefined creative type
            return null;
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        Log.e("TAG", " exc details onbackpress saved");
        DatabaseOperations databaseOperations2 = this.databaseOperations;
        String str = this.day;
        databaseOperations2.insertExcCycles(str, createJsonArray(databaseOperations2.getDayExcCycles(str)));
        if (this.editedValue) {
            Toast.makeText(getApplicationContext(), "Exercise cycles are updated.", Toast.LENGTH_SHORT).show();
        }
        this.editedValue = false;
    }

    public void onCreate(@Nullable Bundle bundle) {
        TextView textView;
        int i;
        Resources resources;
        super.onCreate(bundle);
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.exc_details_layout);
        this.n = this;
        this.databaseOperations = new DatabaseOperations(this);
        Bundle extras = getIntent().getExtras();
        int[] intArray = extras.getIntArray("framesIdArray");
        String string = extras.getString("excName");
        int i2 = extras.getInt("excCycle");
        this.k = extras.getInt("excNameDescResId");
        String str = "";
        this.day = extras.getString("day", str);
        this.Dayvalue = Integer.parseInt(this.day.replaceAll("[^0-9]", str));
        this.exercisepos = extras.getInt("excPosition");
        StringBuilder sb = new StringBuilder();
        sb.append("exercise position ");
        sb.append(this.exercisepos);
        Log.e("TAG", sb.toString());
        String upperCase = string.replace("_", " ").toUpperCase();
        Toolbar toolbar = (Toolbar) findViewById(R.id.exc_details_layout_mtoolbar);
        ((TextView) toolbar.findViewById(R.id.exc_details_layout_toolbar_title)).setText(upperCase);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DatabaseOperations b = ExcDetailsActivity.this.databaseOperations;
                String a2 = ExcDetailsActivity.this.day;
                ExcDetailsActivity excDetailsActivity = ExcDetailsActivity.this;
                b.insertExcCycles(a2, excDetailsActivity.createJsonArray(excDetailsActivity.databaseOperations.getDayExcCycles(ExcDetailsActivity.this.day)));
                if (ExcDetailsActivity.this.editedValue) {
                    Toast.makeText(ExcDetailsActivity.this.getApplicationContext(), "Excercise cycles are updated.", Toast.LENGTH_SHORT).show();
                }
                ExcDetailsActivity.this.editedValue = false;
                ExcDetailsActivity.this.finish();
            }
        });
        this.l = (TextView) findViewById(R.id.description_exDetail);
        this.animImageFull = (FAImageView) findViewById(R.id.animation_exDetail);
        this.animImageFull.setInterval(1000);
        this.animImageFull.setLoop(true);
        this.animImageFull.reset();
        for (int addImageFrame : intArray) {
            this.animImageFull.addImageFrame(addImageFrame);
        }
        this.animImageFull.startAnimation();
        this.l.setText(this.k);
        getScreenHeightWidth();
        b();
        this.m = (TextView) findViewById(R.id.numberpicker_cycles);
        if (upperCase.equalsIgnoreCase("plank")) {
            textView = this.m;
            resources = getResources();
            i = R.string.seconds;
        } else {
            textView = this.m;
            resources = getResources();
            i = R.string.cycles;
        }
        textView.setText(resources.getString(i));
        this.number_picker = (NumberPicker) findViewById(R.id.number_picker);
        this.number_picker.setMax(100);
        this.number_picker.setMin(5);
        this.number_picker.setValue(i2);
        this.editCycle = i2;
        this.number_picker.setValueChangedListener(new ValueChangedListener() {
            public void valueChanged(int i, ActionEnum actionEnum) {
                ExcDetailsActivity.this.editCycle = i;
                StringBuilder sb = new StringBuilder();
                sb.append("Np val ");
                sb.append(i);
                Log.e("TAG", sb.toString());
                ExcDetailsActivity.this.editedValue = true;
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        FAImageView fAImageView = this.animImageFull;
        if (fAImageView != null) {
            fAImageView.reset();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        finish();
        return true;
    }
}
