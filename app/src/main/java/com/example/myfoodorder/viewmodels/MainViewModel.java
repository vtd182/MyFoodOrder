package com.example.myfoodorder.viewmodels;

import android.util.Log;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myfoodorder.R;
import com.example.myfoodorder.adapters.MainViewPagerAdapter;
import com.example.myfoodorder.constants.GlobalFunction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainViewModel {
    ObservableField<String> title = new ObservableField<>();
    ObservableBoolean isShowToolbar = new ObservableBoolean();

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setIsShowToolbar(boolean isShowToolbar) {
        this.isShowToolbar.set(isShowToolbar);
    }

    public ObservableField<String> getTitle() {
        return title;
    }

    public ObservableBoolean getIsShowToolbar() {
        return isShowToolbar;
    }

    @BindingAdapter({"item_selected"})
    public static void setOnNavigationItemSelectedListener(BottomNavigationView bottomNavigation, ViewPager2 viewPager2) {
        viewPager2.setUserInputEnabled(false);
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter((FragmentActivity) viewPager2.getContext());
        viewPager2.setAdapter(mainViewPagerAdapter);

        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            Log.e("TAG", "setOnNavigationItemSelectedListener: " + id);
            if (id == R.id.nav_home) {
                viewPager2.setCurrentItem(0);
            }
            if (id == R.id.nav_bag) {
                viewPager2.setCurrentItem(1);
            }
            if (id == R.id.nav_orders) {
                viewPager2.setCurrentItem(2);
            }
            if (id == R.id.nav_profile) {
                viewPager2.setCurrentItem(3);
            }
            return true;
        });
    }

    public void release() {
        title = null;
        isShowToolbar = null;
    }
}
