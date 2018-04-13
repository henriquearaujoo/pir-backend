package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.dao.InterventionDAO;
import com.samsung.fas.pir.persistence.models.Chapter;
import com.samsung.fas.pir.persistence.models.Intervention;
import com.samsung.fas.pir.rest.dto.InterventionDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.rest.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class InterventionService extends BService<Intervention, InterventionDTO, InterventionDAO, Long> {
	private	final ChapterDAO cdao;

	@Autowired
	public InterventionService(InterventionDAO dao, @Autowired ChapterDAO cdao) {
		super(dao, Intervention.class, InterventionDTO.class);
		this.cdao = cdao;
	}

	@Override
	public InterventionDTO save(InterventionDTO create, UserDetails account) {
		Intervention	model		= create.getModel();
		Chapter 		chapter		= cdao.findOne(IDCoder.decode(create.getChapterdID()));
		model.setChapter(chapter);
		return new InterventionDTO(dao.save(model), true);
	}

	@Override
	public InterventionDTO update(InterventionDTO update, UserDetails account) {
		Intervention	model			= update.getModel();
		Intervention	intervention	= dao.findOne(model.getUuid());

		intervention.setDescription(model.getDescription());
		intervention.setActivity(model.getActivity());
		return new InterventionDTO(dao.save(intervention), true);
	}
}
