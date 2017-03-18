package com.colorhaake.reductor.example.view.gallery;

import android.support.v7.util.DiffUtil;

import com.colorhaake.reductor.example.plain_object.GalleryItem;

import java.util.List;


/**
 * Created by josephcheng on 2017/3/12.
 */
public class GalleryItemsDiffCallback extends DiffUtil.Callback {
    private final List<GalleryItem> oldList;
    private final List<GalleryItem> newList;

    public GalleryItemsDiffCallback(List<GalleryItem> oldList, List<GalleryItem> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        boolean isTheSame = oldList.get(oldItemPosition).id == newList.get(newItemPosition).id;
        return isTheSame;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        boolean isTheSame = oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        return isTheSame;
    }
}
