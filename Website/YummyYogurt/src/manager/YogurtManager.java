package manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Yogurt;
import exception.NoSuchRowException;

public class YogurtManager {
	
	private EntityManager manager;
	
	
	public YogurtManager(String persistenceUnitName) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		this.manager = factory.createEntityManager();
	}
	
	
	public List<Yogurt> listAll() {
		TypedQuery<Yogurt> query = manager.createNamedQuery("Yogurt.listAll", Yogurt.class);
		return query.getResultList();
	}
	
	public Yogurt findByID(int yogurtID) throws NoSuchRowException {
		Yogurt yogurt = manager.find(Yogurt.class, yogurtID);
		if(yogurt == null)
			throw new NoSuchRowException();
		return yogurt;
	}
	
	public void save(Yogurt yogurt) {
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
			Yogurt temp = manager.find(Yogurt.class, yogurt.getYogurtID());
			if(temp == null)
				manager.persist(yogurt);
			else
				manager.merge(yogurt);
		transaction.commit();
	}
	
	public void delete(Yogurt yogurt) throws NoSuchRowException {
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
			Yogurt temp = this.findByID(yogurt.getYogurtID());
			if(temp != null)
				manager.remove(yogurt);
		transaction.commit();
	}
	
	public void close() {
		if(manager != null)
			manager.close();
	}
	
}
