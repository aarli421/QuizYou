package com.example.quizyou.User;

import com.example.quizyou.Test.Test;
import com.example.quizyou.Test.TestResult;

import java.util.ArrayList;
import java.util.HashMap;

public class Teacher implements User {
    private ArrayList<Student> students;
    private ArrayList<Test> made, assigned;
    private HashMap<Test, ArrayList<TestResult>> results;
    private String name, email, password;
    private int ID;
    private static int staticID = 0;

    public Teacher(String name, String email, String password) {
        students = new ArrayList<>();
        made = new ArrayList<>();
        assigned = new ArrayList<>();
        results = new HashMap<>();

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

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Test> getMadeTests() {
        return made;
    }

    public ArrayList<Test> getAssignedTests() {
        return assigned;
    }

    public ArrayList<TestResult> getResults(Test t) {
        return results.get(t);
    }

    public void addMadeTest(Test t) {

    }

    public void addAssignedTest(Test t) {
        results.put(t, new ArrayList<TestResult>());
    }

    public void addTestResults(Test t, TestResult r) {
        results.get(t).add(r);
    }
}
