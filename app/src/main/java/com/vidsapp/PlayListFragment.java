package com.vidsapp;

import android.content.Context;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.util.List;

public class PlayListFragment extends Fragment {


    private List<YoutubePlayListItemEntity> playListItmeArrayList = null;
    private List<Long> playListItmeCount = null;
    private YoutubePlayListEntity playListEntity = null;
    private RecyclerView mRecyclerView;
    private YoutubePlayListsAdapter mDoclevelListAdapter;

    private Context mContext;
    private static final String TAG = "PlayListFragment.class";
    private ProgressBar pBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.youtube_playlists_fragment, container, false);

        mContext = getActivity();


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.youtube_playlists_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mDoclevelListAdapter = new YoutubePlayListsAdapter(mContext);
        pBar = (ProgressBar) rootView.findViewById(R.id.progressbar);
        {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            if (NetworkUtil.isConnected(mContext)) {
                new YoutubeTask().execute();
            }

            return rootView;
        }


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        ViewGroup viewGroup = (ViewGroup) getView();
        viewGroup.removeAllViewsInLayout();
        View view = onCreateView(getActivity().getLayoutInflater(), viewGroup, null);
        viewGroup.addView(view);
    }


    private class YoutubeTask extends AsyncTask<String, Integer, YoutubePlayListEntity> {
        protected YoutubePlayListEntity doInBackground(String... urls) {
            ApiYoutube a=new ApiYoutube();
//            YoutubeNtOVideosListEntity   youtubeNtOVideosListEntity=  a.intiateAPICall("video","cQcSkiOX4c8,wspLLHypZ4M,qYCIci0BHc4,hYorcTW9apA");
            //YoutubeNtOVideosListEntity   youtubeNtOVideosListEntity=  a.intiateAPICall("channel","UCDS9hpqUEXsXUIcf0qDcBIA");
            YoutubePlayListEntity youtubePlayListEntity = a.intiateAPICallPlayList("playlist","zeetv" );

            return youtubePlayListEntity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pBar.setVisibility(View.VISIBLE);
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(YoutubePlayListEntity result) {
            playListEntity = result;
            pBar.setVisibility(View.GONE);
            if (playListEntity != null) {
                playListItmeArrayList = playListEntity.getItems();
                if (playListItmeArrayList != null && playListItmeArrayList.size() > 0) {
                    mDoclevelListAdapter.setDataList(playListItmeArrayList);

                    mRecyclerView.setAdapter(mDoclevelListAdapter);

                } else {
                    Toast.makeText(mContext, "videoListItmeArrayList is null", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "videoListEntity is null", Toast.LENGTH_SHORT).show();
            }

            //  Log.i("logs for youtube",result.getNextPageToken());

        }
    }

}