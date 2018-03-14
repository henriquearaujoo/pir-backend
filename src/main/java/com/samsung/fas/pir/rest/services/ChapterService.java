package com.samsung.fas.pir.rest.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.rest.dto.ChapterDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChapterService extends BService<Chapter, ChapterDTO, ChapterDAO, Long> {
	@Autowired
	public ChapterService(ChapterDAO dao) {
		super(dao, Chapter.class, ChapterDTO.class);
	}

	public Collection<ChapterDTO> findAllValid() {
		return dao.findAllValid().stream().map(item -> new ChapterDTO(item ,false)).collect(Collectors.toSet());
	}

	public Collection<ChapterDTO> findAllValid(Predicate predicate) {
		return dao.findAllValid(predicate).stream().map(item -> new ChapterDTO(item ,false)).collect(Collectors.toSet());
	}

	public Collection<ChapterDTO> findAllInvalid() {
		return dao.findAllInvalid().stream().map(item -> new ChapterDTO(item ,false)).collect(Collectors.toSet());
	}

	public Page<ChapterDTO> findAllValid(Pageable pageable) {
		return dao.findAllValid(pageable).map(item -> new ChapterDTO(item ,false));
	}

	public Page<ChapterDTO> findAllValid(Pageable pageable, Predicate predicate) {
		return dao.findAllValid(predicate, pageable).map(item -> new ChapterDTO(item ,false));
	}

	public Page<ChapterDTO> findAllInvalid(Pageable pageable) {
		return dao.findAllInvalid(pageable).map(item -> new ChapterDTO(item ,false));
	}

	public Page<ChapterDTO> findAllInvalid(Pageable pageable, Predicate predicate) {
		return dao.findAllInvalid(predicate, pageable).map(item -> new ChapterDTO(item ,false));
	}

	@Override
	public ChapterDTO save(ChapterDTO create, UserDetails account) {
		Chapter			model		= create.getModel();
		List<Chapter>	versions	= new ArrayList<>(dao.findAllByChapter(create.getChapter()));

		// Version + 1
		if (versions.size() > 0) {
			versions.sort(Comparator.comparingInt(Chapter::getVersion).reversed());
			model.setVersion(versions.get(0).getVersion() + 1);
		}

		return new ChapterDTO(dao.save(model), true);
	}

	@Override
	public ChapterDTO update(ChapterDTO update, UserDetails account) {
		Chapter			model		= update.getModel();
		Chapter			chapter		= Optional.ofNullable(dao.findOne(Optional.ofNullable(model.getUuid()).orElseThrow(() -> new RESTRuntimeException("id.missing")))).orElseThrow(() -> new RESTRuntimeException("chapter.notfound"));

		// If chapter version differs from persited chapter version
		if (chapter.getVersion() != model.getVersion())
			throw new RESTRuntimeException("chapter.version.differs");

		// Check if the chapter will be active, if true, will invalidate the others
		if (Tools.calculate(chapter) == 100.0f) {
			dao.invalidateAllChapters(model.getChapter());
			chapter.setValid(model.isValid());
		} else {
			chapter.setValid(false);
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
		if (chapter.getMedias() == null)
			chapter.setMedias(new ArrayList<>());
		if (chapter.getThumbnails() == null)
			chapter.setThumbnails(new ArrayList<>());
		chapter.getMedias().clear();
		chapter.getThumbnails().clear();
		chapter.getMedias().addAll(model.getMedias() != null? model.getMedias() : new ArrayList<>());
		chapter.getThumbnails().addAll(model.getThumbnails() != null? model.getThumbnails() : new ArrayList<>());
		return new ChapterDTO(dao.save(chapter), true);
	}
}
