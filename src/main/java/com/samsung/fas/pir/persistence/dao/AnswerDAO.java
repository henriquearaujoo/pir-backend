package com.samsung.fas.pir.persistence.dao;

import com.samsung.fas.pir.persistence.dao.base.BaseDAO;
import com.samsung.fas.pir.persistence.models.Answer;
import com.samsung.fas.pir.persistence.models.QAnswer;
import com.samsung.fas.pir.persistence.repositories.IAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AnswerDAO extends BaseDAO<Answer, Long, IAnswer, QAnswer> {
	@Autowired
	public AnswerDAO(IAnswer repository) {
		super(repository);
	}

	public Answer findOneByAgentAndExternalID(UUID uuid, long externalID) {
		return getRepository().findByAgentUuidAndExternalID(uuid, externalID);
	}
}
