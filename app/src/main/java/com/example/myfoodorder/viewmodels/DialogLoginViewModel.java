package com.example.myfoodorder.viewmodels;

import static android.content.ContentValues.TAG;

import static androidx.core.app.ActivityCompat.finishAffinity;
import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import com.example.myfoodorder.R;
import com.example.myfoodorder.constants.GlobalFunction;
import com.example.myfoodorder.databinding.LayoutBottomSheetRegisterBinding;
import com.example.myfoodorder.views.activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


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
            GlobalFunction.showToastMessage(mActivity, "Please enter email");
            return;
        } else if (password == null || password.isEmpty()) {
            GlobalFunction.showToastMessage(mActivity, "Please enter password");
            return;
        }

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(mActivity, MainActivity.class);
                            startActivity(mActivity, intent, null);
                            finishAffinity(mActivity);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(mActivity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

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
