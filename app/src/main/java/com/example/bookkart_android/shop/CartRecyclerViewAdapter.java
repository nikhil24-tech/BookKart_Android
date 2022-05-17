package com.example.bookkart_android.shop;

import static com.example.bookkart_android.Utils.getImageUrl;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkart_android.databinding.ItemCartBinding;

import com.example.bookkart_android.models.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {

    private final List<Book> mValues;
    private final MainViewModel mainViewModel;

    public CartRecyclerViewAdapter(List<Book> items, MainViewModel viewModel) {
        mValues = items;
        mainViewModel = viewModel;
    }

    void submitList(List<Book> books) {
        mValues.clear();
        mValues.addAll(books);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Book book = mValues.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemCartBinding binding;
        public ViewHolder(ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Book book) {
            binding.bookDate.setText(book.date);
            binding.bookName.setText(book.title);
            binding.bookPrice.setText(String.format("$ %.2f", book.price));
            Picasso.get().load(getImageUrl(book.image)).into(binding.imageView);
//            binding.removeButton.setOnClickListener(v -> mainViewModel.unselectBook(book));
        }

    }
}