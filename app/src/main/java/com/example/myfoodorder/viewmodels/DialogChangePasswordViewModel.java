package com.example.myfoodorder.viewmodels;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.example.myfoodorder.constants.GlobalFunction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DialogChangePasswordViewModel {
    Activity mActivity;
    BottomSheetDialog mBottomSheetDialog;
    public ObservableField<String> oldPassword = new ObservableField<>();
    public ObservableField<String> newPassword = new ObservableField<>();
    public ObservableField<String> confirmPassword = new ObservableField<>();

    public DialogChangePasswordViewModel(Activity mActivity, BottomSheetDialog mBottomSheetDialog) {
        this.mActivity = mActivity;
        this.mBottomSheetDialog = mBottomSheetDialog;
    }

    public void onClickButtonConfirm() {
        GlobalFunction.hideSoftKeyboard(mActivity);
        // Change password
        String oldPassword = this.oldPassword.get();
        String newPassword = this.newPassword.get();
        String confirmPassword = this.confirmPassword.get();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Re-authenticate user
            AuthCredential credential = EmailAuthProvider
                    .getCredential(user.getEmail(), oldPassword);

            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // User re-authenticated successfully, change password
                                if (newPassword.equals(confirmPassword)) {
                                    user.updatePassword(newPassword)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        // Password updated successfully
                                                        GlobalFunction.showToastMessage(mActivity, "Password updated successfully");
                                                        clearFields();
                                                        mBottomSheetDialog.dismiss();
                                                    } else {
                                                        // Failed to update password
                                                        GlobalFunction.showToastMessage(mActivity, "Failed to update password");
                                                    }
                                                }
                                            });
                                } else {
                                    // New passwords do not match
                                    GlobalFunction.showToastMessage(mActivity, "New passwords do not match");
                                }
                            } else {
                                // Failed to re-authenticate user
                                GlobalFunction.showToastMessage(mActivity, "Invalid old password");
                            }
                        }
                    });
        }
    }

    private void clearFields() {
        oldPassword.set("");
        newPassword.set("");
        confirmPassword.set("");
    }

}
