package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.dao.GreetingsDAO;
import com.samsung.fas.pir.persistence.models.Chapter;
import com.samsung.fas.pir.persistence.models.Greetings;
import com.samsung.fas.pir.rest.dto.GreetingsDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GreetingsBO extends BaseBO<Greetings, GreetingsDAO, GreetingsDTO, Long> {
	private	final ChapterDAO cdao;

	@Autowired
	public GreetingsBO(GreetingsDAO dao, @Autowired ChapterDAO cdao) {
		super(dao);
		this.cdao = cdao;
	}

	@Override
	public GreetingsDTO save(GreetingsDTO create, UserDetails account) {
		Greetings	model		= create.getModel();
		Chapter 	chapter		= cdao.findOne(create.getChapterUUID());
		model.setChapter(chapter);
		return new GreetingsDTO(getDao().save(model), true);
	}

	@Override
	public GreetingsDTO update(GreetingsDTO update, UserDetails account) {
		Greetings	model		= update.getModel();
		Greetings	greetings	= getDao().findOne(model.getUuid());

		greetings.setDescription(model.getDescription());
		greetings.setElectronics(model.isElectronics());
		greetings.setStove(model.isStove());
		greetings.setSit(model.isSit());
		greetings.setGoback(model.isGoback());

		return new GreetingsDTO(getDao().save(greetings), true);
	}

	@Override
	public Collection<GreetingsDTO> save(Collection<GreetingsDTO> create, UserDetails details) {
		return null;
	}

	@Override
	public Collection<GreetingsDTO> update(Collection<GreetingsDTO> update, UserDetails details) {
		return null;
	}
}
