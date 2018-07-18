package com.samsung.fas.pir.persistence.models.base;

import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.persistence.models.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.persistence.ForeignKey;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public abstract class Base {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@OrderColumn
	private		Long			id;

	@Getter
	@Setter
	@Column(insertable = false, updatable=false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	@Generated(GenerationTime.INSERT)
	private		UUID			uuid;

	@Getter
	@Setter
	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
	private 	boolean			erased		= false;

	@Getter
	@Setter
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private 	Date			createdAt	= new Date();

	@Getter
	@Setter
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private 	Date			updatedAt	= new Date();

	@Getter
	@Setter
	@CreatedBy
	@ManyToOne
	@JoinColumn(updatable = false, foreignKey = @ForeignKey(name = "fk_created_by"))
	private 	User 			createdBy	= null;

	@Getter
	@Setter
	@LastModifiedBy
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_modified_by"))
	private 	User 			modifiedBy	= null;

	@PrePersist
	public void prePersist() {
		setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails? ((Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser() : null);
		setCreatedAt(new Date());
	}

	@PreUpdate
	public void preUpdate() {
		setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails? ((Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser() : null);
		setUpdatedAt(new Date());
	}

	@Override
	public boolean equals(Object obj) {
		try {
			return getUuid().compareTo(((Base) obj).getUuid()) == 0;
		} catch (Exception e) {
			return super.equals(obj);
		}
	}
}
