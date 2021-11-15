package com.example.quizrun.MainAdapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quizrun.MainModel.MainModel;
import com.example.quizrun.R;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.viewHolder>{

    Context context;
    ArrayList<MainModel> mainModels;

    public MainAdapter(Context context , ArrayList<MainModel> mainModels){
        this.context = context;
        this.mainModels = mainModels;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category,null);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        MainModel model = mainModels.get(position);

        holder.textView.setText(model.getCategoryName());
        Glide.with(context).load(model.getCategoryImage()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return mainModels.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.categoryImage);
            textView = itemView.findViewById(R.id.category);

        }
    }
}
