package entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
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
public class Rating implements Serializable {
	
	private static final long serialVersionUID = 5767727589601503395L;

	@EmbeddedId
	private RatingID ratingID;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="BenutzerID", insertable=false, updatable=false)
	private User user;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="YogurtID", insertable=false, updatable=false)
	private Yogurt yogurt;
	
	@Column(name="Wertung", nullable=false)
	private int rating;
	
	
	public Rating() {}

	public Rating(User user, Yogurt yogurt, int rating) {
		this.ratingID = new RatingID(user.getUserID(), yogurt.getYogurtID());
		this.user = user;
		this.yogurt = yogurt;
		this.rating = rating;
	}

	
	@Override
	public String toString() {
		return "Rating [ratingID=" + ratingID + ", user=" + user + ", yogurt=" + yogurt + ", rating=" + rating + "]";
	}

	@Override
	public boolean equals(Object object) {
		if(object == null) return false;
		if(this == object) return true;
		
		if(this.getClass() != object.getClass())
			return false;
		
		Rating other = (Rating) object;
		return Objects.equals(this.ratingID, other.getRatingID())
			&& Objects.equals(this.user, other.getUser())
			&& Objects.equals(this.yogurt, other.getYogurt())
			&& Objects.equals(this.rating, other.getRating());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.ratingID, this.user, this.yogurt, this.rating);
	}
	

	public RatingID getRatingID() {
		return ratingID;
	}

	public User getUser() {
		return user;
	}

	public Yogurt getYogurt() {
		return yogurt;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
}
