package com.example.fotnewscorner;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment; // Changed to extend Fragment
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

// This class represents the Event News Fragment which displays a list of event news.
public class fragment_event extends Fragment { // Now extends Fragment

    private RecyclerView recyclerView; // RecyclerView to display the list of news cards
    private NewsAdapter newsAdapter; // Adapter to bridge data to RecyclerView
    private List<NewsItem> newsList; // List to hold NewsItem objects fetched from Firebase
    private DatabaseReference databaseReference; // Firebase Database reference to the "Events" node

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_fragment_event, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        // Set a LinearLayoutManager to arrange items in a linear list. Use getContext() for fragments.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsList = new ArrayList<>(); // Initialize the list to store news items
        newsAdapter = new NewsAdapter(newsList); // Initialize the adapter with the list
        recyclerView.setAdapter(newsAdapter); // Set the adapter to the RecyclerView

        // Initialize Firebase database reference.
        // It points to the "news/Events" node in your Realtime Database.
        databaseReference = FirebaseDatabase.getInstance().getReference("news/Events");

        // Call the method to fetch news data from Firebase
        fetchEventNews();

        return view; // Return the inflated view
    }

    /**
     * Fetches event news data from the Firebase Realtime Database.
     * It uses a ValueEventListener to listen for real-time updates to the "news/Events" node.
     */
    private void fetchEventNews() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            /**
             * This method is called whenever data at this location is updated.
             * It iterates through the children of the snapshot to parse each news item.
             * @param snapshot The current data at the database location.
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                newsList.clear(); // Clear the list to avoid duplicate entries on subsequent data changes
                // Iterate through each child (news item) under the "Events" node
                for (DataSnapshot newsSnapshot : snapshot.getChildren()) {
                    // Extract data for each news item.
                    // Based on your JSON, "Events" news uses "title" and "content".
                    String title = newsSnapshot.child("title").getValue(String.class);
                    String content = newsSnapshot.child("content").getValue(String.class);
                    String publishedDate = newsSnapshot.child("publisheddate").getValue(String.class);
                    String imageUrl = newsSnapshot.child("imageurl").getValue(String.class);

                    // Check if all required fields are present before creating a NewsItem
                    if (title != null && content != null && publishedDate != null && imageUrl != null) {
                        NewsItem newsItem = new NewsItem(title, content, publishedDate, imageUrl);
                        newsList.add(newsItem); // Add the parsed NewsItem to the list
                    }
                }
                // Notify the adapter that the underlying data has changed,
                // so the RecyclerView can re-render and display the updated news.
                newsAdapter.notifyDataSetChanged();
            }

            /**
             * This method will be triggered in the event of a database error.
             * @param error The error object containing details about the cancellation.
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Log any errors that occur during data fetching
                Log.e("fragment_event", "Failed to read value.", error.toException());
            }
        });
    }
}
