package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.models.base.Base;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity(name="profile_pages")
@Table(uniqueConstraints= {@UniqueConstraint(columnNames= {"profile_id", "page_id"}, name = "rule")})
@DynamicUpdate
@DynamicInsert
public class Rule extends Base {
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

	@Getter
	@Setter
	@Column
	private		boolean			canCreate;

	@Getter
	@Setter
	@Column
	private		boolean			canRead;

	@Getter
	@Setter
	@Column
	private		boolean			canUpdate;

	@Getter
	@Setter
	@Column
	private		boolean			canDelete;
}
