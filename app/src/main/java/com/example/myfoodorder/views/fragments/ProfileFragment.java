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
import com.example.myfoodorder.databinding.FragmentProfileBinding;
import com.example.myfoodorder.viewmodels.HomeViewModel;
import com.example.myfoodorder.viewmodels.ProfileViewModel;
import com.example.myfoodorder.views.BaseFragment;
import com.example.myfoodorder.views.activities.MainActivity;

public class ProfileFragment extends BaseFragment {

    ProfileViewModel mProfileViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentProfileBinding fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false);
        mProfileViewModel = new ProfileViewModel(getActivity());
        fragmentProfileBinding.setProfileViewModel(mProfileViewModel);

        return fragmentProfileBinding.getRoot();
    }
    @Override
    protected void initToolbar() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null)
            mainActivity.setToolBar(true, "Profile");
    }
}