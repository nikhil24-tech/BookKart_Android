package com.example.bookkart_android.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.bookkart_android.databinding.ActivityMainBinding;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        com.example.bookkart_android.shop.CustomFragmentStateAdapter adapter = new com.example.bookkart_android.shop.CustomFragmentStateAdapter(this);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 3) {
                    binding.title.setTitle("My Profile");
                } else {
                    binding.title.setTitle("Book Kart");
                }
            }
        });
        new TabLayoutMediator(binding.tabs, binding.viewPager, (tab, position) -> {
            tab.setText(null);
            tab.setIcon(adapter.getDrawable(position));
        }).attach();
        MainViewModel model = new ViewModelProvider(this).get(MainViewModel.class);
        model.getSelectedBooks().observe(this, books -> {
            BadgeDrawable badge = binding.tabs.getTabAt(2).getOrCreateBadge();
            badge.setVisible(true);
            badge.setNumber(books.size());
        });
    }
}