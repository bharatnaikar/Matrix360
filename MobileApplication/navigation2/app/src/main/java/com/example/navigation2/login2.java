package com.example.navigation2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class login2 extends AppCompatActivity {
    EditText email, password;
    Button loginbt, rigesterbt;
    private FirebaseAuth auth;

    public static final String SHARED_PREF = "sharedprefs";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        email = findViewById(R.id.id_emaillogin);
        password = findViewById(R.id.id_passwordlogin);
        loginbt = findViewById(R.id.loginbtnlogin);
        rigesterbt = findViewById(R.id.id_rigster_login);

        auth = FirebaseAuth.getInstance();

        // Check if the user is already logged in
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in, redirect to MainActivity
            Intent intent = new Intent(login2.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginuser();
            }
        });

        rigesterbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login2.this, signup.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loginuser() {
        String str_email = email.getText().toString().trim();
        String str_password = password.getText().toString().trim();

        if (TextUtils.isEmpty(str_email)) {
            Toast.makeText(login2.this, "Enter the Email", Toast.LENGTH_SHORT).show();
            email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(str_password)) {
            Toast.makeText(login2.this, "Enter the Password", Toast.LENGTH_SHORT).show();
            password.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
            email.setError("Enter a valid Email Address");
            email.requestFocus();
            return;
        }

        if (str_password.length() < 8) {
            password.setError("Password length should be at least 8 characters");
            password.requestFocus();
            return;
        }

        auth.signInWithEmailAndPassword(str_email, str_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            saveCredentials(str_email, str_password);
                            Toast.makeText(login2.this, "Login successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(login2.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(login2.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveCredentials(String strEmail, String strPassword) {
        SharedPreferences preferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", strEmail);
        editor.putString("password", strPassword);
        editor.apply();
    }
}
