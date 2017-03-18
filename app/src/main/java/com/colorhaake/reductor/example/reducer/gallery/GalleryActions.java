package com.colorhaake.reductor.example.reducer.gallery;

import com.colorhaake.reductor.example.plain_object.Gallery;
import com.yheriatovych.reductor.Action;
import com.yheriatovych.reductor.annotations.ActionCreator;

/**
 * Created by josephcheng on 2017/3/12.
 */

@ActionCreator
public interface GalleryActions {
    String FETCH_DATA = "FETCH_DATA";
    String FETCH_DATA_RES = "FETCH_DATA_RES";

    @ActionCreator.Action(FETCH_DATA)
    Action fetchData(int page);

    @ActionCreator.Action(FETCH_DATA_RES)
    Action fetchDataRes(int page, Gallery result);
}
