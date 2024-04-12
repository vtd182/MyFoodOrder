package com.example.myfoodorder.views.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myfoodorder.R;
import com.example.myfoodorder.constants.Constants;
import com.example.myfoodorder.databinding.ActivityOrderDetailBinding;
import com.example.myfoodorder.databinding.ActivityRestaurantDetailBinding;
import com.example.myfoodorder.models.Order;
import com.example.myfoodorder.models.Restaurant;
import com.example.myfoodorder.viewmodels.OrderDetailViewModel;
import com.example.myfoodorder.viewmodels.RestaurantDetailViewModel;

public class OrderDetailActivity extends AppCompatActivity {

    OrderDetailViewModel orderDetailViewModel;
    Order order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActivityOrderDetailBinding activityOrderDetailBinding = ActivityOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(activityOrderDetailBinding.getRoot());

        getDataIntent();
        orderDetailViewModel = new OrderDetailViewModel(this, order);
        activityOrderDetailBinding.setOrderDetailViewModel(orderDetailViewModel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            order  = (Order) bundle.get(Constants.KEY_INTENT_ORDER_OBJECT);
        }
    }
}