package com.example.myfoodorder.viewmodels;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import com.bumptech.glide.Glide;
import com.example.myfoodorder.ControllerApplication;
import com.example.myfoodorder.R;
import com.example.myfoodorder.callbacks.UploadCallBack;
import com.example.myfoodorder.constants.Constants;
import com.example.myfoodorder.constants.GlobalFunction;
import com.example.myfoodorder.databinding.LayoutBottomChangePasswordBinding;
import com.example.myfoodorder.databinding.LayoutBottomSheetRegisterBinding;
import com.example.myfoodorder.listeners.IUpdateInfoListener;
import com.example.myfoodorder.utils.GlideUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileViewModel {
    Activity mActivity;

    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> phone = new ObservableField<>();

    public ObservableField<String> hintName = new ObservableField<>();
    public ObservableField<String> hintPhone = new ObservableField<>();

    public ObservableField<String> imageUrl = new ObservableField<>();

    IUpdateInfoListener listener;

    public EditProfileViewModel(Activity mActivity, IUpdateInfoListener listener) {
        this.mActivity = mActivity;
        getHintFromFireBase();
        reloadImage();
        this.listener = listener;
    }

    public void onClickButtonBack() {
        mActivity.onBackPressed();
    }

    public void onClickButtonSave() {
        // Save data to database
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }

        String name = this.name.get();
        if (name == null || name.isEmpty() || name.equals(hintName.get())) {
            return;
        }

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Update successful
                    getHintFromFireBase();
                    GlobalFunction.showToastMessage(mActivity, "Update profile successful");
                    listener.onUpdateInfoSuccess();
                    GlobalFunction.hideSoftKeyboard(mActivity);
                }
            }
        });
    }

    public void onClickChangeImage() {
        // Change image
        onClickRequestPermission();
    }

    private void onClickRequestPermission() {
        // Request permission
        if (Build.VERSION .SDK_INT >= Build.VERSION_CODES.M) {
            // Request permission
            openGallery();
            return;
        }
        if (mActivity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Permission granted
            openGallery();
            return;
        } else {
            // Request permission
            mActivity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        mActivity.startActivityForResult(intent, Constants.GALLERY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();


            if (selectedImageUri == null) {
                GlobalFunction.showToastMessage(mActivity, "Selected image URI is null");
                return;
            }

            String realPath = getRealPathFromURI(selectedImageUri);
            if (realPath == null) {
                GlobalFunction.showToastMessage(mActivity, "Failed to get real path from URI");
                return;
            }

            File file = new File(realPath);
            Uri fileUri = Uri.fromFile(file);

            Log.e("EditProfileViewModel", "onActivityResult: " + fileUri.toString());

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            ControllerApplication.get(mActivity).uploadImage(selectedImageUri, user.getUid(), new UploadCallBack() {
                @Override
                public void onUploadSuccess(String imageUrl) {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setPhotoUri(Uri.parse(imageUrl))
                            .build();

                    user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Update successful
                                reloadImage();
                                GlobalFunction.showToastMessage(mActivity, "Update image successful");
                                listener.onUpdateInfoSuccess();
                            }
                        }
                    });
                }

                @Override
                public void onUploadFailure(Exception e) {
                    GlobalFunction.showToastMessage(mActivity, "Update image failure");
                }
            });
        }
    }

    public void getHintFromFireBase() {
        // Get hint from database
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if (user.getDisplayName() != null) {
                hintName.set(user.getDisplayName());
            }
            else
            {
                hintName.set("your name");
            }

            if (user.getPhoneNumber() != null) {
                hintPhone.set(user.getPhoneNumber());
            }
            else
            {
                hintPhone.set("your phone number");
            }
        }
    }

    public void release() {
        mActivity = null;
    }

    @BindingAdapter("user_photo")
    public static void setUserPhoto(CircleImageView imageView, String url) {
        GlideUtils.loadUrlAvatar(url, imageView);
    }

    public void reloadImage() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if (user.getPhotoUrl() != null) {
                imageUrl.set(user.getPhotoUrl().toString());
            }
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = mActivity.getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor == null) {
            return null;
        }
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String realPath = cursor.getString(columnIndex);
        cursor.close();
        return realPath;
    }

    public void onChangingPassword() {
        LayoutBottomChangePasswordBinding bottomChangePasswordBinding = DataBindingUtil.inflate(mActivity.getLayoutInflater(),
                R.layout.layout_bottom_change_password, null, false);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mActivity);
        bottomSheetDialog.setContentView(bottomChangePasswordBinding.getRoot());
        bottomSheetDialog.show();
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        ColorDrawable dimDrawable = new ColorDrawable(Color.BLACK);
        dimDrawable.setAlpha(150); // Đặt độ trong suốt, 0 là hoàn toàn trong suốt, 255 là hoàn toàn không trong suốt

        // Đặt Drawable mờ làm nền cho cửa sổ của bottomSheetDialog
        bottomSheetDialog.getWindow().setBackgroundDrawable(dimDrawable);

        DialogChangePasswordViewModel dialogChangePasswordViewModel = new DialogChangePasswordViewModel(mActivity, bottomSheetDialog);
        bottomChangePasswordBinding.setDialogChangePasswordViewModel(dialogChangePasswordViewModel);
    }
}
