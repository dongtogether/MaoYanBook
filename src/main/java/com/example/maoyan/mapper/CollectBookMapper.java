package com.example.maoyan.mapper;


import com.example.maoyan.entity.CollectBook;

import java.util.List;

public interface CollectBookMapper {


    public List<CollectBook> selectByUserId(String userId);

    void addCollectBook(CollectBook collectBook);

    public void deleteCollectBook(String userId,String bookId);

}

