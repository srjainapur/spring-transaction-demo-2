package com.zensar.transaction.service;

import org.springframework.stereotype.Service;

import com.zensar.transaction.entity.Book;

@Service
public interface BookService {
	public void saveBook(Book book);
	public void saveBookAndStory(Book book);
	public void saveBookAndStory_Exception(Book book);
	public void saveBookAndStory_Exception2(Book book);
}
