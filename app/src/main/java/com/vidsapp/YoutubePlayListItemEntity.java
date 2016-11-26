package com.vidsapp;

import com.google.api.client.util.DateTime;

/**
 * Created by atul.
 */
public class YoutubePlayListItemEntity {

    private String title;
    private String description;
    private String thumbnailURLMedium;
    private String thumbnailURLHigh;
    private String thumbnailURLDefault;
    private String id;
    private DateTime publishedAt;
    private String liveBroadcastContent;
    private Long videoCounts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailURLHigh() {
        return thumbnailURLHigh;
    }

    public void setThumbnailURLHigh(String thumbnailURLHigh) {
        this.thumbnailURLHigh = thumbnailURLHigh;
    }

    public String getThumbnailURLDefault() {
        return thumbnailURLDefault;
    }

    public void setThumbnailURLDefault(String thumbnailURLDefault) {
        this.thumbnailURLDefault = thumbnailURLDefault;
    }

    public String getThumbnailURLMedium() {
        return thumbnailURLMedium;
    }

    public void setThumbnailURLMedium(String thumbnailURLMedium) {
        this.thumbnailURLMedium = thumbnailURLMedium;
    }

    public DateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(DateTime publishedAt) {
        this.publishedAt = publishedAt;
    }


    public String getLiveBroadcastContent() {
        return liveBroadcastContent;
    }

    public void setLiveBroadcastContent(String liveBroadcastContent) {
        this.liveBroadcastContent = liveBroadcastContent;
    }

    public Long getVideoCounts() {
        return videoCounts;
    }

    public void setVideoCounts(Long videoCounts) {
        this.videoCounts = videoCounts;
    }
}
