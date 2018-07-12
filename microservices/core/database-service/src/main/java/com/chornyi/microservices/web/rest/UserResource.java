package com.chornyi.microservices.web.rest;

import com.chornyi.microservices.domain.User;
import com.chornyi.microservices.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for view and managing Log Level at runtime.
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        log.debug("REST request to get a page of users");
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }
    
}
