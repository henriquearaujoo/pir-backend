package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity(name="profile_pages")
@Table(uniqueConstraints= {@UniqueConstraint(columnNames= {"profile_id", "page_id"}, name = "rule")})
@DynamicUpdate
@DynamicInsert
public class Rule extends BaseID {
	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	private		Profile			profile;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	private		Page			page;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column
	private		boolean			canCreate;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column
	private		boolean			canRead;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column
	private		boolean			canUpdate;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column
	private		boolean			canDelete;
}
