package com.pvt.enotes.service.impl;

import com.pvt.enotes.entity.AccountStatus;
import com.pvt.enotes.entity.User;
import com.pvt.enotes.exception.ResourceNotFoundException;
import com.pvt.enotes.exception.SuccessException;
import com.pvt.enotes.repository.UserRepository;
import com.pvt.enotes.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private UserRepository userRepo;


    @Override
    public Boolean verifyAccount(Integer userId, String verificationCode) throws Exception{
        User user=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("Invalid user"));
        if(user.getStatus().getVerificationCode()==null)
        {
            throw new SuccessException("Account alreday verified");
        }

        if(user.getStatus().getVerificationCode().equals(verificationCode)){
            AccountStatus status = user.getStatus();
            status.setIsActive(true);
            status.setVerificationCode(null);

            userRepo.save(user);
            return true;
        }
        return false;
    }
}
