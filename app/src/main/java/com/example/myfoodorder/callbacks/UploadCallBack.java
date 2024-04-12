package com.example.myfoodorder.callbacks;

public interface UploadCallBack {
    void onUploadSuccess(String imageUrl);
    void onUploadFailure(Exception e);
}

