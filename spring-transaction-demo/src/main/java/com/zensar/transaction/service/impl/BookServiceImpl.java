package com.zensar.transaction.service.impl;


import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zensar.transaction.entity.Book;
import com.zensar.transaction.entity.Story;
import com.zensar.transaction.repository.BookRepository;
import com.zensar.transaction.service.BookService;
import com.zensar.transaction.service.StoryService;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private StoryService storyService;
	
	private static final Logger LOG = Logger.getLogger(BookServiceImpl.class.getName());

	@Override
	//@Transactional(isolation=Isolation.DEFAULT, propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class, noRollbackFor=ArithmeticException.class)
	@Transactional(isolation=Isolation.DEFAULT, propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public void saveBook(Book book) {
		LOG.log(Level.INFO, "BEGIN :: saveBook()");
		
		bookRepository.save(book);
		
		// No Rollback even after Arithmetic exception
		int a = 10/0;
		LOG.log(Level.INFO, " Dividing by zero Arithmetic Exception a " + a);
		
		LOG.log(Level.INFO, "END :: saveBook()");
	}
	
	// Example to demonstrate Propogation.REQUIRED & Propogation-REQUIRE_NEW
	
	@Transactional(isolation=Isolation.DEFAULT, propagation=Propagation.REQUIRED)
	public void saveBookAndStory(Book book) {
		bookRepository.save(book);
		
 
		Story story = new Story();
		story.setStoryName("story name1");
		// save story
		storyService.saveStoryRequireNew(story);
	}
	
	@Transactional(isolation=Isolation.DEFAULT, propagation=Propagation.REQUIRED)
	public void saveBookAndStory_Exception(Book book) {
		bookRepository.save(book);
		
 
		Story story = new Story();
		story.setStoryName("story name2");
		// save story
		storyService.saveStoryRequireNew(story);
		
		//thorw exception so that book will not save in DB
		throw new RuntimeException("this exception in throwing intentionally");
	}
	
	@Transactional(isolation=Isolation.DEFAULT, propagation=Propagation.REQUIRED)
	public void saveBookAndStory_Exception2(Book book) {
		bookRepository.save(book);
		
 
		Story story = new Story();
		story.setStoryName("story name3");
		// save story
		storyService.saveStoryRequired(story);
		
		//thorw exception so that book will not save in DB
		throw new RuntimeException("this exception in throwing intentionally");
	}
}
