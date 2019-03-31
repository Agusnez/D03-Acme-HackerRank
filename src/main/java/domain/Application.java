package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity{

	private Date moment;
	private String status;
	private String answer;
	private Date submitMoment;
	
	private Position position;
	private Curriculum curriculum;
	private Hacker hacker;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	@NotBlank
	@Pattern(regexp = "\\ASUBMITTED\\z|\\AREJECTED\\z|\\AACCEPTED\\z|\\APENDING\\z")
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@NotBlank
	@SafeHtml
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getSubmitMoment() {
		return submitMoment;
	}
	
	public void setSubmitMoment(Date submitMoment) {
		this.submitMoment = submitMoment;
	}
	
	@ManyToOne(optional = false)
	@Valid
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
	
	@ManyToOne(optional = false)
	@Valid
	public Curriculum getCurriculum() {
		return curriculum;
	}
	
	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}
	
	@ManyToOne(optional = false)
	@Valid
	public Hacker getHacker() {
		return hacker;
	}
	
	public void setHacker(Hacker hacker) {
		this.hacker = hacker;
	}
	
	
	
	
}
