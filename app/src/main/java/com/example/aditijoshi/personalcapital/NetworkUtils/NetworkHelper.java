package com.example.aditijoshi.personalcapital.NetworkUtils;

import android.content.Context;

import com.example.aditijoshi.personalcapital.DataModel.Article;
import com.example.aditijoshi.personalcapital.R;
import com.example.aditijoshi.personalcapital.db.DatabaseHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;

import de.greenrobot.event.EventBus;
import okhttp3.Response;

/**
 * Created by aditijoshi on 9/25/17.
 */

public class NetworkHelper {

    private static NetworkHelper mInstance;

    public NetworkHelper() {

    }

    public static NetworkHelper getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkHelper();
        }
        return mInstance;
    }

    public void getRSSFeed(final Context context) {

        String requestURL = "https://blog.personalcapital.com/feed/?cat=3,891,890,68,284";

        HttpRequest.getInstance().post(requestURL, 20, new HTTPResponseHandler() {


            @Override
            public void onSuccess(Response root) {
                super.onSuccess(root);
                try {
                    XMLParser parser = new XMLParser();
                    ArrayList<Article> articles = parser.parseRSSFeed(root.body().string());
                    DatabaseHandler db = new DatabaseHandler(context);
                    // instead of deleting all and inserting all articles, I would ideally come up with a scheme to identify
                    //artciles using an id and just update exisitng articles.
                    db.deleteAllArticles();
                    db.addArticles(articles);
                    EventBus.getDefault().post(new ArticleSyncFinishedEvent(parser.getFeedTitle()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response response, Throwable e) {
                super.onError(response, e);
                EventBus.getDefault().post(new ArticleSyncFailedEvent(context.getString(R.string.article_sync_error_message)));
            }
        });

    }
}
