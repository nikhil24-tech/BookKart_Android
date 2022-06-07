package com.example.bookkart_android.shop;

import static com.example.bookkart_android.Utils.getImageUrl;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bookkart_android.R;
import com.example.bookkart_android.databinding.ItemBookBinding;
import com.example.bookkart_android.models.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FavoriteRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteRecyclerViewAdapter.FavoriteViewHolder> {

    private final List<Book> mValues;
    private final MainViewModel mainViewModel;
    private final List<Book> searchedBooks;

    public FavoriteRecyclerViewAdapter(List<Book> items, MainViewModel viewModel) {
        mValues = items;
        mainViewModel = viewModel;
        searchedBooks = new ArrayList<>(mValues);
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteViewHolder holder, int position) {
        Book book = searchedBooks.get(position);
        holder.bind(book);
    }

    public void submitList(List<Book> books) {
        mValues.clear();
        mValues.addAll(books);
        searchedBooks.clear();
        searchedBooks.addAll(books);
        notifyDataSetChanged();
    }

    void search(String term) {
        if (term == null || term.isEmpty()) {
            searchedBooks.clear();
            searchedBooks.addAll(mValues);
            notifyDataSetChanged();
        }
        List<Book> newSearched = new ArrayList<>();
        for (Book book: mValues) {
            if (book.title.toLowerCase(Locale.ROOT).contains(term.toLowerCase(Locale.ROOT))) {
                newSearched.add(book);
            }
        }
        searchedBooks.clear();
        searchedBooks.addAll(newSearched);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return searchedBooks.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        ItemBookBinding binding;

        public FavoriteViewHolder(ItemBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Book book) {
            binding.bookDate.setText(book.date);
            binding.bookName.setText(book.title);
            binding.bookPrice.setText(String.format("$ %.2f", book.price));
            Picasso.get().load(getImageUrl(book.image)).into(binding.imageView);

            binding.addButton.setOnClickListener(v -> mainViewModel.selectBook(book));

            binding.favoriteButton.setImageResource(R.drawable.ic_favorite);
            binding.favoriteButton.setOnClickListener(v -> mainViewModel.removeBookFromFavorites(book));
        }
    }
}