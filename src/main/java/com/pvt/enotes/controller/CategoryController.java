package com.pvt.enotes.controller;

import com.pvt.enotes.dto.CategoryDto;
import com.pvt.enotes.dto.CategoryResponse;
import com.pvt.enotes.entity.Category;
import com.pvt.enotes.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")

public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto){
       Boolean saveCategory= categoryService.saveCategory(categoryDto);

       if(saveCategory){
           return new ResponseEntity<>("saved success", HttpStatus.CREATED);
       }
       return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory(){
         List<CategoryDto> allCategory=categoryService.getAllCategory();

        if(CollectionUtils.isEmpty(allCategory)){
            return  ResponseEntity.noContent().build();

        }
        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }

    @GetMapping("/active-category")
    public ResponseEntity<?> getActiveCategory(){
        List<CategoryResponse> activeCategory=categoryService.getActiveCategory();

        if(CollectionUtils.isEmpty(activeCategory)){
            return  ResponseEntity.noContent().build();

        }
        return new ResponseEntity<>(activeCategory, HttpStatus.OK);
    }

}
