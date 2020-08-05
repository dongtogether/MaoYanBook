package com.example.maoyan.service.impl;

import com.example.maoyan.entity.Article;
import com.example.maoyan.mapper.ArticleMapper;
import com.example.maoyan.redis.RedisService;
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
import java.util.Set;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    RedisService redisService;

    /**
     * 分页查询文章
     * @param pageRequest
     * @return
     */
    public PageResult listAllByPage(PageRequest pageRequest){
        // 用户访问列表页面时按页缓存文章
        String key = "articlepage:" + pageRequest.getPageNum();
        Object articlePageCache = redisService.get(key);

        if (articlePageCache == null) {
            //调用分页插件完成分页
            PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
            List<Article> articleList = articleMapper.findAllByPage();
            PageInfo<Article> pageInfo = new PageInfo<Article>(articleList);
            redisService.set(key,PageUtils.getPageResult(pageInfo));
            return PageUtils.getPageResult(pageInfo);
        }else{
            return (PageResult) articlePageCache;
        }

    }

    /**
     * 文章详情
     * @param articleId
     * @return
     */
    public Article get(String articleId) {
        Article article;
        // 用户访问具体文章时缓存单篇文章，通过 id 区分
        String key = "article:" + articleId;
        Object articleCache = redisService.get(key);

        if (articleCache == null) {
            article = articleMapper.findById(articleId);
            redisService.set(key, article);
        } else {
            article = (Article) articleCache;
        }
        return article;

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

        // 删除当前选中的文章和所有文章页面的缓存
        redisService.delete("article" + article.getArticleId());
        Set<String> keys = redisService.getKeysByPattern("articlepage*");
        redisService.delete(keys);
    }

    public void updateArticle(Article article){

        articleMapper.updateArticle(article);

        // 删除当前选中的文章和所有文章页面的缓存
        redisService.delete("article" + article.getArticleId());
        Set<String> keys = redisService.getKeysByPattern("articlepage*");
        redisService.delete(keys);
    }

    public void deleteArticleById(String articleId){

        articleMapper.deleteArticleById(articleId);

        // 删除当前选中的文章和所有文章页面的缓存
        redisService.delete("article" + articleId);
        Set<String> keys = redisService.getKeysByPattern("articlepage*");
        redisService.delete(keys);
    }

}


