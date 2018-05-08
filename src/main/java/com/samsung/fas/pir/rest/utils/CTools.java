package com.samsung.fas.pir.rest.utils;

import com.samsung.fas.pir.persistence.models.Chapter;
import com.samsung.fas.pir.persistence.models.Conclusion;
import com.samsung.fas.pir.persistence.models.Question;

import java.util.Set;

public class CTools {
	public static float calculateChapterCompleteness(Chapter entity) {
		float	complete	= 25.0f;

		if (entity.getConclusion() != null) {
			complete 	+= 25.0f;
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
