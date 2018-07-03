package manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entity.Rating;
import entity.RatingID;
import exception.NoSuchRowException;

public final class RatingManager {
	
	private final EntityManager manager;
	
	
	public RatingManager(final EntityManagerFactory factory) {
		this.manager = factory.createEntityManager();
	}
	
	
	public List<Rating> listAll() {
		final TypedQuery<Rating> query = manager.createNamedQuery("Rating.listAll", Rating.class);
		return query.getResultList();
	}
	
	public Rating findByID(final RatingID ratingID) throws NoSuchRowException {
		final Rating rating = manager.find(Rating.class, ratingID);
		if(rating == null)
			throw new NoSuchRowException();
		return rating;
	}
	
	public List<Rating> findByYogurt(final String yogurtname) {
		TypedQuery<Rating> query = manager.createQuery("select r from Rating r where r.yogurt.name = :yogurtname", Rating.class);
		query.setParameter("yogurtname", yogurtname);
		return query.getResultList();
	}
	
	/*
	public double getAvgForUser(int userid) {
		Query query = manager.createNativeQuery("select avg(wertung) from bewertung WHERE benutzerid=1");
		Object obj = query.getSingleResult();
		if (obj == null)
			lFEhlet
			
		obj
	}*/
	
	public void save(final Rating rating) {
		final EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
			final Rating temp = manager.find(Rating.class, rating.getID());
			if(temp == null)
				manager.persist(rating);
			else
				manager.merge(rating);
		transaction.commit();
	}
	
	public void delete(final Rating rating) throws NoSuchRowException {
		final EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
			final Rating temp = this.findByID(rating.getID());
			if(temp != null)
				manager.remove(rating);
		transaction.commit();
	}
	
	public void close() {
		if(manager != null)
			manager.close();
	}
	
}
