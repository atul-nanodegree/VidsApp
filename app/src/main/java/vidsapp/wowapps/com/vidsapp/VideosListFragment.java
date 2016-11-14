package vidsapp.wowapps.com.vidsapp;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

public class VideosListFragment extends Fragment {


    private List<YoutubeNtOVideosListItemEntity> videoListItmeArrayList = null;
    private YoutubeNtOVideosListEntity           videoListEntity        = null;
    public static String                         pageToken              = null;

    private RecyclerView mRecyclerView;
    private YoutubeNewTOldVideosListAdapter      mDoclevelListAdapter;

    private Context                              mContext;
    private static final String                  TAG                    = "VideoListF.class";

    // private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.youtube_ntovideolists_fragment, container, false);

        mContext = getActivity();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.youtube_videolists_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));


        mDoclevelListAdapter = new YoutubeNewTOldVideosListAdapter(mContext);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        if (NetworkUtil.isConnected(mContext)) {

            new YoutubeTask().execute();
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


    private class YoutubeTask extends AsyncTask<String, Integer, YoutubeNtOVideosListEntity> {
        protected YoutubeNtOVideosListEntity doInBackground(String... urls) {
            ApiYoutube a=new ApiYoutube();
            YoutubeNtOVideosListEntity   youtubeNtOVideosListEntity=  a.intiateAPICall();

            return youtubeNtOVideosListEntity;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(YoutubeNtOVideosListEntity result) {
            videoListEntity =result;
            videoListItmeArrayList = videoListEntity.getItems();

            if (videoListItmeArrayList != null && videoListItmeArrayList.size() > 0) {



                            mDoclevelListAdapter.setDataList(videoListItmeArrayList);

                            mRecyclerView.setAdapter(mDoclevelListAdapter);


            } else {
                Toast.makeText(mContext, "no docs available for this agenda item ", Toast.LENGTH_SHORT).show();
            }


          //  Log.i("logs for youtube",result.getNextPageToken());

        }
    }


}
