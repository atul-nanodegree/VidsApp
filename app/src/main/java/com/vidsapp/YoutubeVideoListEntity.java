package com.vidsapp;

import java.util.List;

/**
 * Created by atul.
 */
public class YoutubeVideoListEntity {

    private List<YoutubeVideoListItemEntity> items;
    private String nextPageToken;
    private int totalVideos;
    private List<String> videoDuration;

    public List<String> getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(List<String> videoDuration) {
        this.videoDuration = videoDuration;
    }

    public List<YoutubeVideoListItemEntity> getItems() {
        return items;
    }

    public void setItems(List<YoutubeVideoListItemEntity> items) {
        this.items = items;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public int getTotalVideos() {
        return totalVideos;
    }

    public void setTotalVideos(int totalVideos) {
        this.totalVideos = totalVideos;
    }

}
