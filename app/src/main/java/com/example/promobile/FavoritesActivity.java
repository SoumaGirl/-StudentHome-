package com.example.promobile;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

public class FavoritesActivity extends AppCompatActivity {

    // Declare variables for the views
    private ImageView avatar, apartImage, roomImage;
    private TextView userName, userRole, apartType, apartLocation, apartPrice, apartLikes;
    private TextView roomType, roomLocation, roomPrice, roomLikes;
    private ImageView homeIcon, chatIcon, favoriteIcon, profileIcon;

    // Firebase Firestore reference
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);  // Make sure this layout corresponds to the Favorites screen

        // Initialize the views
        avatar = findViewById(R.id.avatar);
        userName = findViewById(R.id.userName);
        userRole = findViewById(R.id.userRole);
        apartImage = findViewById(R.id.apartImage);
        apartType = findViewById(R.id.apartType);
        apartLocation = findViewById(R.id.apartLocation);
        apartPrice = findViewById(R.id.apartPrice);
        apartLikes = findViewById(R.id.apartLikes);
        roomImage = findViewById(R.id.roomImage);
        roomType = findViewById(R.id.roomType);
        roomLocation = findViewById(R.id.roomLocation);
        roomPrice = findViewById(R.id.roomPrice);
        roomLikes = findViewById(R.id.roomLikes);

        homeIcon = findViewById(R.id.homeIcon);
        chatIcon = findViewById(R.id.chatIcon);
        favoriteIcon = findViewById(R.id.favoriteIcon);
        profileIcon = findViewById(R.id.profileIcon);

        // Get current user from Firebase Authentication
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Fetch the user data from Firestore
            fetchUserData(currentUser.getUid());
        } else {
            // Handle case where the user is not logged in
            userName.setText("Guest");
            userRole.setText("None");
        }

        // Set the images (these should be dynamic if you load them from resources or a database)
        avatar.setImageResource(R.drawable.img_6); // Example image, replace with dynamic loading if needed
        apartImage.setImageResource(R.drawable.apart);
        roomImage.setImageResource(R.drawable.apart2);

        // Bottom Navigation Actions (these can be set up with event listeners later)
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle home icon click
            }
        });

        chatIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to MessageActivity
                Intent intent = new Intent(FavoritesActivity.this, MessageActivity.class);
                startActivity(intent);            }
        });

        favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoritesActivity.this, FavoritesActivity.class);
                startActivity(intent);             }
        });

        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoritesActivity.this, ProfileActivity.class);
                startActivity(intent);             }
        });
    }

    // Fetch user data from Firestore
    private void fetchUserData(String userId) {
        db.collection("users").document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Get user data from Firestore
                            String username = document.getString("username");
                            String role = document.getString("role");

                            // Set the username and role on the UI
                            userName.setText(username);
                            userRole.setText(role);
                        } else {
                            // Handle case where the document doesn't exist
                            userName.setText("No username found");
                            userRole.setText("No role found");
                        }
                    } else {
                        // Handle failure in fetching data
                        userName.setText("Error fetching data");
                        userRole.setText("Error");
                    }
                });
    }
}
