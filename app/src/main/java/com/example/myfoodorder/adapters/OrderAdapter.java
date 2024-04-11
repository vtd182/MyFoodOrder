package com.example.myfoodorder.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodorder.databinding.ItemFoodListBinding;
import com.example.myfoodorder.databinding.ItemOrderListBinding;
import com.example.myfoodorder.databinding.ItemOrderListBindingImpl;
import com.example.myfoodorder.models.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{
    private List<Order> mListOrders;

    public OrderAdapter(List<Order> mListOrders) {
        this.mListOrders = mListOrders;
    }

    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderListBinding itemOrderListBinding = ItemOrderListBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new OrderAdapter.OrderViewHolder(itemOrderListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, int position) {
        Order order = mListOrders.get(position);
        if (order == null) {
            return;
        }
        holder.itemOrderListBinding.setOrderModel(order);
    }

    @Override
    public int getItemCount() {
        return (null == mListOrders ? 0 : mListOrders.size());
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        ItemOrderListBinding itemOrderListBinding;
        public OrderViewHolder(ItemOrderListBinding itemOrderListBinding) {
            super(itemOrderListBinding.getRoot());
            this.itemOrderListBinding = itemOrderListBinding;
        }
    }
}
