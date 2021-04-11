package com.nirmalya.ppmtool.repository;

import org.springframework.data.repository.CrudRepository;

import com.nirmalya.ppmtool.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);
    User getById(Long id);
}
