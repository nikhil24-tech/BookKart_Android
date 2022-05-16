package com.example.bookkart_android.shop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookkart_android.R;
import com.example.bookkart_android.databinding.FragmentBooksBinding;

import java.util.ArrayList;

public class BooksFragment extends Fragment {
    FragmentBooksBinding binding;
    BookRecyclerViewAdapter adapter;

    public BooksFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBooksBinding.inflate(inflater, container, false);
        binding.list.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainViewModel viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        adapter = new BookRecyclerViewAdapter(new ArrayList<>(), new ArrayList<>() , viewModel);

        viewModel.getSelectedCategory().observe(getViewLifecycleOwner(), category -> binding.textView2.setText(category.name));
        binding.searchTextField.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.search(s.toString());
            }
        });

        binding.list.setAdapter(adapter);
        viewModel.getCategoryBooks().observe(getViewLifecycleOwner(), adapter::submitList);
//        viewModel.getFavoriteBooks().observe(getViewLifecycleOwner(), v -> {
//            adapter.submitFavorites(v);
//        });
        viewModel.getCurrentBook().observe(getViewLifecycleOwner(), book ->
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_book, BookDetails.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit()
        );
    }
}