package com.example.maoyan.service.impl;

import com.example.maoyan.entity.Article;
import com.example.maoyan.mapper.ArticleMapper;
import com.example.maoyan.service.ArticleService;
import com.example.maoyan.utils.PageRequest;
import com.example.maoyan.utils.PageResult;
import com.example.maoyan.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;

    /**
     * 分页查询文章
     * @param pageRequest
     * @return
     */
    public PageResult listAllByPage(PageRequest pageRequest){
        //调用分页插件完成分页
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        List<Article> articleList = articleMapper.findAllByPage();
        PageInfo<Article> pageInfo = new PageInfo<Article>(articleList);
        return PageUtils.getPageResult(pageInfo);
    }

    /**
     * 文章详情
     * @param articleId
     * @return
     */
    public Article get(String articleId) {
        Article c= articleMapper.findById(articleId);
        if(c != null){
            return c;
        } else{
            return null;
        }

    }

    /**
     * 分页查询某个用户的文章
     * @param pageRequest
     * @param userId
     * @return
     */
    public PageResult queryArticlesByPage(PageRequest pageRequest,String userId) {
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        Map<String, Object> data = new HashMap();
        data.put("userId",userId);
        List<Article> articleList = articleMapper.queryArticlesByPage(data);
        PageInfo<Article> pageInfo = new PageInfo<Article>(articleList);
        return PageUtils.getPageResult( pageInfo);
    }

    public void insertArticle(Article article){
        articleMapper.insertArticle(article);
    }

    public void updateArticle(Article article){
        articleMapper.updateArticle(article);
    }

    public void deleteArticleById(String articleId){
        articleMapper.deleteArticleById(articleId);
    }

}


