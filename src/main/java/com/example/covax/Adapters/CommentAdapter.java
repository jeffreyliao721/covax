package com.example.covax.Adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covax.Models.Comment;
import com.example.covax.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context mContext;
    private List<Comment> mData;

    public CommentAdapter(Context mContext, List<Comment> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View commentsList = LayoutInflater.from(mContext).inflate(R.layout.comments_list, parent, false);
        return new CommentViewHolder(commentsList);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Glide.with(mContext).load(mData.get(position).getcUserIcon()).into(holder.cListIcon);
        holder.cListName.setText(mData.get(position).getcUserName());
        holder.cListDate.setText(timeStampToString((Long) mData.get(position).getTimeStamp()));
        holder.cListComment.setText(mData.get(position).getcText());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        ImageView cListIcon;
        TextView cListName, cListDate, cListComment;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            cListIcon = itemView.findViewById(R.id.cListIcon);
            cListName = itemView.findViewById(R.id.cListName);
            cListDate = itemView.findViewById(R.id.cListDate);
            cListComment = itemView.findViewById(R.id.cListComment);

        }
    }

    private String timeStampToString(long time) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("hh:mm", calendar).toString();
        return date;
    }
}
