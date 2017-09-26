package com.example.aditijoshi.personalcapital.Core;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.aditijoshi.personalcapital.DataModel.Article;
import com.example.aditijoshi.personalcapital.NetworkUtils.ArticleSyncFailedEvent;
import com.example.aditijoshi.personalcapital.NetworkUtils.ArticleSyncFinishedEvent;

import com.example.aditijoshi.personalcapital.NetworkUtils.NetworkHelper;
import com.example.aditijoshi.personalcapital.NetworkUtils.XMLParser;
import com.example.aditijoshi.personalcapital.R;
import com.example.aditijoshi.personalcapital.NetworkUtils.NoNetworkConnectionEvent;
import com.example.aditijoshi.personalcapital.db.DatabaseHandler;


import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    ProgressDialog loadingDialog;
    RecyclerView recyclerView;
    HeaderView headerArticle;
    LinearLayout outerLayout;
    ArrayList<Article> articles = new ArrayList<>();
    Toolbar myToolbar;
    int mColumnCount = 2;
    MyFeedRecyclerViewAdapter myFeedAdapter;
    DatabaseHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUI();


    }

    private void setUI() {

        // Using a nested scrollview to display header followed by a recycler view using grid layout
        NestedScrollView nScrollView = new NestedScrollView(this);
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
        nScrollView.setLayoutParams(params);

        setContentView(nScrollView);



        outerLayout = new LinearLayout(this);
        outerLayout.setOrientation(LinearLayout.VERTICAL);
        outerLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        myToolbar = new Toolbar(this);
        outerLayout.addView(myToolbar);
        myToolbar.setTitle(getString(R.string.feed_title));
        myToolbar.setBackgroundColor(UIHelper.getInstance().getColor(this,R.color.colorPrimary));
        myToolbar.setTitleTextColor(UIHelper.getInstance().getColor(this,R.color.colorWhite));
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Button refresh = new Button(this);

        LinearLayout.LayoutParams tParams = new LinearLayout.LayoutParams(54, 54);
        params.gravity = Gravity.RIGHT;
        params.anchorGravity = Gravity.RIGHT;

        refresh.setBackground(getDrawable(R.drawable.ic_autorenew_white_24dp));
        refresh.setLayoutParams(tParams);
        myToolbar.addView(refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRSSFeedFromURL();
            }
        });
        nScrollView.addView(outerLayout);

        setColumnCount();
        handler = new DatabaseHandler(getApplicationContext());
        articles = handler.getAllArticles();
        setupRecyclerView(outerLayout);
    }

    private void setColumnCount() {
        mColumnCount = isTablet ? 3 : 2;
    }


    private void setupRecyclerView(LinearLayout rootView) {
        LinearLayout layout = new LinearLayout(this);
        headerArticle = new HeaderView(layout, this);
        rootView.addView(layout);
        recyclerView = new RecyclerView(this);
        recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        recyclerView.setNestedScrollingEnabled(false);
        if (recyclerView instanceof RecyclerView) {
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(this, mColumnCount));
            }
            recyclerView.setBackgroundColor(UIHelper.getInstance().getColor(this, R.color.colorGrey));

            rootView.addView(recyclerView);
            myFeedAdapter = new MyFeedRecyclerViewAdapter(articles, getApplicationContext());
            recyclerView.setAdapter(myFeedAdapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // show prgress dialog till rss feed is loaded
        getRSSFeedFromURL();


    }

    private void getRSSFeedFromURL() {
        showProgressDialog();
        NetworkHelper.getInstance().getRSSFeed(getApplicationContext());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onEvent(NoNetworkConnectionEvent e) {


        // hamdling network errors by notifying users to check network connection
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgressDialog();
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.network_error_message), Toast.LENGTH_LONG);
                toast.show();
            }
        });

    }

    public void onEvent(final ArticleSyncFinishedEvent e) {

        // Once article sync from network and into db is complete, get all articles from db and
        //notify the recycler view adapter that new content is available and dismiss the dialog

        final ArrayList<Article> articles = handler.getAllArticles();
        if (articles.size() > 0) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    myToolbar.setTitle(e.getTitle());
                    headerArticle.setup(articles.get(0));
                    articles.remove(0);
                    myFeedAdapter.setFeedArticles(articles);
                    hideProgressDialog();
                    myFeedAdapter.notifyDataSetChanged();

                }
            });
        }
    }

    public void onEvent(final ArticleSyncFailedEvent e) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgressDialog();
                Toast toast = Toast.makeText(getApplicationContext(), e.getErrorMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        });

    }

    public void showProgressDialog() {
        if(loadingDialog==null) {
            loadingDialog = new ProgressDialog(this);
        }
        loadingDialog.setMessage(getString(R.string.loading_message));
        loadingDialog.setCancelable(false);
        loadingDialog.show();
    }

    public void hideProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loadingDialog != null) {
                    loadingDialog.hide();
                }
            }
        });

    }


    public void onEvent(InflateWebViewEvent e) {

        startWebViewActivity(e.url, e.title);
    }

    public void startWebViewActivity(String url, String title) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(WebViewActivity.URL_ARG, url);
        intent.putExtra(WebViewActivity.TOPIC_ARG, title);
        startActivity(intent);

    }





}
