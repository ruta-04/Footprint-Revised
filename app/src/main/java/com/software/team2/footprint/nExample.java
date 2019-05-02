package com.software.team2.footprint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class nExample {


    @SerializedName("newsMatches")
    @Expose
    private List<NewsMatches> newsMatches = null;

    public List<NewsMatches> getNewsMatches() {
        return newsMatches;
    }

    public void setNewsMatches(List<NewsMatches> newsMatches) {
        this.newsMatches = newsMatches;
    }

}

