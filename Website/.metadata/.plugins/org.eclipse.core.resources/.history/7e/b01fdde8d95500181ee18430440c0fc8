package entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RatingID implements Serializable {

	private static final long serialVersionUID = -6468692932627379275L;

	@Column(name="BenutzerID", nullable=false)
	private int userID;
	
	@Column(name="YogurtID", nullable=false)
	private int yogurtID;
	
	public RatingID() {}

	public RatingID(int userID, int yogurtID) {
		this.userID = userID;
		this.yogurtID = yogurtID;
	}

	@Override
	public String toString() {
		return "RatingPK [userID=" + userID + ", yogurtID=" + yogurtID + "]";
	}
	
	@Override
	public boolean equals(Object object) {
		if(object == null) return false;
		if(this == object) return true;
		
		if(this.getClass() != object.getClass())
			return false;
		
		RatingID other = (RatingID) object;
		return Objects.equals(this.userID, other.getUserID())
			&& Objects.equals(this.yogurtID, other.getYogurtID());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.userID, this.yogurtID);
	}

	public int getUserID() {
		return userID;
	}

	public int getYogurtID() {
		return yogurtID;
	}
	
}
