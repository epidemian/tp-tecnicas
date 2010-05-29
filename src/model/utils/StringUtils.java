package model.utils;

import java.util.Collection;
import java.util.Iterator;

public final class StringUtils {

	public static String join(Collection<?> collection, String delimiter) {
		if (collection == null || collection.isEmpty())
			return "";
		StringBuffer buffer = new StringBuffer();
		Iterator<?> it = collection.iterator();
		buffer.append(it.next());
		while (it.hasNext())
			buffer.append(delimiter).append(it.next());
		return buffer.toString();
	}
	

	private StringUtils() {
	}
}
