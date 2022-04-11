package com.techgeeks.workout.trademill;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.techgeeks.workout.R;
import com.techgeeks.workout.utils.GlobalFunction;
import com.techgeeks.workout.utils.SharedPreferenceManager;
import com.techgeeks.workout.utils.TypefaceManager;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;


public class Trademill_Result extends Activity {
    String TAG = getClass().getSimpleName();
    //    AdView adView;
    Bundle extras;
    GlobalFunction globalFunction;
    ImageView iv_close;
    SharedPreferenceManager sharedPreferenceManager;
    Double trademill;
    TextView tv_trademill_result;
    TypefaceManager typefaceManager;


    //    public void attachBaseContext(Context context) {
//        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
//    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.popup_trademill);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.tv_trademill_result = (TextView) findViewById(R.id.tv_trademill_result);
        this.iv_close = (ImageView) findViewById(R.id.iv_close);
//        this.adView = (AdView) findViewById(R.id.adView);
//        AdView mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
        this.tv_trademill_result.setTypeface(this.typefaceManager.getLight());
        this.extras = getIntent().getExtras();
        try{
            if (this.extras != null && this.extras.containsKey("trademill"))
                this.trademill = Double.valueOf(this.extras.getDouble("trademill"));
            else
                this.trademill = 0.0;
        }catch (Exception e){
            this.trademill = 0.0;
        }

        TextView textView = this.tv_trademill_result;
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.Bruce_trade_mill));
        sb.append("%.2f");
        textView.setText(String.format(sb.toString(), new Object[]{this.trademill}));
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
//        if (this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
//            this.adView.setVisibility(8);
//        } else {
//            this.adView.setVisibility(0);
//            this.adView.loadAd(new AdParam.Builder().build());
//            this.adView.setAdListener(new AdListener() {
//                public void onAdLoaded() {
//                    super.onAdLoaded();
//                    Trademill_Result.this.adView.setVisibility(0);
//                }
//
//                public void onAdFailedToLoad(int i) {
//                    super.onAdFailedToLoad(i);
//                    Trademill_Result.this.adView.setVisibility(8);
//                }
//            });
//        }
        this.iv_close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Trademill_Result.this.onBackPressed();
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