package com.example.quizyou.User;

import com.example.quizyou.Test.GradedTest;
import com.example.quizyou.Test.Test;
import com.example.quizyou.Test.TestResult;

import java.util.ArrayList;
import java.util.HashMap;

public class Teacher implements User {
    private ArrayList<Student> students;
    private ArrayList<Test> made, assigned;
    private HashMap<Test, ArrayList<TestResult>> results;
    private HashMap<Test, ArrayList<GradedTest>> gradedTests;
    private String name, email, password;
    private int ID;
    private static int staticID = 0;

    public Teacher(String name, String email, String password) {
        students = new ArrayList<>();
        made = new ArrayList<>();
        assigned = new ArrayList<>();
        results = new HashMap<>();
        gradedTests = new HashMap<>();

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

    public HashMap<Test, ArrayList<TestResult>> getResults() {
        return results;
    }

    public HashMap<Test, ArrayList<GradedTest>> getGradedTests() {
        return gradedTests;
    }

    public ArrayList<TestResult> getResults(Test t) {
        return results.get(t);
    }

    public void addMadeTest(Test t) {
        made.add(t);
    }

    public void addAssignedTest(Test t) {
        made.remove(t);
        results.put(t, new ArrayList<TestResult>());
        gradedTests.put(t, new ArrayList<GradedTest>());
        assigned.add(t);
    }

    public void addTestResults(Test t, TestResult r) {
        results.get(t).add(r);
    }

    public void addGradedTests(Test t, GradedTest g) {
        gradedTests.get(t).add(g);
    }
}
