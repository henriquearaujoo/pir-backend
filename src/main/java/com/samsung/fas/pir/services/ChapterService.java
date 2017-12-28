package com.samsung.fas.pir.services;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.dao.ChapterDAO;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.models.dto.chapter.CChapterDTO;
import com.samsung.fas.pir.models.dto.chapter.RChapterDTO;
import com.samsung.fas.pir.models.dto.chapter.UChapterDTO;
import com.samsung.fas.pir.models.entity.Chapter;
import com.samsung.fas.pir.models.entity.Conclusion;
import com.samsung.fas.pir.models.entity.Question;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChapterService {
	private		ChapterDAO 			cdao;

	public ChapterService(ChapterDAO cdao) {
		this.cdao	= cdao;
	}

	public RChapterDTO findOne(String id) {
		return RChapterDTO.toDTO(cdao.findOne(IDCoder.decodeLong(id)));
	}

	public List<RChapterDTO> findAll() {
		return cdao.findAll().stream().map(RChapterDTO::toDTO).collect(Collectors.toList());
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

	public RChapterDTO save(CChapterDTO dto) {
		List<Chapter>	versions	= cdao.findAllByChapter(dto.getChapter());
		Chapter			entity		= dto.getModel();
		String			response	= null;

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
		Chapter			entity			= dto.getModel();
		Chapter			persisted		= cdao.findOne(entity.getId());
		Conclusion 		c				= entity.getConclusion();
		float 			untilComplete	= 25.0f;

		// If chapter does not exist
		if (persisted == null)
			throw new RESTRuntimeException("chapter.notfound");

		// If chapter differs from persisted chapter
		if (persisted.getChapter() != entity.getChapter())
			throw new RESTRuntimeException("chapter.differs");

		// If chapter version differs from persited chapter version
		if (persisted.getVersion() != entity.getVersion())
			throw new RESTRuntimeException("chapter.version.differs");

		if (c != null) {
			Set<Question> qs = c.getQuestions();
			untilComplete += 12.5f;
			if (qs != null) {
				final int[] questionsWithAnswers = {0};
				qs.forEach(item -> {
					if (item.getAnswers() != null) {
						questionsWithAnswers[0]++;
					}
				});
				if (qs.size() != 0)
					untilComplete += (100 * questionsWithAnswers[0]/qs.size())/12.5f;
			}
		}

		if (entity.getGreetings() != null) {
			untilComplete	+= 25.0f;
		}

		if (entity.getIntervention() != null) {
			untilComplete	+= 25.0f;
		}

		// Check if the chapter will be active, if true, will invalidate the others
		if (untilComplete == 100.0f) {
			cdao.invalidateAllChapters();
		} else {
			if (entity.isValid()) {
				entity.setValid(false);
			}
		}

		return RChapterDTO.toDTO(cdao.save(entity));
	}
}
