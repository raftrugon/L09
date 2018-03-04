
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private Date	creationMoment;
	private String	text;
	private String	picture;
	private Boolean	inappropriate;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCreationMoment() {
		return creationMoment;
	}

	public void setCreationMoment(Date creationMoment) {
		this.creationMoment = creationMoment;
	}

	@SafeHtml(whitelistType=WhiteListType.NONE)
	@NotBlank
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@SafeHtml(whitelistType=WhiteListType.NONE)
	@URL
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@NotNull
	public Boolean getinappropriate() {
		return inappropriate;
	}

	public void setinappropriate(Boolean inappropriate) {
		this.inappropriate = inappropriate;
	}	

	// Relationships ----------------------------------------------------------

	private Collection<Comment>	replies;
	private Comment				replyingTo;
	private User				user;
	private Rendezvous			rendezvous;


	@NotNull
	@OneToMany(mappedBy = "replyingTo")
	public Collection<Comment> getReplies() {
		return replies;
	}

	public void setReplies(Collection<Comment> replies) {
		this.replies = replies;
	}

	@Valid
	@ManyToOne(optional = true)
	public Comment getReplyingTo() {
		return replyingTo;
	}

	public void setReplyingTo(Comment replyingTo) {
		this.replyingTo = replyingTo;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Valid
	@ManyToOne(optional = true)
	public Rendezvous getRendezvous() {
		return rendezvous;
	}

	public void setRendezvous(Rendezvous rendezvous) {
		this.rendezvous = rendezvous;
	}
}
