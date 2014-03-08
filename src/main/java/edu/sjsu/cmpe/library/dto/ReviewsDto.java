package edu.sjsu.cmpe.library.dto;

import edu.sjsu.cmpe.library.domain.Review;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"reviews", "links"})
public class ReviewsDto extends LinksDto {
	
	private List<Review> reviews = new ArrayList<Review>();
	
	public ReviewsDto(List<Review> reviews) {
		super();
		this.reviews = reviews;
	}

    public void addReview(Review review) {
	reviews.add(review);
    }

    /**
     * @return the reviews
     */
    public List<Review> getreviews() {
	return reviews;
    }

    /**
     * @param reviews
     *            the links to set
     */
    public void setReviews(List<Review> reviews) {
	this.reviews = reviews;
    }


}
