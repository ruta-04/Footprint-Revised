package com.software.team2.footprint;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.software.team2.footprint.BestMatch;

public class bExample {


    @SerializedName("bestMatches")
    @Expose
    private List<BestMatch> bestMatches = null;

    public List<BestMatch> getBestMatches() {
        return bestMatches;
    }

    public void setBestMatches(List<BestMatch> bestMatches) {
        this.bestMatches = bestMatches;
    }

}

