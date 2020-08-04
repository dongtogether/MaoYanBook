package com.example.maoyan.service;

import com.example.maoyan.entity.Article;
import com.example.maoyan.utils.PageRequest;
import com.example.maoyan.utils.PageResult;

import java.util.List;

public interface ArticleService {

    public PageResult listAllByPage(PageRequest pageRequest);

    public Article get(String articleId);

    public PageResult queryArticlesByPage(PageRequest pageRequest,String userId);

    public void insertArticle(Article article);

    public void updateArticle(Article article);

    public void deleteArticleById(String articleId);
}
