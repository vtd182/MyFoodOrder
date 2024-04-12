package com.example.myfoodorder.viewmodels;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import com.example.myfoodorder.R;
import com.example.myfoodorder.databinding.LayoutBottomSheetLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class DialogRegisterViewModel {

    Activity mActivity;
    BottomSheetDialog mBottomSheetDialog;

    boolean isValidate = false;

    ObservableField<String> email = new ObservableField<>();
    ObservableField<String> password = new ObservableField<>();
    ObservableField<String> confirmPassword = new ObservableField<>();

    public ObservableField<String> getEmail() {
        return email;
    }

    public void setEmail(ObservableField<String> email) {
        this.email = email;
    }

    public ObservableField<String> getPassword() {
        return password;
    }

    public void setPassword(ObservableField<String> password) {
        this.password = password;
    }

    public ObservableField<String> getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(ObservableField<String> confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public DialogRegisterViewModel(Activity mActivity, BottomSheetDialog mBottomSheetDialog) {
        this.mActivity = mActivity;
        this.mBottomSheetDialog = mBottomSheetDialog;
    }
    public void onRegisterClick() {
        validateForm();
        if (isValidate) {
            registerUser();
        }
    }

    private void registerUser() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String email = this.email.get();
        String password = this.password.get();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            onNavigateToLogin();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(mActivity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

    private void validateForm() {
        if (email.get() == null || email.get().isEmpty()) {
            Toast.makeText(mActivity, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.get() == null || password.get().isEmpty()) {
            Toast.makeText(mActivity, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        } else if (confirmPassword.get() == null || confirmPassword.get().isEmpty()) {
            Toast.makeText(mActivity, "Please enter confirm password", Toast.LENGTH_SHORT).show();
            return;
        } else if (!password.get().equals(confirmPassword.get())) {
            Toast.makeText(mActivity, "Password and confirm password do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        isValidate = true;
    }

    public void release() {
        mActivity = null;
    }
}
