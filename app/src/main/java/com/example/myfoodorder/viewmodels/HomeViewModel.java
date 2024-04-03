package com.example.myfoodorder.viewmodels;

import android.content.Context;

public class HomeViewModel {
    Context mContext;
    public HomeViewModel(Context mContext) {
        this.mContext = mContext;
    }

    public void release() {
        mContext = null;
    }
}
