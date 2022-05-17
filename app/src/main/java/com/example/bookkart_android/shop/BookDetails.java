package com.example.bookkart_android.shop;

import static com.example.bookkart_android.Utils.getImageUrl;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookkart_android.databinding.FragmentBookDetailsBinding;
import com.squareup.picasso.Picasso;

public class BookDetails extends Fragment {
    FragmentBookDetailsBinding binding;

    public BookDetails() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBookDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mainViewModel.getCurrentBook().observe(getViewLifecycleOwner(), book -> {
            if (book == null) {
                return;
            }

            binding.bookDate.setText(book.date);
            binding.bookName.setText(book.title);
            binding.bookPrice.setText(String.format("$ %.2f", book.price));
            Picasso.get().load(getImageUrl(book.image)).into(binding.imageView);

            binding.addButton.setOnClickListener(v -> mainViewModel.selectBook(book));

//            if (mainViewModel.getFavoriteBooks().getValue().contains(book)) {
//                binding.favoriteButton.setImageResource(R.drawable.ic_favorite);
//                binding.favoriteButton.setOnClickListener(v -> {
//                    mainViewModel.removeBookFromFavorites(book);
//                });
//            } else {
//                binding.favoriteButton.setImageResource(R.drawable.ic_favorite_border);
//                binding.favoriteButton.setOnClickListener(v -> {
//                    mainViewModel.addBookToFavorite(book);
//                });
//            }

            binding.ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> binding.ratingText.setText(rating + " / " + 5));

            binding.getRoot().setOnClickListener(v -> mainViewModel.setCurrentBook(book));
        });
    }
}
