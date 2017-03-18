package com.colorhaake.reductor.example.network.Deserializer;

import com.colorhaake.reductor.example.plain_object.Gallery;
import com.colorhaake.reductor.example.plain_object.GalleryItem;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;


import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by josephcheng on 2017/3/15.
 */
public class GalleryDeserializer implements JsonDeserializer<Gallery> {
    @Override
    public Gallery deserialize(
            JsonElement json, Type typeOfT, JsonDeserializationContext context
    ) throws JsonParseException {
        JsonObject root = json.getAsJsonObject();

        Gallery gallery = new Gson().fromJson(root, Gallery.class);

        if (root.has("data")) {
            List<GalleryItem> galleryItems = new Gson().fromJson(
                    root.getAsJsonArray("data"), List.class
            );
            gallery.data = galleryItems;
        }

        return gallery;
    }
}
