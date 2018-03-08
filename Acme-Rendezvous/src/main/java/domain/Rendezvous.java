
package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {@Index(columnList = "user_id, adultOnly, deleted, inappropriate")})
public class Rendezvous extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String				name;
	private String				description;
	private Date				organisationMoment;
	private String				picture;
	private Double				latitude;
	private Double				longitude;
	private Boolean				finalMode;
	private Boolean				deleted;
	private Boolean				adultOnly;
	private Collection<String>	questions;
	private Boolean				inappropriate;

	@SafeHtml(whitelistType=WhiteListType.NONE)
	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@SafeHtml(whitelistType=WhiteListType.NONE)
	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getOrganisationMoment() {
		return organisationMoment;
	}

	public void setOrganisationMoment(final Date organisationMoment) {
		this.organisationMoment = organisationMoment;
	}

	@SafeHtml(whitelistType=WhiteListType.NONE)
	@URL
	public String getPicture() {
		return picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(final Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(final Double longitude) {
		this.longitude = longitude;
	}

	@NotNull
	public Boolean getFinalMode() {
		return finalMode;
	}

	public void setFinalMode(final Boolean finalMode) {
		this.finalMode = finalMode;
	}

	@NotNull
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(final Boolean deleted) {
		this.deleted = deleted;
	}

	@NotNull
	public Boolean getAdultOnly() {
		return adultOnly;
	}

	public void setAdultOnly(final Boolean adultOnly) {
		this.adultOnly = adultOnly;
	}

	@NotNull
	@ElementCollection
	public Collection<String> getQuestions() {
		return questions;
	}

	public void setQuestions(final Collection<String> questions) {
		this.questions = questions;
	}

	@NotNull
	public Boolean getinappropriate() {
		return inappropriate;
	}

	public void setinappropriate(Boolean inappropriate) {
		this.inappropriate = inappropriate;
	}

	// Transient ---------------------------------------------------

	private Collection<Category> categoriesId;

	@Transient
	public Collection<Integer> getCategoriesId() {
		Set<Integer> categories = new HashSet<Integer>();
		for(Request z: getRequests()) categories.add(z.getZervice().getCategory().getId());
		return new ArrayList<Integer>(categories);
	}



	// Relationships ----------------------------------------------------------
	private User						user;
	private Collection<Comment>			comments;
	private Collection<Rsvp>			rsvps;
	private Collection<Announcement>	announcements;
	private Collection<Rendezvous>		rendezvouses;
	private Collection<Request>			requests;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@NotNull
	@OneToMany(mappedBy = "rendezvous")
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}

	@NotNull
	@OneToMany(mappedBy = "rendezvous")
	public Collection<Rsvp> getRsvps() {
		return rsvps;
	}

	public void setRsvps(final Collection<Rsvp> rsvps) {
		this.rsvps = rsvps;
	}

	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Announcement> getAnnouncements() {
		return announcements;
	}

	public void setAnnouncements(final Collection<Announcement> announcements) {
		this.announcements = announcements;
	}

	@NotNull
	@ManyToMany
	@JoinTable(name = "linked_rendezvouses")
	public Collection<Rendezvous> getRendezvouses() {
		return rendezvouses;
	}

	public void setRendezvouses(final Collection<Rendezvous> rendezvouses) {
		this.rendezvouses = rendezvouses;
	}

	@NotNull
	@OneToMany(mappedBy = "rendezvous")
	public Collection<Request> getRequests() {
		return requests;
	}

	public void setRequests(final Collection<Request> requests) {
		this.requests = requests;
	}
}
