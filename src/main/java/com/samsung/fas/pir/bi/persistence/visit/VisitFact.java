package com.samsung.fas.pir.bi.persistence.visit;

import com.samsung.fas.pir.bi.persistence.base.BIFactBase;
import com.samsung.fas.pir.bi.persistence.child.ChildSocialDimension;
import com.samsung.fas.pir.bi.persistence.common.AgentDimension;
import com.samsung.fas.pir.bi.persistence.common.DateDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunityLocationDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunityServiceDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunitySocialDimension;
import com.samsung.fas.pir.bi.persistence.family.FamilyServiceDimension;
import com.samsung.fas.pir.bi.persistence.family.FamilySocialDimension;
import com.samsung.fas.pir.bi.persistence.pregnancy.PregnancyMeasureDimension;
import com.samsung.fas.pir.bi.persistence.pregnant.PregnantSocialDimension;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(schema = "fas_warehouse", indexes = @Index(name = "idx_visit_fact", unique = true, columnList = "date_id, agent_id, data_id, pregnancy_measure_id, pregnant_social_id, child_social_id, family_service_id, family_social_id, community_location_id, community_service_id, community_social_id"))
public class VisitFact extends BIFactBase {
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

	// region Visit
	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_data_dimension"))
	private				VisitDataDimension						data;
	// endregion

	// region Pregnancy
	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_pregnancy_measure_dimension"))
	private				PregnancyMeasureDimension				pregnancyMeasure;
	// endregion

	// region Pregnant
	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_pregnant_social_dimension"))
	private 			PregnantSocialDimension 				pregnantSocial;
	// endregion

	// region Child
	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_child_social_dimension"))
	private			ChildSocialDimension						childSocial;
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