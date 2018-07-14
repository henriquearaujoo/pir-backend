package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.configuration.security.persistence.models.Authority;
import com.samsung.fas.pir.persistence.enums.EProfileType;
import com.samsung.fas.pir.persistence.models.base.Base;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "profile", uniqueConstraints = @UniqueConstraint(columnNames = "title", name = "profile_name"))
@DynamicUpdate
@DynamicInsert
public class Profile extends Base {
	@Getter
	@Setter
	@Column(columnDefinition = "CITEXT")
	private		String					title;
	
	@Getter
	@Setter
	@Column
	private		String					description;
	
	@Getter
	@Setter
	@Column(name="status")
	private		boolean					active;

	@Getter
	@Setter
	@Column(/*nullable = false,*/ columnDefinition = "CITEXT")
	@Enumerated(EnumType.STRING)
	private 	EProfileType			type;
	
	@Getter
	@Setter
	@OneToMany(mappedBy="profile")
	private 	Collection<Account>		accounts;

	@Getter
	@Setter
	@ManyToMany(mappedBy = "profiles", fetch = FetchType.EAGER)
	private 	Collection<Authority>	authorities;

	@Getter
	@Setter
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "profile_id")
	private		Collection<Rule>		rules;
}
