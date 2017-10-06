package com.appsomniac.showbox.model.nearby;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by absolutelysaurabh on 7/10/17.
 */

public class PlacePhoto implements Serializable{


    @SerializedName("photo_reference")
    private String theatre_pic_url;

    public PlacePhoto(){

    }

    public String getTheatre_pic_url() {
        return theatre_pic_url;
    }
}
