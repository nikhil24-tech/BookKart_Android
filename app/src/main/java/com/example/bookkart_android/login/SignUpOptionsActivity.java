package com.example.bookkart_android.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bookkart.R;
import com.example.bookkart.databinding.ActivitySignUpOptionsBinding;
import com.example.bookkart_android.MainActivity;
import com.example.bookkart_android.models.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpOptionsActivity extends AppCompatActivity {

    private GoogleSignInClient client;
    private FirebaseAuth auth;
    private static final int RC_SIGN_IN = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignUpOptionsBinding binding = ActivitySignUpOptionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            FirebaseFirestore
                    .getInstance()
                    .collection( "users")
                    .document(auth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        User user = task.getResult().toObject(User.class);
                        if (user != null) {
                            if (user.isAdmin) {
//                                startActivity(new Intent(this, AdminActivity.class));
                            } else {
                                startActivity(new Intent(this, MainActivity.class));
                            }
                            finish();
                        }
                    });
        }

        binding.emailSignIn.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUpActivity.class));
            finish();
        });
        binding.goToSignIn.setOnClickListener(v -> {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        });

        CallbackManager manager = CallbackManager.Factory.create();
        binding.facebookSignIn.setReadPermissions("email", "public_profile");
        binding.facebookSignIn.registerCallback(manager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("UserDetails", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("UserDetails", "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("UserDetails", "facebook:onError", error);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this, gso);
        binding.googleSignIn.setOnClickListener(v -> startActivityForResult(client.getSignInIntent(), RC_SIGN_IN));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("UserDetails", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.w("UserDetails", "Google sign in failed", e);
            }
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("UserDetails", "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        startSignupActivity();
                    } else {
                        Log.w("UserDetails", "signInWithCredential:failure", task.getException());
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        startSignupActivity();
                    } else {
                        Log.w("UserDetails", "signInWithCredential:failure", task.getException());
                    }
                });
    }

    private void startSignupActivity() {
        Log.d("UserDetails", "signInWithCredential:success");
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra("oauthLogin", true);
        startActivity(intent);
        finish();
    }

}