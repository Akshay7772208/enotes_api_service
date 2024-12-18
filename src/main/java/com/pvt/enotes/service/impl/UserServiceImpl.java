package com.pvt.enotes.service.impl;

import com.pvt.enotes.dto.PasswordChngRequest;
import com.pvt.enotes.entity.User;
import com.pvt.enotes.repository.UserRepository;
import com.pvt.enotes.service.UserService;
import com.pvt.enotes.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepo;

    @Override
    public void changePassword(PasswordChngRequest passwordRequest) {
        User loggedInUser= CommonUtil.getLoggedInUser();
        if(!passwordEncoder.matches(passwordRequest.getOldPassword(),loggedInUser.getPassword())){
            throw new IllegalArgumentException("Your old password is incorrect");
        }
        String encodePassword=passwordEncoder.encode(passwordRequest.getNewPassword());
        loggedInUser.setPassword(encodePassword);
        userRepo.save(loggedInUser);

    }


}
