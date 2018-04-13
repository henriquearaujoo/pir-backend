package com.samsung.fas.pir.rest.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.RESTException;
import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.models.Chapter;
import com.samsung.fas.pir.rest.dto.ChapterDTO;
import com.samsung.fas.pir.rest.dto.ChapterDetailedDTO;
import com.samsung.fas.pir.rest.services.base.BService;
import com.samsung.fas.pir.rest.utils.CTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChapterService extends BService<Chapter, ChapterDTO, ChapterDAO, Long> {
	@Autowired
	public ChapterService(ChapterDAO dao) {
		super(dao, Chapter.class, ChapterDTO.class);
	}

	// region find all valid
	public Collection<ChapterDTO> findAllValid() {
		return dao.findAllValid().stream().map(item -> new ChapterDTO(item ,false)).collect(Collectors.toSet());
	}

	public Collection<ChapterDTO> findAllValid(Predicate predicate) {
		return dao.findAllValid(predicate).stream().map(item -> new ChapterDTO(item ,false)).collect(Collectors.toSet());
	}

	public Page<ChapterDTO> findAllValid(Pageable pageable) {
		return dao.findAllValid(pageable).map(item -> new ChapterDTO((Chapter) item,false));
	}

	public Page<ChapterDTO> findAllValid(Pageable pageable, Predicate predicate) {
		return dao.findAllValid(predicate, pageable).map(item -> new ChapterDTO((Chapter) item,false));
	}

	public Collection<ChapterDetailedDTO> findAllValidDetailed() {
		return dao.findAllValid().stream().map(item -> new ChapterDetailedDTO(item ,false)).collect(Collectors.toSet());
	}

	public Collection<ChapterDetailedDTO> findAllValidDetailed(Predicate predicate) {
		return dao.findAllValid(predicate).stream().map(item -> new ChapterDetailedDTO(item ,false)).collect(Collectors.toSet());
	}

	public Page<ChapterDetailedDTO> findAllValidDetailed(Predicate predicate, Pageable pageable) {
		return dao.findAllValid(predicate, pageable).map(item -> new ChapterDetailedDTO((Chapter) item,false));
	}
	// endregion

	// region find all invalid
	public Collection<ChapterDTO> findAllInvalid() {
		return dao.findAllInvalid().stream().map(item -> new ChapterDTO(item ,false)).collect(Collectors.toSet());
	}

	public Page<ChapterDTO> findAllInvalid(Pageable pageable) {
		return dao.findAllInvalid(pageable).map(item -> new ChapterDTO((Chapter) item,false));
	}

	public Page<ChapterDTO> findAllInvalid(Pageable pageable, Predicate predicate) {
		return dao.findAllInvalid(predicate, pageable).map(item -> new ChapterDTO((Chapter) item,false));
	}

	public Collection<ChapterDetailedDTO> findAllInvalidDetailed() {
		return dao.findAllInvalid().stream().map(item -> new ChapterDetailedDTO(item ,false)).collect(Collectors.toSet());
	}

	public Collection<ChapterDetailedDTO> findAllInvalidDetailed(Predicate predicate) {
		return dao.findAllInvalid(predicate).stream().map(item -> new ChapterDetailedDTO(item ,false)).collect(Collectors.toSet());
	}

	public Page<ChapterDetailedDTO> findAllInvalidDetailed(Predicate predicate, Pageable pageable) {
		return dao.findAllInvalid(predicate, pageable).map(item -> new ChapterDetailedDTO((Chapter) item,false));
	}
	// endregion

	// region find all detailed
	public Collection<ChapterDetailedDTO> findAllDetailed() {
		return dao.findAll().stream().map(item -> new ChapterDetailedDTO(item, true)).collect(Collectors.toSet());
	}

	public Collection<ChapterDetailedDTO> findAllDetailed(Predicate predicate) {
		return dao.findAll(predicate).stream().map(item -> new ChapterDetailedDTO(item, true)).collect(Collectors.toSet());
	}

	public Page<ChapterDetailedDTO> findAllDetailed(Pageable pageable) {
		return dao.findAll(pageable).map(item -> new ChapterDetailedDTO(item, true));
	}

	public Page<ChapterDetailedDTO> findAllDetailed(Predicate predicate, Pageable pageable) {
		return dao.findAll(predicate, pageable).map(item -> new ChapterDetailedDTO(item, true));
	}
	// endregion

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
		Chapter			chapter		= dao.findOne(model.getUuid());

		// If chapter version differs from persited chapter version
		if (chapter.getVersion() != model.getVersion())
			throw new RESTException("chapter.version.differs");

		// Check if the chapter will be active, if true, will invalidate the others
		if (CTools.calculateChapterCompleteness(chapter) == 100.0f) {
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
