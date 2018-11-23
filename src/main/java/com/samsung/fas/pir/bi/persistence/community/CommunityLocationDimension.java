package com.samsung.fas.pir.bi.persistence.community;

import com.samsung.fas.pir.bi.persistence.base.BIBase;
import com.samsung.fas.pir.persistence.enums.ECommunityZone;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(schema = "fas_warehouse", indexes = @Index(name = "idx_community_location", columnList = "zone, access, city, unity, regional, state", unique = true))
public class CommunityLocationDimension extends BIBase {
	@Getter
	@Setter
	@Length(max = 60)
	@Column
	private				String				name;

	@Getter
	@Setter
	@Column
	@Enumerated(EnumType.STRING)
	private				ECommunityZone		zone;

	@Getter
	@Setter
	@Length(max = 60)
	@Column
	private				String				access;

	@Getter
	@Setter
	@Length(max = 60)
	@Column
	private				String				city;

	@Getter
	@Setter
	@Length(max = 60)
	@Column
	private				String				unity;

	@Getter
	@Setter
	@Length(max = 60)
	@Column
	private				String				regional;

	@Getter
	@Setter
	@Length(min = 2, max = 2)
	@Column
	private				String				state;
}