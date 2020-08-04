package com.example.maoyan.mapper;

import com.example.maoyan.entity.Category;

import java.util.List;

public interface CategoryMapper  {

    public List<Category> findAll();

    public Category findById(int id);

}

