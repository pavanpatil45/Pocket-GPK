package com.example.android;

public class Student {
    String sname;
    String sid;
    String classes;
    String spass;
    String rno;


    public Student(String sname, String sid,String classes,String spass, String rno) {
        this.sname = sname;
        this.sid = sid;
        this.classes = classes;
        this.spass = spass;
        this.rno= rno;
    }

    public String getSname() { return sname; }

    public String getSid() {
        return sid;
    }
    public String getrno() {
        return rno;
    }
    public String getClasses() {
        return classes;
    }

    public String getspass() { return spass; }
}
