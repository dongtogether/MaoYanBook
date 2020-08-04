package com.example.maoyan.mapper;

import com.example.maoyan.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CategoryMapper  {

    public List<Category> findAll();

    public Category findById(int id);

}

