package com.adminportal.repository;

import org.springframework.data.repository.CrudRepository;

import com.adminportal.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String user);
	User findByEmail(String email);
}
