package com.samsung.fas.pir.bi.task;

import com.samsung.fas.pir.bi.persistence.answer.AnswerDimension;
import com.samsung.fas.pir.bi.persistence.answer.AnswerFact;
import com.samsung.fas.pir.bi.persistence.answer.QuestionDimension;
import com.samsung.fas.pir.bi.persistence.child.ChildFact;
import com.samsung.fas.pir.bi.persistence.child.ChildSocialDimension;
import com.samsung.fas.pir.bi.persistence.common.AgentDimension;
import com.samsung.fas.pir.bi.persistence.common.DateDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunityLocationDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunityServiceDimension;
import com.samsung.fas.pir.bi.persistence.community.CommunitySocialDimension;
import com.samsung.fas.pir.bi.persistence.family.FamilyFact;
import com.samsung.fas.pir.bi.persistence.family.FamilyServiceDimension;
import com.samsung.fas.pir.bi.persistence.family.FamilySocialDimension;
import com.samsung.fas.pir.bi.persistence.pregnancy.PregnancyFact;
import com.samsung.fas.pir.bi.persistence.pregnancy.PregnancyMeasureDimension;
import com.samsung.fas.pir.bi.persistence.pregnant.PregnantFact;
import com.samsung.fas.pir.bi.persistence.pregnant.PregnantSocialDimension;
import com.samsung.fas.pir.bi.persistence.visit.VisitDataDimension;
import com.samsung.fas.pir.bi.persistence.visit.VisitFact;
import com.samsung.fas.pir.bi.repository.dimensions.*;
import com.samsung.fas.pir.bi.repository.facts.*;
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

import java.util.Optional;
import java.util.TimeZone;

@SuppressWarnings("ALL")
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

	// region Facts
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		IFamilyFact						familyFactRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		IChildFact 						childFactRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		IPregnantFact 					pregnantFactRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		IPregnancyFact 					pregnancyFactRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private			IVisitFact						visitFactRepository;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 		IAnswerFact 					answerFactRepository;
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

	// region Constructor
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
			@Qualifier("BIAnswerFact") IAnswerFact answerFactRepository,
			@Qualifier("BIVisitFact") IVisitFact visitFactRepository,
			@Qualifier("BIPregnancyFact") IPregnancyFact pregnancyFactRepository,
			@Qualifier("BIPregnantFact") IPregnantFact pregnantFactRepository,
			@Qualifier("BIChildFact") IChildFact childFactRepository,
			@Qualifier("BIFamilyFact") IFamilyFact familyFactRepository,
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
		setFamilyFactRepository(familyFactRepository);
		setChildFactRepository(childFactRepository);
		setPregnantFactRepository(pregnantFactRepository);
		setPregnancyFactRepository(pregnancyFactRepository);
		setVisitFactRepository(visitFactRepository);
		setAnswerFactRepository(answerFactRepository);
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
	// endregion

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
		populatePregnancyDimensions();
		populateVisitDimensions();
		populateQuestionDimension();

		populateFamilyFact();
		populateChildFact();
		populatePregnantFact();
		populatePregnancyFact();
		populateVisitFact();
	}

	// region Dimensions
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

	private void populatePregnancyDimensions() {
		for (Pregnancy pregnancy : getPregnancyRepository().findAll(QPregnancy.pregnancy.createdAt.after(getYesterday().toDate()))) {
			try {
				PregnancyMeasureDimension				measure			= new PregnancyMeasureDimension();

				measure.setHeight(pregnancy.getHeight());
				measure.setPlanned(pregnancy.getPlanned());
				measure.setWeight((short) Math.round(pregnancy.getWeight()));

				getPregnancyMeasureDimensionRepository().save(measure);
			} catch (Exception e) {
				// Ignore error for duplicated entries
				Log.error(e.getMessage());
			}
		}
	}

	private void populateVisitDimensions() {
		for (Visit visit : getVisitRepository().findAll(QPregnancy.pregnancy.createdAt.after(getYesterday().toDate()))) {
			try {
				VisitDataDimension						data			= new VisitDataDimension();

				data.setAgentRating(visit.getAgentRating());
				data.setNumber((short) visit.getNumber());
				data.setTitle(visit.getChapter().getTitle());

				getVisitDataDimensionRepository().save(data);
			} catch (Exception e) {
				// Ignore error for duplicated entries
				Log.error(e.getMessage());
			}
		}
	}

	private void populateQuestionDimension() {
		for (Question question : getQuestionRepository().findAll(QQuestion.question.createdAt.after(getYesterday().toDate()))) {
			try {
				QuestionDimension						data			= new QuestionDimension();

				data.setQuestion(question.getDescription());
				data.setType(question.getType());

				getQuestionDimensionRepository().save(data);
			} catch (Exception e) {
				// Ignore error for duplicated entries
				Log.error(e.getMessage());
			}
		}
	}
	// endregion

	// region Facts
	private void populateFamilyFact() {
		for (Family family : getFamilyRepository().findAll(QFamily.family.createdAt.after(getYesterday().toDate()).or(QFamily.family.updatedAt.after(getYesterday().toDate())))) {
			try {
				Community						community						= family.getCommunity();
				DateDimension					dateDimension					= getDateDimensionRepository().findOne(family.getUpdatedAt());
				CommunityLocationDimension		locationDimension				= getCommunityLocationDimensionRepository().findOne(community.getName(), community.getCommunityZone(), community.getAccess(), community.getCity().getName(), community.getUnity().getName(), community.getUnity().getRegional().getName(), community.getCity().getState().getAbbreviation());
				CommunityServiceDimension		communityServiceDimension		= getCommunityServiceDimensionRepository().findOne(community.getHealthServices(), community.getGarbageDestination(), community.getWaterSupply(), community.isElectricity(), community.isKindergarten(), community.isElementarySchool(), community.isHighSchool(), community.isCollege());
				CommunitySocialDimension		communitySocialDimension		= getCommunitySocialDimensionRepository().findOne(community.getCulturalProductions(), community.getMainIncome(), community.isCommunityCenter(), community.isCulturalEvents(), community.isCommunityLeaders(), community.isPatron(), community.isReligiousPlace());
				FamilyServiceDimension			serviceDimension				= getFamilyServiceDimensionRepository().findOne(family.getWaterTreatmentDescription(), family.getSocialProgram(), family.getNearbyUB(), family.getSanitation(), family.getWaterTreatment());
				FamilySocialDimension			socialDimension					= getFamilySocialDimensionRepository().findOne(family.getName(), family.getBirth(), family.getGender(), family.getCivilState(), family.getHabitationType(), family.getMembersCount(), family.getChildrenCount(), family.getIncome());
				FamilyFact						fact							= Optional.of(getFamilyFactRepository().findOne(dateDimension.getId(), serviceDimension.getId(), socialDimension.getId(), locationDimension.getId(), communityServiceDimension.getId(), communitySocialDimension.getId())).orElse(new FamilyFact());

				fact.setCommunityLocation(locationDimension);
				fact.setCommunityService(communityServiceDimension);
				fact.setCommunitySocial(communitySocialDimension);
				fact.setService(serviceDimension);
				fact.setSocial(socialDimension);
				fact.setCounter(fact.getCounter() + 1);

				getFamilyFactRepository().save(fact);
			} catch (Exception e) {
				// Ignore error for duplicated entries
				Log.error(e.getMessage());
			}
		}
	}

	private void populateChildFact() {
		for (Child child : getChildRepository().findAll(QChild.child.createdAt.after(getYesterday().toDate()).or(QChild.child.updatedAt.after(getYesterday().toDate())))) {
			try {
				Family							family							= child.getFamily();
				Community						community						= family.getCommunity();
				DateDimension					dateDimension					= getDateDimensionRepository().findOne(child.getUpdatedAt());
				CommunityLocationDimension		locationDimension				= getCommunityLocationDimensionRepository().findOne(community.getName(), community.getCommunityZone(), community.getAccess(), community.getCity().getName(), community.getUnity().getName(), community.getUnity().getRegional().getName(), community.getCity().getState().getAbbreviation());
				CommunityServiceDimension		communityServiceDimension		= getCommunityServiceDimensionRepository().findOne(community.getHealthServices(), community.getGarbageDestination(), community.getWaterSupply(), community.isElectricity(), community.isKindergarten(), community.isElementarySchool(), community.isHighSchool(), community.isCollege());
				CommunitySocialDimension		communitySocialDimension		= getCommunitySocialDimensionRepository().findOne(community.getCulturalProductions(), community.getMainIncome(), community.isCommunityCenter(), community.isCulturalEvents(), community.isCommunityLeaders(), community.isPatron(), community.isReligiousPlace());
				FamilyServiceDimension			familyServiceDimension			= getFamilyServiceDimensionRepository().findOne(family.getWaterTreatmentDescription(), family.getSocialProgram(), family.getNearbyUB(), family.getSanitation(), family.getWaterTreatment());
				FamilySocialDimension			familySocialDimension			= getFamilySocialDimensionRepository().findOne(family.getName(), family.getBirth(), family.getGender(), family.getCivilState(), family.getHabitationType(), family.getMembersCount(), family.getChildrenCount(), family.getIncome());
				AgentDimension					agentDimension					= getAgentDimensionRepository().findOne(child.getAgent().getPerson().getCpf());
				ChildSocialDimension			socialDimension					= getChildSocialDimensionRepository().findOne(child.getName(), child.getGender(), child.getFatherFullName(), child.getMotherFullName());
				ChildFact 						fact							= Optional.of(getChildFactRepository().findOne(dateDimension.getId(), agentDimension.getId(), socialDimension.getId(), familyServiceDimension.getId(), familySocialDimension.getId(), locationDimension.getId(), communityServiceDimension.getId(), communitySocialDimension.getId())).orElse(new ChildFact());

				fact.setDate(dateDimension);
				fact.setAgent(agentDimension);
				fact.setSocial(socialDimension);
				fact.setFamilyService(familyServiceDimension);
				fact.setFamilySocial(familySocialDimension);
				fact.setCommunityLocation(locationDimension);
				fact.setCommunityService(communityServiceDimension);
				fact.setCommunitySocial(communitySocialDimension);
				fact.setCounter(fact.getCounter() + 1);

				getChildFactRepository().save(fact);
			} catch (Exception e) {
				// Ignore error for duplicated entries
				Log.error(e.getMessage());
			}
		}
	}

	private void populatePregnantFact() {
		for (Pregnant pregnant : getPregnantRepository().findAll(QPregnant.pregnant.createdAt.after(getYesterday().toDate()).or(QPregnant.pregnant.updatedAt.after(getYesterday().toDate())))) {
			try {
				Family							family							= pregnant.getFamily();
				Community						community						= family.getCommunity();
				DateDimension					dateDimension					= getDateDimensionRepository().findOne(pregnant.getUpdatedAt());
				CommunityLocationDimension		locationDimension				= getCommunityLocationDimensionRepository().findOne(community.getName(), community.getCommunityZone(), community.getAccess(), community.getCity().getName(), community.getUnity().getName(), community.getUnity().getRegional().getName(), community.getCity().getState().getAbbreviation());
				CommunityServiceDimension		communityServiceDimension		= getCommunityServiceDimensionRepository().findOne(community.getHealthServices(), community.getGarbageDestination(), community.getWaterSupply(), community.isElectricity(), community.isKindergarten(), community.isElementarySchool(), community.isHighSchool(), community.isCollege());
				CommunitySocialDimension		communitySocialDimension		= getCommunitySocialDimensionRepository().findOne(community.getCulturalProductions(), community.getMainIncome(), community.isCommunityCenter(), community.isCulturalEvents(), community.isCommunityLeaders(), community.isPatron(), community.isReligiousPlace());
				FamilyServiceDimension			familyServiceDimension			= getFamilyServiceDimensionRepository().findOne(family.getWaterTreatmentDescription(), family.getSocialProgram(), family.getNearbyUB(), family.getSanitation(), family.getWaterTreatment());
				FamilySocialDimension			familySocialDimension			= getFamilySocialDimensionRepository().findOne(family.getName(), family.getBirth(), family.getGender(), family.getCivilState(), family.getHabitationType(), family.getMembersCount(), family.getChildrenCount(), family.getIncome());
				AgentDimension					agentDimension					= getAgentDimensionRepository().findOne(pregnant.getAgent().getPerson().getCpf());
				PregnantSocialDimension			socialDimension					= getPregnantSocialDimensionRepository().findOne(pregnant.getName(), pregnant.getCivilState(), pregnant.getEthnicity(), pregnant.getScholarity());
				PregnantFact 					fact							= Optional.of(getPregnantFactRepository().findOne(dateDimension.getId(), agentDimension.getId(), socialDimension.getId(), familyServiceDimension.getId(), familySocialDimension.getId(), locationDimension.getId(), communityServiceDimension.getId(), communitySocialDimension.getId())).orElse(new PregnantFact());

				fact.setDate(dateDimension);
				fact.setAgent(agentDimension);
				fact.setSocial(socialDimension);
				fact.setFamilyService(familyServiceDimension);
				fact.setFamilySocial(familySocialDimension);
				fact.setCommunityLocation(locationDimension);
				fact.setCommunityService(communityServiceDimension);
				fact.setCommunitySocial(communitySocialDimension);
				fact.setCounter(fact.getCounter() + 1);

				getPregnantFactRepository().save(fact);
			} catch (Exception e) {
				// Ignore error for duplicated new entries
				Log.error(e.getMessage());
			}
		}
	}

	private void populatePregnancyFact() {
		for (Pregnancy pregnancy : getPregnancyRepository().findAll(QPregnancy.pregnancy.createdAt.after(getYesterday().toDate()).or(QPregnancy.pregnancy.updatedAt.after(getYesterday().toDate())))) {
			try {
				Pregnant						pregnant						= pregnancy.getPregnant();
				Family							family							= pregnant.getFamily();
				Community						community						= family.getCommunity();
				DateDimension					dateDimension					= getDateDimensionRepository().findOne(pregnancy.getUpdatedAt());
				CommunityLocationDimension		locationDimension				= getCommunityLocationDimensionRepository().findOne(community.getName(), community.getCommunityZone(), community.getAccess(), community.getCity().getName(), community.getUnity().getName(), community.getUnity().getRegional().getName(), community.getCity().getState().getAbbreviation());
				CommunityServiceDimension		communityServiceDimension		= getCommunityServiceDimensionRepository().findOne(community.getHealthServices(), community.getGarbageDestination(), community.getWaterSupply(), community.isElectricity(), community.isKindergarten(), community.isElementarySchool(), community.isHighSchool(), community.isCollege());
				CommunitySocialDimension		communitySocialDimension		= getCommunitySocialDimensionRepository().findOne(community.getCulturalProductions(), community.getMainIncome(), community.isCommunityCenter(), community.isCulturalEvents(), community.isCommunityLeaders(), community.isPatron(), community.isReligiousPlace());
				FamilyServiceDimension			familyServiceDimension			= getFamilyServiceDimensionRepository().findOne(family.getWaterTreatmentDescription(), family.getSocialProgram(), family.getNearbyUB(), family.getSanitation(), family.getWaterTreatment());
				FamilySocialDimension			familySocialDimension			= getFamilySocialDimensionRepository().findOne(family.getName(), family.getBirth(), family.getGender(), family.getCivilState(), family.getHabitationType(), family.getMembersCount(), family.getChildrenCount(), family.getIncome());
				AgentDimension					agentDimension					= getAgentDimensionRepository().findOne(pregnant.getAgent().getPerson().getCpf());
				PregnantSocialDimension			socialDimension					= getPregnantSocialDimensionRepository().findOne(pregnant.getName(), pregnant.getCivilState(), pregnant.getEthnicity(), pregnant.getScholarity());
				PregnancyMeasureDimension		measureDimension				= getPregnancyMeasureDimensionRepository().findOne(pregnancy.getHeight(), (short) Math.round(pregnancy.getWeight()), pregnancy.getPlanned());
				PregnancyFact 					fact							= Optional.of(getPregnancyFactRepository().findOne(dateDimension.getId(), measureDimension.getId(), agentDimension.getId(), socialDimension.getId(), familyServiceDimension.getId(), familySocialDimension.getId(), locationDimension.getId(), communityServiceDimension.getId(), communitySocialDimension.getId())).orElse(new PregnancyFact());

				fact.setDate(dateDimension);
				fact.setAgent(agentDimension);
				fact.setSocial(socialDimension);
				fact.setMeasure(measureDimension);
				fact.setFamilyService(familyServiceDimension);
				fact.setFamilySocial(familySocialDimension);
				fact.setCommunityLocation(locationDimension);
				fact.setCommunityService(communityServiceDimension);
				fact.setCommunitySocial(communitySocialDimension);
				fact.setCounter(fact.getCounter() + 1);

				getPregnancyFactRepository().save(fact);
			} catch (Exception e) {
				// Ignore error for duplicated new entries
				Log.error(e.getMessage());
			}
		}
	}

	private void populateVisitFact() {
		for (Visit visit : getVisitRepository().findAll(QVisit.visit.createdAt.after(getYesterday().toDate()).or(QVisit.visit.updatedAt.after(getYesterday().toDate())))) {
			try {
				Pregnancy						pregnancy						= visit.getPregnancy();
				Child							child							= visit.getChild();

				if (pregnancy != null) {
					Chapter							chapter							= visit.getChapter();
					Pregnant						pregnant						= pregnancy.getPregnant();
					Family							family							= pregnant.getFamily();
					Community						community						= family.getCommunity();
					DateDimension					dateDimension					= getDateDimensionRepository().findOne(pregnancy.getUpdatedAt());
					CommunityLocationDimension		locationDimension				= getCommunityLocationDimensionRepository().findOne(community.getName(), community.getCommunityZone(), community.getAccess(), community.getCity().getName(), community.getUnity().getName(), community.getUnity().getRegional().getName(), community.getCity().getState().getAbbreviation());
					CommunityServiceDimension		communityServiceDimension		= getCommunityServiceDimensionRepository().findOne(community.getHealthServices(), community.getGarbageDestination(), community.getWaterSupply(), community.isElectricity(), community.isKindergarten(), community.isElementarySchool(), community.isHighSchool(), community.isCollege());
					CommunitySocialDimension		communitySocialDimension		= getCommunitySocialDimensionRepository().findOne(community.getCulturalProductions(), community.getMainIncome(), community.isCommunityCenter(), community.isCulturalEvents(), community.isCommunityLeaders(), community.isPatron(), community.isReligiousPlace());
					FamilyServiceDimension			familyServiceDimension			= getFamilyServiceDimensionRepository().findOne(family.getWaterTreatmentDescription(), family.getSocialProgram(), family.getNearbyUB(), family.getSanitation(), family.getWaterTreatment());
					FamilySocialDimension			familySocialDimension			= getFamilySocialDimensionRepository().findOne(family.getName(), family.getBirth(), family.getGender(), family.getCivilState(), family.getHabitationType(), family.getMembersCount(), family.getChildrenCount(), family.getIncome());
					AgentDimension					agentDimension					= getAgentDimensionRepository().findOne(pregnant.getAgent().getPerson().getCpf());
					PregnantSocialDimension			socialDimension					= getPregnantSocialDimensionRepository().findOne(pregnant.getName(), pregnant.getCivilState(), pregnant.getEthnicity(), pregnant.getScholarity());
					PregnancyMeasureDimension		measureDimension				= getPregnancyMeasureDimensionRepository().findOne(pregnancy.getHeight(), (short) Math.round(pregnancy.getWeight()), pregnancy.getPlanned());
					VisitDataDimension				dataDimension					= getVisitDataDimensionRepository().findOne(chapter.getTitle(), (short) visit.getNumber(), visit.getAgentRating());
					VisitFact						fact							= Optional.of(getVisitFactRepository().findOne(dateDimension.getId(), agentDimension.getId(), dataDimension.getId(), measureDimension.getId(), socialDimension.getId(), null, familyServiceDimension.getId(), familySocialDimension.getId(), locationDimension.getId(), communityServiceDimension.getId(), communitySocialDimension.getId())).orElse(new VisitFact());

					fact.setDate(dateDimension);
					fact.setCommunityLocation(locationDimension);
					fact.setCommunityService(communityServiceDimension);
					fact.setCommunitySocial(communitySocialDimension);
					fact.setFamilyService(familyServiceDimension);
					fact.setFamilySocial(familySocialDimension);
					fact.setAgent(agentDimension);
					fact.setPregnancyMeasure(measureDimension);
					fact.setPregnantSocial(socialDimension);
					fact.setData(dataDimension);
					fact.setCounter(fact.getCounter() + 1);

					getVisitFactRepository().save(fact);
				}

				if (child != null) {
					Chapter							chapter							= visit.getChapter();
					Family							family							= child.getFamily();
					Community						community						= family.getCommunity();
					DateDimension					dateDimension					= getDateDimensionRepository().findOne(child.getUpdatedAt());
					CommunityLocationDimension		locationDimension				= getCommunityLocationDimensionRepository().findOne(community.getName(), community.getCommunityZone(), community.getAccess(), community.getCity().getName(), community.getUnity().getName(), community.getUnity().getRegional().getName(), community.getCity().getState().getAbbreviation());
					CommunityServiceDimension		communityServiceDimension		= getCommunityServiceDimensionRepository().findOne(community.getHealthServices(), community.getGarbageDestination(), community.getWaterSupply(), community.isElectricity(), community.isKindergarten(), community.isElementarySchool(), community.isHighSchool(), community.isCollege());
					CommunitySocialDimension		communitySocialDimension		= getCommunitySocialDimensionRepository().findOne(community.getCulturalProductions(), community.getMainIncome(), community.isCommunityCenter(), community.isCulturalEvents(), community.isCommunityLeaders(), community.isPatron(), community.isReligiousPlace());
					FamilyServiceDimension			familyServiceDimension			= getFamilyServiceDimensionRepository().findOne(family.getWaterTreatmentDescription(), family.getSocialProgram(), family.getNearbyUB(), family.getSanitation(), family.getWaterTreatment());
					FamilySocialDimension			familySocialDimension			= getFamilySocialDimensionRepository().findOne(family.getName(), family.getBirth(), family.getGender(), family.getCivilState(), family.getHabitationType(), family.getMembersCount(), family.getChildrenCount(), family.getIncome());
					AgentDimension					agentDimension					= getAgentDimensionRepository().findOne(child.getAgent().getPerson().getCpf());
					VisitDataDimension				dataDimension					= getVisitDataDimensionRepository().findOne(chapter.getTitle(), (short) visit.getNumber(), visit.getAgentRating());
					ChildSocialDimension			socialDimension					= getChildSocialDimensionRepository().findOne(child.getName(), child.getGender(), child.getFatherFullName(), child.getMotherFullName());
					VisitFact						fact							= Optional.of(getVisitFactRepository().findOne(dateDimension.getId(), agentDimension.getId(), dataDimension.getId(), null, null, socialDimension.getId(), familyServiceDimension.getId(), familySocialDimension.getId(), locationDimension.getId(), communityServiceDimension.getId(), communitySocialDimension.getId())).orElse(new VisitFact());

					fact.setDate(dateDimension);
					fact.setCommunityLocation(locationDimension);
					fact.setCommunityService(communityServiceDimension);
					fact.setCommunitySocial(communitySocialDimension);
					fact.setFamilyService(familyServiceDimension);
					fact.setFamilySocial(familySocialDimension);
					fact.setAgent(agentDimension);
					fact.setChildSocial(socialDimension);
					fact.setData(dataDimension);
					fact.setCounter(fact.getCounter() + 1);

					getVisitFactRepository().save(fact);
				}
			} catch (Exception e) {
				// Ignore error for duplicated new entries
				Log.error(e.getMessage());
			}
		}
	}

	private void populateAnswerFact() {
		for (Answer answer : getAnswerRepository().findAll(QAnswer.answer.createdAt.after(getYesterday().toDate()).or(QAnswer.answer.updatedAt.after(getYesterday().toDate())))) {
			try {
				Visit							visit							= answer.getVisit();
				Chapter							chapter							= visit.getChapter();

				if (visit.getChild() != null) {
					Child							child							= visit.getChild();
					Family							family							= child.getFamily();
					Community						community						= family.getCommunity();
					DateDimension 					dateDimension 					= getDateDimensionRepository().findOne(answer.getCreatedAt());
					AgentDimension 					agentDimension 					= getAgentDimensionRepository().findOne(answer.getVisit().getAgent().getPerson().getCpf());
					AnswerDimension 				answerDimension 				= getAnswerDimensionRepository().findOne(answer.getDescription(), answer.getQuestion().getType());
					QuestionDimension 				questionDimension 				= getQuestionDimensionRepository().findOne(answer.getQuestion().getDescription(), answer.getQuestion().getType());
					VisitDataDimension 				dataDimension 					= getVisitDataDimensionRepository().findOne(chapter.getTitle(), (short) visit.getNumber(), visit.getAgentRating());
					ChildSocialDimension			socialDimension					= getChildSocialDimensionRepository().findOne(child.getName(), child.getGender(), child.getFatherFullName(), child.getMotherFullName());
					CommunityLocationDimension		locationDimension				= getCommunityLocationDimensionRepository().findOne(community.getName(), community.getCommunityZone(), community.getAccess(), community.getCity().getName(), community.getUnity().getName(), community.getUnity().getRegional().getName(), community.getCity().getState().getAbbreviation());
					CommunityServiceDimension		communityServiceDimension		= getCommunityServiceDimensionRepository().findOne(community.getHealthServices(), community.getGarbageDestination(), community.getWaterSupply(), community.isElectricity(), community.isKindergarten(), community.isElementarySchool(), community.isHighSchool(), community.isCollege());
					CommunitySocialDimension		communitySocialDimension		= getCommunitySocialDimensionRepository().findOne(community.getCulturalProductions(), community.getMainIncome(), community.isCommunityCenter(), community.isCulturalEvents(), community.isCommunityLeaders(), community.isPatron(), community.isReligiousPlace());
					FamilyServiceDimension			familyServiceDimension			= getFamilyServiceDimensionRepository().findOne(family.getWaterTreatmentDescription(), family.getSocialProgram(), family.getNearbyUB(), family.getSanitation(), family.getWaterTreatment());
					FamilySocialDimension			familySocialDimension			= getFamilySocialDimensionRepository().findOne(family.getName(), family.getBirth(), family.getGender(), family.getCivilState(), family.getHabitationType(), family.getMembersCount(), family.getChildrenCount(), family.getIncome());
					AnswerFact						fact 							= Optional.of(getAnswerFactRepository().findOne(dateDimension.getId(), agentDimension.getId(), answerDimension.getId(), questionDimension.getId(), dataDimension.getId(), null, null, socialDimension.getId(), familyServiceDimension.getId(), familySocialDimension.getId(), locationDimension.getId(), communityServiceDimension.getId(), communitySocialDimension.getId())).orElse(new AnswerFact());

					fact.setAgent(agentDimension);
					fact.setAnswer(answerDimension);
					fact.setChildSocial(socialDimension);
					fact.setCommunityLocation(locationDimension);
					fact.setCommunityService(communityServiceDimension);
					fact.setCommunitySocial(communitySocialDimension);
					fact.setData(dataDimension);
					fact.setDate(dateDimension);
					fact.setFamilyService(familyServiceDimension);
					fact.setFamilySocial(familySocialDimension);
					fact.setQuestion(questionDimension);
					fact.setCounter(fact.getCounter() + 1);

					getAnswerFactRepository().save(fact);
				}

				if (visit.getPregnancy() != null) {
					Pregnancy						pregnancy						= visit.getPregnancy();
					Pregnant						pregnant						= pregnancy.getPregnant();
					Family							family							= pregnant.getFamily();
					Community						community						= family.getCommunity();
					DateDimension					dateDimension					= getDateDimensionRepository().findOne(pregnancy.getUpdatedAt());
					AnswerDimension 				answerDimension 				= getAnswerDimensionRepository().findOne(answer.getDescription(), answer.getQuestion().getType());
					QuestionDimension 				questionDimension 				= getQuestionDimensionRepository().findOne(answer.getQuestion().getDescription(), answer.getQuestion().getType());
					CommunityLocationDimension		locationDimension				= getCommunityLocationDimensionRepository().findOne(community.getName(), community.getCommunityZone(), community.getAccess(), community.getCity().getName(), community.getUnity().getName(), community.getUnity().getRegional().getName(), community.getCity().getState().getAbbreviation());
					CommunityServiceDimension		communityServiceDimension		= getCommunityServiceDimensionRepository().findOne(community.getHealthServices(), community.getGarbageDestination(), community.getWaterSupply(), community.isElectricity(), community.isKindergarten(), community.isElementarySchool(), community.isHighSchool(), community.isCollege());
					CommunitySocialDimension		communitySocialDimension		= getCommunitySocialDimensionRepository().findOne(community.getCulturalProductions(), community.getMainIncome(), community.isCommunityCenter(), community.isCulturalEvents(), community.isCommunityLeaders(), community.isPatron(), community.isReligiousPlace());
					FamilyServiceDimension			familyServiceDimension			= getFamilyServiceDimensionRepository().findOne(family.getWaterTreatmentDescription(), family.getSocialProgram(), family.getNearbyUB(), family.getSanitation(), family.getWaterTreatment());
					FamilySocialDimension			familySocialDimension			= getFamilySocialDimensionRepository().findOne(family.getName(), family.getBirth(), family.getGender(), family.getCivilState(), family.getHabitationType(), family.getMembersCount(), family.getChildrenCount(), family.getIncome());
					AgentDimension					agentDimension					= getAgentDimensionRepository().findOne(pregnant.getAgent().getPerson().getCpf());
					PregnantSocialDimension			socialDimension					= getPregnantSocialDimensionRepository().findOne(pregnant.getName(), pregnant.getCivilState(), pregnant.getEthnicity(), pregnant.getScholarity());
					PregnancyMeasureDimension		measureDimension				= getPregnancyMeasureDimensionRepository().findOne(pregnancy.getHeight(), (short) Math.round(pregnancy.getWeight()), pregnancy.getPlanned());
					VisitDataDimension				dataDimension					= getVisitDataDimensionRepository().findOne(chapter.getTitle(), (short) visit.getNumber(), visit.getAgentRating());
					AnswerFact						fact 							= Optional.of(getAnswerFactRepository().findOne(dateDimension.getId(), agentDimension.getId(), answerDimension.getId(), questionDimension.getId(), dataDimension.getId(), measureDimension.getId(), socialDimension.getId(), null, familyServiceDimension.getId(), familySocialDimension.getId(), locationDimension.getId(), communityServiceDimension.getId(), communitySocialDimension.getId())).orElse(new AnswerFact());

					fact.setAgent(agentDimension);
					fact.setAnswer(answerDimension);
					fact.setPregnantSocial(socialDimension);
					fact.setPregnancyMeasure(measureDimension);
					fact.setCommunityLocation(locationDimension);
					fact.setCommunityService(communityServiceDimension);
					fact.setCommunitySocial(communitySocialDimension);
					fact.setData(dataDimension);
					fact.setDate(dateDimension);
					fact.setFamilyService(familyServiceDimension);
					fact.setFamilySocial(familySocialDimension);
					fact.setQuestion(questionDimension);
					fact.setCounter(fact.getCounter() + 1);

					getAnswerFactRepository().save(fact);
				}

			} catch (Exception e) {
				// Ignore error for duplicated new entries
				Log.error(e.getMessage());
			}
		}
	}
	// endregion
}