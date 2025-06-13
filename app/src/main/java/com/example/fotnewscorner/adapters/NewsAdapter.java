package com.example.fotnewscorner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    // Define a News model class with fields for image, headline, body, published time, etc.
    public static class NewsItem {
        public int imageResId;   // drawable resource ID for simplicity
        public String headline;
        public String body;
        public String publishedTime;

        public NewsItem(int imageResId, String headline, String body, String publishedTime) {
            this.imageResId = imageResId;
            this.headline = headline;
            this.body = body;
            this.publishedTime = publishedTime;
        }
    }

    private List<NewsItem> newsList;

    public NewsAdapter(List<NewsItem> newsList) {
        this.newsList = newsList;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView newsImage;
        TextView newsHeadline;
        TextView newsBody;
        TextView seeMoreText;
        TextView publishedTime;

        public NewsViewHolder(View itemView) {
            super(itemView);
            newsImage = itemView.findViewById(R.id.newsImage);
            newsHeadline = itemView.findViewById(R.id.newsHeadline);
            newsBody = itemView.findViewById(R.id.newsBody);
            seeMoreText = itemView.findViewById(R.id.seeMoreText);
            publishedTime = itemView.findViewById(R.id.publishedTime);
        }
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        NewsItem item = newsList.get(position);
        holder.newsImage.setImageResource(item.imageResId);
        holder.newsHeadline.setText(item.headline);
        holder.newsBody.setText(item.body);
        holder.publishedTime.setText(item.publishedTime);

        // Example for "see more" click to expand the body text
        holder.seeMoreText.setOnClickListener(v -> {
            if (holder.newsBody.getMaxLines() == 4) {
                holder.newsBody.setMaxLines(Integer.MAX_VALUE);  // expand to full
                holder.seeMoreText.setText("see less..");
            } else {
                holder.newsBody.setMaxLines(4);  // collapse
                holder.seeMoreText.setText("see more..");
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
