package com.example.bookkart_android;

public class Utils {
    public static String getImageUrl(String image) {
        return String.format("https://firebasestorage.googleapis.com/v0/b/bookkart-91b6d.appspot.com/o/%s?alt=media&token=f19526d4-71c3-41e5-aaad-333469130f36", image);
    }
}
