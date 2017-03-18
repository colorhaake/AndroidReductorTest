package com.colorhaake.reductor.example.view.gallery;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colorhaake.reductor.example.R;
import com.colorhaake.reductor.example.plain_object.GalleryItem;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import io.reactivex.functions.Consumer;
/**
 * Created by josephcheng on 2017/3/12.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private List<GalleryItem> list;
    private final Consumer<GalleryItem> onClickListener;

    public GalleryAdapter(List<GalleryItem> list, Consumer<GalleryItem> onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GalleryViewHolder(
                LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        final GalleryItem item = list.get(position);
        holder.imageView.setImageURI(item.link);
        holder.titleView.setText(item.title);

        holder.itemView.setOnClickListener(view -> {
            // TODO try to remove try/catch
            try {
                onClickListener.accept(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(list.get(position).id);
    }

    public void setGalleryItems(List<GalleryItem> list) {
        List<GalleryItem> oldList = this.list;
        this.list = list;
        DiffUtil.calculateDiff(new GalleryItemsDiffCallback(oldList, list), false)
                .dispatchUpdatesTo(this);
    }

    static class GalleryViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView imageView;
        public TextView titleView;

        public GalleryViewHolder(View itemView) {
            super(itemView);

            imageView = (SimpleDraweeView) itemView.findViewById(R.id.item_image);
            titleView = (TextView) itemView.findViewById(R.id.item_title);
        }
    }
}
