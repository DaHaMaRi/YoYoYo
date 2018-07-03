package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/*create table Bestellung(
	    ID           int  primary key,
	    BenutzerID   int  not null,
	    Gesamtpreis  int  not null,
	    Bestelldatum timestamp not null,
	    constraint checkGesamtpreis check(Gesamtpreis > 0)
);*/

@Entity
@Table(name="Bestellung")
@NamedQuery(name="Order.listAll", query="select o from Order o")
public final class Order implements Serializable {
	
	private static final long serialVersionUID = 1019596392287525485L;
	private static int numberOfOrders = 20;

	@Id
	@Column(name="ID")
	private int orderID;
	
	@ManyToOne
	@JoinColumn(name="BenutzerID", nullable=false)
	private User purchaser;
	
	@Column(name="Gesamtpreis", nullable=false)
	private int totalPrice;
	
	@Column(name="Bestelldatum", nullable=false)
	private LocalDateTime orderdate;

	
	public Order() {}

	public Order(final User purchaser, final int totalPrice, final LocalDateTime orderdate) {
		Objects.requireNonNull(purchaser, "purchaser is null");
		Objects.requireNonNull(orderdate, "oderdate is null");
		
		this.orderID = numberOfOrders;
		this.purchaser = purchaser;
		this.totalPrice = totalPrice;
		this.orderdate = orderdate;
		numberOfOrders++;
	}

	
	@Override
	public String toString() {
		return "Order [orderID=" + orderID + ", user=" + purchaser + ", totalPrice=" + totalPrice + ", orderdate="
				+ orderdate + "]";
	}
	
	@Override
	public boolean equals(Object object) {
		if(object == null) return false;
		if(this == object) return true;
		
		if(this.getClass() != object.getClass())
			return false;
		
		Order other = (Order) object;
		return Objects.equals(this.orderID, other.getID())
			&& Objects.equals(this.purchaser, other.getPurchaser())
			&& Objects.equals(this.totalPrice, other.getTotalPrice())
			&& Objects.equals(this.orderdate, other.getOrderdate());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.orderID, this.purchaser, this.totalPrice, this.orderdate);
	}
	

	public int getID() {
		return orderID;
	}

	public User getPurchaser() {
		return purchaser;
	}

	public int getTotalPrice() {
		return totalPrice;
	}
	
	public LocalDateTime getOrderdate() {
		return orderdate;
	}

}
