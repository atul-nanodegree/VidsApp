package com.vidsapp;

import java.util.List;

/**
 * Created by atul.
 */
public class YoutubePlayListEntity {

    private List<YoutubePlayListItemEntity> items;
    private String nextPageToken;
    private int totalPlayListCount;
    private List<Long> videoCounts;
    public List<YoutubePlayListItemEntity> getItems() {
        return items;
    }

    public void setItems(List<YoutubePlayListItemEntity> items) {
        this.items = items;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public int getTotalPlayListCount() {
        return totalPlayListCount;
    }

    public void setTotalPlayListCount(int totalPlayListCount) {
        this.totalPlayListCount = totalPlayListCount;
    }

    public List<Long> getVideoCounts() {
        return videoCounts;
    }

    public void setVideoCounts(List<Long> videoCounts) {
        this.videoCounts = videoCounts;
    }
}
