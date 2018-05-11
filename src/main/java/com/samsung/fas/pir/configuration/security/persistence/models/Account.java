package com.samsung.fas.pir.configuration.security.persistence.models;

import com.samsung.fas.pir.persistence.models.Profile;
import com.samsung.fas.pir.persistence.models.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "account", uniqueConstraints = @UniqueConstraint(columnNames = "login", name = "login"))
@DynamicUpdate
@DynamicInsert
public class Account implements UserDetails {
	@Getter
	@Setter
	@Id
	private 	long 				id;

	@Getter
	@Setter
	@MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "id")
	private 	User				user;

	@Getter
	@Setter
	@Column(name="login", nullable=false)
	private		String				username;

	@Getter
	@Setter
	@Column(name="password", nullable=false)
	private		String				password;

	@Getter
	@Setter
	@Column(nullable=false)
	private		boolean				enabled;

	@Getter
	@Setter
	@Column(nullable=false)
	private		boolean				accountNonExpired;

	@Getter
	@Setter
	@Column(nullable=false)
	private		boolean				accountNonLocked;

	@Getter
	@Setter
	@Column(nullable=false)
	private		boolean				credentialsNonExpired;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	private 	Profile 			profile;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getProfile().getAuthorities();
	}
}
