package com.vidsapp;



import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by atul.
 */
public class BaseActivity extends AppCompatActivity {

    public Toolbar mToolbar;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * set actionbar title and back btn
     *
     * @param title    action bar title
     * @param isParent false to set home back btn
     * @param id       toolbar resource id of included view
     */
    public void setActionbarTitle(String title, Boolean isParent, int id) {

        mToolbar = (Toolbar) findViewById(id);
        mToolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        setSupportActionBar(mToolbar);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.drawable.back_icon);

        if (title != null) {
            mActionBar.setTitle(title);
        }
    }
}
