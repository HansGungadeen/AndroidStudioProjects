package com.example.polyblog.model;

public class Comment {
    private String name;
    private String course;
    private String comment;

    public Comment() {
        //default constructor
    }


    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public String getComment() {
        return comment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
