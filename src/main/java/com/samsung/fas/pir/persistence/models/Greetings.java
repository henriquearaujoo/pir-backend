package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.models.base.BaseID;
import com.samsung.fas.pir.persistence.models.base.BaseNID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity(name="greetings")
@Table(name = "greetings")
@DynamicUpdate
@DynamicInsert
public class Greetings extends BaseNID {
	@Getter
	@Setter
	@MapsId
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private 	Chapter			chapter;

	@Getter
	@Setter
	@Column(name = "description", nullable = false, columnDefinition = "TEXT")
	private 	String			description;

	@Getter
	@Setter
	@Column(name = "eletronics", nullable = false, columnDefinition = "TEXT")
	private 	boolean			eletronics;

	@Getter
	@Setter
	@Column(name = "sit", nullable = false)
	private 	boolean			sit;

	@Getter
	@Setter
	@Column(name = "goback", nullable = false)
	private 	boolean			goback;

	@Getter
	@Setter
	@Column(name = "stove", nullable = false)
	private 	boolean			stove;
}
