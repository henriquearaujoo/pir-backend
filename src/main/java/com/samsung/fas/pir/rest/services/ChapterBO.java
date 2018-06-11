package com.samsung.fas.pir.rest.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.RESTException;
import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.models.Chapter;
import com.samsung.fas.pir.rest.dto.ChapterDTO;
import com.samsung.fas.pir.rest.dto.ChapterDetailedDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
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
public class ChapterBO extends BaseBO<Chapter, ChapterDAO, ChapterDTO, Long> {
	@Autowired
	public ChapterBO(ChapterDAO dao) {
		super(dao);
	}

	// region find all valid
	public Collection<ChapterDTO> findAllValid() {
		return getDao().findAllValid().stream().map(item -> new ChapterDTO(item ,false)).collect(Collectors.toList());
	}

	public Collection<ChapterDTO> findAllValid(Predicate predicate) {
		return getDao().findAllValid(predicate).stream().map(item -> new ChapterDTO(item ,false)).collect(Collectors.toList());
	}

	public Page<ChapterDTO> findAllValid(Pageable pageable) {
		return getDao().findAllValid(pageable).map(item -> new ChapterDTO((Chapter) item,false));
	}

	public Page<ChapterDTO> findAllValid(Pageable pageable, Predicate predicate) {
		return getDao().findAllValid(predicate, pageable).map(item -> new ChapterDTO((Chapter) item,false));
	}

	public Collection<ChapterDetailedDTO> findAllValidDetailed() {
		return getDao().findAllValid().stream().map(item -> new ChapterDetailedDTO(item ,false)).collect(Collectors.toList());
	}

	public Collection<ChapterDetailedDTO> findAllValidDetailed(Predicate predicate) {
		return getDao().findAllValid(predicate).stream().map(item -> new ChapterDetailedDTO(item ,false)).collect(Collectors.toList());
	}

	public Page<ChapterDetailedDTO> findAllValidDetailed(Predicate predicate, Pageable pageable) {
		return getDao().findAllValid(predicate, pageable).map(item -> new ChapterDetailedDTO((Chapter) item,false));
	}
	// endregion

	// region find all invalid
	public Collection<ChapterDTO> findAllInvalid() {
		return getDao().findAllInvalid().stream().map(item -> new ChapterDTO(item ,false)).collect(Collectors.toList());
	}

	public Page<ChapterDTO> findAllInvalid(Pageable pageable) {
		return getDao().findAllInvalid(pageable).map(item -> new ChapterDTO((Chapter) item,false));
	}

	public Page<ChapterDTO> findAllInvalid(Pageable pageable, Predicate predicate) {
		return getDao().findAllInvalid(predicate, pageable).map(item -> new ChapterDTO((Chapter) item,false));
	}

	public Collection<ChapterDetailedDTO> findAllInvalidDetailed() {
		return getDao().findAllInvalid().stream().map(item -> new ChapterDetailedDTO(item ,false)).collect(Collectors.toList());
	}

	public Collection<ChapterDetailedDTO> findAllInvalidDetailed(Predicate predicate) {
		return getDao().findAllInvalid(predicate).stream().map(item -> new ChapterDetailedDTO(item ,false)).collect(Collectors.toList());
	}

	public Page<ChapterDetailedDTO> findAllInvalidDetailed(Predicate predicate, Pageable pageable) {
		return getDao().findAllInvalid(predicate, pageable).map(item -> new ChapterDetailedDTO((Chapter) item,false));
	}
	// endregion

	// region find all detailed
	public Collection<ChapterDetailedDTO> findAllDetailed() {
		return getDao().findAll().stream().map(item -> new ChapterDetailedDTO(item, true)).collect(Collectors.toList());
	}

	public Collection<ChapterDetailedDTO> findAllDetailed(Predicate predicate) {
		return getDao().findAll(predicate).stream().sorted(Comparator.comparing(o -> ((Chapter) o).getChapter()).thenComparing(o -> ((Chapter) o).getVersion())).map(item -> new ChapterDetailedDTO(item, true)).collect(Collectors.toList());
	}

	public Page<ChapterDetailedDTO> findAllDetailed(Pageable pageable) {
		return getDao().findAll(pageable).map(item -> new ChapterDetailedDTO(item, true));
	}

	public Page<ChapterDetailedDTO> findAllDetailed(Predicate predicate, Pageable pageable) {
		return getDao().findAll(predicate, pageable).map(item -> new ChapterDetailedDTO(item, true));
	}
	// endregion

	@Override
	public ChapterDTO save(ChapterDTO create, UserDetails account) {
		Chapter			model		= create.getModel();
		List<Chapter>	versions	= new ArrayList<>(getDao().findAllByChapter(create.getChapter()));

		// Version + 1
		if (versions.size() > 0) {
			versions.sort(Comparator.comparingInt(Chapter::getVersion).reversed());
			model.setVersion(versions.get(0).getVersion() + 1);
		}

		return new ChapterDTO(getDao().save(model), true);
	}

	@Override
	public ChapterDTO update(ChapterDTO update, UserDetails account) {
		Chapter			model		= update.getModel();
		Chapter			chapter		= getDao().findOne(model.getUuid());

		// If chapter version differs from persited chapter version
		if (chapter.getVersion() != model.getVersion())
			throw new RESTException("chapter.version.differs");

		// Check if the chapter will be active, if true, will invalidate the others
		if (CTools.calculateChapterCompleteness(chapter) == 100.0f) {
			getDao().invalidateAllChapters(model.getChapter());
			chapter.setValid(model.isValid());
		} else {
			chapter.setValid(false);
		}

		chapter.setPeriod(model.getPeriod());
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

		//noinspection ResultOfMethodCallIgnored
		chapter.getMedias().forEach(media -> model.getMedias().stream().filter(item -> item.getUuid() != null && media.getUuid().compareTo(item.getUuid()) == 0).noneMatch(item -> chapter.getMedias().add(item)));

		return new ChapterDTO(getDao().save(chapter), true);
	}

	@Override
	public Collection<ChapterDTO> save(Collection<ChapterDTO> create, UserDetails details) {
		return null;
	}

	@Override
	public Collection<ChapterDTO> update(Collection<ChapterDTO> update, UserDetails details) {
		return null;
	}
}
