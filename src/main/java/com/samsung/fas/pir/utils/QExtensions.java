package com.samsung.fas.pir.utils;

import com.querydsl.core.annotations.QueryDelegate;
import com.querydsl.core.types.Predicate;
import ext.java.util.QDate;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/*
 * Querydsl query extensions
 */
public class QExtensions {
	@QueryDelegate(Date.class)
	public static Predicate between(QDate entity, Collection<? extends Date> collection) {
		Iterator<? extends Date> 	iterator 	= collection.iterator();
		Date						current		= null;
		Date						last		= null;
		Date						first		= null;

		while (iterator.hasNext()) {
			current = iterator.next();
			last 	= last == null? current : last;
			first	= first == null? current : first;
			last	= current.compareTo(last) > 0? current : last;
			first	= current.compareTo(first) < 0? current : first;
		}

		assert (last != null);

		Calendar l = Calendar.getInstance();
		l.setTime(last);
		l.set(Calendar.HOUR, 23);
		l.set(Calendar.MINUTE, 59);
		l.set(Calendar.SECOND, 59);
		l.set(Calendar.MILLISECOND, 999);
		last = new Date(l.getTimeInMillis());

		return entity.between(first, last);
	}
}
