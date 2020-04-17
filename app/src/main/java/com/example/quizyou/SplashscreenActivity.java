package com.example.quizyou;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizyou.User.Student;
import com.example.quizyou.User.StudentActivity;
import com.example.quizyou.User.Teacher;
import com.example.quizyou.User.TeacherActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class SplashscreenActivity extends AppCompatActivity {

    Animation topAnim, bottomAnim;
    ImageView title, descrip, slogan;

    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();

    private boolean oneFinished = false;

    private static final String TAG = "SplashScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        title = findViewById(R.id.title);
        descrip = findViewById(R.id.imageView2);
        slogan = findViewById(R.id.imageView19);

        title.setAnimation(topAnim);
        descrip.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        mDb.collection("Students")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        int index = 0;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            index++;
                            StudentActivity.students.put(document.getId(), MainActivity.turnHashMapToStudent((HashMap<String, Object>) document.getData()));
                        }

                        Student.setStaticID(index);

                        Log.d(TAG, StudentActivity.students.toString());

                        if (oneFinished) {
                            //handler.post(periodicUpdate);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else {
                            oneFinished = true;
                        }
                    }
                });

        mDb.collection("Teachers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            int index = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                index++;
                                TeacherActivity.teachers.put(document.getId(), MainActivity.turnHashMapToTeacher((HashMap<String, Object>) document.getData()));
                            }

                            Teacher.setStaticID(index);

                            Log.d(TAG, TeacherActivity.teachers.toString());

                            if (oneFinished) {
                                //handler.post(periodicUpdate);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            } else {
                                oneFinished = true;
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
