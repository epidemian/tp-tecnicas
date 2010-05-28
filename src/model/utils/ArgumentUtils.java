package model.utils;

import model.exception.BusinessLogicException;

public final class ArgumentUtils {

	public static <T> T checkNotNull(T arg, String name) {
		if (arg == null)
			raise(arg, name, "must not be null");
		return arg;
	}

	public static <T> T checkNotNull(T arg) {
		return checkNotNull(arg, null);
	}

	public static int checkGreaterThan(int arg, int min, String name) {
		if (arg <= min)
			raise(arg, name, "must be greater than " + min);
		return arg;
	}

	public static int checkGreaterThan(int arg, int min) {
		return checkGreaterThan(arg, min, null);
	}

	public static int checkGreaterEqual(int arg, int min, String name) {
		if (arg < min)
			raise(arg, name, "must be greater than or equal to " + min);
		return arg;
	}

	public static int checkGreaterEqual(int arg, int min) {
		return checkGreaterEqual(arg, min, null);
	}

	public static int checkLessThan(int arg, int max, String name) {
		if (arg >= max)
			raise(arg, name, "must be less than " + max);
		return arg;
	}

	public static int checkLessThan(int arg, int max) {
		return checkLessThan(arg, max, null);
	}

	public static int checkLessEqual(int arg, int max, String name) {
		if (arg > max)
			raise(arg, name, "must be less than or equal to " + max);
		return arg;
	}

	public static int checkLessEqual(int arg, int max) {
		return checkLessEqual(arg, max, null);
	}

	public static int checkInRange(int arg, int min, int max, String name) {
		if (arg < min || max < arg)
			raise(arg, name, "must be in range [" + min + ", " + max + "]");
		return arg;
	}

	public static int checkInRange(int arg, int min, int max) {
		return checkInRange(arg, min, max, null);
	}

	public static <T> T checkArgCondition(T arg, boolean condition,
			String message) {
		if (!condition)
			raise(arg, null, message);
		return arg;
	}

	public static <T> T checkArgCondition(T arg, boolean condition) {
		return checkArgCondition(arg, condition, "");
	}

	private static void raise(Object arg, String name, String message) {
		throw new BusinessLogicException("Illegal argument " + arg + ": "
				+ (name == null ? "" : name + " ") + message);
	}

	private ArgumentUtils() {
	}

}
