package com.samsung.fas.pir.persistence.models.entity;

import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.persistence.models.enums.EUserType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.engine.spi.PersistentAttributeInterceptable;
import org.hibernate.engine.spi.PersistentAttributeInterceptor;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Entity(name="user")
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
@DynamicUpdate
@DynamicInsert
public class User implements PersistentAttributeInterceptable {
	// FieldHandler did not work for lazy fetching!
	// This will do lazy fetching but apparently will read other tables if its children hold reference to parent
	@Transient
	private 	PersistentAttributeInterceptor	interceptor;

	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private		long				id;

	@Getter
	@Setter
	@Column(name="guid", updatable=false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	private 	UUID 				guid;

	@Getter
	@Setter
	@Column(name="full_name", nullable=false)
	private		String				name;

	@Getter
	@Setter
	@Column(name="email", nullable=false, unique = true)
	private		String				email;
	
	@Getter
	@Setter
	@Column(name="type", nullable=false)
	@Enumerated(EnumType.STRING)
	private 	EUserType 			type;

	@Getter
	@Setter
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_register", updatable=false, nullable=false)
	private 	Date 				registerDate;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user", targetEntity = Address.class, orphanRemoval = true)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private		Address				address;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user", targetEntity = Account.class, orphanRemoval = true)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private 	Account				account;

	public Address getAddress() {
		if (interceptor != null) {
			return (Address) interceptor.readObject(this, "address", address);
		}
		return address;
	}

	public void setAddress(Address address) {
		if (interceptor != null) {
			this.address = (Address) interceptor.writeObject(this, "address", this.address, address);
		} else {
			this.address = address;
		}
	}

	public Account getAccount() {
		if (interceptor != null) {
			return (Account) interceptor.readObject(this, "account", account);
		}
		return account;
	}

	public void setAccount(Account account) {
		if (interceptor != null) {
			this.account = (Account) interceptor.writeObject(this, "account", this.account, account);
		} else {
			this.account = account;
		}
	}

	@Override
	public PersistentAttributeInterceptor $$_hibernate_getInterceptor() {
		return interceptor;
	}

	@Override
	public void $$_hibernate_setInterceptor(PersistentAttributeInterceptor interceptor) {
		this.interceptor = interceptor;
	}
}
