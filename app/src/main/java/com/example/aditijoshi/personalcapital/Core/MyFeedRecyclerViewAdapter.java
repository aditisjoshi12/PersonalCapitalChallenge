package com.example.aditijoshi.personalcapital.Core;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.aditijoshi.personalcapital.DataModel.Article;

import java.util.ArrayList;

/**
 * Created by aditijoshi on 9/24/17.
 */

class MyFeedRecyclerViewAdapter extends RecyclerView.Adapter {

    public void setFeedArticles(ArrayList<Article> feedArticles) {
        this.feedArticles = feedArticles;
    }

    ArrayList<Article> feedArticles;
    Context context;

    public MyFeedRecyclerViewAdapter(ArrayList<Article> articles, Context context) {
        feedArticles = articles;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout articleView = new LinearLayout(context);
        return new ArticleViewHolder(articleView, context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((ArticleViewHolder) holder).setup(feedArticles.get(position));

    }

    @Override
    public int getItemCount() {
        if (feedArticles != null) {
            return feedArticles.size();
        }
        return 0;
    }

}