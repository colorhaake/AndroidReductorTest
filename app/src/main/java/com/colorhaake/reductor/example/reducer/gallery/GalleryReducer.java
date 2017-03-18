package com.colorhaake.reductor.example.reducer.gallery;

import com.colorhaake.reductor.example.plain_object.Gallery;
import com.colorhaake.reductor.example.plain_object.GalleryItem;
import com.yheriatovych.reductor.Reducer;
import com.yheriatovych.reductor.annotations.AutoReducer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josephcheng on 2017/3/12.
 */

@AutoReducer
public abstract class GalleryReducer implements Reducer<Gallery> {

    @AutoReducer.InitialState
    Gallery initState() {
        return new Gallery();
    }

    @AutoReducer.Action(
            value = GalleryActions.FETCH_DATA_RES,
            from = GalleryActions.class
    )
    public Gallery fetchDataRes(
            Gallery current, int page, Gallery result
    ) {
        if (page < 0 || result == null || result.status != 200) {
            return current;
        }

        if (page == 0) {
            return new Gallery(page, result.data);
        }


        // TODO find a suitable Immutable lib to replace ArrayList
        // append result into current
        List newList = new ArrayList<GalleryItem>(current.data);
        newList.addAll(result.data);
        return new Gallery(page, newList);
    }

    public static GalleryReducer create() {
        return new GalleryReducerImpl();
    }
}
