package com.samsung.fas.pir.rest.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.ServiceException;
import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.models.Chapter;
import com.samsung.fas.pir.rest.dto.ChapterDTO;
import com.samsung.fas.pir.rest.services.base.BaseBO;
import com.samsung.fas.pir.rest.utils.CTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mobile.device.Device;
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
	public Collection<ChapterDTO> findAllValid(Device device) {
		return getDao().findAllValid().stream().map(item -> new ChapterDTO(item , device, false)).collect(Collectors.toList());
	}

	public Collection<ChapterDTO> findAllValid(Predicate predicate, Device device) {
		return getDao().findAllValid(predicate).stream().map(item -> new ChapterDTO(item , device, false)).collect(Collectors.toList());
	}

	public Page<ChapterDTO> findAllValid(Pageable pageable, Device device) {
		return getDao().findAllValid(pageable).map(item -> new ChapterDTO((Chapter) item, device, false));
	}

	public Page<ChapterDTO> findAllValid(Pageable pageable, Predicate predicate, Device device) {
		return getDao().findAllValid(predicate, pageable).map(item -> new ChapterDTO((Chapter) item, device, false));
	}

	public Collection<ChapterDTO> findAllValidDetailed(Device device) {
		return getDao().findAllValid().stream().map(item -> new ChapterDTO(item, device, false)).collect(Collectors.toList());
	}

	public Collection<ChapterDTO> findAllValidDetailed(Predicate predicate, Device device) {
		return getDao().findAllValid(predicate).stream().map(item -> new ChapterDTO(item, device, false)).collect(Collectors.toList());
	}

	public Page<ChapterDTO> findAllValidDetailed(Predicate predicate, Pageable pageable, Device device) {
		return getDao().findAllValid(predicate, pageable).map(item -> new ChapterDTO((Chapter) item, device, false));
	}
	// endregion

	// region find all invalid
	public Collection<ChapterDTO> findAllInvalid(Device device) {
		return getDao().findAllInvalid().stream().map(item -> new ChapterDTO(item, device, false)).collect(Collectors.toList());
	}

	public Page<ChapterDTO> findAllInvalid(Pageable pageable, Device device) {
		return getDao().findAllInvalid(pageable).map(item -> new ChapterDTO((Chapter) item, device, false));
	}

	public Page<ChapterDTO> findAllInvalid(Pageable pageable, Predicate predicate, Device device) {
		return getDao().findAllInvalid(predicate, pageable).map(item -> new ChapterDTO((Chapter) item, device, false));
	}

	public Collection<ChapterDTO> findAllInvalidDetailed(Device device) {
		return getDao().findAllInvalid().stream().map(item -> new ChapterDTO(item, device, false)).collect(Collectors.toList());
	}

	public Collection<ChapterDTO> findAllInvalidDetailed(Predicate predicate, Device device) {
		return getDao().findAllInvalid(predicate).stream().map(item -> new ChapterDTO(item, device, false)).collect(Collectors.toList());
	}

	public Page<ChapterDTO> findAllInvalidDetailed(Predicate predicate, Pageable pageable, Device device) {
		return getDao().findAllInvalid(predicate, pageable).map(item -> new ChapterDTO((Chapter) item, device, false));
	}
	// endregion

	// region find all detailed
	public Collection<ChapterDTO> findAllDetailed(Device device) {
		return getDao().findAll().stream().map(item -> new ChapterDTO(item, device, true)).collect(Collectors.toList());
	}

	public Collection<ChapterDTO> findAllDetailed(Predicate predicate, Device device) {
		return getDao().findAll(predicate).stream().sorted(Comparator.comparing(o -> ((Chapter) o).getChapter()).thenComparing(o -> ((Chapter) o).getVersion())).map(item -> new ChapterDTO(item, device, true)).collect(Collectors.toList());
	}

	public Page<ChapterDTO> findAllDetailed(Pageable pageable, Device device) {
		return getDao().findAll(pageable).map(item -> new ChapterDTO(item, device, true));
	}

	public Page<ChapterDTO> findAllDetailed(Predicate predicate, Pageable pageable, Device device) {
		return getDao().findAll(predicate, pageable).map(item -> new ChapterDTO(item, device, true));
	}
	// endregion

	@Override
	public ChapterDTO save(ChapterDTO create, Device device, UserDetails account) {
		Chapter			model		= create.getModel();
		List<Chapter>	versions	= new ArrayList<>(getDao().findAllByChapter(create.getChapter()));

		// Version + 1
		if (versions.size() > 0) {
			versions.sort(Comparator.comparingInt(Chapter::getVersion).reversed());
			model.setVersion(versions.get(0).getVersion() + 1);
		}

		return new ChapterDTO(getDao().save(model), device, true);
	}

	@Override
	public ChapterDTO update(ChapterDTO update, Device device, UserDetails account) {
		Chapter			model		= update.getModel();
		Chapter			chapter		= getDao().findOne(model.getUuid());

		// If chapter version differs from persited chapter version
		if (chapter.getVersion() != model.getVersion())
			throw new ServiceException("chapter.version.differs");

		// Check if the chapter will be active, if true, will invalidate the others
		if (CTools.calculateChapterCompleteness(chapter) == 100.0f) {
			getDao().invalidateAllChapters(model.getChapter());
			chapter.setValid(model.isValid());
		} else {
			chapter.setValid(false);
		}

		chapter.setAdditionalForms(model.getAdditionalForms());
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
		chapter.setMedias(model.getMedias().stream().filter(item -> chapter.getMedias().stream().filter(file -> file.getId() - item.getId() == 0).findAny().orElse(null) != null).collect(Collectors.toList()));
//		chapter.getMedias().addAll(model.getMedias().stream().filter(item -> chapter.getMedias().stream().filter(file -> file.getId() - item.getId() == 0).findAny().orElse(null) == null).collect(Collectors.toList()));

		return new ChapterDTO(getDao().save(chapter), device, true);
	}

	@Override
	public Collection<ChapterDTO> save(Collection<ChapterDTO> create, Device device, UserDetails details) {
		return null;
	}

	@Override
	public Collection<ChapterDTO> update(Collection<ChapterDTO> update, Device device, UserDetails details) {
		return null;
	}
}
