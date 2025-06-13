package com.example.fotnewscorner;


import com.example.fotnewscorner.UserInfoActivity;


//newly add
import android.content.Intent;


import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView newsRecyclerView;
    private LinearLayout tabDevInfo, tabSports, tabAcademics, tabEvents;
    private EditText searchBar;
    private ImageView profileIcon, logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        tabDevInfo = findViewById(R.id.tabDevInfo);
        tabSports = findViewById(R.id.tabSports);
        tabAcademics = findViewById(R.id.tabAcademics);
        tabEvents = findViewById(R.id.tabEvents);
        searchBar = findViewById(R.id.searchBar);
        profileIcon = findViewById(R.id.profileIcon);
        logo = findViewById(R.id.logo);

        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, com.example.fotnewscorner.UserInfoActivity.class);
            startActivity(intent);
        });

        logo = findViewById(R.id.logo);

        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Existing simple news list (only headlines)
        ArrayList<String> existingNewsTitles = new ArrayList<>();
        existingNewsTitles.add("AI beats chess world record");
        existingNewsTitles.add("Sri Lanka to host Tech Expo 2025");
        existingNewsTitles.add("University Rankings 2025 released");
        existingNewsTitles.add("NASA confirms Mars rover signal");

        // Convert simple headlines list to NewsItem list with placeholders for other fields
        ArrayList<com.example.fotnewscorner.NewsAdapter.NewsItem> newsItems = new ArrayList<>();
        for (String title : existingNewsTitles) {
            newsItems.add(new com.example.fotnewscorner.NewsAdapter.NewsItem(
                    R.drawable.placeholder_news_image, // Placeholder image
                    title,                             // headline
                    "Details not available.",          // placeholder body text
                    "Unknown time"                    // placeholder published time
            ));
        }

        com.example.fotnewscorner.NewsAdapter adapter = new com.example.fotnewscorner.NewsAdapter(newsItems);
        newsRecyclerView.setAdapter(adapter);

        tabDevInfo.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DevInfoActivity.class);
            startActivity(intent);
        });

        tabSports.setOnClickListener(v -> Toast.makeText(this, "Sports Clicked", Toast.LENGTH_SHORT).show());
        tabAcademics.setOnClickListener(v -> Toast.makeText(this, "Academics Clicked", Toast.LENGTH_SHORT).show());
        tabEvents.setOnClickListener(v -> Toast.makeText(this, "Events Clicked", Toast.LENGTH_SHORT).show());

       searchBar.setOnClickListener(v -> Toast.makeText(this, "Search tapped", Toast.LENGTH_SHORT).show());
    }
}
