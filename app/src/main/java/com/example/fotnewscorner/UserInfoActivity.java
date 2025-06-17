package com.example.fotnewscorner;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInfoActivity extends AppCompatActivity {

    private Button signOutBtn, editInfoBtn, homeBtn;
    private TextView userNameText, emailText;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        signOutBtn = findViewById(R.id.signOutButton);
        editInfoBtn = findViewById(R.id.editInfoButton);
        homeBtn = findViewById(R.id.homeButton);
        userNameText = findViewById(R.id.username_text);
        emailText = findViewById(R.id.email_text);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String uid = currentUser.getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid);

            //  Get user data and display
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String username = snapshot.child("username").getValue(String.class);
                        String email = snapshot.child("email").getValue(String.class);

                        userNameText.setText("User name : " + username);
                        emailText.setText("Email. : " + email);
                    } else {
                        Toast.makeText(UserInfoActivity.this, "User data not found!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(UserInfoActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        signOutBtn.setOnClickListener(v -> showSignOutDialog());
        editInfoBtn.setOnClickListener(v -> showEditInfoDialog());

        homeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }

    private void showSignOutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure?")
                .setMessage("Do you really want to sign out?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, which) -> {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(UserInfoActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showEditInfoDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.popup_infoedit, null);

        EditText editUserName = dialogView.findViewById(R.id.editUserName);
        EditText editEmail = dialogView.findViewById(R.id.editEmail);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnOK = dialogView.findViewById(R.id.btnOK);

        String currentUsername = userNameText.getText().toString().replace("User name : ", "").trim();
        String currentEmail = emailText.getText().toString().replace("Email. : ", "").trim();

        editUserName.setText(currentUsername);
        editEmail.setText(currentEmail);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnOK.setOnClickListener(v -> {
            String updatedName = editUserName.getText().toString().trim();
            String updatedEmail = editEmail.getText().toString().trim();

            if (updatedName.isEmpty() || updatedEmail.isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            //  Update Realtime Database
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String uid = user.getUid();
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);
                userRef.child("username").setValue(updatedName);
                userRef.child("email").setValue(updatedEmail);

                userNameText.setText("User name : " + updatedName);
                emailText.setText("Email. : " + updatedEmail);

                Toast.makeText(this, "User info updated!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}
