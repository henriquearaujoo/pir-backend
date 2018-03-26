package com.samsung.fas.pir.rest.utils;

import com.samsung.fas.pir.persistence.models.Chapter;
import com.samsung.fas.pir.persistence.models.Conclusion;
import com.samsung.fas.pir.persistence.models.Question;

import java.util.Set;

public class CTools {
	public static float calculateChapterCompleteness(Chapter entity) {
		Conclusion 	c			= entity.getConclusion();
		float		complete 	= 25.0f;
		int			qa			= 0;

		if (c != null) {
			Set<Question> qs = c.getQuestions();
			complete += 12.5f;
			if (qs != null) {
				for (Question q : qs) {
					if (q.getAnswers() != null && q.getAnswers().size() > 0) {
						qa++;
					}
				}
				if (qs.size() != 0)
					complete += (12.5 * (qa/qs.size()) );
			}
		}

		if (entity.getGreetings() != null) {
			complete	+= 25.0f;
		}

		if (entity.getIntervention() != null) {
			complete	+= 25.0f;
		}

		return complete;
	}
}
