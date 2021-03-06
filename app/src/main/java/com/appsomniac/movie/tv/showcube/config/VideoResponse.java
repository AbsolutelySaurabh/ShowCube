package com.appsomniac.movie.tv.showcube.config;

import com.appsomniac.movie.tv.showcube.model.Video;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class VideoResponse {

    @SerializedName("id")
    private int page;
    @SerializedName("results")
    private ArrayList<Video> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<Video> getResults() {
        return results;
    }

}
