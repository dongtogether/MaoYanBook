package com.example.maoyan.mapper;

import com.example.maoyan.entity.Article;
import com.example.maoyan.entity.Category;

import java.util.List;
import java.util.Map;

public interface ArticleMapper {

    public List<Article> findAllByPage();

    public Article findById(String articleId);

    public List<Article> queryArticlesByPage(Map<String,Object> data);

    public void insertArticle(Article article);

    public void updateArticle(Article article);

    public void deleteArticleById(String articleId);
}

