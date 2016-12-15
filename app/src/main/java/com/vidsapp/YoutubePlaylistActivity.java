package com.vidsapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.vidsapp.util.VidsApplUtil;

import java.util.List;


/**
 * Created by atul.
 */
public class YoutubePlaylistActivity extends  BaseActivity {

    List<YoutubeVideoListItemEntity> videoListItmeArrayList = null;
    YoutubeVideoListEntity videoListEntity = null;
    private static final String TAG = "YoutubePlaylistA.class";

    private RecyclerView mRecyclerView;
    private YoutubeVideosListAdapter mDoclevelListAdapter;


    private ImageView mPlaylistAll;
    private TextView mPlaylistTitle;

    private String mId;
    private List<String> videoItemDuration = null;
    private ProgressBar pBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_playlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getIntent().getStringExtra(APITags.PLAYLIST_TITLE) != null) {

            setActionbarTitle(getIntent().getStringExtra(APITags.PLAYLIST_TITLE),false,R.id.toolbar);
        }
        else{
            setActionbarTitle("PlayList",false,R.id.toolbar);

        }


        mRecyclerView = (RecyclerView) findViewById(R.id.youtube_playlist_recyclerview);


        mPlaylistAll = (ImageView) findViewById(R.id.playall);
        mPlaylistTitle = (TextView) findViewById(R.id.video_count);

        pBar = (ProgressBar) findViewById(R.id.progressbar);

        mDoclevelListAdapter = new YoutubeVideosListAdapter(this);

        if (VidsApplUtil.isTablet(this)) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, ApplicationConstants.PLAYLIST_NUM_COLUMNS));
            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.margin_10dp);
            mRecyclerView.addItemDecoration(itemDecoration);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }

        if (getIntent().getStringExtra(APITags.ID) != null) {

            mId = getIntent().getStringExtra(APITags.ID);


            if (NetworkUtil.isConnected(YoutubePlaylistActivity.this)) {
                new YoutubeTask().execute();
            }
        }
        mPlaylistAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(YoutubePlaylistActivity.this, YoutubePlaylistPlayerActivity.class);
                i.putExtra("PLAY_LIST_ID", mId);
                startActivity(i);
            }
        });
    }

    private class YoutubeTask extends AsyncTask<String, Integer, YoutubeVideoListEntity> {
        protected YoutubeVideoListEntity doInBackground(String... urls) {
            ApiYoutube a=new ApiYoutube();
//            YoutubeNtOVideosListEntity   youtubeNtOVideosListEntity=  a.intiateAPICall("video","cQcSkiOX4c8,wspLLHypZ4M,qYCIci0BHc4,hYorcTW9apA");
            //YoutubeNtOVideosListEntity   youtubeNtOVideosListEntity=  a.intiateAPICall("channel","UCDS9hpqUEXsXUIcf0qDcBIA");
            YoutubeVideoListEntity youtubeVideoListEntity = a.intiateAPICallPlayListVideos("playlistvideo", mId);

            return youtubeVideoListEntity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pBar.setVisibility(View.VISIBLE);
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(YoutubeVideoListEntity result) {
            videoListEntity = result;
            pBar.setVisibility(View.GONE);
            if (videoListEntity != null) {
                videoListItmeArrayList = videoListEntity.getItems();

                if (videoListItmeArrayList != null && videoListItmeArrayList.size() > 0) {


                    mDoclevelListAdapter.setDataList(videoListItmeArrayList);

                    mRecyclerView.setAdapter(mDoclevelListAdapter);
                    mPlaylistAll.setVisibility(View.VISIBLE);
                    mPlaylistTitle.setVisibility(View.VISIBLE);


                        mPlaylistTitle.setText(videoListItmeArrayList.size()+" "+"Videos");



                } else {
                  //  Toast.makeText(, "videoListItmeArrayList is null", Toast.LENGTH_SHORT).show();
                }
            } else {
               // Toast.makeText(mContext, "videoListEntity is null", Toast.LENGTH_SHORT).show();
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
