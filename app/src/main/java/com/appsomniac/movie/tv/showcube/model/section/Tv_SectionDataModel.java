package com.appsomniac.movie.tv.showcube.model.section;

import com.appsomniac.movie.tv.showcube.model.Tv;

import java.util.ArrayList;

/**
 * Created by absolutelysaurabh on 23/9/17.
 */

public class Tv_SectionDataModel {



    private String headerTitle;
    private ArrayList<Tv> allItemsInSection;


    public Tv_SectionDataModel() {

    }
    public Tv_SectionDataModel(String headerTitle, ArrayList<Tv> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<Tv> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<Tv> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}