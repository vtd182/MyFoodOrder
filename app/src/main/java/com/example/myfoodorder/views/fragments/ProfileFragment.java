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
import com.example.myfoodorder.event.InfoUpdateEvent;
import com.example.myfoodorder.event.PriceUpdateEvent;
import com.example.myfoodorder.viewmodels.HomeViewModel;
import com.example.myfoodorder.viewmodels.ProfileViewModel;
import com.example.myfoodorder.views.BaseFragment;
import com.example.myfoodorder.views.activities.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ProfileFragment extends BaseFragment {

    ProfileViewModel mProfileViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentProfileBinding fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false);
        mProfileViewModel = new ProfileViewModel(getActivity());
        fragmentProfileBinding.setProfileViewModel(mProfileViewModel);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return fragmentProfileBinding.getRoot();
    }
    @Override
    protected void initToolbar() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null)
            mainActivity.setToolBar(true, "Profile");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onInfoUpdateEvent(InfoUpdateEvent event) {
        if (mProfileViewModel != null) {
            mProfileViewModel.reloadUserInfo();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mProfileViewModel != null) {
            mProfileViewModel.release();
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}