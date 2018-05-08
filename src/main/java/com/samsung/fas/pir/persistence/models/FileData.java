package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "files")
@DynamicInsert
@DynamicUpdate
public class FileData extends BaseID {
	@Getter
	@Setter
	@Column(name = "path", updatable = false)
	private		String		path;

	@Getter
	@Setter
	@Column(name = "name", nullable = false)
	private		String		name;

	@Getter
	@Setter
	@Column(name = "extension", nullable = false)
	private		String		extension;

	@Getter
	@Setter
	@Column(name = "content", nullable = false)
	private 	String		content;

	@Getter
	@Setter
	@Column(name = "created_at", updatable = false, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private		Date		createdAt;
}
