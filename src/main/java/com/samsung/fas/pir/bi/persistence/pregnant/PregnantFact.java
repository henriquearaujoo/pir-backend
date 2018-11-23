package com.samsung.fas.pir.bi.persistence.pregnant;

import com.samsung.fas.pir.bi.persistence.base.BIFactBase;
import com.samsung.fas.pir.bi.persistence.common.AgentDimension;
import com.samsung.fas.pir.bi.persistence.common.DateDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunityLocationDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunityServiceDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunitySocialDimension;
import com.samsung.fas.pir.bi.persistence.family.FamilyServiceDimension;
import com.samsung.fas.pir.bi.persistence.family.FamilySocialDimension;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(schema = "fas_warehouse", indexes = @Index(name = "idx_pregnant_fact", unique = true, columnList = "date_id, agent_id, social_id, family_service_id, family_social_id, community_location_id, community_service_id, community_social_id"))
public class PregnantFact extends BIFactBase {
	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_date_dimension"))
	private				DateDimension							date;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_agent_dimension"))
	private 			AgentDimension 							agent;

	// region Pregnant
	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_social_dimension"))
	private 			PregnantSocialDimension 				social;
	// endregion

	// region Family
	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_family_service_dimension"))
	private 		FamilyServiceDimension 						familyService;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_family_social_dimension"))
	private 		FamilySocialDimension 						familySocial;
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
