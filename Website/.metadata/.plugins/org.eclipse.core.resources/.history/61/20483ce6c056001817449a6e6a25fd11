package manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Address;
import exception.NoSuchRowException;

public class AddressManager {
	
	private EntityManager manager;
	
	public AddressManager(String persistenceUnitName) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		this.manager = factory.createEntityManager();
	}
	
	public List<Address> listAll() {
		TypedQuery<Address> query = manager.createNamedQuery("Address.listAll", Address.class);
		return query.getResultList();
	}
	
	public Address findByID(int addressID) throws NoSuchRowException {
		Address address = manager.find(Address.class, addressID);
		if(address == null)
			throw new NoSuchRowException();
		return address;
	}
	
	public void save(Address address) {
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
			Address temp = manager.find(Address.class, address.getAddressID());
			if(temp == null)
				manager.persist(address);
			else
				manager.merge(address);
		transaction.commit();
	}
	
	public void delete(Address address) throws NoSuchRowException {
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
			Address temp = this.findByID(address.getAddressID());
			if(temp != null)
				manager.remove(address);
		transaction.commit();
	}
	
	public void close() {
		if(manager != null)
			manager.close();
	}

}
