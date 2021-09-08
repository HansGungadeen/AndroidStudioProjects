package com.example.polyblog.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Comment {
    public String uid;
    public String comment;
    public String course;
    public String name;

    public Comment() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public Comment(String uid, String author, String text) {
        this.uid = uid;
        this.comment = comment;
        this.course = course;
        this.name = name;
    }
}