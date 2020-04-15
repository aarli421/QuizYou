package com.example.quizyou.signUpSpinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quizyou.R;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<UserItem> {

    public UserAdapter(Context context, ArrayList<UserItem> userList){
        super(context, 0, userList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_customize, parent, false
            );
        }

        ImageView imageViewUser = convertView.findViewById(R.id.image_view_user);
        TextView textViewName = convertView.findViewById(R.id.text_view_name);

        UserItem currentItem = getItem(position);

        if(currentItem != null) {
            imageViewUser.setImageResource(currentItem.getUserImage());
            textViewName.setText(currentItem.getUserName());
        }

        return convertView;
    }
}
