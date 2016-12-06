package com.vidsapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vidsapp.util.VidsApplUtil;

import java.util.List;

/**
 * Created by somendra.
 */
public class VidsFavoriteActivity extends BaseActivity {

    private YoutubeNtOVideosListEntity           videoListEntity        = null;
    private List<YoutubeNtOVideosListItemEntity> videoListItmeArrayList = null;
    private static final String TAG = "VidsFavoriteActivity.class";

    private RecyclerView mRecyclerView;
    private VidsFavoriteAdapter mDoclevelListAdapter;

    private List<String> videoItemDuration = null;
    private ProgressBar pBar;
    private CoordinatorLayout mMainCoordinatorLayout;
    private String vidsIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setActionbarTitle("Favorites", false, R.id.toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.youtube_playlist_recyclerview);

        pBar = (ProgressBar) findViewById(R.id.progressbar);
        vidsIds = getIntent().getStringExtra(VidsApplUtil.TYPE_VIDEO);
        mDoclevelListAdapter = new VidsFavoriteAdapter(this);
        mMainCoordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatelayout);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        if (vidsIds == null || vidsIds =="") {
            // TBD show some message
        } else  if(!NetworkUtil.isConnected(this)) {
            Snackbar.make(mMainCoordinatorLayout, "No internet connection.Check your connection and try again", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            // TBD start the Asynctask here to load
            new YoutubeTask().execute();
        }

    }

    private class YoutubeTask extends AsyncTask<String, Integer, YoutubeNtOVideosListEntity> {
        protected YoutubeNtOVideosListEntity doInBackground(String... urls) {
            ApiYoutube a=new ApiYoutube();
//            YoutubeNtOVideosListEntity   youtubeNtOVideosListEntity=  a.intiateAPICall("video","cQcSkiOX4c8,wspLLHypZ4M,qYCIci0BHc4,hYorcTW9apA");
            //YoutubeNtOVideosListEntity   youtubeNtOVideosListEntity=  a.intiateAPICall("channel","UCDS9hpqUEXsXUIcf0qDcBIA");
            YoutubeNtOVideosListEntity youtubeNtOVideosListEntity = a.intiateAPICall(VidsApplUtil.TYPE_VIDEO, vidsIds);

            return youtubeNtOVideosListEntity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pBar.setVisibility(View.VISIBLE);
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(YoutubeNtOVideosListEntity result) {
            videoListEntity = result;
            pBar.setVisibility(View.GONE);
            if (videoListEntity != null) {
                videoListItmeArrayList = videoListEntity.getItems();

                if (videoListItmeArrayList != null && videoListItmeArrayList.size() > 0) {


                    mDoclevelListAdapter.setDataList(videoListItmeArrayList);

                    mRecyclerView.setAdapter(mDoclevelListAdapter);


                } else {
                    Toast.makeText(VidsFavoriteActivity.this, "videoListItmeArrayList is null", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(VidsFavoriteActivity.this, "videoListEntity is null", Toast.LENGTH_SHORT).show();
            }

            //  Log.i("logs for youtube",result.getNextPageToken());

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
