package com.example.bookkart_android.shop;

import static com.example.bookkart_android.Utils.getImageUrl;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkart_android.databinding.ItemCategoryBinding;
import com.example.bookkart_android.models.Category;
import com.example.bookkart_android.models.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolder> {

    private final List<Category> mCategories;
    private final MainViewModel mainViewModel;

    public CategoryRecyclerViewAdapter(List<Category> categories, MainViewModel viewModel) {
        mCategories = categories;
        mainViewModel = viewModel;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    void submitList(List<Category> categories) {
        mCategories.clear();
        mCategories.addAll(categories);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder holder, int position) {
        Category category = mCategories.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryBinding binding;
        public CategoryViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Category category) {
            binding.textView.setText(category.name);
            Picasso.get().load(getImageUrl(category.image)).into(binding.categoryImage);
            binding.getRoot().setOnClickListener(v -> mainViewModel.selectCategory(category));
        }
    }
}