package com.example.maoyan.service;

import com.example.maoyan.entity.Category;

import java.util.List;

public interface CategoryService {

    public List<Category> list();

    public Category get(int id);
}
