package com.example.fotnewscorner;

// This class represents a single news item, holding the data fetched from Firebase.
public class NewsItem {
    // Member variables to store news attributes
    private String headline;
    private String body;
    private String publisheddate;
    private String imageurl;

    /**
     * Default no-argument constructor required by Firebase for automatic data deserialization.
     * Firebase uses this constructor to create an instance of NewsItem when reading data from the database.
     */
    public NewsItem() {
        // Empty constructor
    }

    /**
     * Constructor used when creating a NewsItem object manually in your application code.
     *
     * @param headline The headline/title of the news item.
     * @param body The main content/body of the news item.
     * @param publisheddate The date the news item was published.
     * @param imageurl The URL of the image associated with the news item.
     */
    public NewsItem(String headline, String body, String publisheddate, String imageurl) {
        this.headline = headline;
        this.body = body;
        this.publisheddate = publisheddate;
        this.imageurl = imageurl;
    }

    // Getter methods for accessing the news item's properties.
    // These are crucial for the NewsAdapter to retrieve data and display it.

    /**
     * Retrieves the headline of the news item.
     * @return The news headline.
     */
    public String getHeadline() {
        return headline;
    }

    /**
     * Retrieves the body content of the news item.
     * @return The news body content.
     */
    public String getBody() {
        return body;
    }

    /**
     * Retrieves the published date of the news item.
     * @return The news published date.
     */
    public String getPublishedDate() {
        return publisheddate;
    }

    /**
     * Retrieves the image URL of the news item.
     * @return The image URL.
     */
    public String getImageUrl() {
        return imageurl;
    }
}
