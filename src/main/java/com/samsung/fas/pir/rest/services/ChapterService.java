package com.samsung.fas.pir.rest.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.rest.dto.chapter.CRUChapterDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChapterService extends BService<Chapter, CRUChapterDTO, ChapterDAO, Long> {
	@Autowired
	public ChapterService(ChapterDAO dao) {
		super(dao, Chapter.class, CRUChapterDTO.class);
	}

	public Collection<CRUChapterDTO> findAllValid() {
		return dao.findAllValid().stream().map(CRUChapterDTO::new).collect(Collectors.toSet());
	}

	public Collection<CRUChapterDTO> findAllValid(Predicate predicate) {
		return dao.findAllValid(predicate).stream().map(CRUChapterDTO::new).collect(Collectors.toSet());
	}

	public Collection<CRUChapterDTO> findAllInvalid() {
		return dao.findAllInvalid().stream().map(CRUChapterDTO::new).collect(Collectors.toSet());
	}

	public Page<CRUChapterDTO> findAllValid(Pageable pageable) {
		return dao.findAllValid(pageable).map(CRUChapterDTO::new);
	}

	public Page<CRUChapterDTO> findAllValid(Pageable pageable, Predicate predicate) {
		return dao.findAllValid(predicate, pageable).map(CRUChapterDTO::new);
	}

	public Page<CRUChapterDTO> findAllInvalid(Pageable pageable) {
		return dao.findAllInvalid(pageable).map(CRUChapterDTO::new);
	}

	public Page<CRUChapterDTO> findAllInvalid(Pageable pageable, Predicate predicate) {
		return dao.findAllInvalid(predicate, pageable).map(CRUChapterDTO::new);
	}

	@Override
	public CRUChapterDTO save(CRUChapterDTO create, Account account) {
		Chapter			model		= create.getModel();
		List<Chapter>	versions	= new ArrayList<>(dao.findAllByChapter(create.getChapter()));

		// Version + 1
		if (versions.size() > 0) {
			versions.sort(Comparator.comparingInt(Chapter::getVersion).reversed());
			model.setVersion(versions.get(0).getVersion() + 1);
		}

		return new CRUChapterDTO(dao.save(model));
	}

	@Override
	public CRUChapterDTO update(CRUChapterDTO update, Account account) {
		Chapter			model		= update.getModel();
		Chapter			chapter		= Optional.ofNullable(dao.findOne(Optional.ofNullable(model.getUuid()).orElseThrow(() -> new RESTRuntimeException("id.missing")))).orElseThrow(() -> new RESTRuntimeException("chapter.notfound"));

		// If chapter version differs from persited chapter version
		if (chapter.getVersion() != model.getVersion())
			throw new RESTRuntimeException("chapter.version.differs");

		// Check if the chapter will be active, if true, will invalidate the others
		if (Tools.calculate(chapter) == 100.0f) {
			dao.invalidateAllChapters(model.getChapter());
		} else {
			if (model.isValid()) {
				chapter.setValid(false);
			}
		}

		chapter.setChapter(model.getChapter());
		chapter.setTitle(model.getTitle());
		chapter.setSubtitle(model.getSubtitle());
		chapter.setResources(model.getResources());
		chapter.setDescription(model.getDescription());
		chapter.setContent(model.getContent());
		chapter.setPurpose(model.getPurpose());
		chapter.setFamilyTasks(model.getFamilyTasks());
		chapter.setEstimatedTime(model.getEstimatedTime());
		chapter.setTimeUntilNext(model.getTimeUntilNext());
		chapter.setValid(model.isValid());
		chapter.setMedias(model.getMedias());
		chapter.setThumbnails(model.getThumbnails());

		return new CRUChapterDTO(dao.save(model));
	}
}
