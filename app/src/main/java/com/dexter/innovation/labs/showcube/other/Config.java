package com.dexter.innovation.labs.showcube.other;

/**
 * Created by absolutelysaurabh on 22/9/17.
 */

public class Config {

    public static String BASE_URL = "http://api.themoviedb.org/3/";

    public static String MOVIE_URL = "/movie";

    public static String NOW_PLAYING = "/now_playing?api_key=";

    public static String  API_KEY = "5b4b359c1e593af429152b47752d4247";

    public static String MOVIE_URL_LANGUAGE = "&language=en-US";
    public static String MOVIE_URL_PAGE = "&page=1";
    public static String MOVIE_URL_REGION = "&region=IN";

    public static String DEVELOPER_KEY = "AIzaSyAQV-kPWSrt6bjxAbc-26oQoN79Wo_ecMM";

    public static String Youtube_movie_get_video_url_part_1 = "https://api.themoviedb.org/3/movie/";
    public static String Youtube_tv_get_video_url_part_1 = "https://api.themoviedb.org/3/tv/";
    public static String Youtube_get_video_url_part_2 = "/videos?api_key=" + API_KEY + "&language=en-US";

    public static String Places_base_url = "https://maps.googleapis.com/maps/api/place/nearbysearch/";
    public static String Places_url_comma = ",";
    public static  String PLaces_base_url_part_2 = "&radius=5000&type=movie_theater&key=";
    public static String Places_api_key = "AIzaSyDpHabw0oOie8gSeu-ZkFMK2cUGWZdh9mA";

    public static String theatre_details_base_url = "https://maps.googleapis.com/maps/api/place/details/json?placeid=";
    public static String theatre_details_base_url_2 = "&key=";


    public static String theatre_photo_base_url_part_1 = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=";
    public static String theatre_photo_base_url_part_2 = "&key=" + Places_api_key;



}
