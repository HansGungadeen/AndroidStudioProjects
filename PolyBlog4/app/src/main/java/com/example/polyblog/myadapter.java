package com.example.polyblog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder> {

    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model) {
        holder.nameText.setText(model.getName());
        holder.commentText.setText(model.getComment());
        holder.courseText.setText(model.getCourse());

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{

        TextView nameText,courseText,commentText;

        public myviewholder(@NonNull View itemView){
            super(itemView);

            nameText=itemView.findViewById(R.id.nameText);
            courseText=itemView.findViewById(R.id.courseText);
            commentText=itemView.findViewById(R.id.commentText);
        }
    }
}
