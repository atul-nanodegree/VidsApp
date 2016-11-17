package com.vidsapp;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by atul on 2/07/16.
 * Refer v3 api to fetch youtube videos
 * https://developers.google.com/youtube/v3/docs/
 */
public class ApiYoutube {

    public static final String TAG = "ApiYoutube.class";
    private YouTube mYoutube;
    private YouTube.Search.List mQueryNtOVideoList;
    YouTube.Videos.List mQueryVideoList;
    private YoutubeNtOVideosListEntity youtubeNtOVideosListEntity=null;

    /**
     * Constructor modified to accept url and any other params
     */
    public ApiYoutube(){


    }

    public YoutubeNtOVideosListEntity intiateAPICall(String type,String requestValue){

                mYoutube = new YouTube.Builder(new NetHttpTransport(),
                        new JacksonFactory(), new HttpRequestInitializer() {
                    @Override
                    public void initialize(HttpRequest hr) throws IOException {}
                }).setApplicationName("VidsApp").build();

        if (type != null) {
            if (type.equals("video")) {
                youtubeNtOVideosListEntity = videosFromArrayList(requestValue);
            } else if (type.equals("channel")) {
                youtubeNtOVideosListEntity = videosNewToOld(requestValue);
            }
        }

        return youtubeNtOVideosListEntity;
    }






// for playing channel
    public YoutubeNtOVideosListEntity videosNewToOld(String requestValue){
        YoutubeNtOVideosListEntity playListItems = null;
        SearchListResponse response = null;
        try{
            mQueryNtOVideoList = mYoutube.search().list("id,snippet");
            mQueryNtOVideoList.setKey(ApplicationConstants.YOUTUBE_DEVELOPER_BROWSER_KEY);
            mQueryNtOVideoList.setType("video");
            mQueryNtOVideoList.setChannelId(requestValue);
            //mQueryNtOVideoList.setQ("Mindtree Ltd.");
            mQueryNtOVideoList.setOrder("date");
          //  mQueryNtOVideoList.setPageToken(pageToken);
          //  mQueryNtOVideoList.setFields("items(id/playlistId,snippet/title,snippet/description,snippet/publishedAt,snippet/liveBroadcastContent,snippet/thumbnails/medium/url)");
            mQueryNtOVideoList.setMaxResults((long) 50);
          //  mQueryNtOVideoList.setQ(query);
            response = mQueryNtOVideoList.execute();
            playListItems = parseSearchListNtOVideosData(response);
        }catch(IOException e){

            return null;
        }
        return playListItems;
    }

    private YoutubeNtOVideosListEntity parseSearchListNtOVideosData(SearchListResponse response) {
        List<SearchResult> results = null;
        YoutubeNtOVideosListEntity videoListItems = null;
        List<YoutubeNtOVideosListItemEntity> items = null;
        try {
            if (response != null) {
                results = response.getItems();

                videoListItems = new YoutubeNtOVideosListEntity();
                items = new ArrayList<YoutubeNtOVideosListItemEntity>();
                for (SearchResult result : results) {
                    YoutubeNtOVideosListItemEntity item = new YoutubeNtOVideosListItemEntity();
                    item.setTitle(result.getSnippet().getTitle());
                    item.setDescription(result.getSnippet().getDescription());
                    item.setThumbnailURL(result.getSnippet().getThumbnails().getMedium().getUrl());
                    item.setId(result.getId().getVideoId());
                    item.setPublishedAt(result.getSnippet().getPublishedAt());
                    item.setLiveBroadcastContent(result.getSnippet().getLiveBroadcastContent());
                    item.setChannelTitle(result.getSnippet().getChannelTitle());
                    items.add(item);

                }
                videoListItems.setItems(items);
                if (response.getPageInfo() != null && response.getPageInfo().getTotalResults() != null) {
                    videoListItems.setTotalVideoListCount(response.getPageInfo().getTotalResults());
                }
                if (response.getNextPageToken() != null) {
                    videoListItems.setNextPageToken(response.getNextPageToken());
                }
            }
        }finally {
            results = null;
        }
        return videoListItems;
    }

// for playing list of Videos
    public YoutubeNtOVideosListEntity videosFromArrayList(String requestValue){
        YoutubeNtOVideosListEntity playListItems = null;
        VideoListResponse response = null;
        try{
            mQueryVideoList = mYoutube.videos().list("id,snippet");
            mQueryVideoList.setKey(ApplicationConstants.YOUTUBE_DEVELOPER_BROWSER_KEY);
            // mQueryNtOVideoList.setType("video");
            //  mQueryNtOVideoList.setChannelId("UCbTLwN10NoCU4WDzLf1JMOA");
            mQueryVideoList.setId(requestValue);
            // mQueryNtOVideoList.setOrder("date");
            //  mQueryNtOVideoList.setPageToken(pageToken);
            //  mQueryNtOVideoList.setFields("items(id/playlistId,snippet/title,snippet/description,snippet/publishedAt,snippet/liveBroadcastContent,snippet/thumbnails/medium/url)");
            mQueryVideoList.setMaxResults((long) 50);
            //  mQueryNtOVideoList.setQ(query);
            response = mQueryVideoList.execute();
            playListItems = parseSearchListforVideosData(response);
        }catch(IOException e){

            return null;
        }
        return playListItems;
    }

    private YoutubeNtOVideosListEntity parseSearchListforVideosData(VideoListResponse response) {
        List<Video> results = null;
        YoutubeNtOVideosListEntity videoListItems = null;
        List<YoutubeNtOVideosListItemEntity> items = null;
        try {
            if (response != null) {
                results = response.getItems();

                videoListItems = new YoutubeNtOVideosListEntity();
                items = new ArrayList<YoutubeNtOVideosListItemEntity>();
                for (Video result : results) {
                    YoutubeNtOVideosListItemEntity item = new YoutubeNtOVideosListItemEntity();
                    item.setTitle(result.getSnippet().getTitle());
                    item.setDescription(result.getSnippet().getDescription());
                    item.setThumbnailURL(result.getSnippet().getThumbnails().getMedium().getUrl());
                    item.setId(result.getId());
                    item.setPublishedAt(result.getSnippet().getPublishedAt());
                    item.setLiveBroadcastContent(result.getSnippet().getLiveBroadcastContent());
                    item.setChannelTitle(result.getSnippet().getChannelTitle());
                    items.add(item);

                }
                videoListItems.setItems(items);
                if (response.getPageInfo() != null && response.getPageInfo().getTotalResults() != null) {
                    videoListItems.setTotalVideoListCount(response.getPageInfo().getTotalResults());
                }
                if (response.getNextPageToken() != null) {
                    videoListItems.setNextPageToken(response.getNextPageToken());
                }
            }
        }finally {
            results = null;
        }
        return videoListItems;
    }






}
