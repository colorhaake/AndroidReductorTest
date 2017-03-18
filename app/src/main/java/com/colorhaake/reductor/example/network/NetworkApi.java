package com.colorhaake.reductor.example.network;

import android.util.Log;

import com.colorhaake.reductor.example.Config;
import com.colorhaake.reductor.example.plain_object.Gallery;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by josephcheng on 2017/3/12.
 */
public class NetworkApi {
    public static final String TAG = NetworkApi.class.getName();
    private static Retrofit retrofit = null;
    private static ImgurService imgurService = null;
    static {
        retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            // TODO check create/createAsync difference
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(Config.BASE_API_URL)
            .build();

        imgurService = retrofit.create(ImgurService.class);
    }

    public static Observable<Gallery> fetchRandomGallery(int page) {
        return imgurService.fetchRandomGallery(page)
            // TODO try to handle all subscribeOn in one place
            .subscribeOn(Schedulers.io())
            // TODO fix error handling for 401
            .doOnError(e -> Log.d(TAG, "EEEEEEEEEEEError: " + e))
            .onErrorReturnItem(new Gallery());
    }
}
