package com.mukesh.retrofitsampleapp.datamodel;

import com.google.gson.annotations.SerializedName;

public class PostModel {
    private int userId;
    private int id;
    private String title;
    @SerializedName("body")
    private String textData;


    /**
     * This constructor will be used for creating post ,
     * We are not passing id because it will be auto-generated
     */
    public PostModel(int userId, String title, String textData) {
        this.userId = userId;
        this.title = title;
        this.textData = textData;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTextData() {
        return textData;
    }
}
