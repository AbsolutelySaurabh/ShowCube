package com.dexter.innovation.labs.showcube.model.section;

import com.dexter.innovation.labs.showcube.model.Movie;

import java.util.ArrayList;

/**
 * Created by absolutelysaurabh on 23/9/17.
 */

public class Movie_SectionDataModel {



    private String headerTitle;
    private ArrayList<Movie> allItemsInSection;


    public Movie_SectionDataModel() {

    }
    public Movie_SectionDataModel(String headerTitle, ArrayList<Movie> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<Movie> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<Movie> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}