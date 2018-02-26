package com.samsung.fas.pir.login.persistence.models.entity;

import com.samsung.fas.pir.persistence.models.entity.Profile;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity(name="authority")
@Table(name = "authority")
@DynamicUpdate
@DynamicInsert
public class Authority implements GrantedAuthority {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private		long				id;

	@Getter
	@Setter
	@Column(name = "authority", unique = true, nullable = false)
	private 	String				authority;

	@Getter
	@Setter
	@ManyToMany(fetch = FetchType.EAGER)
	private 	Collection<Profile>	profiles;

	public void addProfile(Profile profile) {
		if (getProfiles() != null) {
			profiles.add(profile);
		} else {
			setProfiles(new HashSet<>());
			profiles.add(profile);
		}
	}
}
