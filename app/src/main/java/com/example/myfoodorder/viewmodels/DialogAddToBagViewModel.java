package com.example.myfoodorder.viewmodels;

import android.app.Activity;

import com.example.myfoodorder.constants.GlobalFunction;
import com.example.myfoodorder.models.Food;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class DialogAddToBagViewModel {
    Activity mActivity;
    BottomSheetDialog mBottomSheetDialog;
    Food food;

    public DialogAddToBagViewModel(Activity mActivity, BottomSheetDialog mBottomSheetDialog, Food food) {
        this.mActivity = mActivity;
        this.mBottomSheetDialog = mBottomSheetDialog;
        this.food = food;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public void onAddToBagClick() {
        // Add food to bag
        GlobalFunction.showToastMessage(mActivity, "Added to bag:" + food.getName());
    }

    public void onCancelClick() {
        mBottomSheetDialog.dismiss();
    }
}
