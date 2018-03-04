package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "userAccount_id")})
public class Manager extends Actor {

	private String vat;

	@NotBlank
	@Pattern(regexp="(\\w|\\d|-){1,}")	
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getVat() {
		return vat;
	}
	public void setVat(String vat) {
		this.vat = vat;
	}
	
	//Relationships----------------
	private Collection<Zervice> zervices;

	@NotNull
	@OneToMany(mappedBy="manager")
	public Collection<Zervice> getZervices() {
		return zervices;
	}
	public void setZervices(Collection<Zervice> zervices) {
		this.zervices = zervices;
	}
	
	
 	
	
}
