package com.pvt.enotes.controller;

import com.pvt.enotes.dto.CategoryDto;
import com.pvt.enotes.dto.CategoryResponse;
import com.pvt.enotes.entity.Category;
import com.pvt.enotes.exception.ResourceNotFoundException;
import com.pvt.enotes.service.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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

    @GetMapping("/")
    public ResponseEntity<?> getAllCategory(){

         List<CategoryDto> allCategory=categoryService.getAllCategory();

        if(CollectionUtils.isEmpty(allCategory)){
            return  ResponseEntity.noContent().build();

        }
        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveCategory(){
        List<CategoryResponse> activeCategory=categoryService.getActiveCategory();

        if(CollectionUtils.isEmpty(activeCategory)){
            return  ResponseEntity.noContent().build();

        }
        return new ResponseEntity<>(activeCategory, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception{

            CategoryDto categoryDto=categoryService.getCategoryById(id);
            if(ObjectUtils.isEmpty(categoryDto)){

                return new ResponseEntity<>("Internal server error", HttpStatus.NOT_FOUND);

            }
            return new ResponseEntity<>(categoryDto, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id){
        Boolean deleted=categoryService.deleteCategory(id);
        if(deleted){
            return new ResponseEntity<>("Category Deleted", HttpStatus.OK);

        }
        return new ResponseEntity<>("Category Not Deleted", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
