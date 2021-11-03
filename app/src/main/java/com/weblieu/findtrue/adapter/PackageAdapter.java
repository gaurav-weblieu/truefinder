package com.weblieu.findtrue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.weblieu.findtrue.R;
import com.weblieu.findtrue.model.Category;
import com.weblieu.findtrue.model.PackageDetails;

import java.util.List;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder>{
    Context mContext;
    List<PackageDetails> packageDetailsList;

    public PackageAdapter(Context mContext, List<PackageDetails> packageDetailsList){
        this.mContext = mContext;
        this.packageDetailsList = packageDetailsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.package_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PackageDetails packageDetails = packageDetailsList.get(position);

        holder.packageName.setText(packageDetails.getPackageName());
        holder.packagePrice.setText("\u20B9 " + packageDetails.getPrice());
        holder.packageExpDate.setText("Expiry Day : " +packageDetails.getExpiryDay());
        holder.packageleadCount.setText(packageDetails.getLeadsCount());

    }

    @Override
    public int getItemCount() {
        return packageDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView packageName, packagePrice, packageExpDate, packageleadCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            packageName = (TextView)itemView.findViewById(R.id.packageName);
            packagePrice = (TextView)itemView.findViewById(R.id.packagePrice);
            packageExpDate = (TextView)itemView.findViewById(R.id.tvPackageExpireTitle);
            packageleadCount = (TextView)itemView.findViewById(R.id.packageleadCount);
        }
    }
}
