package com.samsung.fas.pir.bi.persistence.family;

import com.samsung.fas.pir.bi.persistence.base.BIBase;
import com.samsung.fas.pir.persistence.enums.ECivilState;
import com.samsung.fas.pir.persistence.enums.EGender;
import com.samsung.fas.pir.persistence.enums.EHabitationType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "fas_warehouse", indexes = @Index(name = "idx_family_social", columnList = "name, birth, gender, civilState, habitationType, membersCount, childCount, income", unique = true))
public class FamilySocialDimension extends BIBase {
	@Getter
	@Setter
	@Length(max = 60)
	@Column
	private				String						name;

	@Getter
	@Setter
	@Column
	@Temporal(TemporalType.DATE)
	private				Date						birth;

	@Getter
	@Setter
	@Column
	private				EGender						gender;

	@Getter
	@Setter
	@Column
	private				ECivilState					civilState;

	@Getter
	@Setter
	@Column
	private				EHabitationType				habitationType;

	@Getter
	@Setter
	@Column
	private				Short						membersCount;

	@Getter
	@Setter
	@Column
	private				Short						childCount;

	@Getter
	@Setter
	@Length(max = 60)
	@Column
	private				String						income;
}