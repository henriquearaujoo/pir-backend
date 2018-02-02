package com.samsung.fas.pir.persistence.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "files")
@Table(name = "files")
@DynamicInsert
@DynamicUpdate
public class MDataFile {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private		long		id;

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

	@Getter
	@Setter
	@Column(name = "chapter")
	private 	Long		chapter;
}
