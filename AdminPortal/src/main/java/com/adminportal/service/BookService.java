package com.adminportal.service;

import java.util.List;

import com.adminportal.domain.Book;

public interface BookService {
	Book save(Book book);
	List<Book> findAll();
	
	void removeOne(Long id);
	
	Book findById(Long id);
}
