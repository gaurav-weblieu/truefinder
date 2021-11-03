package com.weblieu.findtrue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Header;
import com.weblieu.findtrue.R;
import com.weblieu.findtrue.model.Category;
import com.weblieu.findtrue.model.UserLeads;

import java.util.List;

public class LeadTableViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    Context mContext;
    List<UserLeads> userLeadsList;

    public LeadTableViewAdapter(Context mContext, List<UserLeads> userLeadsList){
        this.mContext = mContext;
        this.userLeadsList = userLeadsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_list_item, parent, false);
        return new ItemViewHolder(itemView);
//        if (viewType == TYPE_ITEM){
//            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_list_item, parent, false);
//            return new ItemViewHolder(itemView);
//
//        }else if (viewType == TYPE_HEADER) {
//            //Inflating header view
//            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.leads_header, parent, false);
//            return new HeaderViewHolder(itemView);
//        }else
//            return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        UserLeads userLeads = userLeadsList.get(position);
        itemViewHolder.txtName.setText(userLeads.getName());
        itemViewHolder.txtEmail.setText(userLeads.getEmail());
        itemViewHolder.txtContactNo.setText(userLeads.getContactno());
//        itemViewHolder.txtBookingDate.setText(userLeads.getBookingdate());
//        if (holder instanceof HeaderViewHolder){
//            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
//
//        }else if (holder instanceof ItemViewHolder){
//
//        }

    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0) {
//            return TYPE_HEADER;
//        }
//        return TYPE_ITEM;
//    }

    @Override
    public int getItemCount() {
        return userLeadsList.size();
        //return userLeadsList.size()+1;
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTitle;

        public HeaderViewHolder(View view) {
            super(view);
            //headerTitle = (TextView) view.findViewById(R.id.header_text);
        }
    }
    private class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtEmail, txtContactNo, txtBookingDate, txtView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = (TextView)itemView.findViewById(R.id.txtName);
            txtEmail = (TextView)itemView.findViewById(R.id.txtEmail);
            txtContactNo = (TextView)itemView.findViewById(R.id.txtContactNo);
            //txtBookingDate = (TextView)itemView.findViewById(R.id.txtBookingDate);
            //txtView = (TextView)itemView.findViewById(R.id.txtView);
        }
    }
}
