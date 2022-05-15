package com.example.bookkart_android.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookkart.R;
import com.example.bookkart.databinding.ActivityAdminBinding;
import com.example.bookkart_android.models.Book;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    List<Book> books = new ArrayList<>();
    AdminBookRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAdminBinding binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.title);

        binding.list.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        adapter = new AdminBookRecyclerViewAdapter(books);
        binding.list.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseFirestore
                .getInstance()
                .collection("books")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        books.clear();
                        books.addAll(task.getResult().toObjects(Book.class));
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_add_book) {
            Intent intent = new Intent(this, AddEditBookActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}