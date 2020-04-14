package com.example.quizyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.quizyou.Test.GradedTest;
import com.example.quizyou.Test.Question.Question;
import com.example.quizyou.Test.Test;
import com.example.quizyou.Test.TestResult;
import com.example.quizyou.User.Student;
import com.example.quizyou.User.StudentActivity;
import com.example.quizyou.User.Teacher;
import com.example.quizyou.User.TeacherActivity;
import com.example.quizyou.User.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    // TODO Dropdown select if you are a student or a teacher
    // TODO SignUp as student or teacher
    // TODO LogIn as student or teacher
    // TODO Store in Firebase
    // TODO Adding read from Firebase

    private EditText mLoginEmail, mLoginPassword, mSignUpName, mSignUpEmail, mSignUpPassword;
    private Button mLogin, mSignUp;
    private Spinner mSpinner;
    private String email, password;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseFirestore mDb = FirebaseFirestore.getInstance();

    private final static String TAG = "MainActivity";

    public static User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getId().equals("Student")) {
                                    //StudentActivity.students = document.getData();
                                    for (Map.Entry<String, Object> m : document.getData().entrySet()) {
                                        HashMap<String, Object> studentMap = (HashMap<String, Object>) m.getValue();
                                        Student s = turnHashMapToStudent(studentMap);
                                        StudentActivity.students.put(Long.toString(s.getID()), s);
                                    }

                                    Student.setStaticID(document.getData().size());

                                    Log.d(TAG, document.getId() + " => " + StudentActivity.students);
                                } else if (document.getId().equals("Teacher")) {
                                    //TeacherActivity.teachers = document.getData();
                                    for (Map.Entry<String, Object> m : document.getData().entrySet()) {
                                        HashMap<String, Object> teacherMap = (HashMap<String, Object>) m.getValue();
                                        Teacher t = turnHashMapToTeacher(teacherMap);
                                        TeacherActivity.teachers.put(Long.toString(t.getID()), t);
                                    }
                                    Teacher.setStaticID(document.getData().size());

                                    Log.d(TAG, document.getId() + " => " + TeacherActivity.teachers);
                                }
                            }

                            handler.post(periodicUpdate);
                            userIsLoggedIn();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

//        Log.d(TAG, "Loading...");
//        mDb.collection("users").document("Student").set(StudentActivity.students);
//        System.out.println(StudentActivity.students);
//        mDb.collection("users").document("Teacher").set(TeacherActivity.teachers);
//        System.out.println(TeacherActivity.teachers);

        FirebaseApp.initializeApp(this);

        mLoginEmail = findViewById(R.id.loginEmail);
        mLoginPassword = findViewById(R.id.loginPassword);
        mSignUpName = findViewById(R.id.signUpName);
        mSignUpEmail = findViewById(R.id.signUpEmail);
        mSignUpPassword = findViewById(R.id.signUpPassword);

        mLogin = findViewById(R.id.login);
        mSignUp = findViewById(R.id.signUp);

        mSpinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

//        HashMap<Object, Teacher> students = new HashMap<>();
//        Teacher teacher1 = new Teacher("Teacher Trump", "teachertrump@gmail.com", "teachertrump");
//        ArrayList<Question> questions = new ArrayList<>();
//        questions.add(new ShortAnswerQuestion("Why do you like to eat chicken nuggets?", "Cuz I do", 5));
//        questions.add(new ShortAnswerQuestion("Why do you like to nuggets?", "Bruhhh", 7));
//        Test t1 = new Test(5, questions, "Random name");
//        questions.add(new MultipleChoiceQuestion("You suck diiiiiick", "bruhh", 10));
//        Test t2 = new Test(6, questions, "Test two that I did not add");
//        teacher1.addMadeTest(t1);
//        teacher1.addAssignedTest(t1);
//        teacher1.addMadeTest(t2);
//
//        ArrayList<String> ans = new ArrayList<>();
//        ans.add("69");
//        ans.add("You suck dick");
//        ans.add("you suck dick fuck gay ");
//        teacher1.addTestResults(new TestResult(t1, ans, new Student("fsadghfhkj", "sdfgs", "sdfgsdf")));
//        teacher1.addGradedTests(new GradedTest(t1, 5, 18, "notes"));
        //teacher1.addStudents(new Student("hfaushfdjs", "ilikepie@gmail.com", "fhasgdhfh"));
        //students.put(Integer.toString(2), new Teacher("yeah", "aarondfgsdf", "asasdf"));
        //students.put(Integer.toString(3), new Teacher("ysjadhjfkhkh", "aarondfgsdf", "aem"));
        //TeacherActivity.teachers.put(Integer.toString(0), teacher1);

//        mDb.collection("users")
//                .document("students")
//                .set(students);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selection = mSpinner.getSelectedItem().toString();

//                if (selection.equals("Select One")) {
//                    // TODO Tell user to select one
//                } else {
                    logInWithEmailAuthCredential();
//                }
            }
        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selection = mSpinner.getSelectedItem().toString();

                if (selection.equals("Select One")) {
                    // TODO Tell user to select one
                } else {
                    signUpWithEmailAuthCredential();
                }
            }
        });
    }

    private void logInWithEmailAuthCredential() {
        if (mLoginEmail.getText().toString() == null || mLoginPassword.getText().toString() == null) return;

        mAuth.signInWithEmailAndPassword(mLoginEmail.getText().toString(), mLoginPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            if (mSpinner.getSelectedItem().toString().equals("Student")) {
//
//                            } else if (mSpinner.getSelectedItem().toString().equals("Teacher")) {
//
//                            }

                            email = mLoginEmail.getText().toString();
                            password = mLoginPassword.getText().toString();

                            userIsLoggedIn();
                        } else {
                            mLogin.setText("Login Failed");
                        }
                    }
                });
    }

    private void signUpWithEmailAuthCredential() {
        if (mSignUpName.getText().toString() == null || mSignUpEmail.getText().toString() == null || mSignUpPassword.getText().toString() == null) return;

        mAuth.createUserWithEmailAndPassword(mSignUpEmail.getText().toString(), mSignUpPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (mSpinner.getSelectedItem().toString().equals("Student")) {
                                Student s = new Student(mSignUpName.getText().toString(), mSignUpEmail.getText().toString(), mSignUpPassword.getText().toString());
                                StudentActivity.students.put(Long.toString(s.getID()), s);
                            } else if (mSpinner.getSelectedItem().toString().equals("Teacher")) {
                                Teacher t = new Teacher(mSignUpName.getText().toString(), mSignUpEmail.getText().toString(), mSignUpPassword.getText().toString());
                                TeacherActivity.teachers.put(Long.toString(t.getID()), t);
                            }

                            email = mSignUpEmail.getText().toString();
                            password = mSignUpPassword.getText().toString();

                            userIsLoggedIn();
                        }
                    }
                });
    }

    private void userIsLoggedIn() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            if (email == null) {
                email = user.getEmail();
            }

            for (Object value : TeacherActivity.teachers.values()) {
                if (((Teacher) value).equals(email)) {
                    u = ((Teacher) value);
                    break;
                }
            }

            for (Object value : StudentActivity.students.values()) {
                if (((Student) value).equals(email)) {
                    u = ((Student) value);
                    break;
                }
            }

            if (u != null) {
                try {
                    Student s = ((Student) u);
                    startActivity(new Intent(getApplicationContext(), StudentActivity.class));
                    Log.d(TAG, "User is student");
                } catch (ClassCastException e) {
                    Teacher t = ((Teacher) u);
                    startActivity(new Intent(getApplicationContext(), TeacherActivity.class));
                    Log.d(TAG, "User is teacher");
                }
            }

            //Log.d(TAG, ((Teacher) u).getStudents().get(0).toString());
            //Log.d(TAG, ((Student) u).getPassword());

//            if (mSpinner.getSelectedItem().toString().equals("Student")) {
//                startActivity(new Intent(getApplicationContext(), StudentActivity.class));
//            } else if (mSpinner.getSelectedItem().toString().equals("Teacher")) {
//                startActivity(new Intent(getApplicationContext(), TeacherActivity.class));
//            }
            finish();
            return;
        }
    }

    Handler handler = new Handler();
    private Runnable periodicUpdate = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(periodicUpdate, 5*1000);

            Log.d(TAG, "Saving...");

            mDb.collection("users")
                    .document("Student")
                    .update(StudentActivity.students);

            mDb.collection("users")
                    .document("Teacher")
                    .update(TeacherActivity.teachers);
        }
    };


    public Student turnHashMapToStudent(HashMap<String, Object> map) {
        ArrayList<Test> taken = new ArrayList<>();
        ArrayList<Object> takenList = (ArrayList<Object>) map.get("taken");
        for (Object obj : takenList) {
            HashMap<String, Object> testMap = (HashMap<String, Object>) obj;
            taken.add(turnHashMapToTest(testMap));
        }

        ArrayList<Test> pending = new ArrayList<>();
        ArrayList<Object> pendingList = (ArrayList<Object>) map.get("pending");
        for (Object obj : pendingList) {
            HashMap<String, Object> testMap = (HashMap<String, Object>) obj;
            pending.add(turnHashMapToTest(testMap));
        }

        ArrayList<GradedTest> reports = new ArrayList<>();
        ArrayList<Object> reportsList = (ArrayList<Object>) map.get("reports");
        for (Object obj : reportsList) {
            HashMap<String, Object> gradedTestMap = (HashMap<String, Object>) obj;
            reports.add(turnHashMapToGradedTest(gradedTestMap));
        }

        Student s = new Student((String) map.get("name"), (String) map.get("email"), (String) map.get("password"),
                taken, pending, reports, (Long) map.get("id"));

        return s;
    }

    public Teacher turnHashMapToTeacher(HashMap<String, Object> map) {
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Object> studentsList = (ArrayList<Object>) map.get("students");
        for (Object obj : studentsList) {
            HashMap<String, Object> testMap = (HashMap<String, Object>) obj;
            students.add(turnHashMapToStudent(testMap));
        }

        ArrayList<Test> madeTests = new ArrayList<>();
        ArrayList<Object> madeTestsList = (ArrayList<Object>) map.get("madeTests");
        for (Object obj : madeTestsList) {
            HashMap<String, Object> testMap = (HashMap<String, Object>) obj;
            madeTests.add(turnHashMapToTest(testMap));
        }

        ArrayList<Test> assignedTests = new ArrayList<>();
        ArrayList<Object> assignedTestsList = (ArrayList<Object>) map.get("assignedTests");
        for (Object obj : assignedTestsList) {
            HashMap<String, Object> testMap = (HashMap<String, Object>) obj;
            assignedTests.add(turnHashMapToTest(testMap));
        }

        ArrayList<TestResult> results = new ArrayList<>();
        ArrayList<Object> testResultList = (ArrayList<Object>) map.get("results");
        for (Object obj : testResultList) {
            HashMap<String, Object> testMap = (HashMap<String, Object>) obj;
            results.add(turnHashMapToTestResult(testMap));
        }

        ArrayList<GradedTest> gradedTests = new ArrayList<>();
        ArrayList<Object> gradedTestsList = (ArrayList<Object>) map.get("gradedTests");
        for (Object obj : gradedTestsList) {
            HashMap<String, Object> gradedTestMap = (HashMap<String, Object>) obj;
            gradedTests.add(turnHashMapToGradedTest(gradedTestMap));
        }

        return new Teacher((String) map.get("name"), (String) map.get("email"), (String) map.get("password"),
                students, madeTests, assignedTests, results, gradedTests, (Long) map.get("id"));
    }

    public TestResult turnHashMapToTestResult(HashMap<String, Object> map) {
        Student answerer = turnHashMapToStudent((HashMap<String, Object>) map.get("answerer"));
        Test test = turnHashMapToTest((HashMap<String, Object>) map.get("test"));
        ArrayList<String> answers = (ArrayList<String>) map.get("answers");
        int exitedApp = (int) (long) map.get("exitedApp");
        return new TestResult(test, answers, answerer, exitedApp);
    }

    public GradedTest turnHashMapToGradedTest(HashMap<String, Object> map) {
        String notes = (String) map.get("notes");
        int points = (int) (long) map.get("points");
        int totalPoints = (int) (long) map.get("totalPoints");
        HashMap<String, Object> testMap = (HashMap<String, Object>) map.get("test");

        return new GradedTest(turnHashMapToTest(testMap), points, totalPoints, notes);
    }

    public Test turnHashMapToTest(HashMap<String, Object> map) {
        String name = (String) map.get("name");
        long timeLimit = (Long) map.get("timeLimit");
        ArrayList<Object> questionsList = (ArrayList<Object>) map.get("questions");
        ArrayList<Question> questions = turnObjListToQuestionsList(questionsList);

        return new Test(timeLimit, questions, name);
    }

    public ArrayList<Question> turnObjListToQuestionsList(ArrayList<Object> questionsList) {
        ArrayList<Question> questions = new ArrayList<>();
        for (Object obj : questionsList) {
            HashMap<String, Object> question = (HashMap<String, Object>) obj;
            Question q = new Question((String) question.get("prompt"), (String) question.get("answer"), (int) (long) question.get("points"));
            questions.add(q);
        }

        return questions;
    }
}
