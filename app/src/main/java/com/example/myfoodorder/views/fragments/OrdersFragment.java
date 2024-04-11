package com.example.myfoodorder.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfoodorder.R;
import com.example.myfoodorder.adapters.OrderAdapter;
import com.example.myfoodorder.databinding.FragmentBagBinding;
import com.example.myfoodorder.databinding.FragmentOrdersBinding;
import com.example.myfoodorder.viewmodels.BagViewModel;
import com.example.myfoodorder.viewmodels.OrdersViewModel;
import com.example.myfoodorder.views.BaseFragment;
import com.example.myfoodorder.views.activities.MainActivity;

import org.greenrobot.eventbus.EventBus;

public class OrdersFragment extends BaseFragment {

    OrdersViewModel mOrdersViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentOrdersBinding fragmentOrdersBinding = FragmentOrdersBinding.inflate(inflater, container, false);
        mOrdersViewModel = new OrdersViewModel(getActivity());
        fragmentOrdersBinding.setOrdersViewModel(mOrdersViewModel);
        return fragmentOrdersBinding.getRoot();
    }
    @Override
    protected void initToolbar() {
         MainActivity mainActivity = (MainActivity) getActivity();
         if (mainActivity != null)
             mainActivity.setToolBar(true, "Orders");
    }
}