package com.samsung.fas.pir.bi.persistence.child;

import com.samsung.fas.pir.bi.persistence.base.BIBase;
import com.samsung.fas.pir.persistence.enums.EGender;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(schema = "fas_warehouse", indexes = @Index(name = "idx_child_social", columnList = "name, gender, motherName, fatherName", unique = true))
public class ChildSocialDimension extends BIBase {
	@Getter
	@Setter
	@Length(max = 60)
	@Column
	private				String						name;

	@Getter
	@Setter
	@Enumerated(EnumType.STRING)
	@Column
	private				EGender						gender;

	@Getter
	@Setter
	@Length(max = 60)
	@Column
	private				String						motherName;

	@Getter
	@Setter
	@Length(max = 60)
	@Column
	private				String						fatherName;
}