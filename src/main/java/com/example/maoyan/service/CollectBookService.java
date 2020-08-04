package com.example.maoyan.service;

import com.example.maoyan.entity.CollectBook;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CollectBookService {

    public List<CollectBook> selectByUserId(String userId);

    public void addCollectBook(CollectBook collectBook);

    public void deleteCollectBook(String userId, String bookId);

}
