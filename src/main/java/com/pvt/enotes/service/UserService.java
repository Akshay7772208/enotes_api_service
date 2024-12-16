package com.pvt.enotes.service;

import com.pvt.enotes.dto.UserDto;

public interface UserService {

    public  Boolean register(UserDto userDto, String url) throws Exception;
}
