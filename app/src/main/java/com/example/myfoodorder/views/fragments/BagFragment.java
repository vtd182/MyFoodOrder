package com.example.myfoodorder.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfoodorder.R;
import com.example.myfoodorder.databinding.FragmentBagBinding;
import com.example.myfoodorder.databinding.FragmentProfileBinding;
import com.example.myfoodorder.viewmodels.BagViewModel;
import com.example.myfoodorder.viewmodels.ProfileViewModel;
import com.example.myfoodorder.views.BaseFragment;
import com.example.myfoodorder.views.activities.MainActivity;

public class BagFragment extends BaseFragment {

    BagViewModel mBagViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentBagBinding fragmentBagBinding = FragmentBagBinding.inflate(inflater, container, false);
        mBagViewModel = new BagViewModel(getActivity());
        fragmentBagBinding.setBagViewModel(mBagViewModel);

        return fragmentBagBinding.getRoot();
    }

    @Override
    protected void initToolbar() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null)
            mainActivity.setToolBar(true, "Bag");
    }
}