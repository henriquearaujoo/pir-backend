package com.samsung.fas.pir.rest.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.persistence.models.entity.MDataFile;
import com.samsung.fas.pir.rest.dto.chapter.CChapterDTO;
import com.samsung.fas.pir.rest.dto.chapter.RChapterDTO;
import com.samsung.fas.pir.rest.dto.chapter.UChapterDTO;
import com.samsung.fas.pir.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChapterService {
	private	final	ChapterDAO		cdao;

	@Autowired
	public ChapterService(ChapterDAO cdao) {
		this.cdao = cdao;
	}

	public RChapterDTO findOne(UUID id) {
		return RChapterDTO.toDTO(cdao.findOne(id));
	}

	public List<RChapterDTO> findAll() {
		return cdao.findAll().stream().map(RChapterDTO::toDTO).collect(Collectors.toList());
	}

	public List<RChapterDTO> findAllValid() {
		return cdao.findAllValid().stream().map(RChapterDTO::toDTO).collect(Collectors.toList());
	}

	public List<RChapterDTO> findAllValid(Predicate predicate) {
		return cdao.findAllValid(predicate).stream().map(RChapterDTO::toDTO).collect(Collectors.toList());
	}

	public List<RChapterDTO> findAllInvalid() {
		return cdao.findAllInvalid().stream().map(RChapterDTO::toDTO).collect(Collectors.toList());
	}

	public List<RChapterDTO> findAll(Predicate predicate) {
		return cdao.findAll(predicate).stream().map(RChapterDTO::toDTO).collect(Collectors.toList());
	}

	public Page<RChapterDTO> findAll(Pageable pageable) {
		return cdao.findAll(pageable).map(RChapterDTO::toDTO);
	}

	public Page<RChapterDTO> findAll(Predicate predicate, Pageable pageable) {
		return cdao.findAll(predicate, pageable).map(RChapterDTO::toDTO);
	}

	public Page<RChapterDTO> findAllValid(Pageable pageable) {
		return cdao.findAllValid(pageable).map(RChapterDTO::toDTO);
	}

	public Page<RChapterDTO> findAllValid(Pageable pageable, Predicate predicate) {
		return cdao.findAllValid(predicate, pageable).map(RChapterDTO::toDTO);
	}

	public Page<RChapterDTO> findAllInvalid(Pageable pageable) {
		return cdao.findAllInvalid(pageable).map(RChapterDTO::toDTO);
	}

	public Page<RChapterDTO> findAllInvalid(Pageable pageable, Predicate predicate) {
		return cdao.findAllInvalid(predicate, pageable).map(RChapterDTO::toDTO);
	}

	public RChapterDTO save(CChapterDTO dto) {
		List<Chapter>	versions	= cdao.findAllByChapter(dto.getChapter());
		Chapter			entity		= dto.getModel();

		if (versions.size() > 0) {
			// Chapter exists, sort by version
			versions.sort(Comparator.comparingInt(Chapter::getVersion).reversed());
			// Set last version + 1
			entity.setVersion(versions.get(0).getVersion() + 1);
		}

		// The above code will validate this, but we'll keep it anyway
		if (cdao.findOneByChapterAndVersion(entity.getChapter(), entity.getVersion()) != null)
			throw new RESTRuntimeException("chapter.exists");

		return RChapterDTO.toDTO(cdao.save(entity));
	}

	public RChapterDTO update(UChapterDTO dto) {
		Chapter					model		= dto.getModel();
		Chapter					chapter		= cdao.findOne(model.getUuid());

		// If chapter does not exist
		if (chapter == null)
			throw new RESTRuntimeException("chapter.notfound");

		// If chapter differs from persisted chapter
		if (chapter.getChapter() != model.getChapter())
			throw new RESTRuntimeException("chapter.differs");

		// If chapter version differs from persited chapter version
		if (chapter.getVersion() != model.getVersion())
			throw new RESTRuntimeException("chapter.version.differs");

		// Check if the chapter will be active, if true, will invalidate the others
		if (Tools.calculate(chapter) == 100.0f) {
			cdao.invalidateAllChapters(model.getChapter());
		} else {
			if (model.isValid()) {
				chapter.setValid(false);
			}
		}

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
		if (model.getMedias() != null) {
			if (chapter.getMedias() == null)
				chapter.setMedias(new ArrayList<>());
			chapter.getMedias().clear();
			chapter.getMedias().addAll(model.getMedias());
		}
		return RChapterDTO.toDTO(cdao.save(chapter));
	}
}
