package com.example.maoyan.service.impl;

import com.example.maoyan.mapper.CategoryMapper;
import com.example.maoyan.entity.Category;
import com.example.maoyan.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    public List<Category> list() {
        return categoryMapper.findAll();
    }

    public Category get(int id) {
        Category c= categoryMapper.findById(id);
        if(c != null){
            return c;
        } else{
            return null;
        }

    }
}


