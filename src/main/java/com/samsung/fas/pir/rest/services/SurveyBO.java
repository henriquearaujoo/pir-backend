package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.SAlternativeDAO;
import com.samsung.fas.pir.persistence.dao.SQuestionDAO;
import com.samsung.fas.pir.persistence.dao.SurveyDAO;
import com.samsung.fas.pir.persistence.models.SAlternative;
import com.samsung.fas.pir.persistence.models.SQuestion;
import com.samsung.fas.pir.persistence.models.Survey;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import com.samsung.fas.pir.rest.dto.SurveyDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

@Service
public class SurveyBO extends BaseBO<Survey, SurveyDAO, SurveyDTO, Long> {
	@Getter
	@Setter
	private 		SAlternativeDAO 		sAlternativeDAO;

	@Getter
	@Setter
	private 		SQuestionDAO 			sQuestionDAO;

	@Autowired
	protected SurveyBO(SurveyDAO dao, SQuestionDAO sQuestionDAO, SAlternativeDAO sAlternativeDAO) {
		super(dao);
		setSQuestionDAO(sQuestionDAO);
		setSAlternativeDAO(sAlternativeDAO);
	}

	@Override
	public SurveyDTO save(SurveyDTO create, Device device, UserDetails details) {
		Survey		model		= create.getModel();
		Survey		survey		= model.getUuid() != null? getDao().findOne(model.getUuid()) : null;

		if (survey != null) {
			survey.setDescription(model.getDescription());
			survey.getQuestions().clear();
			survey.getQuestions().addAll(model.getQuestions().stream().map(item -> {
				SQuestion 	question 			= item.getUuid() != null? getSQuestionDAO().findOne(item.getUuid()) : null;
				if (question != null) {
					question.setDescription(item.getDescription());
					question.setType(item.getType());
					question.setValueType(item.getValueType());
					question.getAlternatives().clear();
					question.getAlternatives().addAll(item.getAlternatives().stream().map(amodel -> {
						SAlternative	alternative		= amodel.getUuid() != null? getSAlternativeDAO().findOne(amodel.getUuid()) : null;
						if (alternative != null) {
							alternative.setDescription(amodel.getDescription());
							alternative.setValueType(amodel.getValueType());
							alternative.setQuestion(question);
							return alternative;
						}
						amodel.setQuestion(question);
						return amodel;
					}).sorted(Comparator.comparingLong(BaseID::getId)).collect(Collectors.toList()));
					return question;
				}
				return item;
			}).sorted(Comparator.comparingLong(BaseID::getId)).collect(Collectors.toList()));
			return new SurveyDTO(getDao().save(survey), device, true);
		}
		return new SurveyDTO(getDao().save(model), device, true);
	}

	@Override
	public SurveyDTO update(SurveyDTO update, Device device, UserDetails details) {
		return save(update, device, details);
	}

	@Override
	public Collection<SurveyDTO> save(Collection<SurveyDTO> create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<SurveyDTO> update(Collection<SurveyDTO> update, Device device, UserDetails details) {
		return null;
	}
}