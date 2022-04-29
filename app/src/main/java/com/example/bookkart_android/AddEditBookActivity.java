package com.example.bookkart_android;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookkart.databinding.ActivityAddEditBookBinding;


public class AddEditBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAddEditBookBinding binding = ActivityAddEditBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getIntent().getBooleanExtra("isEdit", true)) {
            binding.addButton.setText("Update Details");
            binding.categoryTextField.getEditText().setText("Business and Economics");
            binding.nameTextField.getEditText().setText("The Psychology of Money");
            binding.priceTextField.getEditText().setText("15.00");
        }
    }
}