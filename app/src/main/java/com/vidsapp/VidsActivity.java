package com.vidsapp;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.vidsapp.util.VidsApplUtil;

public class VidsActivity extends AppCompatActivity {

    private Spinner categorySpinner, subCategorySpinner;
    private  CoordinatorLayout mMainCoordinatorLayout;
    private Fragment mVideoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vids);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_launcher);
        mMainCoordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatelayout);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        initializeVidsCategory();
        mVideoFragment = new VideosListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_frame, mVideoFragment, mVideoFragment.getClass().getSimpleName()).commit();

    }

    private void initializeVidsCategory() {
        categorySpinner = (Spinner) findViewById(R.id.category);
        ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text,
                getResources().getStringArray(R.array.vids_category));
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(catAdapter);
        categorySpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        String selectedCategory = parent.getItemAtPosition(pos).toString();
                        if (selectedCategory != null) {
                            if (selectedCategory.equalsIgnoreCase("Home remedies")) {
                                initializeVidsSubCategory(getResources().getStringArray(R.array.home_remedies_list));
                            }
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
    }

    private void initializeVidsSubCategory(String[] subCategory) {
        subCategorySpinner = (Spinner) findViewById(R.id.sub_category);
        ArrayAdapter<String> subCatAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_text, subCategory);
        subCatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subCategorySpinner.setAdapter(subCatAdapter);
        subCategorySpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        String selectedSubCategory = parent.getItemAtPosition(pos).toString();
                        if (selectedSubCategory != null) {
                            if(!NetworkUtil.isConnected(VidsActivity.this)) {
                                Snackbar.make(mMainCoordinatorLayout, "No internet connection.Check your connection and try again", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                return;
                            }
                            String formatedVidsList = null;
                            if (selectedSubCategory.equalsIgnoreCase("High blood pressure")) {
                                formatedVidsList = VidsApplUtil.formatVidsList(
                                        getResources().getStringArray(R.array.high_bp_vids));
                            } else if (selectedSubCategory.equalsIgnoreCase("Diabetes")) {
                                formatedVidsList = VidsApplUtil.formatVidsList(
                                        getResources().getStringArray(R.array.diabetes_vids));
                            } else if (selectedSubCategory.equalsIgnoreCase("Obesity")) {
                                formatedVidsList = VidsApplUtil.formatVidsList(
                                        getResources().getStringArray(R.array.obesity_vids));
                            }
                            if (listener != null) {
                                listener.onFetchVideo(VidsApplUtil.TYPE_VIDEO, formatedVidsList);
                            }
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!NetworkUtil.isConnected(this)) {
            Snackbar.make(mMainCoordinatorLayout, "No internet connection.Check your connection and try again", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vids, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private FetchVidsListener listener;
    public void setFetchVideoListener(FetchVidsListener fvListener) {
        this.listener = fvListener;
    }

    public interface FetchVidsListener {
        public void onFetchVideo(String vidsType, String vidsIds);
    }
}
