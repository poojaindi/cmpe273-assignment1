package edu.sjsu.cmpe.library.api.resources;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
//import javax.ws.rs.core.CacheControl;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response.ResponseBuilder;
//import javax.ws.rs.core.Request;

//import javax.ws.rs.core.UriInfo;

//import com.sun.research.ws.wadl.Request;
import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.ReviewDto;
import edu.sjsu.cmpe.library.dto.ReviewsDto;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.AuthorsDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;

import java.util.ArrayList;

@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    /** bookRepository instance */
    private final BookRepositoryInterface bookRepository;

    /**
     * BookResource constructor
     * 
     * @param bookRepository
     *            a BookRepository instance
     */
    public BookResource(BookRepositoryInterface bookRepository) {
	this.bookRepository = bookRepository;
    }

    @GET
    @Path("/{isbn}")
    @Timed(name = "view-book")
    public Response getBookByIsbn(@PathParam("isbn") LongParam isbn) {
    	if(bookRepository.getIsbn(isbn.get()))
    	{
    		Book book = bookRepository.getBookByISBN(isbn.get());
    		ArrayList<Review> reviews = book.getReviews();
    		BookDto bookResponse = new BookDto(book);
    		bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(),
    				"GET"));
    		bookResponse.addLink(new LinkDto("update-book",
    				"/books/" + book.getIsbn(), "PUT"));
    		bookResponse.addLink(new LinkDto("delete-book",
    				"/books/" + book.getIsbn(), "DELETE"));
    		bookResponse.addLink(new LinkDto("create-review",
    				"/books/" + book.getIsbn() + "/reviews/", "POST"));
    		if(reviews.size() > 0)
    		{
    			bookResponse.addLink(new LinkDto("view-all-reviews",
    					"/books/" + book.getIsbn() + "/reviews/", "GET"));
    		}
	   
    		return Response.status(200).entity(bookResponse).build();
    	}
    	else
    	{
    		return Response.status(400).entity("Invalid ISBN").build();
    	}
    }

    @POST
    @Timed(name = "create-book")
    public Response createBook(@Valid Book request) {
	// Store the new book in the BookRepository so that we can retrieve it.
	Book savedBook = bookRepository.saveBook(request);

	String location = "/books/" + savedBook.getIsbn();
	//BookDto bookResponse = new BookDto(savedBook);
	LinksDto bookResponse = new LinksDto();
	bookResponse.addLink(new LinkDto("view-book", location, "GET"));
	bookResponse.addLink(new LinkDto("update-book", location, "PUT"));
	bookResponse.addLink(new LinkDto("delete-book", location, "DELETE"));
	bookResponse.addLink(new LinkDto("create-review", location + "/reviews/", "POST"));
	
	return Response.status(201).entity(bookResponse).build();
    }
    
   
    @DELETE
    @Path("/{isbn}")
    @Timed(name = "delete-book")
    public Response deleteBook(@PathParam("isbn") LongParam isbn) {
    	
    	if(bookRepository.getIsbn(isbn.get()))
    	{
       		bookRepository.removeBook(isbn.get());
       		LinksDto links = new LinksDto();
       		links.addLink(new LinkDto("create-book", "/books", "POST"));
    		return Response.status(200).entity(links).build();
    	}
    	else
    	{
    		return Response.status(400).entity("Invalid ISBN").build();
    	}
    	    	    
     }
    
    @PUT
    @Path("/{isbn}")
    @Timed(name = "update-book")
    public Response updateBook(@PathParam("isbn") LongParam isbn, @QueryParam("status") String status)
    {
    	Book book = bookRepository.getBookByISBN(isbn.get());
    	if(status.equalsIgnoreCase("available") || status.equalsIgnoreCase("checked-out")||
    			status.equalsIgnoreCase("in-queue") || status.equalsIgnoreCase("lost"))
    		{
    			book.setStatus(status);
    			ArrayList<Review> reviews = book.getReviews();
    			String location = "/book/" + book.getIsbn();
    			LinksDto bookResponse = new LinksDto();
    			bookResponse.addLink(new LinkDto("view-book", location, "GET"));
    			bookResponse.addLink(new LinkDto("update-book", location, "PUT"));
    			bookResponse.addLink(new LinkDto("delete-book", location, "DELETE"));
    			bookResponse.addLink(new LinkDto("create-review", location + "/reviews/", "POST"));
    			if(reviews.size() > 0){
    				bookResponse.addLink(new LinkDto("view-all-reviews", location + "/reviews", "GET"));
    			}

    			return Response.status(200).entity(bookResponse).build();
    		}
    		else
    		{
    			return Response.status(400).entity("Not a valid status").build();
    		}
    	
    }  
    
    @POST
    @Path("/{isbn}/reviews")
    @Timed(name = "create-book-review")
    public Response createReview(@PathParam("isbn") LongParam isbn,@Valid Review review)
    {
    	if(bookRepository.getIsbn(isbn.get()))
    	{
       		Book book = bookRepository.getBookByISBN(isbn.get());
       		book.addReviews(review);
    		LinksDto links = new LinksDto();
       		links.addLink(new LinkDto("view-review", "/books/" + isbn +"/reviews/" + review.getId(), "GET"));
    		return Response.status(201).entity(links).build();
    	}
    	else
    	{
    		return Response.status(400).entity("Invalid ISBN").build();
    	}
    	
    }
    
    @GET
    @Path("/{isbn}/reviews/{id}")
    @Timed(name = "view-book-review")
    public Response getReviewByID(@PathParam("isbn") LongParam isbn, @PathParam("id") LongParam id) {
    	if(bookRepository.getIsbn(isbn.get()))
    	{
    		Book book = bookRepository.getBookByISBN(isbn.get());
    		Review review = book.getReviewById(id.get());
    		if(review != null){
    			ReviewDto reviewResponse = new ReviewDto(review);
    			reviewResponse.addLink(new LinkDto("view-review", "/books/" + isbn + "/reviews/" + id, "GET"));
    		  
    			return Response.status(200).entity(reviewResponse).build();
    		}
    		else 
    		{
    			return Response.status(400).entity("Invalid id").build();
    		}
    	}
    	else
    	{
    		return Response.status(400).entity("Invalid ISBN").build();
    	}
    }
    
    @GET
    @Path("/{isbn}/reviews")
    @Timed(name = "view-all-reviews")
    public Response getAllReviews(@PathParam("isbn") LongParam isbn) {
    	if(bookRepository.getIsbn(isbn.get()))
    	{
    		Book book = bookRepository.getBookByISBN(isbn.get());
    		ArrayList<Review> reviews = book.getReviews();
    		ReviewsDto reviewResponse = new ReviewsDto(reviews);
    		return Response.status(200).entity(reviewResponse).build();	
    	}
    	else
    	{
    		return Response.status(400).entity("Invalid ISBN").build();
    	}
    }
    
    @GET
    @Path("/{isbn}/authors/{id}")
    @Timed(name = "view-book-author")
    public Response getAuthorByID(@PathParam("isbn") LongParam isbn, @PathParam("id") LongParam id) {
    	if(bookRepository.getIsbn(isbn.get()))
    	{
    		Book book = bookRepository.getBookByISBN(isbn.get());
    		Author author = book.getAuthorById(id.get());
    		if(author != null){
    			AuthorDto authorResponse = new AuthorDto(author);
    			authorResponse.addLink(new LinkDto("view-author", "/books/" + isbn + "/authors/" + id, "GET"));
    		  
    			return Response.status(200).entity(authorResponse).build();
    		}
    		else 
    		{
    			return Response.status(400).entity("Invalid id").build();
    		}
    	}
    	else
    	{
    		return Response.status(400).entity("Invalid ISBN").build();
    	}
    }
    
    @GET
    @Path("/{isbn}/authors")
    @Timed(name = "view-all-authors")
    public Response getAllAuthors(@PathParam("isbn") LongParam isbn) {
    	if(bookRepository.getIsbn(isbn.get()))
    	{
    		Book book = bookRepository.getBookByISBN(isbn.get());
    		ArrayList<Author> authors = book.getAuthors();
    		AuthorsDto authorResponse = new AuthorsDto(authors);
    		return Response.status(200).entity(authorResponse).build();	
    	}
    	else
    	{
    		return Response.status(400).entity("Invalid ISBN").build();
    	}
    }
    
}

