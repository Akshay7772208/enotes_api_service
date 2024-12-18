package com.pvt.enotes.controller;

import com.pvt.enotes.dto.PasswordChngRequest;
import com.pvt.enotes.dto.UserResponse;
import com.pvt.enotes.entity.User;
import com.pvt.enotes.service.UserService;
import com.pvt.enotes.util.CommonUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(){
        User loggedInUser=CommonUtil.getLoggedInUser();
        UserResponse userResponse = mapper.map(loggedInUser, UserResponse.class);
        return CommonUtil.createBuilderResponse(userResponse, HttpStatus.OK);
    }

    @PostMapping("/chng-pswd")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChngRequest passwordRequest){
        userService.changePassword(passwordRequest);
        return CommonUtil.createBuilderResponseMessage("Password changed successfully", HttpStatus.OK);
    }

}
