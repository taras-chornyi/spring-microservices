package com.chornyi.microservices.service.impl;

import com.chornyi.microservices.domain.User;
import com.chornyi.microservices.repository.UserRepository;
import com.chornyi.microservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

}
