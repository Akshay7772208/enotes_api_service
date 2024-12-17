package com.pvt.enotes.controller;

import com.pvt.enotes.dto.LoginRequest;
import com.pvt.enotes.dto.LoginResponse;
import com.pvt.enotes.dto.UserDto;
import com.pvt.enotes.service.UserService;
import com.pvt.enotes.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<?> register(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception {
        String url=CommonUtil.getUrl(request);
        Boolean register= userService.register(userDto,url);

        if(register){
            return CommonUtil.createBuilderResponseMessage("Register success", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Register failed",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        LoginResponse loginResponse=userService.login(loginRequest);
        if(ObjectUtils.isEmpty(loginResponse)){
            return CommonUtil.createErrorResponseMessage("Invalid credentials",HttpStatus.BAD_REQUEST);
        }
        return CommonUtil.createBuilderResponse(loginResponse,HttpStatus.OK);

    }
}


