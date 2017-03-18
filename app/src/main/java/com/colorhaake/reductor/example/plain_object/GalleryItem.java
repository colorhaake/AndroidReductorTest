package com.colorhaake.reductor.example.plain_object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by josephcheng on 2017/3/12.
 */
public class GalleryItem {
    @SerializedName("id")
    public String id;

    @SerializedName("title")
    public String title;

    @SerializedName("description")
    public String description;

    @SerializedName("views")
    public int views;

    @SerializedName("link")
    public String link;
}
