package com.pvt.enotes.service.impl;

import com.pvt.enotes.config.security.CustomUserDetails;
import com.pvt.enotes.dto.EmailRequest;
import com.pvt.enotes.dto.LoginRequest;
import com.pvt.enotes.dto.LoginResponse;
import com.pvt.enotes.dto.UserDto;
import com.pvt.enotes.entity.AccountStatus;
import com.pvt.enotes.entity.Role;
import com.pvt.enotes.entity.User;
import com.pvt.enotes.repository.RoleRepository;
import com.pvt.enotes.repository.UserRepository;
import com.pvt.enotes.service.JwtService;
import com.pvt.enotes.service.UserService;
import com.pvt.enotes.service.EmailService;
import com.pvt.enotes.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    Validation validation;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public Boolean register(UserDto userDto, String url) throws Exception{
        validation.userValidation(userDto);
        User user = mapper.map(userDto, User.class);
        setRole(userDto,user);
        AccountStatus status= AccountStatus.builder().
                isActive(false).
                verificationCode(UUID.randomUUID().toString()).
                build();
        user.setStatus(status);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saveUser=userRepo.save(user);

        if(!ObjectUtils.isEmpty(saveUser)){
            emailSend(saveUser,url);
            return true;
        }
        return false;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        if(authenticate.isAuthenticated()){
            CustomUserDetails costumUserDetails = (CustomUserDetails) authenticate.getPrincipal();

            String token= jwtService.generateToken(costumUserDetails.getUser());

            LoginResponse loginResponse= LoginResponse.builder().
                    user(mapper.map(costumUserDetails.getUser(),UserDto.class)).
                    token(token).
                    build();
            return loginResponse;
        }
        return null;
    }

    private void emailSend(User saveUser, String url) throws Exception{
        String message="Hi,<b>[[username]]</b> "
                + "<br> Your account register sucessfully.<br>"
                +"<br> Click the below link verify & Active your account <br>"
                +"<a href='[[url]]'>Click Here</a> <br><br>"
                +"Thanks,<br>Enotes.com" ;

        message=message.replace("[[username]]", saveUser.getFirstName());
        message=message.replace("[[url]]", url+"/api/v1/home/verify?uid="+saveUser.getId()+"&&code="+saveUser.getStatus().getVerificationCode());


        EmailRequest emailRequest= EmailRequest.builder().
        to(saveUser.getEmail()).
                title("Account creating confirmation").
                subject("Account created success").
                message(message).
                build();
        emailService.sendEmail(emailRequest);
    }

    private void setRole(UserDto userDto,User user) {
        List<Integer> reqRoleId = userDto.getRoles().stream().map(r -> r.getId()).toList();
        List<Role> roles = roleRepo.findAllById(reqRoleId);
        user.setRoles(roles);
    }
}
