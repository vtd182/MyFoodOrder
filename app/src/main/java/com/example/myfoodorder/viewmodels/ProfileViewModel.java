package com.example.myfoodorder.viewmodels;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileViewModel {
    Context mContext;
    ObservableField<String> userName = new ObservableField<>();

    ObservableField<String> userID = new ObservableField<>();

    public ProfileViewModel(Context mContext) {
        this.mContext = mContext;
        getUserNameFromFireBase();
        getUserIDFromFireBase();
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

    private void getUserIDFromFireBase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            setUserID(user.getUid());
        }
    }
    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public ObservableField<String> getUserName() {
        return userName;
    }

    public ObservableField<String> getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID.set(userID);
    }
}
