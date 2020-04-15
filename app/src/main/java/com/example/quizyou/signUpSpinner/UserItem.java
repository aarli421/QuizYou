package com.example.quizyou.signUpSpinner;

public class UserItem {
    private String mUserName;
    private int mUserImage;

    public UserItem(String userName, int userImage){
        mUserName = userName;
        mUserImage = userImage;
    }

    public String getUserName(){
        return mUserName;
    }

    public int getUserImage(){
        return mUserImage;
    }
}
