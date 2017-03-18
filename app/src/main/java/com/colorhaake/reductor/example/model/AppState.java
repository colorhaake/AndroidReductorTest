package com.colorhaake.reductor.example.model;

import com.colorhaake.reductor.example.plain_object.Gallery;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.yheriatovych.reductor.annotations.CombinedState;

/**
 * Created by josephcheng on 2017/3/12.
 */

@CombinedState
@AutoValue
public abstract class AppState {
    public abstract Gallery gallery();

    public static TypeAdapter<AppState> typeAdapter(Gson gson) {
        return new AutoValue_AppState.GsonTypeAdapter(gson);
    }
}
