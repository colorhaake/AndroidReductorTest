package com.colorhaake.reductor.example;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.colorhaake.reductor.example.model.AppState;
import com.colorhaake.reductor.example.reducer.gallery.GalleryActions;
import com.colorhaake.reductor.example.view.gallery.GalleryAdapter;
import com.headerfooter.songhang.library.SmartRecyclerAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.yheriatovych.reductor.Actions;
import com.yheriatovych.reductor.Store;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName();

    private Store<AppState> store;
    private GalleryActions galleryActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       store = ((ReductorApp) getApplicationContext()).store;
       galleryActions = Actions.from(GalleryActions.class);

        // load more button
        Button loadMore = new Button(this);
        loadMore.setText(R.string.load_more);
        RxView.clicks(loadMore)
                .startWith(0)
                // TODO: is it possible to be without useless argument
                .subscribe(evt -> {
                    final int page = store.getState().gallery().page;
                    store.dispatch(galleryActions.fetchData(page + 1));
                });

        // list view
        RecyclerView listView = (RecyclerView) findViewById(R.id.list);
        listView.setLayoutManager(new LinearLayoutManager(this));

        final GalleryAdapter adapter = new GalleryAdapter(
                store.getState().gallery().data,
                item -> Log.d(TAG, "click item: " + item.title)
        );

        // TODO: fix adding footer will scroll down to bottom
        SmartRecyclerAdapter smartRecyclerAdapter = new SmartRecyclerAdapter(adapter);
        smartRecyclerAdapter.setFooterView(loadMore);
        listView.setAdapter(smartRecyclerAdapter);

        store.subscribe(newState -> {
            adapter.setGalleryItems(newState.gallery().data);
        });
    }
}
