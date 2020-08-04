package com.example.maoyan.mapper;

import com.example.maoyan.entity.Book;
import com.example.maoyan.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface BookMapper {

    List<Book> findAll();

    Book  findByBookId(String bookId);

    List<Book> findAllByCategoryId(int cid);

    List<Book> findAllByTitleLikeOrAuthorLike(String keyword);

    List<Book> findCollectBooksByPage(Map<String,Object> data);

    void saveBook(Book book);

    void deleteById(String bookId);

    void updateBook(Book book);
}

