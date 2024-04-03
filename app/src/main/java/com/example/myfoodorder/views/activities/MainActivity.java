package com.example.myfoodorder.views.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myfoodorder.R;
import com.example.myfoodorder.databinding.ActivityMainBinding;
import com.example.myfoodorder.viewmodels.MainViewModel;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mMainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActivityMainBinding activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        mMainViewModel = new MainViewModel();
        activityMainBinding.setMainViewModel(mMainViewModel);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Toast.makeText(this, "User: " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
        }
    }

    public void setToolBar(boolean isShow, String title) {
        mMainViewModel.setIsShowToolbar(isShow);
        if (isShow) {
            mMainViewModel.setTitle(title);
        }
    }
}