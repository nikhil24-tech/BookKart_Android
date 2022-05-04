package com.example.bookkart_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookkart.databinding.FragmentFavoriteListBinding;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class FavoriteFragment extends Fragment {

    public FavoriteFragment() {
    }

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentFavoriteListBinding binding = FragmentFavoriteListBinding.inflate(inflater, container, false);
        binding.list.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        binding.list.setAdapter(new BookRecyclerViewAdapter(new ArrayList<>()));
        return binding.getRoot();
    }
}