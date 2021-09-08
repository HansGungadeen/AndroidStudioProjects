package com.example.polyblog.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.polyblog.R;

public class PublicationViewHolder extends RecyclerView.ViewHolder {

    public TextView comment,course,name;

    public PublicationViewHolder(View itemView) {
        super(itemView);

        comment = (TextView)itemView.findViewById(R.id.comment);
        course = (TextView)itemView.findViewById(R.id.course);
        name = (TextView)itemView.findViewById(R.id.name);
    }



}
