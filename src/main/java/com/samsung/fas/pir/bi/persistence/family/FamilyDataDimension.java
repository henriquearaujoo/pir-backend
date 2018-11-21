package com.samsung.fas.pir.bi.persistence.family;

import com.samsung.fas.pir.bi.persistence.base.BIBase;
import com.samsung.fas.pir.persistence.enums.ECivilState;
import com.samsung.fas.pir.persistence.enums.EGender;
import com.samsung.fas.pir.persistence.enums.EHabitationType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(schema = "fas_warehouse")
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
	private				Date						birth;
}