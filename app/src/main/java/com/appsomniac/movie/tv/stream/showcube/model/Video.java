package com.appsomniac.movie.tv.stream.showcube.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by absolutelysaurabh on 1/10/17.
 */

public class Video {

    @SerializedName("id")
    private String id;
    @SerializedName("key")
    private String key;
    @SerializedName("name")
    private String name;
    @SerializedName("iso_639_1")
    private String iso_639_1;
    @SerializedName("iso_3166_1")
    private String iso_3166_1;
    @SerializedName("site")
    private String site;
    @SerializedName("size")
    private String size;
    @SerializedName("type")
    private String type;

    public Video(String id, String key, String name) {

        this.id = id;
        this.key = key;
        this.name = name;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKey() {return key;}
}
