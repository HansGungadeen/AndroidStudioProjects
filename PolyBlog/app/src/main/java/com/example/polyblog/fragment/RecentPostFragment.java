package com.example.polyblog.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class RecentPostFragment extends PostListFragment {

    public RecentPostFragment(){

    }

    public Query getQuery(DatabaseReference databaseReference){
//        return databaseReference.child("publication").limitToFirst(5);
        return databaseReference.child("publication");

    }


}
