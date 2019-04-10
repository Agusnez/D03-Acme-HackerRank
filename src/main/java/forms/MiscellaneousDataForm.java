
package forms;

import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class MiscellaneousDataForm {

	private int					id;
	private int					version;
	private Collection<String>	attachments;

	private int					curriculumId;


	@NotNull
	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@NotNull
	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@NotEmpty
	@ElementCollection
	public Collection<String> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final Collection<String> attachments) {
		this.attachments = attachments;
	}

	@NotNull
	public int getCurriculumId() {
		return this.curriculumId;
	}

	public void setCurriculumId(final int curriculumId) {
		this.curriculumId = curriculumId;
	}

}
