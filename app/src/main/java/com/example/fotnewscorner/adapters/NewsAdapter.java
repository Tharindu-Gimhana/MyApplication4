package com.example.fotnewscorner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide; // Glide library for efficient image loading

import java.util.List;

// NewsAdapter extends RecyclerView.Adapter to provide data to a RecyclerView.
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsItem> newsList; // List of NewsItem objects to display

    /**
     * Constructor for the NewsAdapter.
     * @param newsList The list of NewsItem objects that this adapter will display.
     */
    public NewsAdapter(List<NewsItem> newsList) {
        this.newsList = newsList;
    }

    /**
     * NewsViewHolder holds the view elements for a single news item in the RecyclerView.
     * This nested static class helps to improve performance by caching view lookups.
     */
    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView newsImage;       // ImageView to display the news image
        TextView newsHeadline;    // TextView to display the news headline
        TextView newsBody;        // TextView to display the news body/content
        TextView seeMoreText;     // TextView for the "see more.." / "see less.." functionality
        TextView publishedTime;   // TextView to display the published date/time

        /**
         * Constructor for NewsViewHolder.
         * @param itemView The root view of the single news card layout (news_card.xml).
         */
        public NewsViewHolder(View itemView) {
            super(itemView);
            // Initialize all view components by finding them by their IDs from the layout.
            newsImage = itemView.findViewById(R.id.newsImage);
            newsHeadline = itemView.findViewById(R.id.newsHeadline);
            newsBody = itemView.findViewById(R.id.newsBody);
            seeMoreText = itemView.findViewById(R.id.seeMoreText);
            publishedTime = itemView.findViewById(R.id.publishedTime);
        }
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     * This method inflates the news_card.xml layout and creates a new NewsViewHolder.
     *
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View.
     * @return A new NewsViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the news_card.xml layout for each item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card, parent, false);
        return new NewsViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method updates the contents of the itemView to reflect the item at the given position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        NewsItem item = newsList.get(position); // Get the NewsItem object for the current position

        // Set the text for headline, body, and published time
        holder.newsHeadline.setText(item.getHeadline());
        holder.newsBody.setText(item.getBody());
        holder.publishedTime.setText(item.getPublishedDate());

        // Load image using Glide.
        // If the image URL is "*", load a local placeholder drawable.
        // Otherwise, load the actual image from the provided URL.
        Glide.with(holder.itemView.getContext())
                .load(item.getImageUrl().equals("*") ? R.drawable.placeholder_news_image : item.getImageUrl())
                .into(holder.newsImage);

        // Set an OnClickListener for the "see more.." text.
        // This toggles the max lines of the news body for truncation/expansion.
        holder.seeMoreText.setOnClickListener(v -> {
            if (holder.newsBody.getMaxLines() == 4) {
                // If currently truncated, expand to show full text
                holder.newsBody.setMaxLines(Integer.MAX_VALUE); // Set to max lines to show full content
                holder.seeMoreText.setText("see less..");       // Change text to "see less.."
            } else {
                // If currently expanded, truncate to 4 lines
                holder.newsBody.setMaxLines(4);                 // Set back to 4 lines
                holder.seeMoreText.setText("see more..");       // Change text to "see more.."
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
