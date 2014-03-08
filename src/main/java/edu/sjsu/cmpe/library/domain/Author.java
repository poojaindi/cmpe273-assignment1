package edu.sjsu.cmpe.library.domain;

//import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class Author {
	
	private long id;
	@NotEmpty(message = "Author name cannot be empty")
	private String name;
	//static int aid = 1;
		
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
}
