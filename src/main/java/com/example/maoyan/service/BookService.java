package com.example.maoyan.service;

import com.example.maoyan.entity.Book;
import com.example.maoyan.entity.Category;
import com.example.maoyan.utils.PageRequest;
import com.example.maoyan.utils.PageResult;

import java.util.List;

public interface BookService {

    public List<Book> list();

    public PageResult listByPage(PageRequest pageRequest);

    public Book findByBookId(String bookId);

    public List<Book> listByCategory(int cid);

    public List<Book> Search(String keywords);

    public PageResult queryCollectionBooksByPage(PageRequest pageRequest, String userId);

    public void addBook(Book book);

    public void updateBookInfo(Book book);

    public void deleteById(String bookId);
}
