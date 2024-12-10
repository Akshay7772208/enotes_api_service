package com.pvt.enotes.service;

import com.pvt.enotes.entity.Category;

import java.util.List;

public interface CategoryService {

    public Boolean saveCategory(Category category);

    public List<Category> getAllCategory();
}
