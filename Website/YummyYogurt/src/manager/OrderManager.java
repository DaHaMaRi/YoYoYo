package manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Order;
import exception.NoSuchRowException;

public final class OrderManager {
	
	private final EntityManager manager;
	
	
	public OrderManager(final String persistenceUnitName) {
		final EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		this.manager = factory.createEntityManager();
	}
	
	
	public List<Order> listAll() {
		final TypedQuery<Order> query = manager.createNamedQuery("Order.listAll", Order.class);
		return query.getResultList();
	}
	
	public Order findByID(final int orderID) throws NoSuchRowException {
		final Order order = manager.find(Order.class, orderID);
		if(order == null)
			throw new NoSuchRowException();
		return order;
	}
	
	public void save(final Order order) {
		final EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
			final Order temp = manager.find(Order.class, order.getID());
			if(temp == null)
				manager.persist(order);
			else
				manager.merge(order);
		transaction.commit();
	}
	
	public void delete(final Order order) throws NoSuchRowException {
		final EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
			final Order temp = this.findByID(order.getID());
			if(temp != null)
				manager.remove(order);
		transaction.commit();
	}
	
	public void close() {
		if(manager != null)
			manager.close();
	}
	
}
