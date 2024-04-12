package com.example.myfoodorder.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfoodorder.R;
import com.example.myfoodorder.databinding.FragmentHomeBinding;
import com.example.myfoodorder.viewmodels.HomeViewModel;
import com.example.myfoodorder.views.BaseFragment;
import com.example.myfoodorder.views.activities.MainActivity;

public class HomeFragment extends BaseFragment {

    private HomeViewModel mHomeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentHomeBinding fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        mHomeViewModel = new HomeViewModel(getActivity());
        fragmentHomeBinding.setHomeViewModel(mHomeViewModel);

        return fragmentHomeBinding.getRoot();
    }

    @Override
    protected void initToolbar() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null)
            mainActivity.setToolBar(true, "Home");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHomeViewModel != null) {
            mHomeViewModel.release();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mHomeViewModel != null) {
            mHomeViewModel.release();
        }
    }
}