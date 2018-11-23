package com.samsung.fas.pir.bi.task;

import com.samsung.fas.pir.bi.persistence.answer.AnswerDimension;
import com.samsung.fas.pir.bi.persistence.child.ChildSocialDimension;
import com.samsung.fas.pir.bi.persistence.common.AgentDimension;
import com.samsung.fas.pir.bi.persistence.common.DateDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunityLocationDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunityServiceDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunitySocialDimension;
import com.samsung.fas.pir.bi.persistence.family.FamilyServiceDimension;
import com.samsung.fas.pir.bi.persistence.family.FamilySocialDimension;
import com.samsung.fas.pir.bi.repository.dimensions.*;
import com.samsung.fas.pir.persistence.models.*;
import com.samsung.fas.pir.persistence.repositories.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

@Component
public class TransformTasks {
	private		static			Logger			Log			= LoggerFactory.getLogger(TransformTasks.class);

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private			LocalDate						yesterday;

	// region Dimensions
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private			IDateDimension					dateDimensionRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		IAgentDimension 				agentDimensionRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		IAnswerDimension 				answerDimensionRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		IChildSocialDimension 			childSocialDimensionRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		ICommunitySocialDimension 		communitySocialDimensionRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		ICommunityServiceDimension 		communityServiceDimensionRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		ICommunityLocationDimension 	communityLocationDimensionRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		IFamilySocialDimension 			familySocialDimensionRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		IFamilyServiceDimension 		familyServiceDimensionRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		IPregnantSocialDimension 		pregnantSocialDimensionRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		IPregnancyMeasureDimension 		pregnancyMeasureDimensionRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		IQuestionDimension 				questionDimensionRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		IVisitDataDimension 			visitDataDimensionRepository;
	// endregion

	// region Data
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		IAgent 							agentRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		IAnswer 						answerRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		IChild 							childRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		ICommunity 						communityRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		IFamily 						familyRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		IPregnancy 						pregnancyRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		IPregnant 						pregnantRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		IQuestion 						questionRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		IVisit 							visitRepository;
	// endregion

	@Autowired
	public TransformTasks(
			@Qualifier("BIDateDimension") IDateDimension dateDimensionRepository,
			@Qualifier("BIAgentDimension") IAgentDimension agentDimensionRepository,
			@Qualifier("BIAnswerDimension") IAnswerDimension answerDimensionRepository,
			@Qualifier("BIChildSocialDimension") IChildSocialDimension childSocialDimensionRepository,
			@Qualifier("BICommunityLocationDimension") ICommunityLocationDimension communityLocationDimensionRepository,
			@Qualifier("BICommunityServiceDimension") ICommunityServiceDimension communityServiceDimensionRepository,
			@Qualifier("BICommunitySocialDimension") ICommunitySocialDimension communitySocialDimensionRepository,
			@Qualifier("BIFamilyServiceDimension") IFamilyServiceDimension familyServiceDimensionRepository,
			@Qualifier("BIFamilySocialDimension") IFamilySocialDimension familySocialDimensionRepository,
			@Qualifier("BIPregnancyMeasureDimension") IPregnancyMeasureDimension pregnancyMeasureDimensionRepository,
			@Qualifier("BIPregnantSocialDimension") IPregnantSocialDimension pregnantSocialDimensionRepository,
			@Qualifier("BIQuestionDimension") IQuestionDimension questionDimensionRepository,
			@Qualifier("BIVisitDataDimension") IVisitDataDimension visitDataDimensionRepository,
			IAgent agentRepository,
			IAnswer answerRepository,
			IChild childRepository,
			ICommunity communityRepository,
			IFamily familyRepository,
			IPregnancy pregnancyRepository,
			IPregnant pregnantRepository,
			IQuestion questionRepository,
			IVisit visitRepository
	) {
		setDateDimensionRepository(dateDimensionRepository);
		setAgentDimensionRepository(agentDimensionRepository);
		setAnswerDimensionRepository(answerDimensionRepository);
		setChildSocialDimensionRepository(childSocialDimensionRepository);
		setCommunityLocationDimensionRepository(communityLocationDimensionRepository);
		setCommunityServiceDimensionRepository(communityServiceDimensionRepository);
		setCommunitySocialDimensionRepository(communitySocialDimensionRepository);
		setFamilyServiceDimensionRepository(familyServiceDimensionRepository);
		setFamilySocialDimensionRepository(familySocialDimensionRepository);
		setPregnancyMeasureDimensionRepository(pregnancyMeasureDimensionRepository);
		setPregnantSocialDimensionRepository(pregnantSocialDimensionRepository);
		setQuestionDimensionRepository(questionDimensionRepository);
		setVisitDataDimensionRepository(visitDataDimensionRepository);
		setAnswerRepository(answerRepository);
		setAgentRepository(agentRepository);
		setChildRepository(childRepository);
		setCommunityRepository(communityRepository);
		setFamilyRepository(familyRepository);
		setPregnancyRepository(pregnancyRepository);
		setPregnantRepository(pregnantRepository);
		setQuestionRepository(questionRepository);
		setVisitRepository(visitRepository);
	}

//	@Scheduled(cron="0 0 0 * * *")
	@Scheduled(fixedDelay = 1200 * 1000)
	public void transform() {
		setYesterday(LocalDate.now(DateTimeZone.forTimeZone(TimeZone.getTimeZone("America/Manaus"))).plusDays(-1));
		populateDateDimensions();
		populateAgentDimensions();
		populateAnswerDimensions();
		populateChildDimensions();
		populateCommunityDimensions();
		populateFamilyDimensions();
	}

	private void populateDateDimensions() {
		try {
			LocalDate 		now		= LocalDate.now(DateTimeZone.forTimeZone(TimeZone.getTimeZone("America/Manaus")));
			DateDimension	date	= new DateDimension();

			date.setYear((short) now.getYear());
			date.setMonth((short) now.getMonthOfYear());
			date.setMonthDay((short) now.getDayOfMonth());
			date.setWeek((short) now.getWeekyear());
			date.setWeekDay((short) now.getDayOfWeek());
			date.setQuarter((short) ((now.getMonthOfYear() / 3) + 1));
			date.setSemester((short) ((now.getMonthOfYear() / 6) + 1));
			date.setDatetime(now.toDateTimeAtStartOfDay().toDate());

			getDateDimensionRepository().save(date);
		} catch (Exception e) {
			// Ignore error for duplicated entries
			Log.error(e.getMessage());
		}
	}

	private void populateAgentDimensions() {
		for (Agent data : getAgentRepository().findAll(QAgent.agent.createdAt.after(getYesterday().toDate()))) {
			try {
				AgentDimension		agent		= new AgentDimension();

				agent.setName(data.getPerson().getUser().getName());
				agent.setCity(data.getCity().getName());
				agent.setCpf(data.getPerson().getCpf());

				getAgentDimensionRepository().save(agent);
			} catch (Exception e) {
				// Ignore error for duplicated entries
				Log.error(e.getMessage());
			}
		}
	}

	private void populateAnswerDimensions() {
		for (Answer data : getAnswerRepository().findAll(QAnswer.answer.createdAt.after(getYesterday().toDate()))) {
			try {
				AnswerDimension			answer			= new AnswerDimension();

				answer.setType(data.getQuestion().getType());
				answer.setValue(data.getDescription());

				getAnswerDimensionRepository().save(answer);
			} catch (Exception e) {
				// Ignore error for duplicated entries
				Log.error(e.getMessage());
			}
		}
	}

	private void populateChildDimensions() {
		for (Child data : getChildRepository().findAll(QChild.child.createdAt.after(getYesterday().toDate()))) {
			try {
				ChildSocialDimension		social		= new ChildSocialDimension();

				social.setFatherName(data.getFatherFullName());
				social.setGender(data.getGender());
				social.setMotherName(data.getMotherFullName());
				social.setName(data.getName());

				getChildSocialDimensionRepository().save(social);
			} catch (Exception e) {
				// Ignore error for duplicated entries
				Log.error(e.getMessage());
			}
		}
	}

	private void populateCommunityDimensions() {
		for (Community data : getCommunityRepository().findAll(QCommunity.community.createdAt.after(getYesterday().toDate()))) {
			{
				try {
					CommunityLocationDimension		location		= new CommunityLocationDimension();

					location.setAccess(data.getAccess());
					location.setName(data.getName());
					location.setCity(data.getCity().getName());
					location.setState(data.getCity().getState().getAbbreviation());
					location.setZone(data.getCommunityZone());
					location.setUnity(data.getUnity().getName());
					location.setRegional(data.getUnity().getRegional().getName());

					getCommunityLocationDimensionRepository().save(location);
				} catch (Exception e) {
					// Ignore error for duplicated entries
					Log.error(e.getMessage());
				}
			}

			{
				try {
					CommunitySocialDimension 		social		= new CommunitySocialDimension();

					social.setCulturalProduction(data.getCulturalProductions());
					social.setIncome(data.getMainIncome());
					social.setHasCivicCenter(data.isCommunityCenter());
					social.setHasCulturalEvent(data.isCulturalEvents());
					social.setHasLeader(data.isCommunityLeaders());
					social.setHasPatron(data.isPatron());
					social.setHasReligiousPlace(data.isReligiousPlace());

					getCommunitySocialDimensionRepository().save(social);

				} catch (Exception e) {
					// Ignore error for duplicated entries
					Log.error(e.getMessage());
				}
			}

			{
				try {
					CommunityServiceDimension		service		= new CommunityServiceDimension();

					service.setGarbageDestination(data.getGarbageDestination());
					service.setHasCollege(data.isCollege());
					service.setHasElectricity(data.isElectricity());
					service.setHasElementarySchool(data.isElementarySchool());
					service.setHasHighSchool(data.isHighSchool());
					service.setHasKindergarten(data.isKindergarten());
					service.setHealthService(data.getHealthServices());
					service.setWaterSupply(data.getWaterSupply());

					getCommunityServiceDimensionRepository().save(service);
				} catch (Exception e) {
					// Ignore error for duplicated entries
					Log.error(e.getMessage());
				}
			}
		}
	}

	private void populateFamilyDimensions() {
		for (Family data : getFamilyRepository().findAll(QFamily.family.createdAt.after(getYesterday().toDate()))) {
			{
				try {
					FamilySocialDimension			social		= new FamilySocialDimension();

					social.setBirth(data.getBirth());
					social.setChildCount(data.getChildrenCount());
					social.setCivilState(data.getCivilState());
					social.setGender(data.getGender());
					social.setHabitationType(data.getHabitationType());
					social.setIncome(data.getIncome());
					social.setMembersCount(data.getMembersCount());
					social.setName(data.getName());

					getFamilySocialDimensionRepository().save(social);
				} catch (Exception e) {
					// Ignore error for duplicated entries
					Log.error(e.getMessage());
				}
			}

			{
				try {
					FamilyServiceDimension 			service			= new FamilyServiceDimension();

					service.setHasNearbyBasicUnity(data.getNearbyUB());
					service.setHasSanitation(data.getSanitation());
					service.setHasWaterTreatment(data.getWaterTreatment());
					service.setInSocialProgram(data.getSocialProgram());
					service.setWaterTreatment(data.getWaterTreatmentDescription());

					getFamilyServiceDimensionRepository().save(service);
				} catch (Exception e) {
					// Ignore error for duplicated entries
					Log.error(e.getMessage());
				}
			}
		}
	}
}