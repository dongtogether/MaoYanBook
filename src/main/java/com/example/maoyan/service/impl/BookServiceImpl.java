package com.example.maoyan.service.impl;

import com.example.maoyan.mapper.BookMapper;
import com.example.maoyan.entity.Book;
import com.example.maoyan.service.BookService;
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
public class BookServiceImpl implements BookService {
    @Autowired
    BookMapper bookMapper;

    public List<Book> list() {
        return bookMapper.findAll();
    }

    /**
     * 分页查得所有图书
     * @param pageRequest
     * @return
     */
    public PageResult listByPage(PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        List<Book> bookList = bookMapper.findAll();
        PageInfo<Book> pageInfo = new PageInfo<Book>(bookList);
        return PageUtils.getPageResult( pageInfo);
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
        bookMapper.saveBook(book);
    }

    public void updateBookInfo(Book book){
        bookMapper.updateBook(book);
    }

    public void deleteById(String bookId) {
        bookMapper.deleteById(bookId);
    }





}


