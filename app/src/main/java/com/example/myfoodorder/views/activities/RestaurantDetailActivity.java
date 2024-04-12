package com.example.myfoodorder.views.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myfoodorder.R;
import com.example.myfoodorder.constants.Constants;
import com.example.myfoodorder.constants.GlobalFunction;
import com.example.myfoodorder.databinding.ActivityRestaurantDetailBinding;
import com.example.myfoodorder.models.Restaurant;
import com.example.myfoodorder.viewmodels.RestaurantDetailViewModel;

public class RestaurantDetailActivity extends AppCompatActivity {
    RestaurantDetailViewModel mRestaurantDetailViewModel;
    Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActivityRestaurantDetailBinding activityRestaurantDetailBinding = ActivityRestaurantDetailBinding.inflate(getLayoutInflater());
        setContentView(activityRestaurantDetailBinding.getRoot());
        // get data from bundle
        getDataIntent();
        mRestaurantDetailViewModel = new RestaurantDetailViewModel(this, restaurant);
        activityRestaurantDetailBinding.setRestaurantDetailViewModel(mRestaurantDetailViewModel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            restaurant = (Restaurant) bundle.get(Constants.KEY_INTENT_RESTAURANT_OBJECT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRestaurantDetailViewModel != null) {
            mRestaurantDetailViewModel.release();
        }
    }
}