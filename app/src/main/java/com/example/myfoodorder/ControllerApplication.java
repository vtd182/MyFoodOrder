package com.example.myfoodorder;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ControllerApplication extends Application {
    private static final String FIREBASE_URL = "https://foodorder-842d1-default-rtdb.firebaseio.com";
    private FirebaseDatabase mFirebaseDatabase;

    public static ControllerApplication get(Context context) {
        return (ControllerApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_URL);
    }

    public DatabaseReference getRestaurantDatabaseReference() {
        return mFirebaseDatabase.getReference("/restaurant");
    }
}
