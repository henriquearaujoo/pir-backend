package com.samsung.fas.pir.bi.persistence.pregnant;

import com.samsung.fas.pir.bi.persistence.base.BIBase;
import com.samsung.fas.pir.persistence.enums.ECivilState;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(schema = "fas_warehouse", indexes = @Index(name = "idx_pregnant_social", columnList = "name, civilState, ethinicity, scholarity", unique = true))
public class PregnantSocialDimension extends BIBase {
	@Getter
	@Setter
	@Length(max = 60)
	@Column
	private				String						name;

	@Getter
	@Setter
	@Enumerated(EnumType.STRING)
	@Column
	private				ECivilState					civilState;

	@Getter
	@Setter
	@Length(max = 60)
	@Column
	private				String						ethinicity;

	@Getter
	@Setter
	@Length(max = 60)
	@Column
	private				String						scholarity;
}