package com.samsung.fas.pir.rest.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.persistence.dao.ChapterDAO;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.persistence.repository.IFileRepository;
import com.samsung.fas.pir.rest.dto.chapter.CChapterDTO;
import com.samsung.fas.pir.rest.dto.chapter.RChapterDTO;
import com.samsung.fas.pir.rest.dto.chapter.UChapterDTO;
import com.samsung.fas.pir.utils.IDCoder;
import com.samsung.fas.pir.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChapterService {
	@Autowired
	private		ChapterDAO 			cdao;

	@Autowired
	private 	IFileRepository		frepository;

	public RChapterDTO findOne(String id) {
		return RChapterDTO.toDTO(cdao.findOne(IDCoder.decodeLong(id)));
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

	// TODO: Rewrite this
	public RChapterDTO update(UChapterDTO dto) {
		Chapter					entity			= dto.getModel();
		Chapter					persisted		= cdao.findOne(entity.getId());

		// If chapter does not exist
		if (persisted == null)
			throw new RESTRuntimeException("chapter.notfound");

		// If chapter differs from persisted chapter
		if (persisted.getChapter() != entity.getChapter())
			throw new RESTRuntimeException("chapter.differs");

		// If chapter version differs from persited chapter version
		if (persisted.getVersion() != entity.getVersion())
			throw new RESTRuntimeException("chapter.version.differs");

		// Check if the chapter will be active, if true, will invalidate the others
		if (Tools.calculate(persisted) == 100.0f) {
			cdao.invalidateAllChapters(entity.getChapter());
		} else {
			if (entity.isValid()) {
				entity.setValid(false);
			}
		}

		if (entity.getMedias() != null && entity.getMedias().size() > 0) {
			entity.getMedias().forEach(item -> item.setChapter(persisted.getId()));
		}

		if (entity.getThumbnails() != null && entity.getThumbnails().size() > 0) {
			entity.getThumbnails().forEach(item -> item.setChapter(persisted.getId()));
		}

		return RChapterDTO.toDTO(cdao.save(entity));
	}
}
