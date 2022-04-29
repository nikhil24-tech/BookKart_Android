package com.example.bookkart_android;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkart.databinding.ItemAdminBookBinding;

import java.util.List;

public class AdminBookRecyclerViewAdapter extends RecyclerView.Adapter<AdminBookRecyclerViewAdapter.ViewHolder> {

    private final List<?> mValues;

    public AdminBookRecyclerViewAdapter(List<?> items) {
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemAdminBookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(ItemAdminBookBinding binding) {
            super(binding.getRoot());
            binding.editButton.setOnClickListener(v -> v.getContext().startActivity(new Intent(v.getContext(), AddEditBookActivity.class)));
        }

    }
}