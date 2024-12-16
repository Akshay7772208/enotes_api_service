package com.pvt.enotes.controller;

import com.pvt.enotes.dto.TodoDto;
import com.pvt.enotes.service.HomeService;
import com.pvt.enotes.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid,@RequestParam String code) throws Exception {
        Boolean verifyAccount= homeService.verifyAccount(uid,code);
        if(verifyAccount){
            return CommonUtil.createBuilderResponseMessage("Verfication successful", HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("Invalid verification link", HttpStatus.BAD_REQUEST);
    }
}
