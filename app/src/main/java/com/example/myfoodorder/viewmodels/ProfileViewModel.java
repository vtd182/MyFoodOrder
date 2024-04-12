package com.example.myfoodorder.viewmodels;

import android.content.Context;
import android.view.View;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;

import com.example.myfoodorder.constants.GlobalFunction;
import com.example.myfoodorder.utils.GlideUtils;
import com.example.myfoodorder.views.activities.EditProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileViewModel {
    Context mContext;
    ObservableField<String> userName = new ObservableField<>();

    ObservableField<String> userID = new ObservableField<>();

    ObservableField<String> avtUrl = new ObservableField<>();

    public ObservableField<String> getAvtUrl() {
        return avtUrl;
    }

    public void setAvtUrl(String avtUrl) {
        this.avtUrl.set(avtUrl);
    }

    public ProfileViewModel(Context mContext) {
        this.mContext = mContext;
        getUserNameFromFireBase();
        getUserPhotoUrlFromFireBase();
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

    private void getUserPhotoUrlFromFireBase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            setAvtUrl(user.getPhotoUrl().toString());
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

    public void onEditProfile(View view) {
        GlobalFunction.startActivity(view.getContext(), EditProfileActivity.class);
    }

    @BindingAdapter("user_image")
    public static void setUserPhoto(CircleImageView imageView, String url) {
        GlideUtils.loadUrlAvatar(url, imageView);
    }
    public void release() {
        mContext = null;
    }

    public void reloadUserInfo() {
        getUserNameFromFireBase();
        getUserPhotoUrlFromFireBase();
    }
}
