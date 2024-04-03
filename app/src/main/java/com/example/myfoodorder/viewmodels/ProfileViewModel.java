package com.example.myfoodorder.viewmodels;

import android.content.Context;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileViewModel {
    Context mContext;
    ObservableField<String> userName = new ObservableField<>();

    public ProfileViewModel(Context mContext) {
        this.mContext = mContext;
        getUserNameFromFireBase();
    }

    private void getUserNameFromFireBase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if (user.getDisplayName() != null) {
                setUserName(user.getDisplayName());
            }
            else
            {
                setUserName(user.getEmail());
            }
        }
    }
    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public ObservableField<String> getUserName() {
        return userName;
    }

}
