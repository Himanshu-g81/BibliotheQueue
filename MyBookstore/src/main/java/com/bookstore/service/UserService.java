package com.bookstore.service;

import java.util.Set;

import com.bookstore.domain.User;
import com.bookstore.domain.UserBilling;
import com.bookstore.domain.UserPayment;
import com.bookstore.domain.UserShipping;
import com.bookstore.domain.security.PasswordResetToken;
import com.bookstore.domain.security.UserRole;

public interface UserService {
	
	PasswordResetToken getPasswordResetToken(final String token);
	
	void createPasswordResetTokenForUser(final User user, final String token);
	
	User findByUsername(String username);
	User findById(Long id);
	User findByEmail(String email);
	
	User createUser(User user, Set<UserRole> userRoles) throws Exception;
	User saveUser(User user);
	
	void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user);
	void setUserDefaultPayment(Long id, User user);
	
	void updateUserShipping(UserShipping userShipping, User user);
	void setUserDefaultShipping(Long id, User user);
}
