package com.example.fotnewscorner;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText usernameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button signupButton;
    private TextView alreadyHaveAccountText;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        usernameEditText = findViewById(R.id.username_edit_text);
        emailEditText = findViewById(R.id.email_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirmpassword_text);
        signupButton = findViewById(R.id.signup_button);
        alreadyHaveAccountText = findViewById(R.id.already_have_account_text);

        signupButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(username)) {
                usernameEditText.setError("Username is required");
                return;
            }
            if (TextUtils.isEmpty(email)) {
                emailEditText.setError("Email is required");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                passwordEditText.setError("Password is required");
                return;
            }
            if (!password.equals(confirmPassword)) {
                confirmPasswordEditText.setError("Passwords do not match");
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String uid = user.getUid();

                                // ✅ Realtime DB Save
                                User userModel = new User(username, email);
                                mDatabase.child(uid).setValue(userModel)
                                        .addOnCompleteListener(dbTask -> {
                                            if (dbTask.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "Registered & saved in Firebase", Toast.LENGTH_SHORT).show();
                                                saveUserToLocalDB(username, email);

                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(RegisterActivity.this, "Firebase DB Error: " + dbTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        alreadyHaveAccountText.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void saveUserToLocalDB(String name, String email) {
        com.example.fotnewscorner.UserDatabaseHelper dbHelper = new com.example.fotnewscorner.UserDatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(com.example.fotnewscorner.UserDatabaseHelper.COLUMN_NAME, name);
        values.put(com.example.fotnewscorner.UserDatabaseHelper.COLUMN_EMAIL, email);

        long result = db.insert(com.example.fotnewscorner.UserDatabaseHelper.TABLE_USERS, null, values);
        db.close();

        if (result != -1) {
            Toast.makeText(this, "User info saved locally", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save user info locally", Toast.LENGTH_SHORT).show();
        }
    }

    // ✅ User model for Firebase
    public static class User {
        public String username;
        public String email;

        public User() {
            // Default constructor required for Firebase
        }

        public User(String username, String email) {
            this.username = username;
            this.email = email;
        }
    }
}
