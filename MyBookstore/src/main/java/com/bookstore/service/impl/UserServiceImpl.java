package com.bookstore.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.domain.ShoppingCart;
import com.bookstore.domain.User;
import com.bookstore.domain.UserBilling;
import com.bookstore.domain.UserPayment;
import com.bookstore.domain.UserShipping;
import com.bookstore.domain.security.PasswordResetToken;
import com.bookstore.domain.security.UserRole;
import com.bookstore.repository.PasswordResetRepository;
import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.UserPaymentRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.repository.UserShippingRepository;
import com.bookstore.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserPaymentRepository userPaymentRepository;
	@Autowired
	private PasswordResetRepository passwordResetTokenRepository;
	@Autowired
	private UserShippingRepository userShippingRepository;
	@Override
	public PasswordResetToken getPasswordResetToken(final String token) {
		// TODO Auto-generated method stub
		return passwordResetTokenRepository.findByToken(token);
	}

	@Override
	public void createPasswordResetTokenForUser(final User user, final String token) {
		// TODO Auto-generated method stub
		final PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordResetTokenRepository.save(myToken);
	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}

	@Override
//	@Transactional
	public User createUser(User user, Set<UserRole> userRoles) throws Exception{
		// TODO Auto-generated method stub
		User localUser = userRepository.findByUsername(user.getUsername());
		
		if(localUser != null) {
			LOG.info("{} already exists.", user.getUsername());
		}else {
			for (UserRole ur: userRoles) {
				roleRepository.save(ur.getRole());
			}
			
			user.getUserRoles().addAll(userRoles);
			
			ShoppingCart shoppingCart = new ShoppingCart();
			
			user.setShoppingCart(shoppingCart);
			shoppingCart.setUser(user);
			
			user.setUserShippingList(new ArrayList<UserShipping>());
			user.setUserPaymentList(new ArrayList<UserPayment>());
			
			localUser = userRepository.save(user);
		}
		return localUser;
	}

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user) {
		// TODO Auto-generated method stub
		
		userPayment.setUser(user);
		userPayment.setUserBilling(userBilling);
		userPayment.setDefaultPayment(true);
		
		userBilling.setUserPayment(userPayment);
		user.getUserPaymentList().add(userPayment);
		this.saveUser(user);
		
		System.out.println("Size: " + user.getUserPaymentList().size());
	}

	@Override
	public void setUserDefaultPayment(Long id, User user) {
		// TODO Auto-generated method stub
		List<UserPayment> userPaymentList = (List<UserPayment>) userPaymentRepository.findAll();
		
		for(UserPayment userPayment: userPaymentList) {
			if(userPayment.getId() == id) {
				userPayment.setDefaultPayment(true);
			} else {
				userPayment.setDefaultPayment(false);
			}
			userPaymentRepository.save(userPayment);
		}
	}

	@Override
	public void updateUserShipping(UserShipping userShipping, User user) {
		// TODO Auto-generated method stub
		userShipping.setUser(user);
		userShipping.setUserShippingDefault(true);
		user.getUserShippingList().add(userShipping);
		this.saveUser(user);
	}

	@Override
	public void setUserDefaultShipping(Long id, User user) {
		// TODO Auto-generated method stub
		List<UserShipping> userShippingList = (List<UserShipping>) userShippingRepository.findAll();
		
		for(UserShipping userShipping: userShippingList) {
			if(userShipping.getId() == id) {
				userShipping.setUserShippingDefault(true);
			} else {
				userShipping.setUserShippingDefault(false);
			}
			userShippingRepository.save(userShipping);
		}
	}

	@Override
	public User findById(Long id) {
		// TODO Auto-generated method stub
		Optional<User> user = userRepository.findById(id);
		return user.get();
	}
	
}
