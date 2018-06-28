package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/*create table Benutzer(
	    ID              int           primary key,
	    Vorname         varchar(64)   not null,
	    Nachname        varchar(64)   not null,
	    Benutzername    varchar(64)   not null unique,
	    Email           varchar(64)   not null unique,
	    Geburtsdatum    date          not null,
	    AdressID        int           not null,
	    Passwort        varchar(1024) not null,
	    Beitrittsdatum  date          not null,
	    constraint checkDatum check(Geburtsdatum < Beitrittsdatum)
);*/

@Entity
@Table(name="Benutzer")
@NamedQuery(name="User.listAll", query="select u from User u")
public final class User implements Serializable {
	
	private static final long serialVersionUID = 4102446862783691913L;
	private static int numberOfUsers = 20;

	
	@Id
	@Column(name="ID")
	private int userID;
	
	@Column(name="Vorname", nullable=false)
	private String firstname;
	
	@Column(name="Nachname", nullable=false)
	private String familyname;
	
	@Column(name="Benutzername", unique=true, nullable=false)
	private String username;

	@Column(name="Email", unique=true, nullable=false)
	private String email;
	
	@Column(name="Geburtsdatum", nullable=false)
	private LocalDate birthday;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="AdressID", nullable=false)
	private Address address;
	
	@Column(name="Beitrittsdatum", nullable=false)
	private LocalDate accessiondate;
	
	@Column(name="Passwort", nullable=false)
	private String password;
	
	
	public User() {}

	public User(final String firstname, final String familyname, final String username, final String email, 
			    final LocalDate birthday, final Address address, final LocalDate accessiondate, final String password) {
		Objects.requireNonNull(birthday, "birthday is null");
		Objects.requireNonNull(address, "address is null");
		Objects.requireNonNull(accessiondate, "accessiondate is null");
		
		this.userID = numberOfUsers;
		this.firstname = firstname;
		this.familyname = familyname;
		this.username = username;
		this.email = email;
		this.birthday = birthday;
		this.address = address;
		this.accessiondate = accessiondate;
		this.password = password;
		numberOfUsers++;
	}
	

	@Override
	public String toString() {
		return "User [userID=" + userID + ", firstname=" + firstname + ", familyname=" + familyname + ", username="
				+ username + ", email=" + email + ", birthday=" + birthday + "\n\taddress=" + address + ", accessiondate="
				+ accessiondate + ", password="+password+"]";
	}

	@Override
	public boolean equals(Object object) {
		if(object == null) return false;
		if(this == object) return true;
		
		if(this.getClass() != object.getClass())
			return false;
		
		User other = (User) object;
		return Objects.equals(this.userID, other.getID())
			&& Objects.equals(this.accessiondate, other.getAccessiondate());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.userID, this.accessiondate);
	}
	

	public int getID() {
		return userID;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(final String firstname) {
		this.firstname = firstname;
	}

	public String getFamilyname() {
		return familyname;
	}

	public void setFamilyname(final String familyname) {
		this.familyname = familyname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(final Address address) {
		this.address = Objects.requireNonNull(address, "address is null");
	}

	public LocalDate getAccessiondate() {
		return accessiondate;
	}
	
	public String getPassword() {
		return password;
	}

}
