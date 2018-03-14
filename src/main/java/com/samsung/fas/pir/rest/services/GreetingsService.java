package com.samsung.fas.pir.rest.services;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.dao.GreetingsDAO;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.persistence.models.entity.Greetings;
import com.samsung.fas.pir.rest.dto.GreetingsDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GreetingsService extends BService<Greetings, GreetingsDTO, GreetingsDAO, Long> {
	private		ChapterDAO		cdao;

	@Autowired
	public GreetingsService(GreetingsDAO dao, ChapterDAO cdao) {
		super(dao, Greetings.class, GreetingsDTO.class);
		this.cdao = cdao;
	}

	@Override
	public GreetingsDTO save(GreetingsDTO create, UserDetails account) {
		Greetings	model		= create.getModel();
		UUID 		chapterID	= create.getChapterID() != null && !create.getChapterID().trim().isEmpty()? IDCoder.decode(create.getChapterID()) : null;
		Chapter		chapter		= Optional.ofNullable(cdao.findOne(Optional.ofNullable(chapterID).orElseThrow(() -> new RESTRuntimeException("chapter.id.missing")))).orElseThrow(() -> new RESTRuntimeException("chapter.notfound"));
		model.setChapter(chapter);
		return new GreetingsDTO(dao.save(model), true);
	}

	@Override
	public GreetingsDTO update(GreetingsDTO update, UserDetails account) {
		Greetings	model		= update.getModel();
		Greetings	greetings	= Optional.ofNullable(dao.findOne(Optional.ofNullable(model.getUuid()).orElseThrow(() -> new RESTRuntimeException("id.missing")))).orElseThrow(() -> new RESTRuntimeException("greetings.notfound"));

		greetings.setDescription(model.getDescription());
		greetings.setEletronics(model.isEletronics());
		greetings.setStove(model.isStove());
		greetings.setSit(model.isSit());
		greetings.setGoback(model.isGoback());

		return new GreetingsDTO(dao.save(greetings), true);
	}
}
