package com.example.maoyan.service.impl;

import com.example.maoyan.mapper.BookMapper;
import com.example.maoyan.entity.Book;
import com.example.maoyan.redis.RedisService;
import com.example.maoyan.service.BookService;
import com.example.maoyan.utils.CastUtils;
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
public class BookServiceImpl implements BookService {
    @Autowired
    BookMapper bookMapper;
    @Autowired
    private RedisService redisService;

    //查询所有图书
    // 改为调用分页接口，未使用,暂留
    public List<Book> list() {
        List<Book> books;
        String key = "booklist";
        Object bookCache = redisService.get(key);

        if (bookCache == null) {
            books = bookMapper.findAll();
            redisService.set(key, books);
        } else {
            books = CastUtils.objectConvertToList(bookCache, Book.class);
        }
        return books;
    }

    /**
     * 分页查得所有图书
     * @param pageRequest
     * @return
     */
    public PageResult listByPage(PageRequest pageRequest) {
        // 用户访问列表页面时按页缓存文章
        String key = "bookPage:" + pageRequest.getPageSize()
                +"-"+ pageRequest.getPageNum();
        Object bookPageCache = redisService.get(key);

        if (bookPageCache == null) {
            //调用分页插件完成分页
            PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
            List<Book> bookList = bookMapper.findAll();
            PageInfo<Book> pageInfo = new PageInfo<Book>(bookList);
            redisService.set(key,PageUtils.getPageResult(pageInfo));
            return PageUtils.getPageResult( pageInfo);
        }else{
            return (PageResult) bookPageCache;
        }
    }

    public Book findByBookId(String bookId){
        return bookMapper.findByBookId(bookId);
    }

    public List<Book> listByCategory(int cid) {
        return bookMapper.findAllByCategoryId(cid);
    }

    public List<Book> Search(String keywords) {
        return bookMapper.findAllByTitleLikeOrAuthorLike(keywords);
    }

    /**
     * 分页查询某个用户的收藏图书
     * @param pageRequest
     * @param userId
     * @return
     */
    public PageResult queryCollectionBooksByPage(PageRequest pageRequest, String userId) {
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        Map<String, Object> data = new HashMap();
        data.put("userId",userId);
        List<Book> collectBookList = bookMapper.findCollectBooksByPage(data);
        PageInfo<Book> pageInfo = new PageInfo<Book>(collectBookList);
        return PageUtils.getPageResult( pageInfo);
    }

    public void addBook(Book book) {
        Set<String> keys = redisService.getKeysByPattern("bookPage*");
        redisService.delete(keys);
        bookMapper.saveBook(book);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        redisService.delete(keys);
    }

    public void updateBookInfo(Book book){
        Set<String> keys = redisService.getKeysByPattern("bookPage*");
        redisService.delete(keys);
        bookMapper.updateBook(book);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        redisService.delete(keys);
    }

    public void deleteById(String bookId) {
        Set<String> keys = redisService.getKeysByPattern("bookPage*");
        redisService.delete(keys);
        bookMapper.deleteById(bookId);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        redisService.delete(keys);
    }

}


