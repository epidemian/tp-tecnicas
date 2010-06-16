package model.utils;

import java.util.Collection;
import java.util.Iterator;

import model.exception.BusinessLogicException;

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
	
	public static char toChar(String string){
		ArgumentUtils.checkNotNull(string);
		if(string.length()>1){
			throw new BusinessLogicException
					("String length is greater than 1 so it cant be converted");
		}
		return string.charAt(0);
	}

	private StringUtils() {
	}
}
