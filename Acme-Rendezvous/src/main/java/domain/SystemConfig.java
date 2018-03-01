package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;



@Entity
@Access(AccessType.PROPERTY)
public class SystemConfig extends DomainEntity {

	private String bussinessName;
	private String banner;
	private String welcomeMessageEN;
	private String welcomeMessageES;
	
	@SafeHtml(whitelistType=WhiteListType.NONE)
	@NotBlank
	public String getBussinessName() {
		return bussinessName;
	}
	public void setBussinessName(String bussinessName) {
		this.bussinessName = bussinessName;
	}
	
	@SafeHtml(whitelistType=WhiteListType.NONE)
	@URL
	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}
	
	@SafeHtml(whitelistType=WhiteListType.NONE)
	@NotBlank
	public String getWelcomeMessageEN() {
		return welcomeMessageEN;
	}
	public void setWelcomeMessageEN(String welcomeMessageEN) {
		this.welcomeMessageEN = welcomeMessageEN;
	}
	
	@SafeHtml(whitelistType=WhiteListType.NONE)
	@NotBlank
	public String getWelcomeMessageES() {
		return welcomeMessageES;
	}
	public void setWelcomeMessageES(String welcomeMessageES) {
		this.welcomeMessageES = welcomeMessageES;
	}
	
	
	
}
