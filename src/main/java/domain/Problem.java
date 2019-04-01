
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Problem extends DomainEntity {

	private String				title;
	private String				statement;
	private String				hint;
	private Collection<String>	atachments;
	private Boolean				finalMode;

	private Company				company;
	private Position			position;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getStatement() {
		return this.statement;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}

	public String getHint() {
		return this.hint;
	}

	public void setHint(final String hint) {
		this.hint = hint;
	}

	@ElementCollection
	public Collection<String> getAtachments() {
		return this.atachments;
	}

	public void setAtachments(final Collection<String> atachments) {
		this.atachments = atachments;
	}

	@NotNull
	public Boolean getFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(final Boolean finalMode) {
		this.finalMode = finalMode;
	}

	@ManyToOne(optional = false)
	@Valid
	public Company getCompany() {
		return this.company;
	}

	public void setCompany(final Company company) {
		this.company = company;
	}

	@ManyToOne(optional = false)
	@Valid
	public Position getPosition() {
		return this.position;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}

}
