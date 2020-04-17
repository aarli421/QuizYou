package com.example.quizyou;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashscreenActivity extends AppCompatActivity {

    Animation topAnim, bottomAnim;
    ImageView title, descrip, slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        title = findViewById(R.id.title);
        descrip = findViewById(R.id.imageView2);
        slogan = findViewById(R.id.imageView19);

        title.setAnimation(topAnim);
        descrip.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);
    }
}
