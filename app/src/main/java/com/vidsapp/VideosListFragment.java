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
public class VideosListFragment extends Fragment implements VidsActivity.FetchVidsListener {


    private List<YoutubeNtOVideosListItemEntity> videoListItmeArrayList = null;
    private YoutubeNtOVideosListEntity           videoListEntity        = null;
    public static String                         pageToken              = null;

    private RecyclerView mRecyclerView;
    private YoutubeNewTOldVideosListAdapter      mDoclevelListAdapter;

    private Context                              mContext;
    private static final String                  TAG                    = "VideoListF.class";

    // private View rootView;

    private String vidsType, vidsIds;

    private ProgressBar pBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.youtube_ntovideolists_fragment, container, false);

        mContext = getActivity();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.youtube_videolists_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        VidsActivity activity = (VidsActivity) mContext;
        activity.setFetchVideoListener(this);
        mDoclevelListAdapter = new YoutubeNewTOldVideosListAdapter(mContext);
        if (VidsApplUtil.isTablet(getActivity().getBaseContext())) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, ApplicationConstants.PLAYLIST_NUM_COLUMNS));
            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext, R.dimen.margin_10dp);
            mRecyclerView.addItemDecoration(itemDecoration);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }
        pBar = (ProgressBar) rootView.findViewById(R.id.progressbar);
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
        this.vidsType = vidsType;
        this.vidsIds = vidsIds;
        if (NetworkUtil.isConnected(mContext)) {
            if(VidsApplUtil.TYPE_VIDEO.equals(vidsType)){
                new YoutubeTask().execute();

            }
        }
    }

    @Override
    public void onFetchPl(String vidsType, String plIds) {

    }

    private class YoutubeTask extends AsyncTask<String, Integer, YoutubeNtOVideosListEntity> {
        protected YoutubeNtOVideosListEntity doInBackground(String... urls) {
            ApiYoutube a=new ApiYoutube();
//            YoutubeNtOVideosListEntity   youtubeNtOVideosListEntity=  a.intiateAPICall("video","cQcSkiOX4c8,wspLLHypZ4M,qYCIci0BHc4,hYorcTW9apA");
            //YoutubeNtOVideosListEntity   youtubeNtOVideosListEntity=  a.intiateAPICall("channel","UCDS9hpqUEXsXUIcf0qDcBIA");
            YoutubeNtOVideosListEntity youtubeNtOVideosListEntity = a.intiateAPICall(vidsType, vidsIds);

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
                    Toast.makeText(mContext, "videoListItmeArrayList is null", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "videoListEntity is null", Toast.LENGTH_SHORT).show();
            }

            //  Log.i("logs for youtube",result.getNextPageToken());

        }
    }

    public YoutubeNewTOldVideosListAdapter getAdapter() {
        return mDoclevelListAdapter;
    }

}
