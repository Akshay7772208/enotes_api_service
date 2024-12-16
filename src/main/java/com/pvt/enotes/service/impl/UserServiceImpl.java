package com.pvt.enotes.service.impl;

import com.pvt.enotes.dto.UserDto;
import com.pvt.enotes.entity.Role;
import com.pvt.enotes.entity.User;
import com.pvt.enotes.repository.RoleRepository;
import com.pvt.enotes.repository.UserRepository;
import com.pvt.enotes.service.UserService;
import com.pvt.enotes.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Stream;

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

    @Override
    public Boolean register(UserDto userDto) throws Exception{
        validation.userValidation(userDto);
        User user = mapper.map(userDto, User.class);
        setRole(userDto,user);
        User saveUser=userRepo.save(user);

        if(!ObjectUtils.isEmpty(saveUser)){
            return true;
        }
        return false;
    }

    private void setRole(UserDto userDto,User user) {
        List<Integer> reqRoleId = userDto.getRoles().stream().map(r -> r.getId()).toList();
        List<Role> roles = roleRepo.findAllById(reqRoleId);
        user.setRoles(roles);
    }
}
