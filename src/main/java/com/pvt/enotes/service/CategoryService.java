package com.pvt.enotes.service;

import com.pvt.enotes.dto.CategoryDto;
import com.pvt.enotes.dto.CategoryResponse;
import com.pvt.enotes.entity.Category;

import java.util.List;

public interface CategoryService {


    public Boolean saveCategory(CategoryDto categoryDto);

    public List<CategoryDto> getAllCategory();

    public List<CategoryResponse> getActiveCategory();



    Boolean deleteCategory(Integer id);


    CategoryDto getCategoryById(Integer id) throws Exception;


}
