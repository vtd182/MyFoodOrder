package com.example.myfoodorder.viewmodels;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.example.myfoodorder.R;
import com.example.myfoodorder.databinding.LayoutBottomSheetLoginBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class DialogRegisterViewModel {

    Activity mActivity;
    BottomSheetDialog mBottomSheetDialog;

    public DialogRegisterViewModel(Activity mActivity, BottomSheetDialog mBottomSheetDialog) {
        this.mActivity = mActivity;
        this.mBottomSheetDialog = mBottomSheetDialog;
    }
    public void onRegisterClick() {
        Toast.makeText(mActivity, "OK", Toast.LENGTH_SHORT).show();

    }

    public void onNavigateToLogin() {
        mBottomSheetDialog.dismiss();

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
}
