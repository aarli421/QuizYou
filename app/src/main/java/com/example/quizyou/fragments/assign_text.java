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
import com.example.quizyou.Test.MakeTestActivity;
import com.example.quizyou.User.ReportActivity;

public class assign_text extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.activity_make_test, container, false);
        Intent intent = new Intent(getActivity().getApplicationContext(), MakeTestActivity.class);
        startActivity(intent);
        return retView;
    }
}
