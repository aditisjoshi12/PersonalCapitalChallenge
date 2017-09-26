package com.example.aditijoshi.personalcapital.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.aditijoshi.personalcapital.DataModel.Article;

import java.util.ArrayList;


public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "articlesManager";

    // Articles table name
    private static final String TABLE_ARTICLES = "articles";

    // Articles Table Columns names
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_URL = "articleUrl";
    private static final String KEY_IMAGEURL = "imageUrl";
    private static final String KEY_TIMESTAMP = "timestamp";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_ARTICLES + "("
                + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT," +
                KEY_URL + " TEXT," +
                KEY_IMAGEURL + " TEXT," +
                KEY_TIMESTAMP + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);

        // Create tables again
        onCreate(db);
    }


    public void addArticles(ArrayList<Article> articles) {

        for(Article article :articles) {
            addArticle(article);
        }
    }
    // Adding new article
    public void addArticle(Article article) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, article.getTitle()); // Article Name
        values.put(KEY_DESCRIPTION, article.getDescription()); // Article description
        values.put(KEY_IMAGEURL, article.getImageURL()); // Article image
        values.put(KEY_URL, article.getUrl()); // Article url
        values.put(KEY_TIMESTAMP, article.getPubDate()); // Article timestamp

        // Inserting Row
        db.insert(TABLE_ARTICLES, null, values);
        db.close(); // Closing database connection
    }


    // Getting All article
    public ArrayList<Article> getAllArticles() {
        ArrayList<Article> articleArrayList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ARTICLES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Article article = new Article(cursor.getString(0), cursor.getString(1), cursor.getString(4), cursor.getString(2), cursor.getString(3));

                // Adding contact to list
                articleArrayList.add(article);
            } while (cursor.moveToNext());
        }

        // return contact list
        return articleArrayList;
    }


    // Deleting single article
    public void deleteAllArticles() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ARTICLES,null,null);
        db.close();
    }
}