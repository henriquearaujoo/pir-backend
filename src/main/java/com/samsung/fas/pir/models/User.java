package com.samsung.fas.pir.models;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.samsung.fas.pir.models.user.Address;
import com.samsung.fas.pir.models.user.Organization;
import com.samsung.fas.pir.models.user.Person;

@Entity(name="user")
public class User implements Serializable {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="id")
	private		UUID			id;
	
	@Column(name="login", unique=true)
	private		String			login;
	
	@Column(name="password", length=20)
	private		String			password;
	
	@Column(name="full_name")
	@NotNull(message = "Invalid value for name field (NULL)")
	@NotEmpty(message = "Invalid value for name field (EMPTY)")
	private		String			name;
	
	@Embedded
	private		Address			address;
	
	@Embedded	
	private		Person			person;
	
	@Embedded	
	private		Organization	organization;
	
	@Column(name="status")
	@NotNull(message = "Invalid value for status field (NULL)")
	private		Boolean			active;
	
	@Column(name="type", length=10)
	@NotNull(message = "Invalid value for type field (NULL)")
	@NotEmpty(message = "Invalid value for type field (EMPTY)")
	private		String			type;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_register", updatable=false)
	private		Date			registerDate;
	
	@OneToMany(mappedBy="agent", targetEntity=Child.class)
	private		List<Child>		children;

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public UUID getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public List<Child> getChildren() {
		return children;
	}

	public void setChildren(List<Child> children) {
		this.children = children;
	}

	public Address getAddress() {
		return address;
	}

	public Person getPerson() {
		return person;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public void copyFrom(Object source) {
		Iterable<Field>		props	= Arrays.asList(this.getClass().getDeclaredFields());
		BeanWrapper			srcwrap = new BeanWrapperImpl(source);
		BeanWrapper			trgwrap = new BeanWrapperImpl(this);
		
		props.forEach(p -> {
			try {
				if (srcwrap.getPropertyValue(p.getName()) != null) {
					trgwrap.setPropertyValue(p.getName(), srcwrap.getPropertyValue(p.getName()));
				}
			} catch (Exception e) {
				LoggerFactory.getLogger(this.getClass()).error(e.getMessage());;
			}
		});
	}
}
