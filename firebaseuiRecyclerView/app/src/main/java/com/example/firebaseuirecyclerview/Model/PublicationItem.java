package com.example.firebaseuirecyclerview.Model;

public class PublicationItem {
    public String comment,course,name;

    public PublicationItem() {
    }

    public PublicationItem(String comment, String course, String name) {
        this.comment = comment;
        this.course = course;
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
