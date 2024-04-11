package com.example.myfoodorder.viewmodels;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodorder.R;
import com.example.myfoodorder.adapters.RestaurantWithFoodsInBagAdapter;
import com.example.myfoodorder.constants.GlobalFunction;
import com.example.myfoodorder.database.AppDatabase;
import com.example.myfoodorder.database.RestaurantWithFoods;
import com.example.myfoodorder.databinding.LayoutBottomSheetBagBinding;
import com.example.myfoodorder.databinding.LayoutBottomSheetCheckoutBinding;
import com.example.myfoodorder.event.PriceUpdateEvent;
import com.example.myfoodorder.models.Food;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class BagViewModel {
    Context mContext;
    static RestaurantWithFoodsInBagAdapter mAdapter;

    public static String strTotalPrice;

    private static int mAmount;
    public ObservableList<RestaurantWithFoods> restaurantsWithFoods;

    public ObservableField<String> strPrice = new ObservableField<>("0 VND");


    public BagViewModel(Context mContext) {
        this.mContext = mContext;
        getAllItemInBag();
        strPrice.set(getValueTotalPrice(mContext));
    }

    public void getAllItemInBag() {
        Log.e("getAllItemInBag", "getAllItemInBag");
        // Get all item in bag
        if (restaurantsWithFoods != null) {
            restaurantsWithFoods.clear();
        } else {
            restaurantsWithFoods = new ObservableArrayList<>();
        }
        List<RestaurantWithFoods> list = AppDatabase.getInstance(mContext).restaurantDAO().getRestaurantsWithFoods();
        restaurantsWithFoods.addAll(list);
    }
    @BindingAdapter({"list_food_in_bag", "calculate_price"})
    public static void loadFoodInBag(RecyclerView recyclerView, ObservableList<RestaurantWithFoods> list, TextView textView) {
        // Load food in bag
        Log.e("loadFoodInBag", "loadFoodInBag");
        mAdapter = new RestaurantWithFoodsInBagAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        strTotalPrice = getValueTotalPrice(recyclerView.getContext());
        textView.setText(strTotalPrice);
        recyclerView.setAdapter(mAdapter);
    }

    private static String getValueTotalPrice(Context context) {
        List<Food> listFoodCart = AppDatabase.getInstance(context).foodDAO().getListFoodBag();
        if (listFoodCart == null || listFoodCart.isEmpty()) {
            mAmount = 0;
            return 0 + " VND";
        }

        int totalPrice = 0;
        for (Food food : listFoodCart) {
            totalPrice = totalPrice + food.getTotalPrice();
        }

        mAmount = totalPrice;
        return totalPrice + " VND";
    }

    @SuppressLint("NotifyDataSetChanged")
    private void clearBag() {
        if (restaurantsWithFoods != null) {
            restaurantsWithFoods.clear();
        }
        mAdapter.notifyDataSetChanged();
    }

    public void updatePrice(String price, int amount) {
        strTotalPrice = price;
        mAmount = amount;
        strPrice.set(strTotalPrice);
    }

    public void onClickCheckOut() {
        if (mContext == null) {
            return;
        }
        if (mAmount == 0) {
            return;
        }

        // Check out
        LayoutBottomSheetCheckoutBinding bottomSheetCheckoutBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.layout_bottom_sheet_checkout, null, false);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
        bottomSheetDialog.setContentView(bottomSheetCheckoutBinding.getRoot());
        bottomSheetDialog.show();
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        ColorDrawable dimDrawable = new ColorDrawable(Color.BLACK);
        dimDrawable.setAlpha(150); // Đặt độ trong suốt, 0 là hoàn toàn trong suốt, 255 là hoàn toàn không trong suốt

        // Đặt Drawable mờ làm nền cho cửa sổ của bottomSheetDialog
        bottomSheetDialog.getWindow().setBackgroundDrawable(dimDrawable);

        DialogCheckoutViewModel dialogCheckoutViewModel = new DialogCheckoutViewModel(mContext, bottomSheetDialog, restaurantsWithFoods, () -> {
            GlobalFunction.showToastMessage(mContext, "Order successfully");
            GlobalFunction.hideSoftKeyboard((Activity) mContext);
            bottomSheetDialog.dismiss();

            AppDatabase.getInstance(mContext).foodDAO().deleteAllFood();
            AppDatabase.getInstance(mContext).restaurantDAO().deleteAllRestaurants();
            clearBag();
        });
        bottomSheetCheckoutBinding.setDialogCheckoutViewModel(dialogCheckoutViewModel);
    }

}
