package com.example.myfoodorder.viewmodels;

import android.app.Activity;
import android.database.Observable;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import com.example.myfoodorder.ControllerApplication;
import com.example.myfoodorder.R;
import com.example.myfoodorder.databinding.LayoutBottomSheetBagBinding;
import com.example.myfoodorder.databinding.LayoutBottomSheetLoginBinding;
import com.example.myfoodorder.models.Food;
import com.example.myfoodorder.models.Restaurant;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class FoodDetailViewModel {
    Activity mActivity;

    ObservableField<Food> food = new ObservableField<>();

    public void onClickButtonBack() {
        mActivity.onBackPressed();
    }

    public FoodDetailViewModel(Activity mActivity, Food mFood) {
            this.mActivity = mActivity;
            this.food.set(mFood);
    }

    public ObservableField<Food> getFood() {
        return food;
    }

    public void setFood(ObservableField<Food> food) {
        this.food = food;
    }

    public void onAddToBagClick() {
        LayoutBottomSheetBagBinding bottomSheetBagBinding = DataBindingUtil.inflate(mActivity.getLayoutInflater(),
                R.layout.layout_bottom_sheet_bag, null, false);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mActivity);
        bottomSheetDialog.setContentView(bottomSheetBagBinding.getRoot());
        bottomSheetDialog.show();
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        ColorDrawable dimDrawable = new ColorDrawable(Color.BLACK);
        dimDrawable.setAlpha(150); // Đặt độ trong suốt, 0 là hoàn toàn trong suốt, 255 là hoàn toàn không trong suốt

        // Đặt Drawable mờ làm nền cho cửa sổ của bottomSheetDialog
        bottomSheetDialog.getWindow().setBackgroundDrawable(dimDrawable);

        DialogAddToBagViewModel dialogAddToBagViewModel = new DialogAddToBagViewModel(mActivity, bottomSheetDialog, food.get());
        bottomSheetBagBinding.setDialogAddToBagViewModel(dialogAddToBagViewModel);
    }

    public void release() {
        mActivity = null;
    }
}
