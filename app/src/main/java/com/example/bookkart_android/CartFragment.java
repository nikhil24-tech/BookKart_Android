package com.example.bookkart_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookkart.databinding.FragmentCartListBinding;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class CartFragment extends Fragment {

    public CartFragment() {
    }

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
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
        FragmentCartListBinding binding = FragmentCartListBinding.inflate(inflater, container, false);
        binding.list.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        binding.list.setAdapter(new CartRecyclerViewAdapter(new ArrayList<>()));
        return binding.getRoot();
    }
}