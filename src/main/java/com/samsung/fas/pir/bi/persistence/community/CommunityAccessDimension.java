package com.samsung.fas.pir.bi.persistence.community;

import com.samsung.fas.pir.bi.persistence.base.BIBase;
import com.samsung.fas.pir.persistence.enums.ECommunityZone;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(schema = "fas_warehouse", indexes = @Index(name = "idx_community_access", columnList = "value", unique = true))
public class CommunityAccessDimension extends BIBase {
	@Getter
	@Setter
	@Column
	private				String				value;
}
