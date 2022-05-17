package com.example.bookkart_android.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookkart.databinding.FragmentCartListBinding;
import com.example.bookkart_android.models.Book;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private FragmentCartListBinding binding;
    private CartRecyclerViewAdapter adapter;
    double subtotal, delivery = 2.50, total, tax;

    private MainViewModel viewModel;

    public CartFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartListBinding.inflate(inflater, container, false);
        binding.list.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        adapter = new CartRecyclerViewAdapter(new ArrayList<>(), viewModel);
        binding.list.setAdapter(adapter);
        viewModel.getSelectedBooks().observe(getViewLifecycleOwner(), books -> {
            adapter.submitList(books);
            calculateTotals(books);
        });
        binding.checkoutButton.setOnClickListener(v -> {
            viewModel.order();
            Toast.makeText(requireActivity(), "Books Ordered!", Toast.LENGTH_SHORT).show();
            resetTotals();
        });
    }

    public void calculateTotals(List<Book> books) {
        subtotal = 0.0;
        for (Book book: books) {
            subtotal += book.price;
        }

        if (subtotal == 0.0) {
            resetTotals();
        } else {
            tax = 18.0 / 100.0 * subtotal;
            total = subtotal + tax + delivery;

            binding.subtotalTextView.setText(String.format("$ %.2f", subtotal));
            binding.deliveryTextView.setText(String.format("$ %.2f", delivery));
            binding.taxTextView.setText(String.format("$ %.2f", tax));
            binding.totalTextView.setText(String.format("$ %.2f", total));
        }
    }

    public void resetTotals() {
        binding.subtotalTextView.setText("$ 0.00");
        binding.deliveryTextView.setText("$ 0.00");
        binding.taxTextView.setText("$ 0.00");
        binding.totalTextView.setText("$ 0.00");
    }
}
