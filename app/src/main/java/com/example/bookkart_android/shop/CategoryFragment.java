package com.example.bookkart_android.shop;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookkart_android.R;
import com.example.bookkart_android.databinding.FragmentCategoryBinding;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    FragmentCategoryBinding binding;
    CategoryRecyclerViewAdapter adapter;

    public CategoryFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        binding.list.setLayoutManager(new GridLayoutManager(binding.getRoot().getContext(), 2));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainViewModel viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        adapter = new CategoryRecyclerViewAdapter(new ArrayList<>(),viewModel);
        binding.list.setAdapter(adapter);
        viewModel.getCategories().observe(getViewLifecycleOwner(), adapter::submitList);

        viewModel
                .getSelectedCategory()
                .observe(getViewLifecycleOwner(), category ->
                        getParentFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, BooksFragment.class, null)
                                .setReorderingAllowed(true)
                                .addToBackStack(null)
                                .commit()
                );
    }

}
