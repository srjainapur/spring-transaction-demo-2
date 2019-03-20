package com.zensar.transaction.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zensar.transaction.entity.Book;
import com.zensar.transaction.entity.Story;
import com.zensar.transaction.service.BookService;
import com.zensar.transaction.service.StoryService;

@RestController
@RequestMapping(value="/transaction")
public class TransactionController {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private StoryService storyService;
	
	private static final Logger LOG = Logger.getLogger(TransactionController.class.getName());
	
	@RequestMapping(value="/book", method=RequestMethod.POST)
	public String saveBook(@RequestBody Book book) {
		LOG.log(Level.INFO, "BEGIN :: saveBook()");
		
		bookService.saveBook(book);
		
		LOG.log(Level.INFO, "END :: saveBook()");
		return "Book Saved successfully";
	}
	
	@RequestMapping(value="/story", method=RequestMethod.POST)
	public String saveStory(@RequestBody Story story) {
		LOG.log(Level.INFO, "BEGIN :: saveStory()");
		storyService.saveStory(story);
		LOG.log(Level.INFO, "END :: saveStory()");
		
		return "Story saved Successfully";
	}
	
	// SaveBook method uses Propogation.REQUIRED & saveStory() method uses Propogation.REQUIRES_NEW
	// In saveBook() method if transaction exist then it uses existing transaction if not create new transaction.
	// In saveStory() method since it's Propagation is REQUIRES_NEW even though there is transaction it will not use it. it will suspend it & create a new transaction.
	// If there is exception in saveBook method only Book transaction is rolled back not story transaction (because it is running in new transaction)
	@RequestMapping(value="/bookAndstory", method=RequestMethod.POST)
	public String saveBookAndStory(@RequestBody Book book) {
		LOG.log(Level.INFO, "BEGIN :: saveStory()");
		bookService.saveBookAndStory(book);
		LOG.log(Level.INFO, "END :: saveStory()");
		
		return "Story saved Successfully";
	}
	
	/* In the above example, we are intentionally throwing RuntimeException , since we have an exception in our code for this transaction i.e saveBook() 
	 * method(something wrong happened), spring will not save the book record in the database. Now, what about the Story entity? It will save in Database or not?
	 * 
	 * Yes, we will have a record for Story entity in the database. Why? Don’t forget we are using propagation = Propagation.REQUIRES_NEW 
	 * with saveBook() which means we have a new transaction for saveStory() which doesn’t depends on previous one i.e saveBook().
	 */
	@RequestMapping(value="/bookAndstoryExcep", method=RequestMethod.POST)
	public String saveBookAndStory_Exception(@RequestBody Book book) {
		LOG.log(Level.INFO, "BEGIN :: saveStory()");
		bookService.saveBookAndStory_Exception(book);
		LOG.log(Level.INFO, "END :: saveStory()");
		
		return "Story saved Successfully";
	}
	
	@RequestMapping(value="/bookAndstoryExcep2", method=RequestMethod.POST)
	public String saveBookAndStory_Exception2(@RequestBody Book book) {
		LOG.log(Level.INFO, "BEGIN :: saveStory()");
		bookService.saveBookAndStory_Exception2(book);
		LOG.log(Level.INFO, "END :: saveStory()");
		
		return "Story saved Successfully";
	}
	
}
