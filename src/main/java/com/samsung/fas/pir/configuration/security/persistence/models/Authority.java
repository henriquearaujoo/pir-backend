package com.samsung.fas.pir.configuration.security.persistence.models;

import com.samsung.fas.pir.persistence.models.Profile;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

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
}
