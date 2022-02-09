package com.inocreativeStudio.IncreaseHeightWorkout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.banner.BannerView;
import com.huawei.hms.ads.consent.bean.AdProvider;
import com.huawei.hms.ads.consent.constant.ConsentStatus;
import com.huawei.hms.ads.consent.inter.Consent;
import com.huawei.hms.ads.consent.inter.ConsentUpdateListener;
import com.inocreativeStudio.IncreaseHeightWorkout.WalkandStep.activities.PreferencesActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.inocreativeStudio.IncreaseHeightWorkout.WalkandStep.utils.StepDetectionServiceHelper;
import com.inocreativeStudio.IncreaseHeightWorkout.fragment.Fragment_Calculate;
import com.inocreativeStudio.IncreaseHeightWorkout.fragment.Fragment_Reminder;
import com.inocreativeStudio.IncreaseHeightWorkout.fragment.Fragment_Walk_and_Step;
import com.inocreativeStudio.IncreaseHeightWorkout.fragment.Fragment_Workout;
import com.inocreativeStudio.IncreaseHeightWorkout.fragment.MainFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigation;

    DrawerLayout drawer;
    ImageView imageView1;
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            String str = "";
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    ivPlay.setVisibility(View.GONE);
                    ivPause.setVisibility(View.GONE);

                    toolbar.setTitle(getString(R.string.app_name));
//                    MainActivity.this.openFragment(Fragment_Overview.newInstance(str, str,MainActivity.this));
                    MainActivity.this.openFragment(MainFragment.newInstance(str, str, MainActivity.this));
                    return true;

                case R.id.navigation_map:
                    ivPlay.setVisibility(View.GONE);
                    ivPause.setVisibility(View.GONE);
                    toolbar.setTitle("Workouts");
                    MainActivity.this.openFragment(Fragment_Workout.newInstance(str, str));
                    return true;

                case R.id.navigation_world:
                    ivPlay.setVisibility(View.GONE);
                    ivPause.setVisibility(View.GONE);
                    toolbar.setTitle("Calculater");
                    MainActivity.this.openFragment(Fragment_Calculate.newInstance(str, str));
                    return true;

                case R.id.navigation_walk:
                    if (isPlay) {
                        ivPlay.setVisibility(View.GONE);
                        ivPause.setVisibility(View.VISIBLE);
                    } else {
                        ivPlay.setVisibility(View.VISIBLE);
                        ivPause.setVisibility(View.GONE);
                    }
                    toolbar.setTitle("Walk & Step");
//                    MainActivity.this.openFragment(Fragment_Overview.newInstance(str, str));
                    MainActivity.this.openFragment(Fragment_Walk_and_Step.newInstance(str, str));

//                    Intent i = new Intent(MainActivity.this, Activity_Main.class);
//                    startActivity(i);

                    return true;
             /*   case R.id.test_navigation_walk:
                    toolbar.setTitle("Walk & Step");

                 Intent i = new Intent(MainActivity.this, Activity_Main.class);
                 startActivity(i);

//                    MainActivity.this.openFragment(Fragment_Overview.newInstance(str, str));
//                    MainActivity.this.openFragment(Fragment_Walk_and_Step.newInstance(str, str));
                    return true;*/

                case R.id.navigation_news:
                    ivPlay.setVisibility(View.GONE);
                    ivPause.setVisibility(View.GONE);
                    toolbar.setTitle("Reminders");
                    MainActivity.this.openFragment(Fragment_Reminder.newInstance(str, str));
                    return true;

                default:

                    return false;
            }
        }
    };

    NavigationView navigationView;
    Toolbar toolbar;
    ImageView ivPlay, ivPause, settle;
    boolean isPlay = true;


    @SuppressLint("ResourceType")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (VERSION.SDK_INT > 21) {
            StrictMode.setThreadPolicy(new Builder().permitAll().build());
        }
        setContentView((int) R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT > 21 && !checkPermission()) {
            requestPermission();
        }
        StepDetectionServiceHelper.startAllIfEnabled(true, this);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();

//        StepDetectionServiceHelper.startAllIfEnabled(true, MainActivity.this);

        this.navigationView = (NavigationView) findViewById(R.id.nav_views);
//        bottomNavigation.setItemIconTintList(null);
        this.ivPause = (ImageView) findViewById(R.id.ivPause);
        this.ivPlay = (ImageView) findViewById(R.id.ivPlay);
        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlay = true;
                ivPause.setVisibility(View.VISIBLE);
                ivPlay.setVisibility(View.GONE);
                editor.putBoolean(getString(R.string.pref_step_counter_enabled), true);
                editor.apply();
                StepDetectionServiceHelper.startAllIfEnabled(true, MainActivity.this);
            }
        });
        ivPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean(getString(R.string.pref_step_counter_enabled), false);
                editor.apply();
                StepDetectionServiceHelper.stopAllIfNotRequired(MainActivity.this);

                isPlay = false;
                ivPlay.setVisibility(View.VISIBLE);
                ivPause.setVisibility(View.GONE);
            }
        });
        this.settle = (ImageView) findViewById(R.id.settle);
        this.settle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPref.edit();
                Intent intent = new Intent(MainActivity.this, PreferencesActivity.class);
                startActivity(intent);
            }
        });
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
//            window.setStatusBarColor(Color.parseColor("#EF5050"));
        }
        this.toolbar = initToolbar();
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.drawer = drawerLayout;
        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout, this.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.drawer.addDrawerListener(actionBarDrawerToggle);
        this.drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            public void onDrawerClosed(View view) {
            }

            public void onDrawerOpened(View view) {
            }
        });
        actionBarDrawerToggle.syncState();
        this.navigationView.setNavigationItemSelectedListener(this);
        String str = "#ffffff";
//        this.toolbar.setTitleTextColor(Color.parseColor(str));
//        this.toolbar.getNavigationIcon().setColorFilter(Color.parseColor(str), Mode.MULTIPLY);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        this.bottomNavigation = bottomNavigationView;

        bottomNavigationView.setOnNavigationItemSelectedListener(this.navigationItemSelectedListener);
        String str2 = "";

//        MainActivity mainActivity = null;
//        openFragment(Fragment_Overview.newInstance(str2 ,str2 ,this));
        openFragment(MainFragment.newInstance(str2, str2, this));
//        ((AdView) findViewById(R.id.adView)).loadAd(new AdRequest.Builder().build());

        showBannerAdd();
    }

    public void showBannerAdd() {
        RelativeLayout adLayout = findViewById(R.id.bannerAddView);
        if (adLayout != null) {
            Log.d("AdvertActivityTag=", "load add");
            adLayout.removeAllViews();
            adLayout.setVisibility(View.VISIBLE);
            BannerView bannerView = new BannerView(this);
            bannerView.setAdId(getResources().getString(R.string.huawei_banner_add));
            bannerView.setBannerAdSize(BannerAdSize.BANNER_SIZE_360_57
            );
            AdParam adParam = new AdParam.Builder().build();
            bannerView.loadAd(adParam);
            bannerView.setAdListener(new com.huawei.hms.ads.AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    Log.d("AdvertActivityTag=", "add loaded");
                }

                @Override
                public void onAdFailed(int i) {
                    super.onAdFailed(i);
                    Log.d("AdvertActivityTag=", "add fail =" + i);
                }
            });
            adLayout.addView(bannerView);

        } else if (adLayout != null) {
            adLayout.setVisibility(View.GONE);
            adLayout.removeAllViews();
        }

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
        int result2 = ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE");
        int result3 = ContextCompat.checkSelfPermission(this, "android.permission.ACTIVITY_RECOGNITION");
        int result4 = ContextCompat.checkSelfPermission(this, "android.permission.WAKE_LOCK");
        int result5 = ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION");
        int result6 = ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION");
        if (result == 0 && result2 == 0 && result3 == 0 && result4 == 0 && result5 == 0 && result6 == 0) {
            return true;
        }
        return false;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.READ_EXTERNAL_STORAGE", "android.permission.ACTIVITY_RECOGNITION",
                "android.permission.WAKE_LOCK", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}, 1);
    }

    @RequiresApi(api = 23)
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == 0 && grantResults[1] == 0) {
                    Toast.makeText(this, "Permission Granted, Now you can access Storage.", Toast.LENGTH_SHORT).show();
                    return;
                }
                return;
            default:
                return;
        }
    }


    public void openFragment(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.nav_host_fragment, fragment);
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
    }

    public void loadFragmentworkout(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.nav_host_fragment, fragment);
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
        toolbar.setTitle("workout");
        bottomNavigation.setSelectedItemId(R.id.navigation_map);
    }

    public void loadFragment_water(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.nav_host_fragment, fragment);
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
        toolbar.setTitle("Walk & Step");
        bottomNavigation.setSelectedItemId(R.id.navigation_walk);
    }

    private Toolbar initToolbar() {
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar2);

        return toolbar2;
    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();

        String str = "android.intent.extra.TEXT";
        String str2 = "android.intent.extra.SUBJECT";


        if (itemId == R.id.nav_rateus) {
//            Intent intent = new Intent("com.huawei.appmarket.appmarket.intent.action.AppDetail.withid");
//            intent.setPackage(getPackageName());
//            intent.putExtra("appId", "104455617");
//            startActivity(intent);
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("hiapplink://com.huawei.appmarket?appId=C104455617")));
        } else if (itemId == R.id.nav_share) {

            Intent intent2 = new Intent("android.intent.action.SEND");
            intent2.setType("text/plain");
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Best Free Home Workout - Step Tracker app download now.\n Thnak You!\n  hiapplink://com.huawei.appmarket?appId=C104455617");
//            sb3.append(getApplicationContext().getPackageName());
            String sb4 = sb3.toString();
            intent2.putExtra(str2, "Share App");
            intent2.putExtra(str, sb4);
            startActivity(Intent.createChooser(intent2, "Share via"));
        } else if (itemId == R.id.nav_privacy) {

            Uri uri = Uri.parse("https://www.termsfeed.com/live/4eef6268-da08-42f2-b36d-12edf6e92397");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        }

        this.drawer.closeDrawer((int) GravityCompat.START);
        return true;
    }


    public void onBackPressed() {
        StepDetectionServiceHelper.stopAllIfNotRequired(this.getApplicationContext());
//        StepDetectionServiceHelper.startAllIfEnabled(true, MainActivity.this);

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.adview_layout_exit);
        ((GifImageView) dialog.findViewById(R.id.GifImageView)).setGifImageResource(R.drawable.rate);
        ((Button) dialog.findViewById(R.id.btnno)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ((Button) dialog.findViewById(R.id.btnrate)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    MainActivity mainActivity = MainActivity.this;
                    mainActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("hiapplink://com.huawei.appmarket?appId=C104455617")));
                } catch (ActivityNotFoundException unused) {
                    MainActivity mainActivity2 = MainActivity.this;
                    mainActivity2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName())));
                }
            }
        });
        ((Button) dialog.findViewById(R.id.btnyes)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
                MainActivity.this.finish();
//                System.exit(1);


            }
        });
        dialog.show();
    }

    private void checkConsentStatus() {

        Consent consentInfo = Consent.getInstance(this);
        consentInfo.requestConsentUpdate(new ConsentUpdateListener() {
            @Override
            public void onSuccess(ConsentStatus consentStatus, boolean isNeedConsent, List<AdProvider> adProviders) {
                // User consent status successfully updated.
                if (isNeedConsent) {
                    // If ConsentStatus is set to UNKNOWN, ask for user consent again.
                    if (consentStatus == ConsentStatus.UNKNOWN) {
//                        showConsentDialog();
                    }
                    // If ConsentStatus is set to PERSONALIZED or NON_PERSONALIZED, no dialog box is displayed to ask for user consent.
                    else {

                    }
                } else {
                }

            }

            @Override
            public void onFail(String errorDescription) {
                // Failed to update user consent status.

            }
        });

    }

//    private void showConsentDialog() {
//        // Start to process the consent dialog box.
//        ConsentDialog dialog = new ConsentDialog(this, mAdProviders);
//        dialog.setCallback(this);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.show();
//    }
}
