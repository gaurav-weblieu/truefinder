package com.weblieu.findtrue.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.weblieu.findtrue.R;
import com.weblieu.findtrue.ViewProfileActivity;
import com.weblieu.findtrue.model.Category;
import com.weblieu.findtrue.model.WishlistData;
import com.weblieu.findtrue.service.AppConfig;
import com.weblieu.findtrue.utils.AppConstant;
import com.weblieu.findtrue.utils.CommonUtils;

import java.util.List;

public class AddShortListAdapter extends RecyclerView.Adapter<AddShortListAdapter.ViewHolder>{
    private Context mContext;
    List<WishlistData> listWishList;

    public AddShortListAdapter(Context mContext, List<WishlistData> listWishList){
        this.mContext = mContext;
        this.listWishList = listWishList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.category_details_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WishlistData wishlistData = listWishList.get(position);
        Glide.with(mContext).load(AppConfig.CATEGORY_DETAILS_IMAGE + wishlistData.getProfilepic()).placeholder(R.drawable.no_img).into(holder.ivImage);

        holder.tvVenderName.setText(wishlistData.getVendorName());
        holder.tvAddress.setText(wishlistData.getAddress());
        if (wishlistData.getVendorDetails().getPackagePrice().equals("0")){
            holder.tvPrice.setText("PACKAGE PRICE : " +"\u20B9" +"Ask for Price");
        }else {
            holder.tvPrice.setText("PACKAGE PRICE : " +"\u20B9" +wishlistData.getVendorDetails().getPackagePrice());
        }

        holder.linear_cat_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ViewProfileActivity.class);
                intent.putExtra("PROFILE_IMAGE", listWishList.get(position).getProfilepic());
                intent.putExtra("VENDOR_NAME", listWishList.get(position).getVendorName());
                CommonUtils.savePreferenceString(mContext, AppConstant.VENDOR_EMAIL, listWishList.get(position).getEmail());
                mContext.startActivity(intent);
            }
        });


        holder.image_shortlist.setVisibility(View.GONE);
        holder.btnEnquiry.setVisibility(View.GONE);
        holder.btnProfile.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return listWishList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btnEnquiry, btnProfile;
        RoundedImageView ivImage;
        TextView tvVenderName, tvAddress, tvPrice;
        LinearLayout linear_cat_details;
        ImageView image_shortlist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnEnquiry = (Button)itemView.findViewById(R.id.btnSendEnquery);
            btnProfile = (Button)itemView.findViewById(R.id.btnProfile);
            ivImage = (RoundedImageView)itemView.findViewById(R.id.ivImage);
            tvVenderName = (TextView)itemView.findViewById(R.id.tvName);
            tvAddress = (TextView)itemView.findViewById(R.id.tvAddress);
            tvPrice = (TextView)itemView.findViewById(R.id.tvPrice);
            linear_cat_details = (LinearLayout)itemView.findViewById(R.id.linear_cat_details);
            image_shortlist = (ImageView)itemView.findViewById(R.id.image_shortlist);
        }
    }
}
