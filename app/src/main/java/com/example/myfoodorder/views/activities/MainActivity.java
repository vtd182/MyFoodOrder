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

import org.greenrobot.eventbus.EventBus;

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
    }

    public void setToolBar(boolean isShow, String title) {
        mMainViewModel.setIsShowToolbar(isShow);
        if (isShow) {
            mMainViewModel.setTitle(title);
        }
    }
}