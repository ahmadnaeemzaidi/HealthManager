package com.techgeeks.workout.sugar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.techgeeks.workout.R;
import com.techgeeks.workout.general.MyApplication;
import com.techgeeks.workout.utils.ClickAdd;
import com.techgeeks.workout.utils.ConnectionDetector;
import com.techgeeks.workout.utils.GlobalFunction;
import com.techgeeks.workout.utils.SharedPreferenceManager;
import com.techgeeks.workout.utils.TypefaceManager;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;


public class Sugar_Result extends Activity {
    String TAG = getClass().getSimpleName();
//    AdView adView;
    ConnectionDetector connectionDetector;
    Bundle extras;
    Double final_bloodsugar_val;
    GlobalFunction globalFunction;
    ImageView iv_close;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_ans_bloodsugar;
    TextView tv_bloodsugar_chart;
    TypefaceManager typefaceManager;


   //    public void attachBaseContext(Context context) {
//        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
//    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.popup_sugar);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.connectionDetector = new ConnectionDetector(this);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.tv_ans_bloodsugar = (TextView) findViewById(R.id.tv_ans_bloodsugar);
        this.tv_bloodsugar_chart = (TextView) findViewById(R.id.tv_bloodsugar_chart);
//        this.adView = (AdView) findViewById(R.id.adView);
//        AdView mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        this.iv_close = (ImageView) findViewById(R.id.iv_close);
        this.tv_ans_bloodsugar.setTypeface(this.typefaceManager.getLight());
        this.tv_bloodsugar_chart.setTypeface(this.typefaceManager.getBold());
        this.extras = getIntent().getExtras();
        if (this.extras!=null && this.extras.containsKey("final_bloodsugar_val"))
            this.final_bloodsugar_val = Double.valueOf(this.extras.getDouble("final_bloodsugar_val"));
        else
            this.final_bloodsugar_val = 0.0D;
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        if (this.final_bloodsugar_val.doubleValue() < 0.0d) {
            TextView textView = this.tv_ans_bloodsugar;
            StringBuilder sb = new StringBuilder();
            sb.append(getString(R.string.Blood_Sugar));
            sb.append(" : 0 ]");
            sb.append(getString(R.string.mmol_a));
            textView.setText(sb.toString());
        } else {
            TextView textView2 = this.tv_ans_bloodsugar;
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(getString(R.string.Blood_Sugar));
            sb3.append(" : %.2f");
            sb2.append(String.format(sb3.toString(), new Object[]{this.final_bloodsugar_val}));
            sb2.append(" ");
            sb2.append(getString(R.string.mmol_a));
            textView2.setText(sb2.toString());
        }
        this.tv_bloodsugar_chart.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
//                int random = ((int) (Math.random() * 2.0d)) + 1;
//                PrintStream printStream = System.out;
//                StringBuilder sb = new StringBuilder();
//                sb.append("random_number==>");
//                sb.append(random);
//                printStream.println(sb.toString());
//                if (random == 2) {
                    Sugar_Result.this.showIntertitial();
//                    return;
//                }
//                Sugar_Result.this.startActivity(new Intent(Sugar_Result.this, Sugar_Chart.class));
            }
        });
        this.iv_close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Sugar_Result.this.onBackPressed();
            }
        });
    }

    public void showIntertitial() {
        new MyApplication().showInterstitialAd(this, new ClickAdd() {
            @Override
            public void clickAdd() {
                startActivity(new Intent(Sugar_Result.this, Sugar_Chart.class));
            }
        });
//        if (this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
//            startActivity(new Intent(this, Sugar_Chart.class));
//        } else if (MyApplication.interstitial == null || !MyApplication.interstitial.isLoaded()) {
//            if (!MyApplication.interstitial.isLoading()) {
//                ConnectionBuddy.getInstance().hasNetworkConnection(new NetworkRequestCheckListener() {
//                    public void onNoResponse() {
//                    }
//
//                    public void onResponseObtained() {
//                        MyApplication.interstitial.loadAd(new AdParam.Builder().build());
//                    }
//                });
//            }
//            startActivity(new Intent(this, Sugar_Chart.class));
//        } else {
//            MyApplication.interstitial.show();
//        }
    }
}
