package com.vidsapp;

import android.app.Activity;
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
 * Created by somendra on 06-Dec-16.
 */
public class VidsFavoriteAdapter extends RecyclerView.Adapter<VidsFavoriteAdapter.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private List<YoutubeNtOVideosListItemEntity> mYoutubePlaylistsList;

    public VidsFavoriteAdapter(Context mContext) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.vids_favorite_item, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (mYoutubePlaylistsList != null) {

            holder.mYoutubePlaySubTitle.setText(mYoutubePlaylistsList.get(position).getTitle());
            Picasso.with(mContext).load(mYoutubePlaylistsList.get(position).getThumbnailURL()).into(holder.mYoutubeThumb);
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

        private ImageView mYoutubeShare = null;
        private TextView mYoutubePlaySubTitle = null;
        private ImageView mYoutubeThumb = null;
        private ImageView mPlay = null;
        private ImageView mYoutubeFavRemove = null;

        public ViewHolder(View itemView) {
            super(itemView);

            mYoutubeShare = (ImageView) itemView.findViewById(R.id.share);
            mYoutubePlaySubTitle = (TextView) itemView.findViewById(R.id.youtube_sub_title);
            mYoutubeThumb = (ImageView) itemView.findViewById(R.id.youtube_playlist_thumb);
            mPlay = (ImageView) itemView.findViewById(R.id.play);
            mYoutubeFavRemove = (ImageView) itemView.findViewById(R.id.fav_remove);
            mPlay.setOnClickListener(this);
            mYoutubeShare.setOnClickListener(this);
            mYoutubeFavRemove.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.play) {
                Intent intent = new Intent(mContext, YoutubePlayerActivity.class);
                intent.putExtra("VIDEO_ID", mYoutubePlaylistsList.get(getAdapterPosition()).getId());
                mContext.startActivity(intent);
            } else if (v.getId() == R.id.share) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v=" + mYoutubePlaylistsList.get(getAdapterPosition()).getId());
                shareIntent.setType("text/plain");
                mContext.startActivity(Intent.createChooser(shareIntent, "Share this Video with..."));

            } else if (v.getId() == R.id.fav_remove) {
                // remove the video from internal file storage and from list
                removeFromFavorite();
            }
        }

        private void removeFromFavorite() {
            String videoId = mYoutubePlaylistsList.get(getAdapterPosition()).getId();
            String currentData = VidsApplUtil.readDataFromFile(mContext, VidsApplUtil.FAV_FILE_NAME);
            String finalData = currentData.replace("," + videoId, "");
            VidsApplUtil.writeDataInFile(mContext, VidsApplUtil.FAV_FILE_NAME,
                    finalData);
            Toast.makeText(mContext, mContext.getResources().getString(
                    R.string.remove_favorite), Toast.LENGTH_SHORT).show();
            Log.i("VidsActivity", "Fav file data = " + VidsApplUtil.readDataFromFile(mContext, VidsApplUtil.FAV_FILE_NAME));
            mYoutubePlaylistsList.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            notifyItemRangeChanged(getAdapterPosition(), mYoutubePlaylistsList.size());

            if (mYoutubePlaylistsList.size() == 0) {
                VidsFavoriteActivity vidsFavActivity = (VidsFavoriteActivity) mContext;
                vidsFavActivity.getNoFavLayout().setVisibility(View.VISIBLE);
            }
        }

    }

    public void setDataList(List<YoutubeNtOVideosListItemEntity> docsList) {

        if (mYoutubePlaylistsList == null) {
            mYoutubePlaylistsList = docsList;
        } else {
            mYoutubePlaylistsList.clear();
            mYoutubePlaylistsList.addAll(docsList);
        }
        notifyDataSetChanged();
    }
}
