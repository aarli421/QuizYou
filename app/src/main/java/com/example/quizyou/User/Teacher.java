package com.example.quizyou.User;

import com.example.quizyou.Test.GradedTest;
import com.example.quizyou.Test.Test;
import com.example.quizyou.Test.TestResult;

import java.util.ArrayList;
import java.util.HashMap;

public class Teacher implements User {
    private ArrayList<Student> students;
    private ArrayList<Test> made, assigned;
    private ArrayList<TestResult> results;
    private ArrayList<GradedTest> gradedTests;
    private String name, email, password;
    private int ID;
    private static int staticID = 0;

    public Teacher(String name, String email, String password) {
        students = new ArrayList<>();
        made = new ArrayList<>();
        assigned = new ArrayList<>();
        results = new ArrayList<>();
        gradedTests = new ArrayList<>();

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

    public String getPassword() {
        return password;
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

    public ArrayList<TestResult> getResults() {
        return results;
    }

    public ArrayList<GradedTest> getGradedTests() {
        return gradedTests;
    }

    public void addMadeTest(Test t) {
        made.add(t);
    }

    public void addAssignedTest(Test t) {
        made.remove(t);
        assigned.add(t);
    }

    public void addTestResults(TestResult r) {
        results.add(r);
    }

    public void addGradedTests(GradedTest g) {
        gradedTests.add(g);
    }
}
