package com.example.myfoodorder.viewmodels;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.example.myfoodorder.R;
import com.example.myfoodorder.databinding.LayoutBottomSheetLoginBinding;
import com.example.myfoodorder.databinding.LayoutBottomSheetRegisterBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class GetStartedViewModel {
    private Activity mActivity;

    public GetStartedViewModel(Activity mActivity) {
        this.mActivity = mActivity;
    }
    public void onRegisterClick() {
        LayoutBottomSheetRegisterBinding bottomSheetRegisterBinding = DataBindingUtil.inflate(mActivity.getLayoutInflater(),
                R.layout.layout_bottom_sheet_register, null, false);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mActivity);
        bottomSheetDialog.setContentView(bottomSheetRegisterBinding.getRoot());
        bottomSheetDialog.show();
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        ColorDrawable dimDrawable = new ColorDrawable(Color.BLACK);
        dimDrawable.setAlpha(150); // Đặt độ trong suốt, 0 là hoàn toàn trong suốt, 255 là hoàn toàn không trong suốt

        // Đặt Drawable mờ làm nền cho cửa sổ của bottomSheetDialog
        bottomSheetDialog.getWindow().setBackgroundDrawable(dimDrawable);

        DialogRegisterViewModel dialogRegisterViewModel = new DialogRegisterViewModel(mActivity, bottomSheetDialog);
        bottomSheetRegisterBinding.setDialogRegisterViewModel(dialogRegisterViewModel);
    }

    public void onLoginClick() {
        LayoutBottomSheetLoginBinding bottomSheetLoginBinding = DataBindingUtil.inflate(mActivity.getLayoutInflater(),
                R.layout.layout_bottom_sheet_login, null, false);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mActivity);
        bottomSheetDialog.setContentView(bottomSheetLoginBinding.getRoot());
        bottomSheetDialog.show();
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        ColorDrawable dimDrawable = new ColorDrawable(Color.BLACK);
        dimDrawable.setAlpha(150); // Đặt độ trong suốt, 0 là hoàn toàn trong suốt, 255 là hoàn toàn không trong suốt

        // Đặt Drawable mờ làm nền cho cửa sổ của bottomSheetDialog
        bottomSheetDialog.getWindow().setBackgroundDrawable(dimDrawable);

        DialogLoginViewModel dialogLoginViewModel = new DialogLoginViewModel(mActivity, bottomSheetDialog);
        bottomSheetLoginBinding.setDialogLoginViewModel(dialogLoginViewModel);
    }

    public void release() {
        mActivity = null;
    }
}
