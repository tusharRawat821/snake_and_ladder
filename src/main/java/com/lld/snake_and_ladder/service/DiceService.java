package com.lld.snake_and_ladder.service;

import com.lld.snake_and_ladder.util.RandomUtils;

public class DiceService {
	
	private DiceService() {}
	
	private static final int MIN_VALUE = 1;
	private static final int MAX_VALUE = 6;
	
	public static int roll() {
		return RandomUtils.range(MIN_VALUE, MAX_VALUE);
	}
	
}
