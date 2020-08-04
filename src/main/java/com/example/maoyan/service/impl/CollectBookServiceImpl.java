package com.example.maoyan.service.impl;


import com.example.maoyan.entity.CollectBook;
import com.example.maoyan.mapper.CollectBookMapper;
import com.example.maoyan.service.CollectBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectBookServiceImpl implements CollectBookService {

    @Autowired
    CollectBookMapper collectionMapper;

    public List<CollectBook> selectByUserId(String userId) {
        return collectionMapper.selectByUserId(userId);
    }

    public void addCollectBook(CollectBook collectBook) {
        collectionMapper.addCollectBook(collectBook);
    }

    public void deleteCollectBook(String userId,String bookId) {
        collectionMapper.deleteCollectBook(userId,bookId);
    }
}

