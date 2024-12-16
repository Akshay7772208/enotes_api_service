package com.pvt.enotes.controller;

import com.pvt.enotes.dto.UserDto;
import com.pvt.enotes.service.NotesService;
import com.pvt.enotes.service.UserService;
import com.pvt.enotes.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) throws Exception {
        Boolean register= userService.register(userDto);

        if(register){
            return CommonUtil.createBuilderResponseMessage("Register success", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Register failed",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


