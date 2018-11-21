package com.samsung.fas.pir.bi.persistence.answer;

import com.samsung.fas.pir.bi.persistence.base.BIBase;
import com.samsung.fas.pir.persistence.enums.EAnswerType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(	schema = "fas_warehouse", indexes = @Index(name = "idx_question", columnList = "question, type", unique = true))
public class QuestionDimension extends BIBase {
	@Getter
	@Setter
	@Column
	private				String				question;

	@Getter
	@Setter
	@Column
	@Enumerated(EnumType.STRING)
	private 			EAnswerType 		type;
}
