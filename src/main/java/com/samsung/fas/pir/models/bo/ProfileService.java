package com.samsung.fas.pir.models.bo;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.dao.ProfileDAO;
import com.samsung.fas.pir.dao.RuleDAO;
import com.samsung.fas.pir.dao.UsersDAO;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.models.dto.page.RCompletePageDTO;
import com.samsung.fas.pir.models.dto.profile.CProfileDTO;
import com.samsung.fas.pir.models.dto.profile.RProfileDTO;
import com.samsung.fas.pir.models.dto.profile.UProfileDTO;
import com.samsung.fas.pir.models.entity.Profile;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {
	private 	ProfileDAO 	pdao;
	private		RuleDAO		rdao;

	@Autowired
	public ProfileService(ProfileDAO pdao, UsersDAO udao, RuleDAO rdao) {
		this.pdao		= pdao;
		this.rdao		= rdao;
	}

	public RProfileDTO findOne(String id) {
		Profile profile = pdao.findOne(IDCoder.decode(id));
		if (profile == null)
			throw new RESTRuntimeException("profile.notfound");
		return RProfileDTO.toDTO(profile);
	}

	public List<RProfileDTO> findAll() {
		return pdao.findAll().stream().map(RProfileDTO::toDTO).collect(Collectors.toList());
	}

	public Page<RProfileDTO> findAll(Pageable pageable) {
		return pdao.findAll(pageable).map(RProfileDTO::toDTO);
	}

	public List<RProfileDTO> findAll(Predicate predicate) {
		return pdao.findAll(predicate).stream().map(RProfileDTO::toDTO).collect(Collectors.toList());
	}

	public Page<RProfileDTO> findAll(Predicate predicate, Pageable pageable) {
		return pdao.findAll(predicate, pageable).map(RProfileDTO::toDTO);
	}

	public List<RCompletePageDTO> findPagesByProfileID(String id) {
		return rdao.findByProfileID(IDCoder.decode(id)).stream().map(m -> RCompletePageDTO.toDTO(m.getPage())).collect(Collectors.toList());
	}
	
	public void save(CProfileDTO profile) {
		// Verify if title exists, if exists, may the user want to update the profile
		if (pdao.findOneByTitle(profile.getTitle()) != null) 
			throw new RESTRuntimeException("profile.title.exists");

		Profile model = profile.getModel();
		pdao.save(model);
	}
	
	public void update(UProfileDTO profile) {
		Profile model = pdao.findOne(profile.getModel().getGuid());

		if (model == null)
			throw new RESTRuntimeException("profile.id.notfound");
		
		// Verify if title exists in another profile
		Profile ptitle = pdao.findOneByTitle(profile.getTitle());
		if (ptitle != null && ptitle.getId().compareTo(model.getId()) != 0)
			throw new RESTRuntimeException("profile.title.exists");
		
		// If all OK
		pdao.update(profile.getModel(), model.getId());
	}
}