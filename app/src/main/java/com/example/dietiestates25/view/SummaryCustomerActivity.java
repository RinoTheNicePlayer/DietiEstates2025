package com.example.dietiestates25.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.example.dietiestates25.R;

public class SummaryCustomerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_customer);

        // Modifica lo stato della Navbar
        LinearLayout homeState = findViewById(R.id.navbar_item_home_state);
        LinearLayout summaryState = findViewById(R.id.navbar_item_summary_state);
        LinearLayout profileState = findViewById(R.id.navbar_item_profile_state);

        // Imposta il background del pulsante attivo
        homeState.setBackgroundResource(android.R.color.transparent);
        summaryState.setBackgroundResource(R.drawable.button_state);
        profileState.setBackgroundResource(android.R.color.transparent);
    }
}