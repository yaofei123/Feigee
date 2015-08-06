package com.feigee.feigee.app.entity;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by fei.yao on 7/28/15.
 */
public class News {

    private ArrayList<News> mNewsList;
    private static News sNews;
    private Context mAppcontext;
    /**
     * id
     */
    private int id;
    /**
     * 标题
     */
    private String title;
    /**
     * 作者
     */
    private String author;
    /**
     * 主题
     */
    private String theme;
    /**
     * 日期
     */
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public News() {
    }

    public News(Context appcontext) {
        this.mAppcontext = appcontext;
        mNewsList = new ArrayList<News>();
        for (int i = 0; i < 20; i++) {
            News news = new News();
            news.setId(i);
            news.setTheme("news #" + i);
            news.setAuthor("author #" + i);
            news.setDate((new SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
            news.setTheme("theme #" + i);
            mNewsList.add(news);
        }
    }

    public ArrayList<News> getNewsList() {
        return mNewsList;
    }

    /**
     * 单例
     * @param context
     * @return
     */
    public static News get(Context context) {
        if (sNews == null) {
            sNews = new News(context.getApplicationContext());
        }
        return sNews;
    }
}
