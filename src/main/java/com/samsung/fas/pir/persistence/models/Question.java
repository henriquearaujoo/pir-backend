package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "questions", uniqueConstraints = @UniqueConstraint(columnNames = {"conclusion_id", "description"}, name = "question"))
@DynamicUpdate
@DynamicInsert
public class Question extends BaseID {
	@Getter
	@Setter
	@Column(name = "description", nullable = false, columnDefinition = "citext")
	private 	String					description;

	@Getter
	@Setter
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
	private 	Collection<Alternative>	alternatives;

	@Getter
	@Setter
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn
	private 	Conclusion				conclusion;
}
