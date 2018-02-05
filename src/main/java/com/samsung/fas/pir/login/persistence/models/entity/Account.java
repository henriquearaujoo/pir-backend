package com.samsung.fas.pir.login.persistence.models.entity;

import com.samsung.fas.pir.login.listeners.AccountChanged;
import com.samsung.fas.pir.persistence.models.entity.Profile;
import com.samsung.fas.pir.persistence.models.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity(name="account")
@Table(name = "account")
@DynamicUpdate
@DynamicInsert
@EntityListeners(value = AccountChanged.class)
public class Account implements UserDetails {
	@Getter
	@Setter
	@Id
	private 	long 			id;

	@Getter
	@Setter
	@OneToOne
	@MapsId
	@JoinColumn(name = "id")
	private 	User				user;

	@Getter
	@Setter
	@Column(name="login", unique=true, nullable=false)
	private		String				username;

	@Getter
	@Setter
	@Column(name="password", nullable=false)
	private		String				password;

	@Setter
	@Column(name="enabled", nullable=false)
	private		boolean				enabled;

	@Setter
	@Column(name="expired", nullable=false)
	private		boolean				expired;

	@Setter
	@Column(name="locked", nullable=false)
	private		boolean				locked;

	@Setter
	@Column(name="credentials_expired", nullable=false)
	private		boolean				credentialsExpired;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	private 	Profile				profile;

	// region Overrides
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getProfile().getAuthorities();
	}

	@Override
	public boolean isAccountNonExpired() {
		return !expired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
	// endregion
}
