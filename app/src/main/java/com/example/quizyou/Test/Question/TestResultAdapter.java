package com.example.quizyou.Test.Question;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quizyou.R;
import com.example.quizyou.Test.TestResult;
import com.example.quizyou.User.Student;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TestResultAdapter extends ArrayAdapter<TestResult> {

    private static final String TAG = "TestResultAdapter";

    private Context mContext;
    int mResource;

    public TestResultAdapter(@NonNull Context context, int resource, ArrayList<TestResult> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String testName = getItem(position).getTest().getName();
        String studentName = getItem(position).getAnswerer().getName();


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvTestName = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvStudentName = (TextView) convertView.findViewById(R.id.textView1);

        tvTestName.setText(testName);
        tvStudentName.setText(studentName);

        return convertView;
    }
}
