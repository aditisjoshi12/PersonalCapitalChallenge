package com.example.aditijoshi.personalcapital.NetworkUtils;

/**
 * Created by aditijoshi on 9/24/17.
 */

public class ArticleSyncFinishedEvent {



    public String getTitle() {
        return title;
    }

    String title;
    public ArticleSyncFinishedEvent(String title) {
        this.title = title;
    }

}
