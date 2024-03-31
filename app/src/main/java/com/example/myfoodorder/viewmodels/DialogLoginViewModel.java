package com.example.myfoodorder.viewmodels;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import com.example.myfoodorder.R;
import com.example.myfoodorder.databinding.LayoutBottomSheetRegisterBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;


@SuppressLint("ValidFragment")
public class DialogLoginViewModel {
    Activity mActivity;
    BottomSheetDialog mBottomSheetDialog;

    public ObservableField<String> email = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();

    public DialogLoginViewModel(Activity mActivity, BottomSheetDialog mBottomSheetDialog) {
        this.mActivity = mActivity;
        this.mBottomSheetDialog = mBottomSheetDialog;
    }
    public void onLoginClick() {
        String email = this.email.get();
        String password = this.password.get();
        if (email == null || email.isEmpty()) {
            Toast.makeText(mActivity, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        } else if (password == null || password.isEmpty()) {
            Toast.makeText(mActivity, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(mActivity, "OK", Toast.LENGTH_SHORT).show();
        }
    }

    public void onNavigateToRegister() {
        mBottomSheetDialog.dismiss();

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

}
