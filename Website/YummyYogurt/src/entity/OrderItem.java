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


/*create table Bestellposition(
    BestellungID  int,
    YogurtID      int,
    primary key(BestellungID,YogurtID),
    
    Menge         int not null,
    constraint checkMenge check(Menge > 0)
);*/


@Entity
@Table(name="Bestellposition")
@NamedQuery(name="OrderItem.listAll", query="select i from OrderItem i")
public final class OrderItem implements Serializable {
	
	private static final long serialVersionUID = -632106431641713377L;

	@EmbeddedId
	private OrderItemID orderItemID;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="BestellungID", insertable=false, updatable=false)
	private Order order;
	
	@ManyToOne
	@JoinColumn(name="YogurtID", insertable=false, updatable=false)
	private Yogurt yogurt;
	
	@Column(name="Menge", nullable=false)
	private int amount;
	
	
	public OrderItem() {}

	public OrderItem(final Order order, final Yogurt yogurt, final int amount) {
		Objects.requireNonNull(order, "order is null");
		Objects.requireNonNull(yogurt, "yogurt is null");
		
		this.orderItemID = new OrderItemID(order.getID(), yogurt.getID());
		this.order = order;
		this.yogurt = yogurt;
		this.amount = amount;
	}
	
	
	@Override
	public String toString() {
		return "OrderItem [orderItemID=" + orderItemID + ", order=" + order + ", yogurt=" + yogurt + ", amount="
				+ amount + "]";
	}

	@Override
	public boolean equals(Object object) {
		if(object == null) return false;
		if(this == object) return true;
		
		if(this.getClass() != object.getClass())
			return false;
		
		OrderItem other = (OrderItem) object;
		return Objects.equals(this.orderItemID, other.getID())
			&& Objects.equals(this.order, other.getOrder())
			&& Objects.equals(this.yogurt, other.getYogurt())
			&& Objects.equals(this.amount, other.getAmount());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.orderItemID, this.order, this.yogurt, this.amount);
	}

	
	public OrderItemID getID() {
		return orderItemID;
	}

	public Order getOrder() {
		return order;
	}

	public Yogurt getYogurt() {
		return yogurt;
	}

	public int getAmount() {
		return amount;
	}

}
