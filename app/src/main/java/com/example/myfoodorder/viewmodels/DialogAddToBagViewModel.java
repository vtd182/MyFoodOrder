package com.example.myfoodorder.viewmodels;

import android.app.Activity;
import android.app.Application;
import android.widget.TextView;

import androidx.databinding.ObservableField;

import com.example.myfoodorder.ControllerApplication;
import com.example.myfoodorder.constants.GlobalFunction;
import com.example.myfoodorder.database.AppDatabase;
import com.example.myfoodorder.event.ReloadListCartEvent;
import com.example.myfoodorder.models.Food;
import com.example.myfoodorder.models.Restaurant;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class DialogAddToBagViewModel {
    Activity mActivity;
    BottomSheetDialog mBottomSheetDialog;
    Food food;
    public ObservableField<String> strTotalPrice = new ObservableField<>();

    public DialogAddToBagViewModel(Activity mActivity, BottomSheetDialog mBottomSheetDialog, Food food) {
        this.mActivity = mActivity;
        this.mBottomSheetDialog = mBottomSheetDialog;
        this.food = food;
        strTotalPrice.set(food.getPrice() + " VND");
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public void onAddToBagClick() {
        ControllerApplication app = ControllerApplication.get(mActivity);
        app.getRestaurantById(food.getRestaurantID(), restaurant -> {
            if (restaurant != null) {
                // Check restaurant is in database
                List<Restaurant> data = AppDatabase.getInstance(mActivity).restaurantDAO().checkRestaurant(restaurant.getId());
                if (data.size() == 0 && restaurant != null) {
                    // Insert restaurant to database
                    AppDatabase.getInstance(mActivity).restaurantDAO().insertRestaurant(restaurant);
                }

                // Check food is in database
                List<Food> foods = AppDatabase.getInstance(mActivity).foodDAO().checkFoodInBag(food.getId());
                if (foods.size() == 0 && food != null) {
                    // Insert food to database
                    AppDatabase.getInstance(mActivity).foodDAO().insertFood(food);
                } else {
                    AppDatabase.getInstance(mActivity).foodDAO().updateFood(food);
                }
            }
            EventBus.getDefault().post(new ReloadListCartEvent());
        });
        GlobalFunction.showToastMessage(mActivity, "Add to bag successfully");
        mBottomSheetDialog.dismiss();
    }

    public void onClickSubtractCount(TextView tvCount) {
        int count = Integer.parseInt(tvCount.getText().toString());
        if (count <= 1) {
            return;
        }
        int newCount = Integer.parseInt(tvCount.getText().toString()) - 1;
        tvCount.setText(String.valueOf(newCount));

        int totalPrice = food.getPrice() * newCount;
        String strTotalPrice1 = totalPrice + " VND";
        strTotalPrice.set(strTotalPrice1);

        food.setOrderQuantity(newCount);
        food.setTotalPrice(totalPrice);
    }

    public void onClickAddCount(TextView tvCount) {
        int newCount = Integer.parseInt(tvCount.getText().toString()) + 1;
        tvCount.setText(String.valueOf(newCount));

        int totalPrice2 = food.getPrice() * newCount;
        String strTotalPrice2 = totalPrice2 + " VND";
        strTotalPrice.set(strTotalPrice2);

        food.setOrderQuantity(newCount);
        food.setTotalPrice(totalPrice2);
    }

    public void onCancelClick(TextView tvCount) {
        strTotalPrice.set(food.getPrice() + " VND");
        food.setOrderQuantity(1);
        mBottomSheetDialog.dismiss();
    }
}
