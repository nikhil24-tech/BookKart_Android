package com.example.bookkart_android.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.bookkart_android.databinding.ActivitySignUpBinding;
import com.example.bookkart_android.shop.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());

        auth = FirebaseAuth.getInstance();

        if (getIntent().getBooleanExtra("oauthLogin", false)) {
            updateUI();
        } else {
            setContentView(binding.getRoot());

            binding.signUpButton.setOnClickListener(v -> {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            });
            binding.goToSignIn.setOnClickListener(v -> {
                startActivity(new Intent(this, SignInActivity.class));
                finish();
            });


            binding.signUpButton.setOnClickListener(v -> {
                String email = binding.emailTextField.getEditText().getText().toString();
                String password = binding.passwordTextField.getEditText().getText().toString();

                auth
                        .createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                Log.d("EmailSignUp", "createUserWithEmail:success");
                                updateUI();
                            } else {
                                Log.w("EmailSignUp", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        });
            });
        }
    }

    public void updateUI() {
        FirebaseUser user = auth.getCurrentUser();
        FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(user.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            toMainActivity();
                        } else {
                            setContentView(binding.getRoot());
                            binding.passwordTextField.setVisibility(View.GONE);
                            binding.confirmPasswordTextField.setVisibility(View.GONE);
                            binding.emailTextField.setVisibility(View.GONE);
                            binding.signUpButton.setOnClickListener(v -> updateFirebaseUser());
                        }
                    } else {
                        Log.w("EmailSignUp", "loadUsers:failure", task.getException());
                        Toast.makeText(this, "Unable to load users failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void updateFirebaseUser() {
        String address = binding.addressTextField.getEditText().getText().toString();
        String name = binding.nameTextField.getEditText().getText().toString();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String phone = binding.phoneTextField.getEditText().getText().toString();

        Map<Object, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);
        map.put("phone", phone);
        map.put("address", address);
        map.put("isAdmin", false);

        FirebaseFirestore
                .getInstance()
                .collection( "users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .set(map)
                .addOnCompleteListener(task -> toMainActivity());
    }

    public void toMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

}