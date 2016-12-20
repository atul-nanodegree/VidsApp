package com.vidsapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.vidsapp.util.VidsApplUtil;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by somendra.
 */
public class VidsFavoriteActivity extends BaseActivity {

    private YoutubeNtOVideosListEntity videoListEntity = null;
    private List<YoutubeNtOVideosListItemEntity> videoListItmeArrayList = null;
    private static final String TAG = "VidsFavoriteActivity.class";

    private RecyclerView mRecyclerView;
    private VidsFavoriteAdapter mDoclevelListAdapter;

    private ProgressBar pBar;
    private CoordinatorLayout mMainCoordinatorLayout;
    private String vidsIds;
    private RelativeLayout no_fav_layout;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite);

        //Displaying banner ads at bottom of screen

                //  mAdView = (AdView) findViewById(R.id.adView);

        mAdView=new AdView(this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(getResources().getString(R.string.banner_home_footer_favouritevideo));

        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM;
        // layoutParams.gravity = isTopPosition ? Gravity.TOP : Gravity.BOTTOM;

        this.addContentView(mAdView, layoutParams);

        AdRequest adRequest = new AdRequest.Builder()
                // Add a test device to show Test Ads
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("501CA82BA12681A250E422CC4BF70A13") //Random Text
                .build();
        mAdView.loadAd(adRequest);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setActionbarTitle("Favorites", false, R.id.toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.youtube_playlist_recyclerview);

        pBar = (ProgressBar) findViewById(R.id.progressbar);
        vidsIds = getIntent().getStringExtra(VidsApplUtil.TYPE_VIDEO);
        mDoclevelListAdapter = new VidsFavoriteAdapter(this);
        mMainCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatelayout);

        no_fav_layout = (RelativeLayout) findViewById(R.id.no_fav_layout);

        if (VidsApplUtil.isTablet(this)) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, ApplicationConstants.PLAYLIST_NUM_COLUMNS));
            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.margin_10dp);
            mRecyclerView.addItemDecoration(itemDecoration);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }

        initializeFavTextInfo();

        if (vidsIds == null || vidsIds.equals("")) {
            no_fav_layout.setVisibility(View.VISIBLE);
        } else if (!NetworkUtil.isConnected(this)) {
            Snackbar.make(mMainCoordinatorLayout, "No internet connection.Check your connection and try again", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            new YoutubeTask().execute();
        }

    }

    private class YoutubeTask extends AsyncTask<String, Integer, YoutubeNtOVideosListEntity> {
        protected YoutubeNtOVideosListEntity doInBackground(String... urls) {
            ApiYoutube a = new ApiYoutube();
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

    private void initializeFavTextInfo() {
        TextView no_fav_text = (TextView) findViewById(R.id.no_fav_text);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("To mark the video as your favorite, please tap on").append(" ");
        builder.setSpan(new ImageSpan(this, R.drawable.fav_no_info),
                builder.length() - 1, builder.length(), 0);
        builder.append("icon at the top right corner of video thumbnail.");
        no_fav_text.setText(builder);
    }

    public RelativeLayout getNoFavLayout() {
        return no_fav_layout;
    }
}
