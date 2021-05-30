package com.example.expensemanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<Model> mList;
    Context context;
    public MyAdapter(Context context, ArrayList<Model> mList){
        this.mList = mList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  MyAdapter.MyViewHolder holder, int position) {

        Model model = mList.get(position);
        holder.transaction.setText(model.getTransaction());
        holder.amount.setText(String.valueOf(model.getAmount()));
        holder.description.setText(model.getDescription());
        holder.category.setText(model.getCategory());
        holder.datetime.setText(model.getDatetime());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView transaction, amount,category, description,datetime;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

           transaction = itemView.findViewById(R.id.type_text);
           amount = itemView.findViewById(R.id.amount_text);
           category = itemView.findViewById(R.id.category_text);
           description = itemView.findViewById(R.id.description_text);
           datetime = itemView.findViewById(R.id.date_time);

        }
    }

}
