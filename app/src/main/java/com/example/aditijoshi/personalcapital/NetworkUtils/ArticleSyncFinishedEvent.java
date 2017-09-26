package com.example.aditijoshi.personalcapital.NetworkUtils;

import com.example.aditijoshi.personalcapital.DataModel.Article;

import java.util.ArrayList;

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
