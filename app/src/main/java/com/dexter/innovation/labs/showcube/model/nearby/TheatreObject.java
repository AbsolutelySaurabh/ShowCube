package com.dexter.innovation.labs.showcube.model.nearby;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TheatreObject {

    private String contact;
    private String theatre_id;
    private String theatre_name;
    private String rating;
    private String theatre_image_url;
    private String map_url;
    private String theatre_address;
    private String website;

    public TheatreObject(String contact, String website, String map_url, String rating){

        this.rating = rating;
        this.contact = contact;
        this.map_url = map_url;
        this.website= website;
    }

    public TheatreObject(String name){

        this.theatre_name = name;
    }

    public String getTheatre_image_url(){
        return theatre_image_url;
    }
    public String getTheatre_phone_number(){
        return contact;
    }

    public String getTheatre_name(){

        return theatre_name;
    }

    public String getTheatre_rating(){
        return rating;
    }

    public String getTheatre_map_url(){
        return map_url;
    }

    public String getTheatre_website(){
        return website;
    }

    public String getTheatre_id(){
        return theatre_id;
    }
    public String getTheatre_Address(){
        return theatre_address;
    }

}
