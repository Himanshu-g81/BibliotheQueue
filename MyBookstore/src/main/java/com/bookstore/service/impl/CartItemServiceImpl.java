package com.bookstore.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.domain.Book;
import com.bookstore.domain.BookToCartItem;
import com.bookstore.domain.CartItem;
import com.bookstore.domain.Order;
import com.bookstore.domain.ShoppingCart;
import com.bookstore.domain.User;
import com.bookstore.repository.BookToCartItemRepository;
import com.bookstore.repository.CartItemRepository;
import com.bookstore.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService{
	
	@Autowired
	CartItemRepository cartItemRepository;
	
	@Autowired
	BookToCartItemRepository bookToCartItemRepository;
	@Override
	public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
		// TODO Auto-generated method stub
		return cartItemRepository.findByShoppingCart(shoppingCart);
	}

	@Override
	public CartItem updateCartItem(CartItem cartItem) {
		// TODO Auto-generated method stub
		BigDecimal amount = new BigDecimal(cartItem.getBook().getOurPrice()).multiply(new BigDecimal(cartItem.getQuantity()));
		
		amount = amount.round(new MathContext(4, RoundingMode.HALF_UP));
		cartItem.setSubtotal(amount);
		cartItemRepository.save(cartItem);
		
		return cartItem;
	}

	@Override
	public CartItem addBookToCartItem(Book book, User user, int quantity) {
		// TODO Auto-generated method stub
		List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());
		
		for(CartItem cartItem : cartItemList) {
			if(book.getId() == cartItem.getBook().getId()) {
				int totalQuantity = cartItem.getQuantity() + quantity;
				
				if(totalQuantity > book.getInStockNumber())
					return cartItem;
				
				cartItem.setQuantity(totalQuantity);
				cartItem.setSubtotal(new BigDecimal(book.getOurPrice()).multiply(new BigDecimal(totalQuantity)));
				cartItemRepository.save(cartItem);
				return cartItem;
			}
		}
		
		// Book not found in shopping cart
		CartItem cartItem = new CartItem();
		cartItem.setShoppingCart(user.getShoppingCart());
		cartItem.setBook(book);
		
		cartItem.setQuantity(quantity);
		cartItem.setSubtotal(new BigDecimal(book.getOurPrice()).multiply(new BigDecimal(quantity)));
		
		
		
		cartItem = cartItemRepository.save(cartItem);
		
		BookToCartItem bookToCartItem = new BookToCartItem();
		bookToCartItem.setBook(book);
		bookToCartItem.setCartItem(cartItem);
		
		bookToCartItemRepository.save(bookToCartItem);
		return cartItem;
	}

	@Override
	public CartItem findById(Long cartItemId) {
		// TODO Auto-generated method stub
		Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
		return cartItem.get();
	}

	@Override
	public void removeCartItem(CartItem cartItem) {
		// TODO Auto-generated method stub
		System.out.println("id: " + cartItem.getId());
		bookToCartItemRepository.deleteByCartItem(cartItem);
		cartItemRepository.delete(cartItem);
	}

	@Override
	public void removeById(Long id) {
		// TODO Auto-generated method stub
		cartItemRepository.deleteById(id);
	}

	@Override
	public CartItem save(CartItem cartItem) {
		// TODO Auto-generated method stub
		return cartItemRepository.save(cartItem);
	}

	@Override
	public List<CartItem> findByOrder(Order order) {
		// TODO Auto-generated method stub
		return cartItemRepository.findByOrder(order);
	}

}
