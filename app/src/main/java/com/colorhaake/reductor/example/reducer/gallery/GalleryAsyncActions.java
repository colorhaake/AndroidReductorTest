package com.colorhaake.reductor.example.reducer.gallery;

import android.util.Log;

import com.colorhaake.reductor.example.model.AppState;
import com.colorhaake.reductor.example.network.NetworkApi;
import com.colorhaake.reductor.example.plain_object.Gallery;
import com.yheriatovych.reductor.Actions;
import com.yheriatovych.reductor.observable.Epic;
import com.yheriatovych.reductor.observable.Epics;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by josephcheng on 2017/3/12.
 */
public class GalleryAsyncActions {
    public static final String TAG = NetworkApi.class.getName();
    static GalleryActions galleryActions = Actions.from(GalleryActions.class);
    public static Epic<AppState> fetchData = (actions, store) ->
        actions.filter(Epics.ofType(GalleryActions.FETCH_DATA))
            .flatMap(action -> NetworkApi.fetchRandomGallery(action.getValue(0))
                            .map(payload -> Arrays.asList((int) action.getValue(0), payload))
            )
            .map(payload ->
                    (Object) galleryActions.fetchDataRes(
                                    (int) payload.get(0), (Gallery) payload.get(1)
                            )
            )
            .observeOn(AndroidSchedulers.mainThread());
}
