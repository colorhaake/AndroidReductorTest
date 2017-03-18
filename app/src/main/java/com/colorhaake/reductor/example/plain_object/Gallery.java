package com.colorhaake.reductor.example.plain_object;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josephcheng on 2017/3/12.
 */
public class Gallery {
    @SerializedName("data")
    public List<GalleryItem> data = new ArrayList<>();

    @SerializedName("success")
    public boolean success;

    @SerializedName("status")
    public int status;

    public int page = -1;

    public Gallery() {}
    public Gallery(int page, List<GalleryItem> data) {
        this.page = page;
        this.data = data;
    }
}
