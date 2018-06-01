package com.ashima.android.Models;
/**
 * Pojo Class Used to Bound JSON DATA : Used in Retrofit
 *
 * @author Ashima
 * @see com.ashima.androidexercise.Utils.ApiInterface
 * Using GSON
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FeedModel {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("rows")
    @Expose
    private ArrayList<RowDataFeed> rows;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<RowDataFeed> getRows() {
        return rows;
    }

    public void setRows(ArrayList<RowDataFeed> rows) {
        this.rows = rows;
    }
}