package model.utils;

import model.exception.BusinessLogicException;

public class RandomUtils {
	
	/**
	 * Generates a random boolean with chance probability of being true.
	 * 
	 * @param chance
	 * @return
	 */
	public static boolean randomBoolean(double chance) {
		if (chance < 0 || 1.0 < chance)
			throw new BusinessLogicException("Invalid chance");
		return Math.random() < chance;
	}
	
	public static boolean randomBoolean() {
		return randomBoolean(0.5);
	}
}
