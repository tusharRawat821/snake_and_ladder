package com.lld.snake_and_ladder.util;

import java.util.Random;

public class RandomUtils {
	
	private RandomUtils() {}
	
	private static final Random random = new Random();
	
	public static int range(int start, int end) {
		return random.nextInt(end - start + 1) + start;
	}
	
}
