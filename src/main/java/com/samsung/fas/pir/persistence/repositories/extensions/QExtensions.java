package com.samsung.fas.pir.persistence.repositories.extensions;

import com.querydsl.core.annotations.QueryDelegate;
import com.querydsl.core.types.Predicate;
import ext.java.util.QDate;

import java.util.*;

/*
 * Querydsl query extensions
 */
public class QExtensions {
	@QueryDelegate(Date.class)
	public static Predicate between(QDate date, Collection<? extends Date> collection) {
		Calendar 	l 		= Calendar.getInstance();
		List<Date>	dates	= new ArrayList<>(collection);

		dates.sort((date1, date2) -> date1 != null && date2 != null ? date1.compareTo(date2) : 0);

		l.setTime(dates.get(dates.size() - 1));
		l.set(Calendar.HOUR, 23);
		l.set(Calendar.MINUTE, 59);
		l.set(Calendar.SECOND, 59);
		l.set(Calendar.MILLISECOND, 999);

		return date.between(dates.get(0), new Date(l.getTimeInMillis()));
	}
}
