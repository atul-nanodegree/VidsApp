package com.vidsapp;

import android.content.Context;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.vidsapp.util.VidsApplUtil;

import java.util.List;


/**
 * Created by atul.
 */
public class PlayListFragment extends Fragment implements VidsActivity.FetchVidsListener {


    private List<YoutubePlayListItemEntity> playListItmeArrayList = null;
    private List<Long> playListItmeCount = null;
    private YoutubePlayListEntity playListEntity = null;
    private RecyclerView mRecyclerView;
    private YoutubePlayListsAdapter mDoclevelListAdapter;
    private String vidsType, vidsIds;
    private Context mContext;
    private static final String TAG = "PlayListFragment.class";
    private ProgressBar pBar;

    public PlayListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.youtube_playlists_fragment, container, false);

        mContext = getActivity();


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.youtube_playlists_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        VidsActivity activity = (VidsActivity) mContext;
        activity.setFetchPlListener(this);
        mDoclevelListAdapter = new YoutubePlayListsAdapter(mContext);
        pBar = (ProgressBar) rootView.findViewById(R.id.progressbar);
        if (VidsApplUtil.isTablet(getActivity())) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, ApplicationConstants.PLAYLIST_NUM_COLUMNS));
            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext, R.dimen.margin_10dp);
            mRecyclerView.addItemDecoration(itemDecoration);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        } else  {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        }

        return rootView;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        ViewGroup viewGroup = (ViewGroup) getView();
        viewGroup.removeAllViewsInLayout();
        View view = onCreateView(getActivity().getLayoutInflater(), viewGroup, null);
        viewGroup.addView(view);
    }

    @Override
    public void onFetchVideo(String vidsType, String vidsIds) {

    }
    @Override
    public void onFetchPl(String vidsType, String plIds) {
        this.vidsType = vidsType;
        this.vidsIds = plIds;
        if (NetworkUtil.isConnected(mContext)) {
                new YoutubeTask().execute();


        }
    }

    private class YoutubeTask extends AsyncTask<String, Integer, YoutubePlayListEntity> {
        protected YoutubePlayListEntity doInBackground(String... urls) {
            ApiYoutube a=new ApiYoutube();
//            YoutubeNtOVideosListEntity   youtubeNtOVideosListEntity=  a.intiateAPICall("video","cQcSkiOX4c8,wspLLHypZ4M,qYCIci0BHc4,hYorcTW9apA");
            //YoutubeNtOVideosListEntity   youtubeNtOVideosListEntity=  a.intiateAPICall("channel","UCDS9hpqUEXsXUIcf0qDcBIA");
            YoutubePlayListEntity youtubePlayListEntity=null;
            if(VidsApplUtil.TYPE_CHANNEL.equals(vidsType)){
                 youtubePlayListEntity = a.intiateAPICallPlayList("playlist",vidsIds );

            }
            else if(VidsApplUtil.TYPE_PLAYLIST.equals(vidsType)){
                 youtubePlayListEntity = a.intiateAPICallPlayListCustom("playlistcustom",vidsIds );

            }
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