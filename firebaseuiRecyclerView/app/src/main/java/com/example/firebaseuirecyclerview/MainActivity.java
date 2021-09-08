package com.example.firebaseuirecyclerview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaseuirecyclerview.Model.PublicationItem;
import com.example.firebaseuirecyclerview.ViewHolder.PublicationViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;

    FirebaseRecyclerOptions<PublicationItem> options;
    FirebaseRecyclerAdapter<PublicationItem, PublicationViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.reclyclerview);
        recyclerView.setHasFixedSize(true);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("publication");

        options = new FirebaseRecyclerOptions.Builder<PublicationItem>()
                .setQuery(databaseReference,PublicationItem.class).build();

        adapter = new FirebaseRecyclerAdapter<PublicationItem, PublicationViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull PublicationViewHolder holder, int position, @NonNull @NotNull PublicationItem model) {
                holder.comment.setText(model.getComment());
                holder.course.setText(model.getCourse());
                holder.name.setText(model.getName());


            }

            @NonNull
            @NotNull
            @Override
            public PublicationViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_item,parent,false);

                return new PublicationViewHolder(view);
            }
        };

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,true);
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