package com.vidsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.vidsapp.VidsActivity;

/**
 * Created by atul.
 */
public class SplashActivity extends Activity {

    /**
     * Splash delay seconds
     */
    public static final long mSplashTimeDelay = 2 * 1000;

    /**
     * handler object which handles the certain period of time to hold the screen.
     */
    private Handler mHandler = null;

    /**
     * Runnable object which is used in the handler to do the operations on completion of the milleseconds
     */
    private SplashRunnableThread mSplashRunnableThread = null;

    private static final String TAG = "SplashActivity.class";

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_layout);

        initFields();

    }

    /**
     * initializing all the necessary objects which is used in the screen
     */
    private void initFields() {
        mHandler = new Handler();
        mSplashRunnableThread = new SplashRunnableThread();
        mHandler.postDelayed(mSplashRunnableThread, mSplashTimeDelay);

    }

    /**
     * life cycle method is called once it is finishing the activity and doing the operation like removing the handler objects and clearing all the objects.
     * {@inheritDoc}
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeRunnableHandlers();
        clearAllObjects();
    }

    /**
     * removes the callbacks on exit of the screen
     */
    private void removeRunnableHandlers() {
        if (mHandler != null) {
            mHandler.removeCallbacks(mSplashRunnableThread);
        }
    }

    /**
     * clears all the objects used in the class
     */
    private void clearAllObjects() {
        mHandler = null;
        mSplashRunnableThread = null;
    }

    /**
     * Runnable class which navigates screen onces the handler finishes the specified milliseconds
     */
    public class SplashRunnableThread implements Runnable {

        @Override
        public void run() {


                Intent intent = new Intent(SplashActivity.this, VidsActivity.class);
                startActivity(intent);
                finish();


            }
        }
    }

