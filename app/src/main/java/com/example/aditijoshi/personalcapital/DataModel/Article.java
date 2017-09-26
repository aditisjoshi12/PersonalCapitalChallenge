package com.example.aditijoshi.personalcapital.DataModel;

/**
 * Created by aditijoshi on 9/24/17.
 */

public class Article {

    private String title;
    private String description;
    private String pubDate;
    private String url;
    private String imageURL;

    public Article(String title, String description, String pubDate, String url, String imageURL) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.url = url;
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
