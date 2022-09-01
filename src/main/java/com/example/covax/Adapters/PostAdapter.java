package com.example.covax.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covax.Activities.PostDetails;
import com.example.covax.Models.Post;
import com.example.covax.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    Context mContext;
    List<Post> mData;

    public PostAdapter(Context mContext, List<Post> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View postsList = LayoutInflater.from(mContext).inflate(R.layout.posts_list, parent, false);
        return new MyViewHolder(postsList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.listTitle.setText(mData.get(position).getTitle());
        Glide.with(mContext).load(mData.get(position).getImage()).into(holder.listImage);
        Glide.with(mContext).load(mData.get(position).getUserIcon()).into(holder.listIcon);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView listTitle;
        ImageView listImage, listIcon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            listTitle = itemView.findViewById(R.id.listTitle);
            listImage = itemView.findViewById(R.id.listImage);
            listIcon = itemView.findViewById(R.id.listIcon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent postDetailsActivity = new Intent(mContext, PostDetails.class);
                    int position = getAdapterPosition();

                    postDetailsActivity.putExtra("image", mData.get(position).getImage());
                    postDetailsActivity.putExtra("address", mData.get(position).getAddress());
                    postDetailsActivity.putExtra("title", mData.get(position).getTitle());
                    long timeStamp = (long) mData.get(position).getTimeStamp();
                    postDetailsActivity.putExtra("timeStamp", timeStamp);
                    postDetailsActivity.putExtra("userName", mData.get(position).getUserName());
                    postDetailsActivity.putExtra("userIcon", mData.get(position).getUserIcon());
                    postDetailsActivity.putExtra("description", mData.get(position).getDescription());
                    postDetailsActivity.putExtra("key", mData.get(position).getKey());
                    mContext.startActivity(postDetailsActivity);
                }
            });

        }
    }
}
