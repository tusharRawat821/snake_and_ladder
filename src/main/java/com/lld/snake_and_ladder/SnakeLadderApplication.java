package com.lld.snake_and_ladder;

import java.util.Scanner;

import com.lld.snake_and_ladder.model.Player;
import com.lld.snake_and_ladder.service.SnakeLadderGame;

public class SnakeLadderApplication {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Game Starts..");

		System.out.print("Enter the board size: ");
		int boardSize = scanner.nextInt();

		System.out.print("Enter the number of snakes: ");
		int numOfSnakes = scanner.nextInt();

		System.out.print("Enter the number of ladders: ");
		int numOfLadders = scanner.nextInt();

		System.out.print("Enter the number of players: ");
		int numOfPlayers = scanner.nextInt();

		SnakeLadderGame game = new SnakeLadderGame(boardSize, numOfSnakes, numOfLadders);
		for (int p = 1; p <= numOfPlayers; p++) {
			System.out.print("Enter player's name: ");
			String pName = scanner.next();
			Player player = new Player(pName);
			game.addPlayer(player);
		}

		game.play();

		scanner.close();
	}
}
