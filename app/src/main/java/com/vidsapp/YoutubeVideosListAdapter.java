package com.vidsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;



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
            holder.mYoutubePlaylistCount.setText("");
            holder.mYoutubePlaySubTitle.setText(mYoutubePlaylistsList.get(position).getDescription());
            holder.mYoutubePlayDist.setText(mYoutubePlaylistsList.get(position).getDescription());
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

        private TextView mYoutubePlaylistTitle = null;
        private TextView mYoutubePlaylistCount = null;
        private TextView mYoutubePlaySubTitle = null;
        private TextView mYoutubePlayDist = null;
        private ImageView mYoutubeThumb = null;
        private ImageView mPlay = null;

        public ViewHolder(View itemView) {
            super(itemView);

            mYoutubePlaylistTitle = (TextView) itemView.findViewById(R.id.youtube_playlist_title);
            mYoutubePlaylistCount = (TextView) itemView.findViewById(R.id.youtube_playlist_count);
            mYoutubePlaySubTitle = (TextView) itemView.findViewById(R.id.youtube_sub_title);
            mYoutubePlayDist = (TextView) itemView.findViewById(R.id.youtube_discription);
            mYoutubeThumb = (ImageView) itemView.findViewById(R.id.youtube_playlist_thumb);
            mPlay = (ImageView) itemView.findViewById(R.id.play);
            mPlay.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.play) {
                Intent intent = new Intent(mContext, YoutubePlayerActivity.class);
                intent.putExtra("VIDEO_ID", mYoutubePlaylistsList.get(getAdapterPosition()).getId());
                mContext.startActivity(intent);
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
