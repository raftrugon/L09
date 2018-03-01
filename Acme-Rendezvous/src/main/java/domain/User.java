
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "userAccount_id")})
public class User extends Actor {

	// Attributes -------------------------------------------------------------
	private Date	birthDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy") //Acorde al input date de input.tag
	@NotNull
	@Past
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}


	// Relationships ----------------------------------------------------------

	private Collection<Rsvp>		rsvps;
	private Collection<Rendezvous>	rendezvouses;


	@NotNull
	@OneToMany(mappedBy = "user")
	public Collection<Rsvp> getRsvps() {
		return rsvps;
	}

	public void setRsvps(Collection<Rsvp> rsvps) {
		this.rsvps = rsvps;
	}

	@NotNull
	@OneToMany(mappedBy = "user")
	public Collection<Rendezvous> getRendezvouses() {
		return rendezvouses;
	}

	public void setRendezvouses(Collection<Rendezvous> rendezvouses) {
		this.rendezvouses = rendezvouses;
	}

}
