package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.dao.InterventionDAO;
import com.samsung.fas.pir.persistence.models.Chapter;
import com.samsung.fas.pir.persistence.models.Intervention;
import com.samsung.fas.pir.rest.dto.InterventionDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
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
	public InterventionDTO save(InterventionDTO create, Device device, UserDetails account) {
		Intervention	model		= create.getModel();
		Chapter 		chapter		= cdao.findOne(create.getChapterUUID());
		model.setChapter(chapter);
		return new InterventionDTO(getDao().save(model), device, true);
	}

	@Override
	public InterventionDTO update(InterventionDTO update, Device device, UserDetails account) {
		Intervention	model			= update.getModel();
		Intervention	intervention	= getDao().findOne(model.getUuid());

		intervention.setDescription(model.getDescription());
		intervention.setActivity(model.getActivity());
		return new InterventionDTO(getDao().save(intervention), device, true);
	}

	@Override
	public Collection<InterventionDTO> save(Collection<InterventionDTO> create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<InterventionDTO> update(Collection<InterventionDTO> update, Device device, UserDetails details) {
		return null;
	}
}
