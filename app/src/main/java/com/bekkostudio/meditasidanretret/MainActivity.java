package com.bekkostudio.meditasidanretret;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.bekkostudio.meditasidanretret.Course.CourseFragment;
import com.bekkostudio.meditasidanretret.Course.Retret.BillingParameter;
import com.bekkostudio.meditasidanretret.Timer.TimerFragment;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_timer:
                    fragment = new TimerFragment();
                    break;
                case R.id.navigation_article:
                    fragment = new ArticleFragment();
                    break;
                case R.id.navigation_course:
                    fragment = new CourseFragment();
                    break;
                case R.id.navigation_about:
                    fragment = new AboutFragment();
                    break;
            }
            return loadFragment(fragment);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //loading the default fragment
        loadFragment(new TimerFragment());

        //get recent meditation database
        Global.getRecentMeditation(getApplicationContext());

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //get active retret database
        Global.getActiveRetret(getApplicationContext());

        //get active retret detail database
        Global.getActiveRetretDetail(getApplicationContext());

        //debug only, refresh database
        //Global.initializeRetretDetail();
        //Global.setActiveRetretDetail(getApplicationContext());

        //Log.d("Check title", "active title: "+Global.courseRetret.get(Global.activeRetretId).title);


        //debug refresh retret end date
        //Global.setActiveRetret(getApplicationContext(),Global.activeRetretId,"");

    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (Global.currentWebview != null){
            if (Global.currentWebview.canGoBack()) {
                Global.currentWebview.goBack();
            } else {
                exitingApp();
            }
        } else {
            exitingApp();
        }
    }

    public void exitingApp(){
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }

}
