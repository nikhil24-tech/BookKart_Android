package com.example.bookkart_android.shop;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.bookkart_android.models.Book;
import com.example.bookkart_android.models.Category;
import com.example.bookkart_android.models.Favorite;
import com.example.bookkart_android.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<Book>> allBooks;
    private MutableLiveData<List<Book>> selectedBooks;
    private MutableLiveData<List<Favorite>> favorites;
    private MutableLiveData<User> currentUser;
    private MutableLiveData<List<Category>> categories;

    private MutableLiveData<Category> selectedCategory;

    private MediatorLiveData<List<Book>> favoriteBooks;
    private MediatorLiveData<List<Book>> categoryBooks;

    private MutableLiveData<Book> currentBook;

    public MainViewModel() { }

    public MutableLiveData<Book> getCurrentBook() {
        if (currentBook == null) {
            currentBook = new MutableLiveData<>();
        }
        return currentBook;
    }

    public void setCurrentBook(Book book) {
        getCurrentBook().setValue(book);
    }

    public MutableLiveData<Category> getSelectedCategory() {
        if (selectedCategory == null) {
            selectedCategory = new MutableLiveData<>();
        }
        return selectedCategory;
    }

    public void selectCategory(Category category) {
        getSelectedCategory().setValue(category);
    }

    public MutableLiveData<List<Category>> getCategories() {
        if (categories == null) {
            categories = new MutableLiveData<>(new ArrayList<>());
            FirebaseFirestore
                    .getInstance()
                    .collection("categories")
                    .get()
                    .addOnCompleteListener(task -> categories.setValue(task.getResult().toObjects(Category.class)));
        }
        return categories;
    }

    public MutableLiveData<List<Book>> getSelectedBooks() {
        if (selectedBooks == null) {
            selectedBooks = new MutableLiveData<>(new ArrayList<>());
        }
        return selectedBooks;
    }

    public MutableLiveData<List<Book>> getAllBooks() {
        if (allBooks == null) {
            allBooks = new MutableLiveData<>(new ArrayList<>());
            FirebaseFirestore
                    .getInstance()
                    .collection("books")
                    .get()
                    .addOnCompleteListener(task -> allBooks.setValue(task.getResult().toObjects(Book.class)));
        }
        return allBooks;
    }

    public MutableLiveData<List<Favorite>> getFavorites() {
        if (favorites == null) {
            favorites = new MutableLiveData<>(new ArrayList<>());
            FirebaseFirestore
                    .getInstance()
                    .collection("favorites")
                    .whereEqualTo("user", FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(task -> favorites.setValue(task.getResult().toObjects(Favorite.class)));
        }
        return favorites;
    }

    public MediatorLiveData<List<Book>> getFavoriteBooks() {
        if (favoriteBooks == null) {
            favoriteBooks = new MediatorLiveData<>();
            favoriteBooks.addSource(getAllBooks(), result -> processFavorites(allBooks.getValue(), favorites.getValue()));
            favoriteBooks.addSource(getFavorites(), result -> processFavorites(allBooks.getValue(), favorites.getValue()));
        }
        return favoriteBooks;
    }

    List<Book> processFavorites(List<Book> allBooks, List<Favorite> favorites) {
        List<Book> filteredBooks = new ArrayList<>();
        if (allBooks == null || favorites == null || allBooks.isEmpty() || favorites.isEmpty()) {
            getFavoriteBooks().setValue(filteredBooks);
            return filteredBooks;
        }

        Map<String, Book> reffedBooks = new HashMap<>();
        for (Book book: allBooks) {
            reffedBooks.put(book.ref, book);
        }
        for (Favorite favorite: favorites) {
            Book book = reffedBooks.get(favorite.book);
            filteredBooks.add(book);
        }
        getFavoriteBooks().setValue(filteredBooks);
        return filteredBooks;
    }

    public MediatorLiveData<List<Book>> getCategoryBooks() {
        if (categoryBooks == null) {
            categoryBooks = new MediatorLiveData<>();
            categoryBooks.addSource(getAllBooks(), result -> processCategory(allBooks.getValue(), selectedCategory.getValue()));
            categoryBooks.addSource(getSelectedCategory(), result -> processCategory(allBooks.getValue(), selectedCategory.getValue()));
        }
        return categoryBooks;
    }


    List<Book> processCategory(List<Book> allBooks, Category category) {
        List<Book> categorizedBooks = new ArrayList<>();
        if (allBooks == null || category == null) {
            return categorizedBooks;
        }
        for (Book book: allBooks) {
            if (book.category.equalsIgnoreCase(category.name)) {
                categorizedBooks.add(book);
            }
        }
        getCategoryBooks().setValue(categorizedBooks);
        return categorizedBooks;
    }

    public MutableLiveData<User> getCurrentUser() {
        if (currentUser == null) {
            currentUser = new MutableLiveData<>(new User());

            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseFirestore
                    .getInstance()
                    .collection("users")
                    .document(uid)
                    .get()
                    .addOnCompleteListener(task -> currentUser.setValue(task.getResult().toObject(User.class)));
        }
        return currentUser;
    }

    public void selectBook(Book book) {
        MutableLiveData<List<Book>> selected = getSelectedBooks();
        List<Book> newBooks = selected.getValue();
        newBooks.add(book);
        selected.setValue(newBooks);
    }

    public void unselectBook(Book book) {
        List<Book> newBooks = selectedBooks.getValue();
        newBooks.remove(book);
        selectedBooks.setValue(newBooks);
    }

    public void order() {
        List<Book> books = getSelectedBooks().getValue();
        books.clear();
        getSelectedBooks().setValue(books);
    }

    public void addBookToFavorite(Book book) {
        Map<String, String> map = new HashMap<>();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        map.put("user", uid);
        map.put("book", book.ref);

        FirebaseFirestore
                .getInstance()
                .collection("favorites")
                .add(map);

        Favorite favorite = new Favorite();
        favorite.book = book.ref;
        favorite.user = uid;
        List<Favorite> favorites = getFavorites().getValue();
        favorites.add(favorite);
        getFavorites().setValue(favorites);
    }

    public void removeBookFromFavorites(Book book) {
        List<Favorite> favorites = getFavorites().getValue();
        Favorite favorite = null;
        for (Favorite f: favorites) {
            if (f.book.equalsIgnoreCase(book.ref)) {
                favorite = f;
                break;
            }
        }

        if (favorite == null) {
            return;
        }

        favorites.remove(favorite);
        getFavorites().setValue(favorites);

        if (favorite.ref == null) {
            return;
        }

        FirebaseFirestore
                .getInstance()
                .collection("favorites")
                .document(favorite.ref)
                .delete();
    }

}
