package com.weblieu.findtrue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.weblieu.findtrue.R;
import com.weblieu.findtrue.model.ImageGallery;
import com.weblieu.findtrue.service.AppConfig;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>{

    private Context context;
    private List<ImageGallery> listImageGalley;

    public GalleryAdapter(Context context, List<ImageGallery> listImageGalley){
        this.context = context;
        this.listImageGalley = listImageGalley;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.image_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageGallery imageGallery = listImageGalley.get(position);
        System.out.println("----------------------------------"+imageGallery.getPicPath());
        Glide.with(context).load(AppConfig.VENDOR_IMAGE + imageGallery.getPicPath()).centerCrop().placeholder(R.drawable.no_img).into(holder.imageViewGallery);
    }

    @Override
    public int getItemCount() {
        return listImageGalley.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewGallery;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewGallery = (ImageView)itemView.findViewById(R.id.imageViewGallery);
        }
    }
}
