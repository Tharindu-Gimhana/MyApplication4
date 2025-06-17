package com.example.fotnewscorner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment; // Import Fragment
import androidx.fragment.app.FragmentManager; // Import FragmentManager
import androidx.fragment.app.FragmentTransaction; // Import FragmentTransaction

public class MainActivity extends AppCompatActivity {

    private LinearLayout tabDevInfo, tabSports, tabAcademics, tabEvents;
    private EditText searchBar;
    private ImageView profileIcon, logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        tabDevInfo = findViewById(R.id.tabDevInfo);
        tabSports = findViewById(R.id.tabSports);
        tabAcademics = findViewById(R.id.tabAcademics);
        tabEvents = findViewById(R.id.tabEvents);
        searchBar = findViewById(R.id.searchBar);
        profileIcon = findViewById(R.id.profileIcon);
        logo = findViewById(R.id.logo);

        // Set up click listener for the profile icon to navigate to UserInfoActivity
        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, com.example.fotnewscorner.UserInfoActivity.class);
            startActivity(intent);
        });

        // --- Fragment Handling ---
        // Set the default fragment to SportsFragment when the app starts
        if (savedInstanceState == null) {
            replaceFragment(new fragment_sport());
            // Optionally, you might want to highlight the Sports tab as selected here
            // e.g., tabSports.setBackgroundColor(getColor(R.color.selected_tab_color));
        }

        // Set up click listeners for the bottom navigation tabs to switch fragments
        tabDevInfo.setOnClickListener(v -> {
            // DevInfoActivity is assumed to be a separate activity, not a fragment
            Intent intent = new Intent(MainActivity.this, DevInfoActivity.class);
            startActivity(intent);
        });

        // Replace the fragment in the container when Sports tab is clicked
        tabSports.setOnClickListener(v -> replaceFragment(new fragment_sport()));

        // Replace the fragment in the container when Academics tab is clicked
        tabAcademics.setOnClickListener(v -> replaceFragment(new fragment_academic()));

        // Replace the fragment in the container when Events tab is clicked
        tabEvents.setOnClickListener(v -> replaceFragment(new fragment_event()));

        // Set up click listener for the search bar (currently just shows a Toast)
        searchBar.setOnClickListener(v -> Toast.makeText(this, "Search tapped", Toast.LENGTH_SHORT).show());
    }

    /**
     * Replaces the current fragment in the fragment_container with a new one.
     * @param fragment The new fragment to display.
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
