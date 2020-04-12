package com.example.quizyou.User;

import com.example.quizyou.Test.GradedTest;
import com.example.quizyou.Test.Test;

import java.util.ArrayList;

public class Student implements User {
    private ArrayList<Test> taken, pending;
    private ArrayList<GradedTest> reports;
    private String name, email, password;
    private int ID;
    private static int staticID = 0;

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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getID() {
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

    public void addTaken(Test t) {

    }

    public void addPending(Test t) {

    }

    public void addReport(GradedTest t) {

    }
}
