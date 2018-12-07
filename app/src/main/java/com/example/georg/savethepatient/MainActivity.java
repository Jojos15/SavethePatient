package com.example.georg.savethepatient;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    private FragmentManager manager;
    private long timeInSeconds = 0;
    private MenuFragment menuFragment;
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

        menuFragment = new MenuFragment();
        manager = getSupportFragmentManager();

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, 100);

        goToMenu();
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

    public void switchLevel(long timePassed, int level){
        if(timeInSeconds == 0){
            handler.post(runnable);
        }
        else timeInSeconds = timePassed;
        int levelTime;
        if(level == 1) {
         levelTime = 30;
        }
        else if(level==2){
            levelTime = 45;
        }
        else levelTime = 60;
        QuizFragment quizFragment = new QuizFragment(level, levelTime);
        manager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.fullscreen_content, quizFragment).commit();


    }

    public void switchLevel( String text){
        AnnouncementFragment announcementFragment = new AnnouncementFragment(text, timeInSeconds);
        manager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.fullscreen_content, announcementFragment).commit();


    }

    public void goToMenu(){
        manager.beginTransaction().replace(R.id.fullscreen_content, menuFragment).commit();
    }

    public boolean[] getLives() {
        return lives;
    }
}

//TODO: Calculate implications, make a case for that   LOADING
//TODO: Make case for questions with asterisk LOADING
//TODO: Make menu ADDFEATURE
//TODO: Add icons in Announcemnt fragment ADDBEAUTIFULLISM
//TODO: Update Fundapter only on results MUST