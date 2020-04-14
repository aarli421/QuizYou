package com.example.quizyou.User;

import com.example.quizyou.Test.GradedTest;
import com.example.quizyou.Test.Test;

import java.util.ArrayList;

public class Student implements User {
    private ArrayList<Test> taken, pending;
    private ArrayList<GradedTest> reports;
    private String name, email, password;
    private long ID;
    private static long staticID = 0;

    public Student(String name, String email, String password) {
        taken = new ArrayList<>();
        pending = new ArrayList<>();
        reports = new ArrayList<>();

        this.name = name;
        this.email = email;
        this.password = password;
        ID = staticID;
        staticID++;
    }

    public Student(String name, String email, String password, ArrayList<Test> taken, ArrayList<Test> pending, ArrayList<GradedTest> reports, long id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.taken = taken;
        this.pending = pending;
        this.reports = reports;
        this.ID = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public long getID() {
        return ID;
    }

    public ArrayList<Test> getTaken() {
        return taken;
    }

    public ArrayList<Test> getPending() {
        return pending;
    }

    public ArrayList<GradedTest> getReports() {
        return reports;
    }

    public static void setStaticID(int staticID) {
        Student.staticID = staticID;
    }

    public void addTaken(Test t) {
        taken.add(t);
    }

    public void addPending(Test t) {
        pending.add(t);
    }

    public void removePending(Test t) {
        pending.remove(t);
    }

    public void addReport(GradedTest t) {
        reports.add(t);
    }

    public boolean equals(String email) {
        if (email.equals(this.email)) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return "Name: " + name + " Email: " + email + " Password: " + password;
    }
}
