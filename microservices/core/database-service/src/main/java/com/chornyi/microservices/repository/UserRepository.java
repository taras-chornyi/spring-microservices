package com.chornyi.microservices.repository;

import com.chornyi.microservices.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
