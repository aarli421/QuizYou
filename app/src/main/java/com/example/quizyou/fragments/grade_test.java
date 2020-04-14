package com.example.quizyou.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizyou.R;
import com.example.quizyou.Test.GradeTestActivity;
import com.example.quizyou.User.ReportActivity;
import com.example.quizyou.User.TeacherActivity;

public class grade_test extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.activity_grade_test, container, false);
        Intent intent = new Intent(getActivity().getApplicationContext(), GradeTestActivity.class);
        startActivity(intent);
        return retView;
    }
}
