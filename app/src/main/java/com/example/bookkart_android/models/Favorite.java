package com.example.bookkart_android.models;

import com.google.firebase.firestore.DocumentId;

public class Favorite {
    public @DocumentId String ref;
    public String user;
    public String book;
}
