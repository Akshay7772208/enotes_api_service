package com.pvt.enotes.service.impl;

import com.pvt.enotes.dto.CategoryDto;
import com.pvt.enotes.dto.CategoryResponse;
import com.pvt.enotes.entity.Category;
import com.pvt.enotes.repository.CategoryRepository;
import com.pvt.enotes.service.CategoryService;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {


//          Category category=new Category();
//          category.setName(categoryDto.getName());
//          category.setDescription(categoryDto.getDescription());
//          category.setIsActive(categoryDto.getIsActive());
          Category category=mapper.map(categoryDto,Category.class);

          category.setIsDeleted(false);
          category.setCreatedBy(1);
          category.setCreatedOn(new Date());


          Category saveCategory= categoryRepo.save(category);

          if(ObjectUtils.isEmpty(saveCategory)){
            return false;
          }
          return true;
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories=categoryRepo.findAll();
        List<CategoryDto> categoryDtoList=categories.stream().map(cat -> mapper.map(cat,CategoryDto.class)).toList();
//        if(!CollectionUtils.isEmpty(categories)){
//            return categories;
//        }

        return categoryDtoList;
    }

    @Override
    public List<CategoryResponse> getActiveCategory() {
        List<Category> categories=categoryRepo.findByIsActiveTrue();
        List<CategoryResponse> categoryList=categories.stream().map(cat -> mapper.map(cat,CategoryResponse.class)).toList();

        return categoryList;
    }


}
