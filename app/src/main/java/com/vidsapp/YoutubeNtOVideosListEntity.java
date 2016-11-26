package com.vidsapp;

import java.util.List;

/**
 * Created by atul.
 */
public class YoutubeNtOVideosListEntity {

    private List<YoutubeNtOVideosListItemEntity> items;
    private String nextPageToken;
    private int totalVideoListCount;

    public List<YoutubeNtOVideosListItemEntity> getItems() {
        return items;
    }

    public void setItems(List<YoutubeNtOVideosListItemEntity> items) {
        this.items = items;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public int getTotalVideoListCount() {
        return totalVideoListCount;
    }

    public void setTotalVideoListCount(int totalVideoListCount) {
        this.totalVideoListCount = totalVideoListCount;
    }
}
