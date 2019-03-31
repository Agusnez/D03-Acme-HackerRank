package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Position extends DomainEntity {
	
	private String ticker;
	private String title;
	private String description;
	private Date deadline;
	private String profile;
	private String skills;
	private String technologies;
	private Double offeredSalary;
	private Boolean finalMode;
	
	private Company company;
	
	
	public String getTicker() {
		return ticker;
	}
	
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	@NotBlank
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotBlank
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@NotNull
	public Date getDeadline() {
		return deadline;
	}
	
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
	@NotBlank
	public String getProfile() {
		return profile;
	}
	
	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	@NotBlank
	public String getSkills() {
		return skills;
	}
	
	public void setSkills(String skills) {
		this.skills = skills;
	}
	
	@NotBlank
	public String getTechnologies() {
		return technologies;
	}
	
	public void setTechnologies(String technologies) {
		this.technologies = technologies;
	}
	
	@NotNull
	public Double getOfferedSalary() {
		return offeredSalary;
	}
	
	public void setOfferedSalary(Double offeredSalary) {
		this.offeredSalary = offeredSalary;
	}
	
	@NotNull
	public Boolean getFinalMode() {
		return finalMode;
	}
	
	public void setFinalMode(Boolean finalMode) {
		this.finalMode = finalMode;
	}

	@ManyToOne(optional = false)
	@Valid
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	
	
	

}
