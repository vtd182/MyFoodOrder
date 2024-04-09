package com.example.myfoodorder.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfoodorder.R;
import com.example.myfoodorder.views.BaseFragment;
import com.example.myfoodorder.views.activities.MainActivity;

public class OrdersFragment extends BaseFragment {


    @Override
    protected void initToolbar() {
         MainActivity mainActivity = (MainActivity) getActivity();
         if (mainActivity != null)
             mainActivity.setToolBar(true, "Orders");
    }
}