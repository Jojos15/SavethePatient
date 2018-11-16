package com.example.georg.savethepatient;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    private QuizFragment quizFragment;
    private FragmentManager manager;
    private long timeInSeconds = 0;
    private boolean[] lives = {true, true, true, true, true, true};
    private long levelTime = 0;

    final Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            try{
                timeInSeconds++;
            }
            catch (Exception e) {
                // TODO: handle exception
            }
            finally{
                //also call the same runnable to call it at regular interval
                handler.postDelayed(this, 1000);
            }
        }
    };

    ///////////////////////////////////////////////////////////////////////////
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
        }
    };

    /////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContentView = findViewById(R.id.fullscreen_content);

        levelTime = 46;
        quizFragment = new QuizFragment(1, 46, 0);
        manager = getSupportFragmentManager();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, 100);

        manager.beginTransaction().replace(R.id.fullscreen_content, quizFragment).commit();

        handler.post(runnable);
    }

    public long getTimeInSeconds() {
        return timeInSeconds;
    }

    public boolean getLives(int pos) {
        return lives[pos];
    }

    public void setLives(boolean lives, int pos) {
        this.lives[pos] = lives;
    }

    public long getLevelTime(){
        return levelTime;
    }
}

//TODO: Calculate implications, make a case for that
