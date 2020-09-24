package com.example.android;


public class Teacher {
    String tname;
    String tid;
    String subject;
    String classes;
    String tpass;





    public Teacher(String tname, String tid, String subject, String classes, String tpass) {
        this.tname = tname;
        this.tid = tid;
        this.subject = subject;
        this.classes = classes;
        this.tpass = tpass;
    }

    public String getTname() {
        return tname;
    }

    public String getTid() {
        return tid;
    }

    public String getSubject() {
        return subject;
    }

    public String getClasses() {
        return classes;
    }

    public String gettpass() {
        return tpass;
    }

}
