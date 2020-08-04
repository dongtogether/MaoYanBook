package com.example.maoyan.mapper;


import com.example.maoyan.entity.CollectBook;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CollectBookMapper {


    public List<CollectBook> selectByUserId(String userId);

    void addCollectBook(CollectBook collectBook);

    public void deleteCollectBook(String userId,String bookId);

}

