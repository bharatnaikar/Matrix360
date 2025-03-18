package com.example.navigation2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
    EditText nameEt,emailEt,phno,passwordEtt,reenterpassword;
    Button rigesterbtn_ri,loginbtn_ri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        nameEt=findViewById(R.id.id_username_ri);
        emailEt=findViewById(R.id.id_email_ri);
        phno=findViewById(R.id.id_phone_ri);
        passwordEtt=findViewById(R.id.id_password_ri);
        reenterpassword=findViewById(R.id.id_repassword_ri);
        rigesterbtn_ri=findViewById(R.id.id_rigester_ri);
        loginbtn_ri=findViewById(R.id.id_login_ri);
        rigesterbtn_ri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
//                Intent intent=new Intent(signup.this,login2.class);
//                startActivity(intent);
//                finish();
            }
        });
        loginbtn_ri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(signup.this,login2.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void registerUser() {
        // Initialize FirebaseAuth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // Initialize FirebaseDatabase reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = database.getReference("users");

        // Get user input
        String str_username = nameEt.getText().toString().trim();
        String str_email = emailEt.getText().toString().trim();
        String str_password = passwordEtt.getText().toString().trim();
        String str_reenterpassword = reenterpassword.getText().toString().trim();
        String phoneNumber = phno.getText().toString().trim();

        // Validate user input
        if (str_username.isEmpty() || str_email.isEmpty() || str_password.isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
            emailEt.setError("Enter a valid email");
            emailEt.requestFocus();
            emailEt.setText("");
            return;
        }

        if (str_password.length() < 8) {
            passwordEtt.setError("Password length should be at least 8 characters");
            passwordEtt.requestFocus();
            return;
        }
        if (!str_password.equals(str_reenterpassword)) {
            Toast.makeText(this, "RE enter password", Toast.LENGTH_SHORT).show();
            reenterpassword.requestFocus();
            reenterpassword.setText("");
            return;
        }

        // Create user with email and password
        mAuth.createUserWithEmailAndPassword(str_email, str_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registration successful, save user data to database
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String userId = user.getUid();
                                User newUser = new User(userId, str_username, str_email, str_password, phoneNumber);
                                mDatabase.child(userId).setValue(newUser);
                                Toast.makeText(signup.this, "Registration successful", Toast.LENGTH_SHORT).show();

                                // Clear input fields
                                nameEt.setText("");
                                emailEt.setText("");
                                passwordEtt.setText("");
                                reenterpassword.setText("");
                                phno.setText("");

                                // Navigate to login activity
                                Intent intent=new Intent(signup.this,login2.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            // Registration failed
                            nameEt.setText("");
                            emailEt.setText("");
                            passwordEtt.setText("");
                            reenterpassword.setText("");
                            phno.setText("");
                            if (task.getException() != null) {
                                Toast.makeText(signup.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

}