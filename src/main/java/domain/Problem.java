package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Problem extends DomainEntity {
	
	private String title;
	private String statement;
	private String hint;
	private Collection<String> atachments;
	private Boolean finalMode;
	
	private Company company;
	private Position position;
	
	@NotBlank
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotBlank
	public String getStatement() {
		return statement;
	}
	
	public void setStatement(String statement) {
		this.statement = statement;
	}
	
	public String getHint() {
		return hint;
	}
	
	public void setHint(String hint) {
		this.hint = hint;
	}
	
	public Collection<String> getAtachments() {
		return atachments;
	}
	
	public void setAtachments(Collection<String> atachments) {
		this.atachments = atachments;
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
	
	@ManyToOne(optional = false)
	@Valid
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
	
	
	

}
