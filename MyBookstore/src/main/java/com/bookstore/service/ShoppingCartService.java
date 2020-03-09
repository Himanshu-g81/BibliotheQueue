package com.bookstore.service;

import org.springframework.stereotype.Service;

import com.bookstore.domain.ShoppingCart;

public interface ShoppingCartService {
	ShoppingCart updateShoppingCart(ShoppingCart shoppingCart);
	void clearShoppingCart(ShoppingCart shoppingCart);
}
