package com.example.myfoodorder.views.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myfoodorder.R;
import com.example.myfoodorder.databinding.ActivityGetStartedBinding;
import com.example.myfoodorder.databinding.ActivityMainBinding;
import com.example.myfoodorder.viewmodels.GetStartedViewModel;

public class GetStartedActivity extends AppCompatActivity {

    private ActivityGetStartedBinding activityGetStartedBinding;
    private GetStartedViewModel getStartedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        activityGetStartedBinding = ActivityGetStartedBinding.inflate(getLayoutInflater());
        getStartedViewModel = new GetStartedViewModel(this);
        activityGetStartedBinding.setGetStartedViewModel(getStartedViewModel);
        setContentView(activityGetStartedBinding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getStartedViewModel != null) {
            getStartedViewModel.release();
        }
    }
}