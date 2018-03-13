package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Request extends DomainEntity{

	private CreditCard creditCard;
	private String comment;
	
	@NotNull
	public CreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	//Relationships--------
	private Rendezvous rendezvous;
	private Zervice zervice;
	
	@Valid
	@ManyToOne(optional=false)
	public Rendezvous getRendezvous() {
		return rendezvous;
	}
	public void setRendezvous(Rendezvous rendezvous) {
		this.rendezvous = rendezvous;
	}
	
	@Valid
	@ManyToOne(optional=false)
	public Zervice getZervice() {
		return zervice;
	}
	public void setZervice(Zervice zervice) {
		this.zervice = zervice;
	}
	
	
	
	
}
