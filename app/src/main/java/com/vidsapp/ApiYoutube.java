package com.vidsapp;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by atul .
 * Refer v3 api to fetch youtube videos
 * https://developers.google.com/youtube/v3/docs/
 */
public class ApiYoutube {

    public static final String TAG = "ApiYoutube.class";
    private YouTube mYoutube;
    private YouTube.Search.List mQueryNtOVideoList;
    private YouTube.Videos.List mQueryVideoList;
    private YouTube.Playlists.List mQueryPlaylistCustom;

    private YoutubePlayListEntity youtubePlayListEntity=null;
    private YoutubeVideoListEntity youtubeVideoListEntity=null;
    private YoutubeNtOVideosListEntity youtubeNtOVideosListEntity=null;

    private YouTube.Search.List mQueryPlayList;
    private YouTube.PlaylistItems.List mQueryVideos;


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

            else if (type.equals("playlist")) {
                youtubePlayListEntity = searchPlayList(requestValue);
            }
        }

        return youtubeNtOVideosListEntity;
    }

    public YoutubePlayListEntity intiateAPICallPlayList(String type,String requestValue){

        mYoutube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) throws IOException {}
        }).setApplicationName("VidsApp").build();

        if (type != null) {

            if (type.equals("playlist")) {
                youtubePlayListEntity = searchPlayList(requestValue);
            }
        }

        return youtubePlayListEntity;
    }

    public YoutubePlayListEntity intiateAPICallPlayListCustom(String type,String requestValue){

        mYoutube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) throws IOException {}
        }).setApplicationName("VidsApp").build();

        if (type != null) {

            if (type.equals("playlistcustom")) {
                youtubePlayListEntity = searchPlayListCustom(requestValue);
            }
        }

        return youtubePlayListEntity;
    }


    public YoutubeVideoListEntity intiateAPICallPlayListVideos(String type,String requestValue){

        mYoutube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) throws IOException {}
        }).setApplicationName("VidsApp").build();

        if (type != null) {

            if (type.equals("playlistvideo")) {
                youtubeVideoListEntity = searchPlaylistVideos(requestValue);
            }
        }

        return youtubeVideoListEntity;
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
                    if(result.getSnippet().getThumbnails()!=null && result.getSnippet().getThumbnails().getHigh()!=null && result.getSnippet().getThumbnails().getHigh().getUrl()!=null){
                        item.setThumbnailURL(result.getSnippet().getThumbnails().getHigh().getUrl());

                    }
                    else if(result.getSnippet().getThumbnails()!=null && result.getSnippet().getThumbnails().getMedium()!=null && result.getSnippet().getThumbnails().getMedium().getUrl()!=null){
                        item.setThumbnailURL(result.getSnippet().getThumbnails().getMedium().getUrl());

                    }
                    else if(result.getSnippet().getThumbnails()!=null && result.getSnippet().getThumbnails().getDefault()!=null && result.getSnippet().getThumbnails().getDefault().getUrl()!=null){
                        item.setThumbnailURL(result.getSnippet().getThumbnails().getMedium().getUrl());

                    }
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
                    if(result.getSnippet().getThumbnails()!=null && result.getSnippet().getThumbnails().getHigh()!=null && result.getSnippet().getThumbnails().getHigh().getUrl()!=null){
                        item.setThumbnailURL(result.getSnippet().getThumbnails().getHigh().getUrl());

                    }
                    else if(result.getSnippet().getThumbnails()!=null && result.getSnippet().getThumbnails().getMedium()!=null && result.getSnippet().getThumbnails().getMedium().getUrl()!=null){
                        item.setThumbnailURL(result.getSnippet().getThumbnails().getMedium().getUrl());

                    }
                    else if(result.getSnippet().getThumbnails()!=null && result.getSnippet().getThumbnails().getDefault()!=null && result.getSnippet().getThumbnails().getDefault().getUrl()!=null){
                        item.setThumbnailURL(result.getSnippet().getThumbnails().getMedium().getUrl());

                    }
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


//playlist code

    public YoutubePlayListEntity searchPlayList(String query){
        YoutubePlayListEntity playListItems = null;
        SearchListResponse response = null;
        try{
            mQueryPlayList = mYoutube.search().list("id,snippet");
            mQueryPlayList.setKey(ApplicationConstants.YOUTUBE_DEVELOPER_BROWSER_KEY);
            mQueryPlayList.setType("playlist");
            //to fetch medium ,high,default thumbnail set the snippet mentioned below
            //snippet/thumbnails/medium/url,snippet/thumbnails/default/url,snippet/thumbnails/high/url
            mQueryPlayList.setFields("items(id/playlistId,snippet/title,snippet/description,snippet/publishedAt,snippet/liveBroadcastContent,snippet/thumbnails/medium/url,snippet/thumbnails/default/url,snippet/thumbnails/high/url)");
            //max limit to fetch videos is 50 and default in 5
            mQueryPlayList.setMaxResults((long) 50);
            mQueryPlayList.setChannelId(query);
           // mQueryPlayList.setQ(query);
            mQueryPlayList.setOrder("date");
            response = mQueryPlayList.execute();
            playListItems = parseSearchListData(response);
        }catch(IOException e){
            return null;
        }
        return playListItems;
    }

    private YoutubePlayListEntity parseSearchListData(SearchListResponse response) {
        List<SearchResult> results = null;
        YoutubePlayListEntity playListItems = null;
        List<YoutubePlayListItemEntity> items = null;
        try {
            if (response != null) {
                results = response.getItems();

                playListItems = new YoutubePlayListEntity();
                items = new ArrayList<YoutubePlayListItemEntity>();
                for (SearchResult result : results) {
                    YoutubePlayListItemEntity item = new YoutubePlayListItemEntity();
                    item.setTitle(result.getSnippet().getTitle());
                    item.setDescription(result.getSnippet().getDescription());
                    if(result.getSnippet().getThumbnails()!=null && result.getSnippet().getThumbnails().getHigh()!=null){
                        item.setThumbnailURLDefault(result.getSnippet().getThumbnails().getHigh().getUrl());
                    }
                   else if(result.getSnippet().getThumbnails()!=null && result.getSnippet().getThumbnails().getMedium()!=null){
                        item.setThumbnailURLDefault(result.getSnippet().getThumbnails().getMedium().getUrl());
                    }
                    else if(result.getSnippet().getThumbnails()!=null && result.getSnippet().getThumbnails().getDefault()!=null){
                        item.setThumbnailURLDefault(result.getSnippet().getThumbnails().getDefault().getUrl());
                    }

                    item.setId(result.getId().getPlaylistId());
                    item.setPublishedAt(result.getSnippet().getPublishedAt());
                    item.setLiveBroadcastContent(result.getSnippet().getLiveBroadcastContent());
                    items.add(item);

                }
                playListItems.setItems(items);
                if (response.getPageInfo() != null && response.getPageInfo().getTotalResults() != null) {
                    playListItems.setTotalPlayListCount(response.getPageInfo().getTotalResults());
                }
                if (response.getNextPageToken() != null) {
                    playListItems.setNextPageToken(response.getNextPageToken());
                }
            }
        }finally {
            results = null;
        }
        return playListItems;
    }

//playlist videos

    public YoutubeVideoListEntity searchPlaylistVideos(String playListId){
        PlaylistItemListResponse response = null;
        YoutubeVideoListEntity videoListItems = null;
        try{
            mQueryVideos = mYoutube.playlistItems().list("id,snippet");
            mQueryVideos.setKey(ApplicationConstants.YOUTUBE_DEVELOPER_BROWSER_KEY);
            mQueryVideos.setPlaylistId(playListId);
            //queryVideos.setFields("items(id/resourceId/videoId,snippet/title,snippet/description,snippet/publishedAt,snippet/thumbnails/medium/url,snippet/thumbnails/medium/width,snippet/thumbnails/medium/height)");
            //"PLHLkfcOqwa-wZqZ1ktkEwUrK7nJHCGWiy"
            mQueryVideos.setMaxResults((long) 50);
            response = mQueryVideos.execute();
            videoListItems = parseVideoListData(response);


        }catch(IOException e){
        }finally {
            response = null;
        }
        return videoListItems;
    }

    private YoutubeVideoListEntity parseVideoListData(PlaylistItemListResponse response) {
        List<PlaylistItem> results = null;
        YoutubeVideoListEntity videoListItems = null;
        List<YoutubeVideoListItemEntity> items = null;
        try {
            if (response != null) {
                results = response.getItems();
                videoListItems = new YoutubeVideoListEntity();
                items = new ArrayList<YoutubeVideoListItemEntity>();

                for (PlaylistItem result : results) {
                    YoutubeVideoListItemEntity item = new YoutubeVideoListItemEntity();
                    if (!result.getSnippet().getTitle().startsWith("Deleted")) {
                        item.setTitle(result.getSnippet().getTitle());
                        item.setDescription(result.getSnippet().getDescription());
                        if(result.getSnippet().getThumbnails()!=null && result.getSnippet().getThumbnails().getHigh()!=null && result.getSnippet().getThumbnails().getHigh().getUrl()!=null){
                            item.setThumbnailURL(result.getSnippet().getThumbnails().getHigh().getUrl());

                        }
                        else if(result.getSnippet().getThumbnails()!=null && result.getSnippet().getThumbnails().getMedium()!=null && result.getSnippet().getThumbnails().getMedium().getUrl()!=null){
                            item.setThumbnailURL(result.getSnippet().getThumbnails().getMedium().getUrl());
                            item.setVideoThumW(result.getSnippet().getThumbnails().getMedium().getWidth());
                            item.setVideoThumH(result.getSnippet().getThumbnails().getMedium().getHeight());

                        }
                        item.setId(result.getSnippet().getResourceId().getVideoId());
                        //item.setNextPageToken(result.);

                        //item.setTotalVideos(result.);
                        items.add(item);
                    }
                }

                videoListItems.setItems(items);

                if (response.getNextPageToken() != null) {
                    videoListItems.setNextPageToken(response.getNextPageToken());
                }
                if (response.getPageInfo() != null && response.getPageInfo().getTotalResults() != null) {
                    videoListItems.setTotalVideos(response.getPageInfo().getTotalResults());
                }
            }
        }finally {
            results = null;
        }
        return videoListItems;
    }



    //custom playlist


    //playlist code

    public YoutubePlayListEntity searchPlayListCustom(String query){
        YoutubePlayListEntity playListItems = null;
        PlaylistListResponse response= null;
        try{
            mQueryPlaylistCustom = mYoutube.playlists().list("snippet,localizations");
            mQueryPlaylistCustom.setKey(ApplicationConstants.YOUTUBE_DEVELOPER_BROWSER_KEY);
            //to fetch medium ,high,default thumbnail set the snippet mentioned below
            //snippet/thumbnails/medium/url,snippet/thumbnails/default/url,snippet/thumbnails/high/url
          //  mQueryPlaylistCustom.setFields("items(id/playlistId,snippet/title,snippet/description,snippet/publishedAt,snippet/liveBroadcastContent,snippet/thumbnails/medium/url,snippet/thumbnails/default/url,snippet/thumbnails/high/url)");
            //max limit to fetch videos is 50 and default in 5
            mQueryPlaylistCustom.setMaxResults((long) 50);
            mQueryPlaylistCustom.setId(query);
            // mQueryPlayList.setQ(query);
            response = mQueryPlaylistCustom.execute();
            playListItems = parseSearchListDatacustom(response);
        }catch(IOException e){
            return null;
        }
        return playListItems;
    }

    private YoutubePlayListEntity parseSearchListDatacustom(PlaylistListResponse response) {
        List<Playlist> results = null;
        YoutubePlayListEntity playListItems = null;
        List<YoutubePlayListItemEntity> items = null;
        try {
            if (response != null) {
                results = response.getItems();

                playListItems = new YoutubePlayListEntity();
                items = new ArrayList<YoutubePlayListItemEntity>();
                for (Playlist result : results) {
                    YoutubePlayListItemEntity item = new YoutubePlayListItemEntity();
                    item.setTitle(result.getSnippet().getTitle());
                    item.setDescription(result.getSnippet().getDescription());
                    if(result.getSnippet().getThumbnails()!=null && result.getSnippet().getThumbnails().getHigh()!=null){
                        item.setThumbnailURLDefault(result.getSnippet().getThumbnails().getHigh().getUrl());
                    }
                    else if(result.getSnippet().getThumbnails()!=null && result.getSnippet().getThumbnails().getMedium()!=null){
                        item.setThumbnailURLDefault(result.getSnippet().getThumbnails().getMedium().getUrl());
                    }
                    else if(result.getSnippet().getThumbnails()!=null && result.getSnippet().getThumbnails().getDefault()!=null){
                        item.setThumbnailURLDefault(result.getSnippet().getThumbnails().getDefault().getUrl());
                    }

                    item.setId(result.getId());
                    item.setPublishedAt(result.getSnippet().getPublishedAt());
                    items.add(item);

                }
                playListItems.setItems(items);
                if (response.getPageInfo() != null && response.getPageInfo().getTotalResults() != null) {
                    playListItems.setTotalPlayListCount(response.getPageInfo().getTotalResults());
                }
                if (response.getNextPageToken() != null) {
                    playListItems.setNextPageToken(response.getNextPageToken());
                }
            }
        }finally {
            results = null;
        }
        return playListItems;
    }

}
