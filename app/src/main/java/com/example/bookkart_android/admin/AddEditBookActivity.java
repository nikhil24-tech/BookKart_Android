package com.example.bookkart_android.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.bookkart_android.databinding.ActivityAddEditBookBinding;
import com.example.bookkart_android.models.Book;
import com.example.bookkart_android.models.Category;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddEditBookActivity extends AppCompatActivity {
    private Book book;
    private ActivityAddEditBookBinding binding;
    public static final int PICK_IMAGE = 1;
    private String imageName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        book = (Book) getIntent().getSerializableExtra("book");
        binding.categoryField.setEnabled(false);
        binding.categoryField.setPrompt("Category");
        FirebaseFirestore
                .getInstance()
                .collection("categories")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Category> results = task.getResult().toObjects(Category.class);
                        List<String> titles = new ArrayList<>();
                        for (Category c: results) {
                            titles.add(c.name);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, titles);
                        binding.categoryField.setAdapter(adapter);
                        binding.categoryField.setEnabled(true);

                        if (book != null) {
                            int idx = 0;
                            for (int i = 0; i < titles.size(); i++) {
                                if (titles.get(i).equalsIgnoreCase(book.category)) {
                                    idx = i;
                                }
                            }
                            binding.categoryField.setSelection(idx);
                        } else {
                            binding.categoryField.setSelection(0);
                        }
                    }
                });

        if (book != null) {
            binding.nameTextField.getEditText().setText(book.title);
            binding.priceTextField.getEditText().setText(String.format("%.2f", book.price));
            binding.dateTextField.getEditText().setText(book.date);
            binding.addButton.setText("Update Details");
            binding.addButton.setOnClickListener(v -> editBook());

            binding.uploadImageButton.setVisibility(View.GONE);
        } else {
            binding.uploadImageButton.setOnClickListener(v -> pickImage());
            binding.addButton.setOnClickListener(v -> addBook());
        }
    }

    void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                String name = binding.nameTextField.getEditText().getText().toString();

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                StorageReference imageRef = storageRef.child(name);

                imageRef.putStream(inputStream).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Image uploaded!", Toast.LENGTH_SHORT).show();
                        binding.uploadImageButton.setEnabled(false);
                        binding.uploadImageButton.setText("Image uploaded");
                        imageName = name;
                    } else {
                        Toast.makeText(this, "Failed to upload image!", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    Map<String, Object> getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("price", Double.parseDouble(binding.priceTextField.getEditText().getText().toString()));
        map.put("title", binding.nameTextField.getEditText().getText().toString());
        map.put("category", binding.categoryField.getSelectedItem());
        map.put("date", binding.dateTextField.getEditText().getText().toString());
        return map;
    }

    void editBook() {
        Map<String, Object> map = getData();
        FirebaseFirestore
                .getInstance()
                .collection("books")
                .document(book.ref)
                .update(map)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Book updated successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Book couldn't be updated!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void addBook() {
        Map<String, Object> map = getData();
        map.put("image", imageName);
        FirebaseFirestore
                .getInstance()
                .collection("books")
                .add(map)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Book added successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Book couldn't be added!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
