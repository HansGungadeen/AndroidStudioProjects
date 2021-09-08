package com.example.polyblog.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polyblog.R;

import org.jetbrains.annotations.NotNull;

public class EventViewHolder extends RecyclerView.ViewHolder {

    public TextView event_date,event_location,event_name;

    public EventViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        event_name = (TextView)itemView.findViewById(R.id.event_name);
        event_location = (TextView)itemView.findViewById(R.id.event_location);
        event_date = (TextView)itemView.findViewById(R.id.event_date);


    }
}
