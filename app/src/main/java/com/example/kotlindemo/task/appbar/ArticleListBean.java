package com.example.kotlindemo.task.appbar;

import java.io.Serializable;

/**
 * @Description
 * @Author
 * @Date
 */
public class ArticleListBean implements Serializable {
    public String article;

    public void setText(String article) {
        this.article = article;
    }

    public String getArticle() {
        return article;
    }
}
