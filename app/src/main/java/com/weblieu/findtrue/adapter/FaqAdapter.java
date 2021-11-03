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
import com.weblieu.findtrue.model.FAQData;

import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.ViewHolder>{
    Context mContext;
    List<FAQData> faqList;

    public FaqAdapter(Context mContext,List<FAQData> faqList){
        this.mContext = mContext;
        this.faqList = faqList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.faq_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FAQData faqData = faqList.get(position);

        holder.tvQuestion.setText(faqData.getFaqquestion());
        holder.tvAnswer.setText(faqData.getFaqtext());
    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuestion, tvAnswer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = (TextView)itemView.findViewById(R.id.tvQuestion);
            tvAnswer = (TextView)itemView.findViewById(R.id.tvAnswer);
        }
    }
}
