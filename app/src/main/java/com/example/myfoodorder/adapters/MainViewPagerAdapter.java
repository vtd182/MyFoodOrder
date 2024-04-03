package com.example.myfoodorder.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myfoodorder.views.fragments.HomeFragment;
import com.example.myfoodorder.views.fragments.ProfileFragment;

public class MainViewPagerAdapter extends FragmentStateAdapter {
    public MainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public MainViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public MainViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
            {
                Log.d("MainViewPagerAdapter", "createFragment: HomeFragment");
                return new HomeFragment();
            }
            case 3:
                Log.d("MainViewPagerAdapter", "createFragment: ProfileFragment");
                return new ProfileFragment();

            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

}
