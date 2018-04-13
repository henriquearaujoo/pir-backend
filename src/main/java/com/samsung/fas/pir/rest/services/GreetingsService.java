package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.dao.GreetingsDAO;
import com.samsung.fas.pir.persistence.models.Chapter;
import com.samsung.fas.pir.persistence.models.Greetings;
import com.samsung.fas.pir.rest.dto.GreetingsDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.rest.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GreetingsService extends BService<Greetings, GreetingsDTO, GreetingsDAO, Long> {
	private	final ChapterDAO cdao;

	@Autowired
	public GreetingsService(GreetingsDAO dao, @Autowired ChapterDAO cdao) {
		super(dao, Greetings.class, GreetingsDTO.class);
		this.cdao = cdao;
	}

	@Override
	public GreetingsDTO save(GreetingsDTO create, UserDetails account) {
		Greetings	model		= create.getModel();
		UUID 		chapterID	= IDCoder.decode(create.getChapterID());
		Chapter 	chapter		= cdao.findOne(chapterID);
		model.setChapter(chapter);
		return new GreetingsDTO(dao.save(model), true);
	}

	@Override
	public GreetingsDTO update(GreetingsDTO update, UserDetails account) {
		Greetings	model		= update.getModel();
		Greetings	greetings	= dao.findOne(model.getUuid());

		greetings.setDescription(model.getDescription());
		greetings.setEletronics(model.isEletronics());
		greetings.setStove(model.isStove());
		greetings.setSit(model.isSit());
		greetings.setGoback(model.isGoback());

		return new GreetingsDTO(dao.save(greetings), true);
	}
}
