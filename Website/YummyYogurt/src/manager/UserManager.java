package manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entity.User;
import entity.Yogurt;
import exception.NoSuchRowException;

public final class UserManager {
	
	private final EntityManager manager;
	
	
	public UserManager(final String persistenceUnitName) {
		final EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		this.manager = factory.createEntityManager();
	}
	
	
	public List<User> listAll() {
		final TypedQuery<User> query = manager.createNamedQuery("User.listAll", User.class);
		return query.getResultList();
	}
	
	public User findByID(final int userID) throws NoSuchRowException {
		final User user = manager.find(User.class, userID);
		if(user == null)
			throw new NoSuchRowException();
		return user;
	}
	
	public User findByUsername(final String username) throws NoSuchRowException {
		TypedQuery<User> query = manager.createQuery("select user from User user where user.username = :username", User.class);
		query.setParameter("username", username);
		User user = query.getResultList().get(0);
		
		if(user == null)
			throw new NoSuchRowException();
		return user;
	}
	
	public List<Yogurt> getAllYogurts(final int user) throws NoSuchRowException{
		TypedQuery<Yogurt> query = manager.createQuery("select yogurt from Yogurt yogurt where yogurt.owner = :ID", Yogurt.class);
		System.out.println("Test22");
		query.setParameter("ID", findByID(user));
		System.out.println("Test23");
		List<Yogurt> yogurts = query.getResultList();
		return yogurts;
	}
	
	public void save(final User user) {
		final EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
			final User temp = manager.find(User.class, user.getID());
			if(temp == null)
				manager.persist(user);
			else
				manager.merge(user);
		transaction.commit();
	}
	
	public void delete(final User user) throws NoSuchRowException {
		final EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
			final User temp = this.findByID(user.getID());
			if(temp != null)
				manager.remove(user);
		transaction.commit();
	}
	
	public void close() {
		if(manager != null)
			manager.close();
	}

}

