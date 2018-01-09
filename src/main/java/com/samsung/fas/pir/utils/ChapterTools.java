package com.samsung.fas.pir.utils;

import com.samsung.fas.pir.models.entity.Chapter;
import com.samsung.fas.pir.models.entity.Conclusion;
import com.samsung.fas.pir.models.entity.Question;

import java.util.Set;

public class ChapterTools {
	public static float calculate(Chapter entity) {
		Conclusion c			= entity.getConclusion();
		float		complete 	= 25.0f;

		if (c != null) {
			Set<Question> qs = c.getQuestions();
			complete += 12.5f;
			if (qs != null) {
				final int[] questionsWithAnswers = {0};
				qs.forEach(item -> {
					if (item.getAnswers() != null) {
						questionsWithAnswers[0]++;
					}
				});
				if (qs.size() != 0)
					complete += (12.5 * (questionsWithAnswers[0]/qs.size()) );
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
