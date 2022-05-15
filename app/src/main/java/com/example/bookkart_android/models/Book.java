package com.example.bookkart_android.models;

import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;

public class Book implements Serializable {
    public @DocumentId String ref;
    public String title;
    public double price;
    public String date;
    public String image;
    public String category;
}
