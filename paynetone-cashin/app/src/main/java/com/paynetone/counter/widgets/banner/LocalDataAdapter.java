package com.paynetone.counter.widgets.banner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.paynetone.counter.R;
import com.paynetone.counter.model.response.BannerResponse;
import com.paynetone.counter.utils.Utils;

import java.util.ArrayList;

public class LocalDataAdapter extends RecyclerView.Adapter<LocalDataAdapter.ViewHolder> {
    private ArrayList<String> images = new ArrayList<>();

    public LocalDataAdapter(ArrayList<BannerResponse> newContent ){
        images.clear();
        for (BannerResponse item : newContent){
            images.add(item.getBannerValue());
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        loadImageBanner(holder.imageView,images.get(position));
        holder.imageView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
//
        }
    }
    private void loadImageBanner(ImageView imageView,String path){
        Glide.with(imageView)
                .load(Utils.getUrlImage(path))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(R.drawable.ic_banner1)
                .into(imageView);
    }
}