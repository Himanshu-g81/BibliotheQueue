package com.bookstore.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.domain.Book;
import com.bookstore.domain.User;
import com.bookstore.service.BookService;
import com.bookstore.service.UserService;

@Controller
public class SearchController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookService bookService;
	@RequestMapping("/searchByCategory")
	public String searchByCategory(
			@RequestParam("category") String category,
			Model model, Principal principal
			) {
		if(principal != null) {
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user", user);
		}
		
		String classActiveCategory = "active" + category;
		classActiveCategory = classActiveCategory.replace("\\s+", "");
		classActiveCategory = classActiveCategory.replace("&", "");
		model.addAttribute(classActiveCategory, true);
		
		List<Book> bookList = bookService.findByCategory(category);
		
		if(bookList.isEmpty()) {
			model.addAttribute("emptyList", true);
			return "bookShelf";
		}
		
		model.addAttribute("bookList", bookList);
		return "bookShelf";
	}
	
	@RequestMapping("/searchBook")
	public String searchBook(
			@ModelAttribute("keyword") String keyword,
			Model model, 
			Principal principal
			) {
		if(principal != null) {
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user", user);
		}
		
		
		List<Book> bookList = bookService.searchByKeyWord(keyword);
		
		if(bookList.isEmpty()) {
			model.addAttribute("emptyList", true);
			return "bookShelf";
		}
		
		model.addAttribute("bookList", bookList);
		
		return "bookShelf";
	}
}
