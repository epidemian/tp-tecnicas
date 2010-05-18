package model.core;

public final class ArgumentUtils {

	public static void checkNotNull(Object arg, String name) {
		if (arg == null)
			raise(arg, name, "must not be null");
	}

	public static void checkNotNull(Object arg) {
		checkNotNull(arg, null);
	}

	public static void checkGreaterThan(int arg, int min, String name) {
		if (arg <= min)
			raise(arg, name, "must be greater than " + min);
	}

	public static void checkGreaterThan(int arg, int min) {
		checkGreaterThan(arg, min, null);
	}

	public static void checkGreaterEqual(int arg, int min, String name) {
		if (arg < min)
			raise(arg, name, "must be greater than or equal to " + min);
	}

	public static void checkGreaterEqual(int arg, int min) {
		checkGreaterEqual(arg, min, null);
	}

	public static void checkLessThan(int arg, int max, String name) {
		if (arg >= max)
			raise(arg, name, "must be less than " + max);
	}

	public static void checkLessThan(int arg, int max) {
		checkLessThan(arg, max, null);
	}

	public static void checkLessEqual(int arg, int max, String name) {
		if (arg > max)
			raise(arg, name, "must be less than or equal to " + max);
	}

	public static void checkLessEqual(int arg, int max) {
		checkLessEqual(arg, max, null);
	}

	public static void checkInRange(int arg, int min, int max, String name) {
		if (arg < min || max < arg)
			raise(arg, name, "must be in range [" + min + ", " + max + "]");
	}

	public static void checkInRange(int arg, int min, int max) {
		checkInRange(arg, min, max, null);
	}

	public static void checkArgCondition(Object arg, boolean condition,
			String message) {
		if (!condition)
			raise(arg, null, message);
	}

	public static void checkArgCondition(Object arg, boolean condition) {
		checkArgCondition(arg, condition, "");
	}

	private static void raise(Object arg, String name, String message) {
		throw new BusinessLogicException("Illegal argument " + arg + ": "
				+ (name == null ? "" : name + " ") + message);
	}

	private ArgumentUtils() {
	}

}
