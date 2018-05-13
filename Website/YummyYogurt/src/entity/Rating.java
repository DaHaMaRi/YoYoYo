package entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/*create table Bewertung(
	    BenutzerID   int,
	    YogurtID     int,
	    primary key(BenutzerID,YogurtID),
	    
	    Wertung int not null,
	    constraint checkWertung check(Wertung >= 1 and Wertung <= 5)
);*/

@Entity
@Table(name="Bewertung")
@NamedQuery(name="Rating.listAll", query="select r from Rating r")
public final class Rating implements Serializable {
	
	private static final long serialVersionUID = 5767727589601503395L;

	@EmbeddedId
	private RatingID ratingID;
	
	@ManyToOne
	@JoinColumn(name="BenutzerID", insertable=false, updatable=false)
	private User evaluator;
	
	@ManyToOne
	@JoinColumn(name="YogurtID", insertable=false, updatable=false)
	private Yogurt yogurt;
	
	@Column(name="Wertung", nullable=false)
	private int rating;
	
	
	public Rating() {}

	public Rating(final User evaluator, final Yogurt yogurt, final int rating) {
		Objects.requireNonNull(evaluator, "evaluator is null");
		Objects.requireNonNull(yogurt, "yogurt is null");
		
		this.ratingID = new RatingID(evaluator.getUserID(), yogurt.getYogurtID());
		this.evaluator = evaluator;
		this.yogurt = yogurt;
		this.rating = rating;
	}

	
	@Override
	public String toString() {
		return "Rating [ratingID=" + ratingID + ", user=" + evaluator + ", yogurt=" + yogurt + ", rating=" + rating + "]";
	}

	@Override
	public boolean equals(Object object) {
		if(object == null) return false;
		if(this == object) return true;
		
		if(this.getClass() != object.getClass())
			return false;
		
		Rating other = (Rating) object;
		return Objects.equals(this.ratingID, other.getRatingID())
			&& Objects.equals(this.evaluator, other.getEvaluator())
			&& Objects.equals(this.yogurt, other.getYogurt())
			&& Objects.equals(this.rating, other.getRating());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.ratingID, this.evaluator, this.yogurt, this.rating);
	}
	

	public RatingID getRatingID() {
		return ratingID;
	}

	public User getEvaluator() {
		return evaluator;
	}

	public Yogurt getYogurt() {
		return yogurt;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(final int rating) {
		this.rating = rating;
	}
	
}
