package com.example.myfoodorder.views.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myfoodorder.R;
import com.example.myfoodorder.databinding.ActivityEditProfileBinding;
import com.example.myfoodorder.databinding.ActivityFoodDetailBinding;
import com.example.myfoodorder.event.InfoUpdateEvent;
import com.example.myfoodorder.event.ReloadListCartEvent;
import com.example.myfoodorder.listeners.IUpdateInfoListener;
import com.example.myfoodorder.viewmodels.EditProfileViewModel;

import org.greenrobot.eventbus.EventBus;

public class EditProfileActivity extends AppCompatActivity {
    EditProfileViewModel mEditProfileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActivityEditProfileBinding activityEditProfileBinding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(activityEditProfileBinding.getRoot());

        mEditProfileViewModel = new EditProfileViewModel(this, new IUpdateInfoListener() {
            @Override
            public void onUpdateInfoSuccess() {
                // event bus for reload profile
                EventBus.getDefault().post(new InfoUpdateEvent());
            }
        });
        activityEditProfileBinding.setEditProfileViewModel(mEditProfileViewModel);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mEditProfileViewModel.onActivityResult(requestCode, resultCode, data);
    }
}