package com.chornyi.microservices.service;

import com.chornyi.microservices.domain.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

}
