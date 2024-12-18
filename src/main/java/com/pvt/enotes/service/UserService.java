package com.pvt.enotes.service;

import com.pvt.enotes.dto.PasswordChngRequest;

public interface UserService {

    public void changePassword(PasswordChngRequest passwordRequest);
}
