package com.samsung.fas.pir.bi.persistence.family;

import com.samsung.fas.pir.bi.persistence.base.BIFactBase;
import com.samsung.fas.pir.bi.persistence.common.DateDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunityLocationDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunityServiceDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunitySocialDimension;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(schema = "fas_warehouse", indexes = @Index(name = "idx_family_fact", columnList = "date_id, service_id, social_id, community_location_id, community_service_id, community_social_id", unique = true))
public class FamilyFact extends BIFactBase {
	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_date_dimension"))
	private			DateDimension								date;

	// region Family
	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_service_dimension"))
	private 		FamilyServiceDimension 						service;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_social_dimension"))
	private 		FamilySocialDimension 						social;
	// endregion

	// region Community
	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_community_location_dimension"))
	private 		CommunityLocationDimension 					communityLocation;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_community_service_dimension"))
	private 		CommunityServiceDimension					communityService;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_community_social_dimension"))
	private 		CommunitySocialDimension 					communitySocial;
	// endregion
}