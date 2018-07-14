package com.samsung.fas.pir.persistence.models.base;

import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.persistence.models.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import javax.persistence.ForeignKey;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@MappedSuperclass
public abstract class Base {
	@Getter
	@Setter
	@Id
	@SequenceGenerator(name = "sequence-gen", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(generator = "sequence-gen")
	private		long			id			= 0;

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
	@CreatedDate
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private 	Date			createdAt	= new Date();

	@Getter
	@Setter
	@LastModifiedDate
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private 	Date			updatedAt	= new Date();

	@Getter
	@Setter
	@CreatedBy
	@ManyToOne
	@JoinColumn(name = "created_by", foreignKey = @ForeignKey(name = "fk_created_by"))
	private 	User 			createdBy	= null;

	@Getter
	@Setter
	@LastModifiedBy
	@ManyToOne
	@JoinColumn(name = "modified_by", foreignKey = @ForeignKey(name = "fk_modified_by"))
	private 	User 			modifiedBy	= null;

	@PrePersist
	public void prePersist() {
		setCreatedBy(SecurityContextHolder.getContext().getAuthentication() != null? Optional.ofNullable(((Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser()).orElse(null) : null);
		setCreatedAt(new Date());
	}

	@PreUpdate
	public void preUpdate() {
		setModifiedBy(SecurityContextHolder.getContext().getAuthentication() != null? Optional.ofNullable(((Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser()).orElse(null) : null);
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
