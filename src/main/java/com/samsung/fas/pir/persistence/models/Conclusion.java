package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.BaseNID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "conslusions")
@DynamicUpdate
@DynamicInsert
@Alias("Conclusão")
public class Conclusion extends BaseNID {
	@Getter
	@Setter
	@MapsId
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	@Alias("Capítulo")
	private 	Chapter			chapter;

	@Getter
	@Setter
	@Column(nullable = false, columnDefinition = "TEXT")
	@Alias("Descrição")
	private 	String			description;

	@Getter
	@Setter
	@OneToMany(mappedBy = "conclusion", cascade = CascadeType.ALL, orphanRemoval = true)
	@Alias("Questões")
	private 	Set<Question>	questions;
}
