package com.example.polyblog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polyblog.ViewHolder.EventViewHolder;
import com.example.polyblog.model.EventItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class Events extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;

    FirebaseRecyclerOptions<EventItem> options;
    FirebaseRecyclerAdapter<EventItem, EventViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        recyclerView = (RecyclerView)findViewById(R.id.RecyclerViewEvent);
        recyclerView.setHasFixedSize(true);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("events");

        options = new FirebaseRecyclerOptions.Builder<EventItem>()
                .setQuery(databaseReference,EventItem.class).build();

        adapter = new FirebaseRecyclerAdapter<EventItem, EventViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull EventViewHolder holder,
                                            int position,
                                            @NonNull @NotNull EventItem model) {
                holder.event_name.setText(model.getEvent_name());
                holder.event_location.setText(model.getEvent_location());
                holder.event_date.setText(model.getEvent_date());
            }

            @NonNull
            @NotNull
            @Override
            public EventViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent,
                                                      int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_view_events,
                        parent,false);

                return new EventViewHolder(view);
            }
        };

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),
                RecyclerView.VERTICAL,true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
//        recyclerView.setLayoutManager(gridLayoutManager);
//        adapter.startListening();
//        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(adapter != null)
            adapter.startListening();
    }

    @Override
    protected void onStop() {
        if(adapter != null)
            adapter.stopListening();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter != null)
            adapter.startListening();
    }
}