package com.example.promobile;

// AddOffer.java

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;public class AddOffer extends AppCompatActivity {

    private EditText titleEditText, priceEditText, typeEditText, locationEditText;
    private ImageView offerImageView;
    private Button saveButton, cancelButton;
    private ProgressBar progressBar;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);

        // Initialize Firebase references
        databaseReference = FirebaseDatabase.getInstance().getReference("offers");
        storageReference = FirebaseStorage.getInstance().getReference("offer_images");

        // Initialize views
        titleEditText = findViewById(R.id.titleEditText);
        priceEditText = findViewById(R.id.priceEditText);
        typeEditText = findViewById(R.id.typeEditText);
        locationEditText = findViewById(R.id.locationEditText);
        offerImageView = findViewById(R.id.offerImageView);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        progressBar = findViewById(R.id.progressBar);

        // Set a drawable resource as the default image
        offerImageView.setImageResource(R.drawable.ic_add_image);

        // Set click listeners
        saveButton.setOnClickListener(v -> saveOffer());
        cancelButton.setOnClickListener(v -> finish());
    }

    private void saveOffer() {
        // Get input data from EditText fields
        String title = titleEditText.getText().toString().trim();
        String price = priceEditText.getText().toString().trim();
        String type = typeEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();

        // Validate inputs
        if (title.isEmpty() || price.isEmpty() || type.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show the progress bar while saving
        progressBar.setVisibility(View.VISIBLE);
        Log.d("AddOffer", "Starting save process...");

        // Check if the user has selected an image or if it's the default one
        Bitmap imageBitmap;
        if (offerImageView.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_add_image).getConstantState()) {
            // Use a placeholder image if no image is selected
            Log.d("AddOffer", "No image selected, using default placeholder.");
            imageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.border);
        } else {
            // Get the selected image from ImageView (for now using a placeholder)
            Log.d("AddOffer", "Image selected by user.");
            imageBitmap = ((BitmapDrawable) offerImageView.getDrawable()).getBitmap();
        }

        // Upload image to Firebase Storage
        String imageName = System.currentTimeMillis() + ".jpg"; // Unique image name
        StorageReference imageRef = storageReference.child(imageName);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        Log.d("AddOffer", "Uploading image to Firebase Storage...");
        imageRef.putBytes(data).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Image upload successful, now get the image URL
                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    Log.d("AddOffer", "Image uploaded successfully, URI: " + uri.toString());

                    // Create Offer object
                    Offer offer = new Offer(title, price, type, location, uri.toString());

                    // Save offer data to Firebase Realtime Database
                    Log.d("AddOffer", "Saving offer data to Firebase Database...");
                    databaseReference.push().setValue(offer).addOnCompleteListener(task1 -> {
                        progressBar.setVisibility(View.GONE); // Hide progress bar
                        if (task1.isSuccessful()) {
                            Log.d("AddOffer", "Offer saved successfully!");
                            Toast.makeText(this, "Offer added successfully", Toast.LENGTH_SHORT).show();
                            finish(); // Close activity after saving
                        } else {
                            Log.e("AddOffer", "Failed to save offer data to Firebase Database.");
                            Toast.makeText(this, "Failed to save offer", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            } else {
                progressBar.setVisibility(View.GONE); // Hide progress bar if image upload fails
                Log.e("AddOffer", "Failed to upload image to Firebase Storage.");
                Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
