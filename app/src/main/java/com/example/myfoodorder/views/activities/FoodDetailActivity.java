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
import com.example.myfoodorder.databinding.ActivityFoodDetailBinding;
import com.example.myfoodorder.models.Food;
import com.example.myfoodorder.viewmodels.FoodDetailViewModel;

public class FoodDetailActivity extends AppCompatActivity {

    FoodDetailViewModel mFoodDetailViewModel;
    Food food;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Get data from bundle
        getDataIntent();

        ActivityFoodDetailBinding activityFoodDetailBinding = ActivityFoodDetailBinding.inflate(getLayoutInflater());
        setContentView(activityFoodDetailBinding.getRoot());
        mFoodDetailViewModel = new FoodDetailViewModel(this, food);
        activityFoodDetailBinding.setFoodDetailViewModel(mFoodDetailViewModel);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            food = (Food) bundle.get(Constants.KEY_INTENT_FOOD_OBJECT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFoodDetailViewModel != null) {
            mFoodDetailViewModel.release();
        }
    }

}