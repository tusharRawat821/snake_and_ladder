package com.lld.snake_and_ladder.service;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import com.lld.snake_and_ladder.model.Board;
import com.lld.snake_and_ladder.model.Ladder;
import com.lld.snake_and_ladder.model.Player;
import com.lld.snake_and_ladder.model.Snake;
import com.lld.snake_and_ladder.util.RandomUtils;

// Game Service class - contains the core logic of game.
public class SnakeLadderGame {
	
	private Board board;
	private Queue<Player> players;
	private int playersCount;
	
	public SnakeLadderGame(int boardSize, int numOfSnakes, int numOfLadders) {
		this.players = new ArrayDeque<>();
		this.board = new Board(boardSize);
		setSnakeAndLadderOnBoard(numOfSnakes, numOfLadders);
	}
	
	// Automatically generates snakes and ladders on the board.
	private void setSnakeAndLadderOnBoard(int numOfSnakes, int numOfLadders) {
		Set<Integer> snakesOnBoard = new HashSet<>();
		while(numOfSnakes != 0) {
			int snakeHead = RandomUtils.range(board.getStart(), board.getEnd());
			int snakeTail = RandomUtils.range(board.getStart(), board.getEnd());
			
			// 2 snakes can't have the same head.
			if(snakeHead <= snakeTail || snakesOnBoard.contains(snakeHead)) 
				continue;
			
			board.addSnake(new Snake(snakeHead, snakeTail));
			snakesOnBoard.add(snakeHead);
			numOfSnakes -= 1;
		}
		Set<Integer> laddersOnBoard = new HashSet<>();
		while(numOfLadders != 0) {
			int ladderStart = RandomUtils.range(board.getStart(), board.getEnd());
			int ladderEnd   = RandomUtils.range(board.getStart(), board.getEnd());
			
			// 2 ladders can't have the same start 
			// Also, we are assuming that ladderEnd can't be same as snakeHead to prevent us from creating
			// a cycle of ladders and snake.
			if(ladderStart >= ladderEnd || laddersOnBoard.contains(ladderStart) || snakesOnBoard.contains(ladderEnd))
				continue;
			
			board.addLadder(new Ladder(ladderStart, ladderEnd));
			laddersOnBoard.add(ladderStart);
			numOfLadders -= 1;
		}
	}
	
	public void addPlayer(Player player) {
		this.players.add(player);
		this.playersCount += 1;
	}
	
	// runs the game play btw players.
	public void play() {
		while(!isGameCompleted()) {
			
			Player currentPlayer = players.poll();              // select player
			
			int totalDiceValue = getTotalValueAfterDiceRolls(); // player rolls the dice
									
			movePlayer(currentPlayer, totalDiceValue);          // player makes the move.
			
			if(hasPlayerWon(currentPlayer)) {                   // checks if player has won.
				currentPlayer.setWon(true);
				System.out.println("Player " + currentPlayer.getName() + " won.");
			} else {											// if player doesn't win, add back of queue
				players.offer(currentPlayer);
			}
		}
	}
	
	private boolean hasPlayerWon(Player player) {
		return player.getPosition() == board.getEnd(); // when player reaches the endPosition on board.
	}
	
	private void movePlayer(Player player, int positions) {
		int currentPosition = player.getPosition();
		int newPosition = currentPosition + positions;
		if(newPosition > board.getEnd())
			newPosition = currentPosition; // remains at the same position, newPosition is outside the board.
		else
			newPosition = getNewPositionAfterGoingThroughSnakesAndLadders(newPosition);
		
		player.setPosition(newPosition);
		System.out.println("Player " + player.getName() + " moves to " + newPosition);
	}
	
	// Keep moving until we find a position where there's no snakeHead/ladderStart.
	private int getNewPositionAfterGoingThroughSnakesAndLadders(int position) {
		for(Snake snake: board.getSnakes()) {
			if(snake.getHead() == position) {
				position = snake.getTail();
				System.out.println("Snake Bit");
				return getNewPositionAfterGoingThroughSnakesAndLadders(position);
			}
		}
		for(Ladder ladder: board.getLadders()) {
			if(ladder.getStart() == position) {
				position = ladder.getEnd();
				System.out.println("Climbed ladder");
				return getNewPositionAfterGoingThroughSnakesAndLadders(position);
			}
		}
		return position;
	}
	
	private int getTotalValueAfterDiceRolls() {
		// Optional : we can have 2-dice games and rolls the dice twice and returns the sum.
		return DiceService.roll();
	}
	
	
	private boolean isGameCompleted() {
		// Optional: we can also stop the game when first player wins.
		return this.players.size() < 2; // OR this.players.size() < playersCount
	}
}
