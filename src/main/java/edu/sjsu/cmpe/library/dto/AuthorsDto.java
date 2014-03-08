package edu.sjsu.cmpe.library.dto;

import edu.sjsu.cmpe.library.domain.Author;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"authors", "links"})
public class AuthorsDto extends LinksDto {
	
	private List<Author> authors = new ArrayList<Author>();
	
	public AuthorsDto(List<Author> Authors) {
		super();
		this.authors = Authors;
	}

    public void addAuthor(Author Author) {
	authors.add(Author);
    }

    /**
     * @return the Authors
     */
    public List<Author> getAuthors() {
	return authors;
    }

    /**
     * @param Authors
     *            the links to set
     */
    public void setAuthors(List<Author> Authors) {
	this.authors = Authors;
    }


}
