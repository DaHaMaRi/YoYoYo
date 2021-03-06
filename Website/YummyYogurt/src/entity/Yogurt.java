package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;


/*create table Yogurt(
	    ID              int          primary key,
	    Name            varchar(64)  unique not null,
	    BenutzerID      int          not null,
	    Veroeffentlicht  varchar(8)   not null,
);*/

@Entity
@NamedQueries({
	@NamedQuery(name="Yogurt.listAll", query="select y from Yogurt y"),
	@NamedQuery(name="Yogurt.findByName", query="select y from Yogurt y where y.name = :name")
})
public final class Yogurt implements Serializable {
	
	private static final long serialVersionUID = -6134440095691454868L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="YogurtGenerator")
	@SequenceGenerator(name="YogurtGenerator", 
		sequenceName="YogurtSequence", allocationSize=1)
	@Column(name="ID")
	private int yogurtID;
	
	@Column(name="Name", unique=true, nullable=false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name="BenutzerID", nullable=false)
	private User owner;
	
	@Column(name="Veroeffentlicht", nullable=false)
	private String visible;
	
	@ManyToMany
	@JoinTable(name="Zutatenliste", joinColumns={@JoinColumn(name="YogurtID")}, 
		inverseJoinColumns={@JoinColumn(name="ZutatenID")})
	private List<Ingredient> recipe;
	
	
	public Yogurt() {}
	
	public Yogurt(final String name, final User owner, final boolean visibility) {
		Objects.requireNonNull(owner, "owner is null");
		
		this.name = name;
		this.owner = owner;
		this.visible = Boolean.toString(visibility);
		this.recipe = new ArrayList<>();
	}

	public Yogurt(final int yogurtID, final String name, final User owner, final boolean visibility) {
		Objects.requireNonNull(owner, "owner is null");
		
		this.yogurtID = yogurtID;
		this.name = name;
		this.owner = owner;
		this.visible = Boolean.toString(visibility);
		this.recipe = new ArrayList<>();
	}
	
	
	@Override
	public String toString() {
		return "Yogurt [yogurtID=" + yogurtID + ", name=" + name + ", visibility=" + visible + "\n\towner=" + owner
				+ "\n\trecipe=" + recipe + "]";
	}

	@Override
	public boolean equals(Object object) {
		if(object == null) return false;
		if(this == object) return true;
		
		if(this.getClass() != object.getClass())
			return false;
		
		Yogurt other = (Yogurt) object;
		return Objects.equals(this.yogurtID, other.getID())
			&& Objects.equals(this.name, other.getName())
			&& Objects.equals(this.owner, other.getOwner());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.yogurtID, this.name, this.owner);
	}
	

	public int getID() {
		return yogurtID;
	}

	public String getName() {
		return name;
	}

	public boolean isVisible() {
		Boolean visible = new Boolean(this.visible);
		return visible.booleanValue();
	}

	public void setVisibility(final boolean visible) {
		this.visible = Boolean.toString(visible);
	}

	public User getOwner() {
		return owner;
	}
	
	public void addToRecipe(final Ingredient ingredient) {
		Objects.requireNonNull(ingredient);
		this.recipe.add(ingredient);
	}

	public List<Ingredient> getRecipe() {
		return recipe;
	}

}
