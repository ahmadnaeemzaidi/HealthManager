package com.inocreativeStudio.IncreaseHeightWorkout.blood_volume;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.inocreativeStudio.IncreaseHeightWorkout.R;
import com.inocreativeStudio.IncreaseHeightWorkout.utils.GlobalFunction;
import com.inocreativeStudio.IncreaseHeightWorkout.utils.SharedPreferenceManager;
import com.inocreativeStudio.IncreaseHeightWorkout.utils.TypefaceManager;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;


public class BloodVolume_Result extends Activity {
    String TAG = getClass().getSimpleName();
//    AdView adView;
    double blood_volume;
    Bundle extras;
    GlobalFunction globalFunction;
    ImageView iv_close;
    LinearLayout rl_main;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_ans_bmr;
    TypefaceManager typefaceManager;


   //    public void attachBaseContext(Context context) {
//        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
//    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.popup_bmr);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);

//        this.adView = (AdView) findViewById(R.id.adView);
//        AdView mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        this.iv_close = (ImageView) findViewById(R.id.iv_close);
        this.rl_main = (LinearLayout) findViewById(R.id.rl_main);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.extras = getIntent().getExtras();
        try{
            this.blood_volume = this.extras.getDouble("blood_volume");

        }catch (Exception e){
            this.blood_volume = 0.0;
        }
        this.tv_ans_bmr = (TextView) findViewById(R.id.tv_ans_bmr);
        this.tv_ans_bmr.setTypeface(this.typefaceManager.getLight());
//        this.rl_main.setBackgroundResource(R.drawable.popup_background_gradient6);
//        if (this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
//            this.adView.setVisibility(8);
//        } else {
//            this.adView.setVisibility(0);
//            this.adView.loadAd(new AdParam.Builder().build());
//            this.adView.setAdListener(new AdListener() {
//                public void onAdLoaded() {
//                    super.onAdLoaded();
//                    BloodVolume_Result.this.adView.setVisibility(0);
//                }
//
//                public void onAdFailedToLoad(int i) {
//                    super.onAdFailedToLoad(i);
//                    BloodVolume_Result.this.adView.setVisibility(8);
//                }
//            });
//        }
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(this.blood_volume);
        Log.d("bmr_val->", sb.toString());
        TextView textView = this.tv_ans_bmr;
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(getString(R.string.Your_bloodvolume_is));
        sb3.append(" :\n");
        sb3.append(String.format("%.02f", new Object[]{Double.valueOf(this.blood_volume)}));
        sb2.append(String.valueOf(sb3.toString()));
        sb2.append(" ");
        sb2.append(getString(R.string.liter));
        textView.setText(sb2.toString());
        this.iv_close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BloodVolume_Result.this.onBackPressed();
            }
        });
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
