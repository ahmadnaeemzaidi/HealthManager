package com.techgeeks.workout.blood_pressure;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.banner.BannerView;
import com.techgeeks.workout.R;
import com.techgeeks.workout.utils.GlobalFunction;
import com.techgeeks.workout.utils.SharedPreferenceManager;
import com.techgeeks.workout.utils.TypefaceManager;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;


public class Bloodpressure_Chart extends Activity {
    String TAG = getClass().getSimpleName();
//    AdView adView;
    GlobalFunction globalFunction;
    ImageView iv_back;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_bodayfat_classification;
    TextView tv_bodayfat_classification_man;
    TextView tv_bodayfat_percentage;
    TextView tv_bodyfat;
    TextView tv_fat_level;
    TextView tv_men_fatlevel;
    TextView tv_title;
    TypefaceManager typefaceManager;
    BannerView bannerView;


    //    public void attachBaseContext(Context context) {
//        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
//    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.blood_pressure_chart);
        this.globalFunction = new GlobalFunction(this);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.tv_title = (TextView) findViewById(R.id.tv_title);
        this.tv_bodyfat = (TextView) findViewById(R.id.tv_bodyfat);
        this.tv_fat_level = (TextView) findViewById(R.id.tv_fat_level);
        this.tv_men_fatlevel = (TextView) findViewById(R.id.tv_men_fatlevel);
        this.tv_bodayfat_percentage = (TextView) findViewById(R.id.tv_bodayfat_percentage);
        this.tv_bodayfat_classification = (TextView) findViewById(R.id.tv_bodayfat_classification);
        this.tv_bodayfat_classification_man = (TextView) findViewById(R.id.tv_bodayfat_classification_man);
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.tv_title.setTypeface(this.typefaceManager.getBold());
        this.tv_bodyfat.setTypeface(this.typefaceManager.getBold());
        this.tv_fat_level.setTypeface(this.typefaceManager.getBold());
        this.tv_men_fatlevel.setTypeface(this.typefaceManager.getBold());
        this.tv_bodayfat_percentage.setTypeface(this.typefaceManager.getBold());
        this.tv_bodayfat_classification.setTypeface(this.typefaceManager.getBold());
        this.tv_bodayfat_classification_man.setTypeface(this.typefaceManager.getBold());
        bannerView = findViewById(R.id.adView);
        bannerView.setAdId(getResources().getString(R.string.huawei_banner_add));
        bannerView.setBannerAdSize(BannerAdSize.BANNER_SIZE_360_57);
        AdParam adParam = new AdParam.Builder().build();
        bannerView.loadAd(adParam);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Bloodpressure_Chart.this.finish();
            }
        });
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
//        if (this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
//            this.adView.setVisibility(8);
//            return;
//        }
//        this.adView.setVisibility(0);
//        this.adView.loadAd(new AdParam.Builder().build());
//        this.adView.setAdListener(new AdListener() {
//            public void onAdLoaded() {
//                super.onAdLoaded();
//                Bloodpressure_Chart.this.adView.setVisibility(0);
//            }
//
//            public void onAdFailedToLoad(int i) {
//                super.onAdFailedToLoad(i);
//                Bloodpressure_Chart.this.adView.setVisibility(8);
//            }
//        });
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public void onResume() {
        super.onResume();
        if (!this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
//            this.adView.setVisibility(0);
        } else {
//            this.adView.setVisibility(8);
        }
    }
}
