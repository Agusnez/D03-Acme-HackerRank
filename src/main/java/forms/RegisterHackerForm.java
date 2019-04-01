
package forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

public class RegisterHackerForm {

	// Constructors -----------------------------------------------------------

	public RegisterHackerForm() {
		super();
	}


	// Properties -------------------------------------------------------------

	private String	name;
	private String	surnames;
	private Double	vat;
	private String	photo;
	private String	email;
	private String	phone;
	private String	address;
	private String	username;
	private String	password;
	private String	confirmPassword;
	private Boolean	checkbox;
	//Credit card
	private String	holderName;
	private String	make;
	private String	number;
	private Integer	expMonth;
	private Integer	expYear;
	private Integer	cvv;


	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml
	public String getSurnames() {
		return this.surnames;
	}
	public void setSurnames(final String surnames) {
		this.surnames = surnames;
	}

	@NotNull
	public Double getVat() {
		return this.vat;
	}
	public void setVat(final Double vat) {
		this.vat = vat;
	}

	@URL
	@SafeHtml
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	@SafeHtml
	@NotBlank
	@Pattern(regexp = "^[\\w]+@(?:[a-zA-Z0-9]+\\.)+[a-zA-Z0-9]+|(([\\w]\\s)*[\\w])+<\\w+@(?:[a-zA-Z0-9]+\\.)+[a-zA-Z0-9]+>$")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@SafeHtml
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@SafeHtml
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@Size(min = 5, max = 32)
	@SafeHtml
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@Size(min = 5, max = 32)
	@SafeHtml
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@Size(min = 5, max = 32)
	@SafeHtml
	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@NotNull
	public Boolean getCheckbox() {
		return this.checkbox;
	}

	public void setCheckbox(final Boolean checkbox) {
		this.checkbox = checkbox;
	}

	//Atributos Credit Card
	@NotBlank
	@SafeHtml
	public String getHolderName() {
		return this.holderName;
	}

	public void setHolderName(final String holderName) {
		this.holderName = holderName;
	}

	@NotBlank
	@SafeHtml
	public String getMake() {
		return this.make;
	}

	public void setMake(final String make) {
		this.make = make;
	}

	@CreditCardNumber
	@NotBlank
	@SafeHtml
	@Pattern(regexp = "[0-9]+")
	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	@Range(min = 1, max = 12)
	@NotNull
	public Integer getExpMonth() {
		return this.expMonth;
	}

	public void setExpMonth(final Integer expMonth) {
		this.expMonth = expMonth;
	}
	@NotNull
	public Integer getExpYear() {
		return this.expYear;
	}

	public void setExpYear(final Integer expYear) {
		this.expYear = expYear;
	}

	@Range(min = 100, max = 999)
	@NotNull
	public Integer getCvv() {
		return this.cvv;
	}

	public void setCvv(final Integer cvv) {
		this.cvv = cvv;
	}

	//Business metohds--------------------------------------------
	public Boolean checkPassword() {
		Boolean res;

		if (this.getPassword().equals(this.getConfirmPassword()))
			res = true;
		else
			res = false;

		return res;
	}
}
