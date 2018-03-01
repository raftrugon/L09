
package domain;

import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {@Index(columnList = "user_id, rendezvous_id")})
public class Rsvp extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private Map<String,String>	questionsAndAnswers;

	@NotNull
	@ElementCollection
	public Map<String,String> getQuestionsAndAnswers() {
		return questionsAndAnswers;
	}

	public void setQuestionsAndAnswers(Map<String,String> questionsAndAnswers) {
		this.questionsAndAnswers = questionsAndAnswers;
	}
	


	// Relationships ----------------------------------------------------------

	private User		user;
	private Rendezvous	rendezvous;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Rendezvous getRendezvous() {
		return rendezvous;
	}

	public void setRendezvous(Rendezvous rendezvous) {
		this.rendezvous = rendezvous;
	}



}
