package entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/*create table Zutat(
	    ID    int           primary key,
	    Name  varchar(64)   unique not null,
	    Vegan varchar(8)    not null,
	    Haram varchar(8)    not null,
	    ZutatenkategorieID  int      not null,
	    constraint checkVegan check(Vegan in ('true', 'false')),
	    constraint checkHaram check(Haram in ('true', 'false'))
);*/

@Entity
@Table(name="Zutat")
@NamedQuery(name="Ingredient.listAll", query="select i from Ingredient i")
public class Ingredient {
	
	@Id
	@Column(name="ID")
	private int ingredientID;
	
	@Column(unique=true, nullable=false)
	private String name;
	
	@Column(name="Vegan", nullable=false)
	private String isVegan;
	
	@Column(name="Haram", nullable=false)
	private String isHaram;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ZutatenkategorieID", nullable=false)
	private Category category;
	
	public Ingredient() {}

	public Ingredient(int ingredientID, String name, 
			String isVegan, String isHaram, Category category) {
		this.ingredientID = ingredientID;
		this.name = name;
		this.isVegan = isVegan;
		this.isHaram = isHaram;
		this.category = category;
	}

	@Override
	public String toString() {
		return "Ingredient [ingredientID=" + ingredientID + ", name=" + name + ", isVegan=" + isVegan + ", isHaram="
				+ isHaram + "\n\tcategory=" + category + "]";
	}

	public int getIngredientID() {
		return ingredientID;
	}

	public void setIngredientID(int ingredientID) {
		this.ingredientID = ingredientID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isVegan() {
		Boolean isVegan = new Boolean(this.isVegan);
		return isVegan.booleanValue();
	}

	public void setIsVegan(boolean isVegan) {
		Boolean temp = new Boolean(isVegan);
		this.isVegan = temp.toString();
	}

	public boolean isHaram() {
		Boolean isHaram = new Boolean(this.isHaram);
		return isHaram.booleanValue();
	}

	public void setIsHaram(boolean isHaram) {
		Boolean temp = new Boolean(isHaram);
		this.isHaram = temp.toString();
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
}
