package com.example.promobile;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/** @noinspection deprecation*/
public class MessageActivity extends AppCompatActivity {

    private EditText editTextMessage;
    private LinearLayout messageContainer;
    private ScrollView scrollViewMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        // Initialize views
        editTextMessage = findViewById(R.id.editTextMessage);
        ImageButton sendButton = findViewById(R.id.sendButton);
        messageContainer = findViewById(R.id.messageContainer);
        scrollViewMessages = findViewById(R.id.scrollViewMessages);

        // Set onClickListener for Send Button
        sendButton.setOnClickListener(v -> {
            String message = editTextMessage.getText().toString().trim();

            if (!message.isEmpty()) {
                addMessageToContainer(message);
                editTextMessage.setText("");
            }
        });
    }

    // Function to add a message to the message container
    private void addMessageToContainer(String message) {
        TextView messageView = new TextView(this);
        messageView.setText(message);
        messageView.setBackgroundResource(R.drawable.rounded_message);
        messageView.setPadding(16, 16, 16, 16);
        messageView.setTextColor(getResources().getColor(android.R.color.black));
        messageView.setTextSize(16);

        // Add message to container
        messageContainer.addView(messageView);

        // Scroll to the bottom
        scrollViewMessages.post(() -> scrollViewMessages.fullScroll(View.FOCUS_DOWN));
    }
}
