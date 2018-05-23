package manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Yogurt;
import exception.NoSuchRowException;

public final class YogurtManager {
	
	private final EntityManager manager;
	
	
	public YogurtManager(final String persistenceUnitName) {
		final EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		this.manager = factory.createEntityManager();
	}
	
	
	public List<Yogurt> listAll() {
		final TypedQuery<Yogurt> query = manager.createNamedQuery("Yogurt.listAll", Yogurt.class);
		return query.getResultList();
	}
	
	public Yogurt findByID(final int yogurtID) throws NoSuchRowException {
		final Yogurt yogurt = manager.find(Yogurt.class, yogurtID);
		if(yogurt == null)
			throw new NoSuchRowException();
		return yogurt;
	}
	
	public void save(final Yogurt yogurt) {
		final EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
			final Yogurt temp = manager.find(Yogurt.class, yogurt.getID());
			if(temp == null)
				manager.persist(yogurt);
			else
				manager.merge(yogurt);
		transaction.commit();
	}
	
	public void delete(final Yogurt yogurt) throws NoSuchRowException {
		final EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
			final Yogurt temp = this.findByID(yogurt.getID());
			if(temp != null)
				manager.remove(yogurt);
		transaction.commit();
	}
	
	public void close() {
		if(manager != null)
			manager.close();
	}
	
}
