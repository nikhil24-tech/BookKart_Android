package com.example.bookkart_android;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.bookkart.R;

public class CustomFragmentStateAdapter extends FragmentStateAdapter {

    public CustomFragmentStateAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return CategoryFragment.newInstance();
            case 2 : return CartFragment.newInstance();
            case 3: return ProfileFragment.newInstance();
            default: return FavoriteFragment.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public String getText(int position) {
        switch (position) {
            case 1: return "Favorites";
            case 2: return "Cart";
            case 3: return "Profile";
            default: return "Home";
        }
    }

    public int getDrawable(int position) {
        switch (position) {
            case 1: return R.drawable.ic_favorite;
            case 2: return R.drawable.ic_cart;
            case 3: return R.drawable.ic_profile;
            default: return R.drawable.ic_home;
        }
    }
}
