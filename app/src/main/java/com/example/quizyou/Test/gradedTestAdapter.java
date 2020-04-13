package com.example.quizyou.Test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quizyou.R;
import com.example.quizyou.User.Student;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class gradedTestAdapter extends ArrayAdapter<GradedTest> {

    private static final String Tag = "gradedTestAdapter";

    private Context mContext;
    int mResource;

    public gradedTestAdapter(@NonNull Context context, int resource, ArrayList<GradedTest> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getTestName();
        String score = getItem(position).getPoints() + " / " + getItem(position).getTotalPoints();
        String comments = getItem(position).getNotes();

        GradedTest gradedTest = new GradedTest(name, score, comments);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvTestName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvScore = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvComments = (TextView) convertView.findViewById(R.id.textView3);

        tvTestName.setText(name);
        tvScore.setText(score);
        tvComments.setText(comments);

        return convertView;


    }
}
