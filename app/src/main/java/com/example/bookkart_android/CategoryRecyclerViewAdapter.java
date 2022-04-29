package com.example.bookkart_android;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkart.databinding.ItemCategoryBinding;

import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {

    private final List<?> mValues;

    public CategoryRecyclerViewAdapter(List<?> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
        }

    }
}