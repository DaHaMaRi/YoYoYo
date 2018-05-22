package manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Ingredient;
import exception.NoSuchRowException;

public class IngredientManager {
	
	private EntityManager manager;
	
	
	public IngredientManager(String persistenceUnitName) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		this.manager = factory.createEntityManager();
	}
	
	
	public List<Ingredient> listAll() {
		TypedQuery<Ingredient> query = manager.createNamedQuery("Ingredient.listAll", Ingredient.class);
		return query.getResultList();
	}
	
	public Ingredient findByID(int ingredientID) throws NoSuchRowException {
		Ingredient ingredient = manager.find(Ingredient.class, ingredientID);
		if(ingredient == null)
			throw new NoSuchRowException();
		return ingredient;
	}
	
	public void save(Ingredient ingredient) {
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
			Ingredient temp = manager.find(Ingredient.class, ingredient.getIngredientID());
			if(temp == null)
				manager.persist(ingredient);
			else
				manager.merge(ingredient);
		transaction.commit();
	}
	
	public void delete(Ingredient ingredient) throws NoSuchRowException {
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
			Ingredient temp = this.findByID(ingredient.getIngredientID());
			if(temp != null)
				manager.remove(ingredient);
		transaction.commit();
	}
	
	public void close() {
		if(manager != null)
			manager.close();
	}
	
}
