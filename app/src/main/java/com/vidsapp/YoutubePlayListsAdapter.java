package com.vidsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;



import java.util.List;

/**
 * Created by atul.
 */
public class YoutubePlayListsAdapter extends RecyclerView.Adapter<YoutubePlayListsAdapter.ViewHolder> {

    /**
     * calling Context
     */
    private final Context mContext;

    /**
     * infiater fo inflate the list item
     */
    private final LayoutInflater mLayoutInflater;

    private List<YoutubePlayListItemEntity> mYoutubePlaylistsList;

    public YoutubePlayListsAdapter(Context mContext) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.youtube_playlists_item, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (mYoutubePlaylistsList != null) {

            holder.mYoutubePlaylistTitle.setText("# " + mYoutubePlaylistsList.get(position).getTitle());
            holder.mYoutubePlaylistCount.setText("");
            Picasso.with(mContext).load(mYoutubePlaylistsList.get(position).getThumbnailURLMedium()).into(holder.mYoutubeThumb);
            Picasso.with(mContext).load(mYoutubePlaylistsList.get(position).getThumbnailURLMedium()).into(holder.mYoutubePicOneIv);
            Picasso.with(mContext).load(mYoutubePlaylistsList.get(position).getThumbnailURLMedium()).into(holder.mYoutubePicTwoIv);
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
        private ImageView mYoutubeThumb = null;
        private ImageView mPlayAll = null;
        private ImageView mYoutubePicOneIv = null;
        private ImageView mYoutubePicTwoIv = null;
        private LinearLayout mYoutubeThreeView = null;


        public ViewHolder(View itemView) {
            super(itemView);

            mYoutubePlaylistTitle = (TextView) itemView.findViewById(R.id.youtube_playlist_title);
            mYoutubePlaylistCount = (TextView) itemView.findViewById(R.id.youtube_playlist_count);
            mYoutubeThumb = (ImageView) itemView.findViewById(R.id.youtube_playlist_thumb);
            mYoutubePicOneIv = (ImageView) itemView.findViewById(R.id.youtube_playlist_thumb_one);
            mYoutubePicTwoIv = (ImageView) itemView.findViewById(R.id.youtube_playlist_thumb_two);
            mYoutubeThreeView = (LinearLayout) itemView.findViewById(R.id.playlist_thumbnail_ll);
            mPlayAll = (ImageView) itemView.findViewById(R.id.play_all);
            mPlayAll.setOnClickListener(this);
            mYoutubeThreeView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.play_all) {
                Intent intent = new Intent(mContext, YoutubePlaylistActivity.class);
                intent.putExtra(APITags.ID, mYoutubePlaylistsList.get(getAdapterPosition()).getId());
                intent.putExtra(APITags.NAME, mYoutubePlaylistsList.get(getAdapterPosition()).getTitle());
                intent.putExtra(APITags.DESCRIPTION, mYoutubePlaylistsList.get(getAdapterPosition()).getDescription());
                ((Activity) mContext).startActivityForResult(intent, 20001);
            }


        }
    }

    public void setDataList(List<YoutubePlayListItemEntity> docsList) {

        if (mYoutubePlaylistsList == null) {
            mYoutubePlaylistsList = docsList;
        } else {
            mYoutubePlaylistsList.clear();
            mYoutubePlaylistsList.addAll(docsList);
        }
        notifyDataSetChanged();
    }
}

