package com.example.bookkart_android;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkart.databinding.ItemCartBinding;

import java.util.List;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {

    private final List<?> mValues;

    public CartRecyclerViewAdapter(List<?> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(ItemCartBinding binding) {
            super(binding.getRoot());
        }

    }
}