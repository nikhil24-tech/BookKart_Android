package com.example.bookkart_android.shop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookkart.databinding.FragmentFavoriteListBinding;
import com.example.bookkart_android.models.Favorite;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class FavoriteFragment extends Fragment {
    private MainViewModel viewModel;
    private FragmentFavoriteListBinding binding;
    private FavoriteRecyclerViewAdapter adapter;

    public FavoriteFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteListBinding.inflate(inflater, container, false);
        binding.list.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        adapter = new FavoriteRecyclerViewAdapter(new ArrayList<>(), viewModel);
        binding.list.setAdapter(adapter);

        binding.searchTextField.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.search(s.toString());
            }
        });


        viewModel.getFavoriteBooks().observe(getViewLifecycleOwner(), v -> adapter.submitList(v));
    }
}