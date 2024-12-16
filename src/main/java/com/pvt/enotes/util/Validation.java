package com.pvt.enotes.util;

import com.pvt.enotes.dto.CategoryDto;
import com.pvt.enotes.dto.TodoDto;
import com.pvt.enotes.dto.TodoDto.StatusDto;
import com.pvt.enotes.dto.UserDto;
import com.pvt.enotes.enums.TodoStatus;
import com.pvt.enotes.exception.ExistDataException;
import com.pvt.enotes.exception.ResourceNotFoundException;
import com.pvt.enotes.exception.ValidationException;
import com.pvt.enotes.repository.RoleRepository;
import com.pvt.enotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class Validation {

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private UserRepository userRepo;

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

    public void userValidation(UserDto userDto) throws Exception{

        if (!StringUtils.hasText(userDto.getFirstName())) {
            throw new IllegalArgumentException("first name is invalid");
        }

        if (!StringUtils.hasText(userDto.getLastName())) {
            throw new IllegalArgumentException("last name is invalid");
        }

        if (!StringUtils.hasText(userDto.getEmail()) || !userDto.getEmail().matches(Constants.EMAIL_REGEX)) {
            throw new IllegalArgumentException("email is invalid");
        }else{
            Boolean existEmail = userRepo.existsByEmail(userDto.getEmail());
            if(existEmail){
                throw new ExistDataException("Email already exists");
            }
        }

        if (!StringUtils.hasText(userDto.getMobNo()) || !userDto.getMobNo().matches(Constants.MOBNO_REGEX)) {
            throw new IllegalArgumentException("mobno is invalid");
        }

        if (CollectionUtils.isEmpty(userDto.getRoles())) {
            throw new IllegalArgumentException("role is invalid");
        } else {

            List<Integer> roleIds = roleRepo.findAll().stream().map(r -> r.getId()).toList();

            List<Integer> invalidReqRoleids = userDto.getRoles().stream().map(r -> r.getId())
                    .filter(roleId -> !roleIds.contains(roleId)).toList();

            if (!CollectionUtils.isEmpty(invalidReqRoleids)) {
                throw new IllegalArgumentException("role is invalid" + invalidReqRoleids);
            }
        }

        
    }

}
