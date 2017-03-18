package com.colorhaake.reductor.example;

import android.app.Application;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.colorhaake.reductor.example.model.AppState;
import com.colorhaake.reductor.example.model.AppStateReducer;
import com.colorhaake.reductor.example.reducer.ReductorAsync;
import com.colorhaake.reductor.example.reducer.gallery.GalleryReducer;
import com.colorhaake.reductor.example.reducer.utils.SetStateReducer;
import com.colorhaake.reductor.example.reducer.utils.UndoableReducer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.inspector.console.RuntimeReplFactory;
import com.facebook.stetho.rhino.JsRuntimeReplFactoryBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yheriatovych.reductor.Store;

import org.mozilla.javascript.BaseFunction;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

/**
 * Created by josephcheng on 2017/3/12.
 */
public class ReductorApp extends Application {
    public final String TAG = ReductorApp.class.getName();

    public Store<AppState> store;
    Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(MyAdapterFactory.create())
            .create();

    @Override
    public void onCreate() {
        super.onCreate();

        final AppStateReducer vanillaReducer = AppStateReducer.builder()
                .galleryReducer(GalleryReducer.create())
                .build();

        store = Store.create(
                new SetStateReducer<>(
                    new UndoableReducer<>(vanillaReducer)
                ),
                ReductorAsync.middleware
        );

        // for remote debugging
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableWebKitInspector(
                        () -> new Stetho.DefaultInspectorModulesBuilder(ReductorApp.this)
                            .runtimeRepl(createRuntimeRepl())
                            .finish()
                )
                .build()
        );

        // for using Fresco Image
        Fresco.initialize(this);
    }

    Handler handler = new Handler();
    private RuntimeReplFactory createRuntimeRepl() {
        return new JsRuntimeReplFactoryBuilder(this)
                .addFunction("getState", new BaseFunction() {
                    @Override
                    public Object call(
                            Context cx, Scriptable scope, Scriptable thisObj, Object[] args
                    ) {
                        final String jsonString = gson.toJson(store.getState());
                        Scriptable json = (Scriptable) scope.get("JSON", scope);
                        Function parseFunction = (Function) json.get("parse", scope);
                        return parseFunction.call(cx, json, scope, new Object[]{jsonString});
                    }
                })
                .addFunction("setState", new BaseFunction() {
                    @Override
                    public Object call(
                            Context cx, Scriptable scope, Scriptable thisObj, Object[] args
                    ) {
                        Scriptable json = (Scriptable) scope.get("JSON", scope);
                        Function stringifyFunction = (Function) json.get("stringify", scope);
                        String jsonString = (String) stringifyFunction.call(
                                cx, json, scope, new Object[]{args[0]}
                        );

                        final AppState arg = gson.fromJson(jsonString, AppState.class);
                        Log.d(TAG, arg.toString());
                        handler.post(() -> store.dispatch(SetStateReducer.createSetStateAction(arg)));
                        return arg;
                    }
                })
                .build();
    }

    @Override
    protected void attachBaseContext(android.content.Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
