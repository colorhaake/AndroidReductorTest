package com.colorhaake.reductor.example.network;

import com.colorhaake.reductor.example.plain_object.Gallery;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by josephcheng on 2017/3/12.
 */
public interface ImgurService {
    @GET("random/random/{page}")
    Observable<Gallery> fetchRandomGallery(@Path("page") int page);
}
