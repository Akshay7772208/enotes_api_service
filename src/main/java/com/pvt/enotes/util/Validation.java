package com.pvt.enotes.util;

import com.pvt.enotes.dto.CategoryDto;
import com.pvt.enotes.dto.TodoDto;
import com.pvt.enotes.dto.TodoDto.StatusDto;
import com.pvt.enotes.enums.TodoStatus;
import com.pvt.enotes.exception.ResourceNotFoundException;
import com.pvt.enotes.exception.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class Validation {

    public void categoryValidation(CategoryDto categoryDto){

        Map<String,Object> error=new LinkedHashMap<>();

        if(ObjectUtils.isEmpty(categoryDto)){
            throw new IllegalArgumentException("Category Object/JSON shouldn't be null or empty");
        }else{

            if(ObjectUtils.isEmpty(categoryDto.getName())){
                error.put("name","Name is empty or null");
                //throw new IllegalArgumentException("Name is empty or null");
            }else{
                if(categoryDto.getName().length()<3){
                    error.put("name","Name length min 3");
                }
                if(categoryDto.getName().length()>100){
                    error.put("name","Name length max 100");
                }
            }

            //description validation
            if(ObjectUtils.isEmpty(categoryDto.getDescription())){
                error.put("description","Description is empty or null");
                //throw new IllegalArgumentException("Name is empty or null");
            }

            //isActive
            if(ObjectUtils.isEmpty(categoryDto.getIsActive())){
                error.put("isActive","isActive is empty or null");
                //throw new IllegalArgumentException("Name is empty or null");
            }else{
                if(categoryDto.getIsActive().booleanValue() != Boolean.TRUE && categoryDto.getIsActive().booleanValue() != Boolean.FALSE){
                    error.put("isActive","isActive invalid value");
                }
            }
        }

        if(!error.isEmpty()){
            throw new ValidationException(error);
        }
    }

    public void todoValidation(TodoDto todoDto) throws Exception{
        StatusDto reqStatus = todoDto.getStatus();
        Boolean statusFound=false;
        for(TodoStatus st: TodoStatus.values()){
            if(st.getId().equals(reqStatus.getId())){
                statusFound=true;
            }
        }
        if(!statusFound){
            throw new ResourceNotFoundException("invalid status");
        }
    }
}
