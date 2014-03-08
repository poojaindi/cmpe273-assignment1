package edu.sjsu.cmpe.library.domain;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
//import javax.validation.Valid;

//import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
public class Review {
	
	private long id;
	@NotNull
	@Range(min = 1, max = 5)
	private int rating;
	@NotEmpty(message = "Review comment cannot be empty")
	private String comment;
	//private Book book;
	
	public long getId()
	{
		return this.id;
	}
	
	public void setId(long id)
	{
		this.id = id;
	}
	
	
	public int getRating()
	{
		return this.rating;
	}
	
	public void setRating(int rating)
	{
	/*	if( rating < 1 || rating > 5)
		{
			if(rating < 1 )
				this.rating = 1; //setting rating to 1 if it is invalid
			else
				this.rating = 5; //setting rating to 5 if it is invalid
		}
		else
		{*/
			this.rating = rating;
		
	}
	
	public String getComment()
	{
		return this.comment;
	}

	public void setComment (String comment)
	{
		this.comment = comment;
	}
	
		
}
