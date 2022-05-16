package com.example.bookkart_android.shop;

import static com.example.bookkart_android.Utils.getImageUrl;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.bookkart_android.R;
import com.example.bookkart_android.databinding.ItemBookBinding;
import com.example.bookkart_android.models.Book;
import com.example.bookkart_android.models.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Book> mValues;
    private final List<Book> mFavorites;
    private final List<Book> searchedBooks;
    private final MainViewModel mainViewModel;

    public BookRecyclerViewAdapter(List<Book> items, List<Book> favorites, MainViewModel viewModel) {
        mValues = items;
        mFavorites = favorites;
        mainViewModel = viewModel;
        searchedBooks = new ArrayList<>();
        searchedBooks.addAll(mValues);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    void submitList(List<Book> books) {
        mValues.clear();
        mValues.addAll(books);
        searchedBooks.clear();
        searchedBooks.addAll(mValues);
        notifyDataSetChanged();
    }

    void submitFavorites(List<Book> favorites) {
        mFavorites.clear();
        mFavorites.addAll(favorites);
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
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        Category category;
        Book book = searchedBooks.get(position);
        ((BookViewHolder) holder).bind(book);
    }

    @Override
    public int getItemCount() {
        return searchedBooks.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        ItemBookBinding binding;
        public BookViewHolder(ItemBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Book book) {
            binding.bookDate.setText(book.date);
            binding.bookName.setText(book.title);
            binding.bookPrice.setText(String.format("$ %.2f", book.price));
            Picasso.get().load(getImageUrl(book.image)).into(binding.imageView);

            binding.addButton.setOnClickListener(v -> mainViewModel.selectBook(book));

            if (mFavorites.contains(book)) {
                binding.favoriteButton.setImageResource(R.drawable.ic_favorite);
                binding.favoriteButton.setOnClickListener(v -> {
//                    mainViewModel.removeBookFromFavorites(book);
                });
            } else {
                binding.favoriteButton.setImageResource(R.drawable.ic_favorite_border);
                binding.favoriteButton.setOnClickListener(v -> {
                    mainViewModel.addBookToFavorite(book);
                });
            }

            binding.getRoot().setOnClickListener(v -> mainViewModel.setCurrentBook(book));
        }

    }

}