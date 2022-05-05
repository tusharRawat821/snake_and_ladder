package com.lld.snake_and_ladder.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class Board {
	
	private final int size;
	private final int start;
	private final int end;
	
	private final List<Snake> snakes;
	private final List<Ladder> ladders;
	
	public Board(int size) {
		this.size = size;
		this.start = 1;
		this.end = start + size - 1;
		this.snakes = new ArrayList<>();
		this.ladders = new ArrayList<>();
	}
	
	public void addSnake(Snake snake) {
		this.snakes.add(snake);
	}
	
	public void addLadder(Ladder ladder) {
		this.ladders.add(ladder);
	}
	
}
