package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.dao.InterventionDAO;
import com.samsung.fas.pir.persistence.models.Chapter;
import com.samsung.fas.pir.persistence.models.Intervention;
import com.samsung.fas.pir.rest.dto.InterventionDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class InterventionBO extends BaseBO<Intervention, InterventionDAO, InterventionDTO, Long> {
	private	final ChapterDAO cdao;

	@Autowired
	public InterventionBO(InterventionDAO dao, ChapterDAO cdao) {
		super(dao);
		this.cdao = cdao;
	}

	@Override
	public InterventionDTO save(InterventionDTO create, UserDetails account) {
		Intervention	model		= create.getModel();
		Chapter 		chapter		= cdao.findOne(create.getChapterdUUID());
		model.setChapter(chapter);
		return new InterventionDTO(getDao().save(model), true);
	}

	@Override
	public InterventionDTO update(InterventionDTO update, UserDetails account) {
		Intervention	model			= update.getModel();
		Intervention	intervention	= getDao().findOne(model.getUuid());

		intervention.setDescription(model.getDescription());
		intervention.setActivity(model.getActivity());
		return new InterventionDTO(getDao().save(intervention), true);
	}

	@Override
	public Collection<InterventionDTO> save(Collection<InterventionDTO> create, UserDetails details) {
		return null;
	}

	@Override
	public Collection<InterventionDTO> update(Collection<InterventionDTO> update, UserDetails details) {
		return null;
	}
}
