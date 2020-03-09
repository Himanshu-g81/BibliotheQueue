package com.bookstore.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.domain.Book;
import com.bookstore.repository.BookRepository;
import com.bookstore.service.BookService;

@Service
public class BookServiceImpl implements BookService{
	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public List<Book> findAll() {
		// TODO Auto-generated method stub
		List<Book> bookList = (List<Book>) bookRepository.findAll();
		List<Book> activeList = new ArrayList<>();
		
		for(Book book : bookList) {
			if(book.isActive()) {
				activeList.add(book);
			}
		}
		return activeList;
	}

	@Override
	public Book findById(Long id) {
		// TODO Auto-generated method stub
		Optional<Book> book = bookRepository.findById(id);
		return book.get();
	}

	@Override
	public List<Book> findByCategory(String category) {
		// TODO Auto-generated method stub
		List<Book> bookList = bookRepository.findByCategory(category);
		
		List<Book> activeBookList = new ArrayList<>();
		for(Book book : bookList) {
			if(book.isActive()) {
				activeBookList.add(book);
			}
		}
		return activeBookList;
	}

	@Override
	public List<Book> searchByKeyWord(String key) {
		
		List<Book> bookList = bookRepository.findByTitleContaining(key);
		List<Book> activeList = new ArrayList<>();
		
		for(Book book : bookList) {
			if(book.isActive()) {
				activeList.add(book);
			}
		}
		return activeList;
	//	return activeList;
	}
}
