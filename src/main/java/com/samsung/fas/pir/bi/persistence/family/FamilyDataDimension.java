package com.samsung.fas.pir.bi.persistence.family;

import com.samsung.fas.pir.bi.persistence.base.BIBase;
import com.samsung.fas.pir.persistence.enums.ECivilState;
import com.samsung.fas.pir.persistence.enums.EGender;
import com.samsung.fas.pir.persistence.enums.EHabitationType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "fas_warehouse", indexes = @Index(name = "idx_family_data", columnList = "gender, civilState, habitationType, socialProgram, nearbyBasicUnity, sanitation, waterTreatment, membersCount, childCount, birth", unique = true))
public class FamilyDataDimension extends BIBase {
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
	private				Boolean						socialProgram;

	@Getter
	@Setter
	@Column
	private				Boolean						nearbyBasicUnity;

	@Getter
	@Setter
	@Column
	private				Boolean						sanitation;

	@Getter
	@Setter
	@Column
	private				Boolean						waterTreatment;

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
	@Column
	@Temporal(TemporalType.DATE)
	private				Date						birth;
}