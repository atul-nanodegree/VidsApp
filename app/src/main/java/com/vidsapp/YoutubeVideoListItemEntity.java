package com.vidsapp;

/**
 * Created by atul.
 */
public class YoutubeVideoListItemEntity {

    private String title;
    private String description;
    private String thumbnailURL;
    private String id;
    private Long videoThumW;
    private Long videoThumH;
    private String videoDuration;

    public String getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

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

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnail) {
        this.thumbnailURL = thumbnail;
    }

    public Long getVideoThumW() {
        return videoThumW;
    }

    public void setVideoThumW(Long videoThumW) {
        this.videoThumW = videoThumW;
    }

    public Long getVideoThumH() {
        return videoThumH;
    }

    public void setVideoThumH(Long videoThumH) {
        this.videoThumH = videoThumH;
    }
}
