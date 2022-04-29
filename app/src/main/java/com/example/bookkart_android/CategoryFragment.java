package com.example.bookkart_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.bookkart.databinding.FragmentCategoryListBinding;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    public CategoryFragment() {
    }

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
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
        FragmentCategoryListBinding binding = FragmentCategoryListBinding.inflate(inflater, container, false);
        binding.list.setLayoutManager(new GridLayoutManager(binding.getRoot().getContext(), 2));
        binding.list.setAdapter(new CategoryRecyclerViewAdapter(new ArrayList<>()));
        return binding.getRoot();
    }
}