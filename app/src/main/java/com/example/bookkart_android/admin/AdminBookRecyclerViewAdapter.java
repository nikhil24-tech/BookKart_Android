package com.example.bookkart_android.admin;

import static com.example.bookkart_android.Utils.getImageUrl;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkart.databinding.ItemAdminBookBinding;
import com.example.bookkart_android.models.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminBookRecyclerViewAdapter extends RecyclerView.Adapter<AdminBookRecyclerViewAdapter.ViewHolder> {

    private final List<Book> mValues;

    public AdminBookRecyclerViewAdapter(List<Book> items) {
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemAdminBookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Book book = mValues.get(position);
        holder.binding.bookDate.setText(book.date);
        holder.binding.bookName.setText(book.title);
        holder.binding.bookPrice.setText(String.format("$ %.2f", book.price));
        Picasso.get().load(getImageUrl(book.image)).into(holder.binding.imageView);

        holder.binding.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddEditBookActivity.class);
            intent.putExtra("book", book);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemAdminBookBinding binding;
        public ViewHolder(ItemAdminBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}