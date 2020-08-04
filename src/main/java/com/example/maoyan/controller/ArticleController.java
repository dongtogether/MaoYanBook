package com.example.maoyan.controller;

import com.example.maoyan.entity.Article;
import com.example.maoyan.result.Result;
import com.example.maoyan.result.ResultFactory;
import com.example.maoyan.service.ArticleService;
import com.example.maoyan.utils.OrderCodeFactory;
import com.example.maoyan.utils.PageRequest;
import com.example.maoyan.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class ArticleController {
    @Autowired
    ArticleService articleService;

    /**
     * 列出所有笔记文章
     * @param size
     * @param page
     * @return
     * @throws Exception
     */
    @GetMapping("/api/jotter/article/{size}/{page}")
    public Result listAllArticle(@PathVariable("size") int size,
                                     @PathVariable("page") int page) throws Exception {

        PageRequest pageQuery =new PageRequest();
        pageQuery.setPageSize(size);
        pageQuery.setPageNum(page);

        PageResult pageResult = articleService.listAllByPage(pageQuery);
        return ResultFactory.buildSuccessResult(pageResult);
    }

    /**
     * 列出特定用户的笔记文章
     * @param userId
     * @param size
     * @param page
     * @return
     * @throws Exception
     */
    @GetMapping("/api/jotter/article/{userId}/{size}/{page}")
    public Result listArticle(@PathVariable("userId") String userId,
                                     @PathVariable("size") int size,
                                     @PathVariable("page") int page) throws Exception {

        PageRequest pageQuery =new PageRequest();
        pageQuery.setPageSize(size);
        pageQuery.setPageNum(page);

        PageResult pageResult =articleService.queryArticlesByPage(pageQuery,userId );
        return ResultFactory.buildSuccessResult(pageResult);
    }

    /**
     * 某篇笔记文章的详情
     * @param id
     * @return
     */
    @GetMapping("/api/jotter/article/{id}")
    public Result getOneArticle(@PathVariable("id") String id) {
        Article article = articleService.get(id);
        return ResultFactory.buildSuccessResult(article);
    }

    /**
     * 保存文章--新增或修改
     * @param article
     * @return
     * @throws Exception
     */
    @PostMapping("/api/jotter/article/save-article")
    public Result saveArticle(@RequestBody Article article) throws Exception {
        String nowDay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString();
        article.setArticleDate(nowDay);
        //id存在则是修改，没有则新增
        if(article.getArticleId() != null && article.getArticleId() != ""){
            articleService.updateArticle(article);
        } else {

            String id = OrderCodeFactory.generateArticleID();
            article.setArticleId(id);
            articleService.insertArticle(article);
        }
         return ResultFactory.buildSuccessResult("保存文章成功");
    }

    /**
     * 删除笔记文章
     * @param articleId
     * @return
     */
    @DeleteMapping("/api/jotter/article/{id}")
    public Result deleteArticle(@PathVariable("id") String articleId) {
        articleService.deleteArticleById(articleId);
        return ResultFactory.buildSuccessResult("删除成功");
    }


}
