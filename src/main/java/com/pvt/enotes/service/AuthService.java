package com.pvt.enotes.service;

import com.pvt.enotes.dto.LoginRequest;
import com.pvt.enotes.dto.LoginResponse;
import com.pvt.enotes.dto.UserRequest;

public interface AuthService {

    public  Boolean register(UserRequest userRequest, String url) throws Exception;

    LoginResponse login(LoginRequest loginRequest);
}
