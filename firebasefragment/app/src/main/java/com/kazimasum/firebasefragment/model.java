package com.kazimasum.firebasefragment;

public class model {
    String comment, course, name,Purl;

    public model() {
    }

    public model(String comment, String course, String name) {
        this.comment = comment;
        this.course = course;
        this.name = name;
        this.Purl = Purl;
    }


    public String getPurl() {
        return Purl;
    }

    public void setPurl(String purl) {
        Purl = purl;
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
