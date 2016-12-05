package com.vidsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vidsapp.util.VidsApplUtil;


import java.util.List;

/**
 * Created by atul.
 */
public class YoutubeVideosListAdapter extends RecyclerView.Adapter<YoutubeVideosListAdapter.ViewHolder> {

    /**
     * calling Context
     */
    private final Context mContext;

    /**
     * infiater fo inflate the list item
     */
    private final LayoutInflater mLayoutInflater;

    private List<YoutubeVideoListItemEntity> mYoutubePlaylistsList;

    public YoutubeVideosListAdapter(Context mContext) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.yt_playlist_item, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (mYoutubePlaylistsList != null) {
            holder.mYoutubePlaylistTitle.setText(mYoutubePlaylistsList.get(position).getTitle());

            Picasso.with(mContext).load(mYoutubePlaylistsList.get(position).getThumbnailURL()).into(holder.mYoutubeThumb);
            holder.setAppropriateFavIcon(mYoutubePlaylistsList.get(position).getId());
        }
    }

    @Override
    public int getItemCount() {
        if (mYoutubePlaylistsList != null) {
            return mYoutubePlaylistsList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mYoutubePlaylistTitle = null;
        private ImageView mYoutubeShare = null;
        private ImageView mYoutubeThumb = null;
        private ImageView mPlay = null;
        private ImageView mYoutubeFavYes = null;
        private ImageView mYoutubeFavNo = null;

        public ViewHolder(View itemView) {
            super(itemView);

            mYoutubePlaylistTitle = (TextView) itemView.findViewById(R.id.youtube_playlist_title);
            mYoutubeShare = (ImageView) itemView.findViewById(R.id.share);
            mYoutubeFavYes = (ImageView) itemView.findViewById(R.id.fav_yes);
            mYoutubeFavNo = (ImageView) itemView.findViewById(R.id.fav_no);

            mYoutubeThumb = (ImageView) itemView.findViewById(R.id.youtube_playlist_thumb);
            mPlay = (ImageView) itemView.findViewById(R.id.play);
            mPlay.setOnClickListener(this);
            mYoutubeShare.setOnClickListener(this);
            mYoutubeFavYes.setOnClickListener(this);
            mYoutubeFavNo.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.play) {
                Intent intent = new Intent(mContext, YoutubePlayerActivity.class);
                intent.putExtra("VIDEO_ID", mYoutubePlaylistsList.get(getAdapterPosition()).getId());
                mContext.startActivity(intent);
            }
            else  if (v.getId() == R.id.share) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v="+mYoutubePlaylistsList.get(getAdapterPosition()).getId());
                shareIntent.setType("text/plain");
                mContext.startActivity(Intent.createChooser(shareIntent, "Share this Video with..."));

            } else  if (v.getId() == R.id.fav_no) {
                // persist the video id in internal file storage
                addToFavorite();
            } else  if (v.getId() == R.id.fav_yes) {
                // remove the video from internal file storage
                removeFromFavorite();
            }
        }

        private void addToFavorite() {
            String videoId = mYoutubePlaylistsList.get(getAdapterPosition()).getId();
            String currentData = VidsApplUtil.readDataFromFile(mContext, VidsApplUtil.FAV_FILE_NAME);
            String finalData = currentData + "," + videoId;
            VidsApplUtil.writeDataInFile(mContext, VidsApplUtil.FAV_FILE_NAME,
                    finalData);
            notifyDataSetChanged();
            Toast.makeText(mContext, "Added to favorite", Toast.LENGTH_SHORT).show();
            Log.i("VidsActivity", "Fav file data = " + VidsApplUtil.readDataFromFile(mContext, VidsApplUtil.FAV_FILE_NAME));
        }

        private void removeFromFavorite() {
            String videoId = mYoutubePlaylistsList.get(getAdapterPosition()).getId();
            String currentData = VidsApplUtil.readDataFromFile(mContext, VidsApplUtil.FAV_FILE_NAME);
            String finalData = currentData.replace(","+videoId, "");
            VidsApplUtil.writeDataInFile(mContext, VidsApplUtil.FAV_FILE_NAME,
                    finalData);
            notifyDataSetChanged();
            Toast.makeText(mContext, "Removed to favorite", Toast.LENGTH_SHORT).show();
            Log.i("VidsActivity", "Fav file data = " + VidsApplUtil.readDataFromFile(mContext, VidsApplUtil.FAV_FILE_NAME));
        }

        private void setAppropriateFavIcon(String videoId) {
            String vidId = null;
            vidId = VidsApplUtil.readDataFromFile(mContext, VidsApplUtil.FAV_FILE_NAME);
            if (vidId == null || vidId == "" || !vidId.contains(videoId)) {
                mYoutubeFavYes.setVisibility(View.GONE);
                mYoutubeFavNo.setVisibility(View.VISIBLE);
            } else if (vidId.contains(videoId)) {
                mYoutubeFavYes.setVisibility(View.VISIBLE);
                mYoutubeFavNo.setVisibility(View.GONE);
            }
        }
    }

    public void setDataList(List<YoutubeVideoListItemEntity> docsList) {

        if (mYoutubePlaylistsList == null) {
            mYoutubePlaylistsList = docsList;
        } else {
            mYoutubePlaylistsList.clear();
            mYoutubePlaylistsList.addAll(docsList);
        }
        notifyDataSetChanged();
    }
}

