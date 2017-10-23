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
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.samsung.fas.pir.enums.UserType;
import com.samsung.fas.pir.models.user.embedded.Address;
import com.samsung.fas.pir.models.user.embedded.Organization;
import com.samsung.fas.pir.models.user.embedded.Person;

import lombok.Getter;
import lombok.Setter;

@Entity(name="user")
public class User implements Serializable {
	private static final long serialVersionUID = -2390821297865569815L;

	@Getter
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="id", updatable=false, nullable=false)
	private		UUID			id;
	
	@Getter
	@Setter
	@Column(name="login", unique=true, nullable=false)
	private		String			login;
	
	@Getter
	@Setter
	@Column(name="password", nullable=false)
	private		String			password;
	
	@Getter
	@Setter
	@Column(name="full_name", nullable=false)
	private		String			name;
	
	@Getter
	@Setter
	@Column(name="status", nullable=false)
	private		Boolean			active;
	
	@Getter
	@Setter
	@Column(name="type", nullable=false)
	private		UserType		type;
	
	@Getter
	@Setter
	@Embedded
	private		Address			address;
	
	@Getter
	@Setter
	@Embedded	
	private		Person			person;
	
	@Getter
	@Setter
	@Embedded	
	private		Organization	organization;
	
	@Getter
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_register", updatable=false, nullable=false)
	private		Date			registerDate;
	
	@Getter
	@Setter
	@OneToMany(mappedBy="agent", targetEntity=Child.class)
	private		List<Child>		children;

	public void copyFrom(Object source) {
		Iterable<Field>		props	= Arrays.asList(this.getClass().getDeclaredFields());
		BeanWrapper			srcwrap = new BeanWrapperImpl(source);
		BeanWrapper			trgwrap = new BeanWrapperImpl(this);
		
		props.forEach(p -> {
			try {
				trgwrap.setPropertyValue(p.getName(), srcwrap.getPropertyValue(p.getName()));
			} catch (Exception e) {
				LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
			}
		});
	}
}
