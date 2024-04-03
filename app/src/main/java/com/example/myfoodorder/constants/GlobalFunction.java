package com.example.myfoodorder.constants;

import android.content.Context;
import android.widget.Toast;

public class GlobalFunction {
    public static void showToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
