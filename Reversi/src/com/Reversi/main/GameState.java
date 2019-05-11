package com.Reversi.main;

import java.io.IOException;
import java.util.Scanner;

public class GameState {

		public boolean player1Turn;
		public boolean player2Turn;
		GameBoard board;
		Scanner scanner = new Scanner(System.in);
	
		public GameState()
		{
			board = new GameBoard();
			player1Turn = true;
			player2Turn = false;
		}
		
		public void startGame() throws IOException
		{
			while(!board.gameOverCheck())
			{
				playTurn();
			}
			board.printBoard();
			printScores();
			System.out.println("Game Over!");
		}
		
		public void playTurn()
		{
			board.printBoard();
			printScores();
			if(player1Turn)
			{
				for(int x = 0; x <= 7; x++)
				{
					for(int y = 0; y<=7; y++)
					{
						if(board.validMove(x,y,1))
						{
							System.out.println("Making move: "+x+" "+y+" "+1);
							board.makeMove(x, y, 1);
							swapTurns();
							return;
						}
					}
				}
			}
			if(player2Turn)
			{
				for(int x = 7; x >= 0; x--)
				{
					for(int y = 7; y>=0; y--)
					{
						if(board.validMove(x,y,2))
						{
							System.out.println("Making move: "+x+" "+y+" "+2);
							board.makeMove(x, y, 2);
							swapTurns();
							return;
						}
					}
				}
			}
			System.out.println("No Valid Moves");
			swapTurns();
		}
		
		// Human based playTurn
		/*
		public void playTurn() throws IOException
		{
			board.printBoard();
			
			int player = 999;
			
			if(player1Turn)
			{
				player = 1;
			}
			else if(player2Turn)
			{
				player = 2;
			}
			
			System.out.println("Player "+player+"'s Turn");
			System.out.print("Please enter the depth coordinate for your move: ");
			int depth = scanner.nextInt();
			System.out.println("");
			System.out.print("Please enter the width coordinate for your move: ");
			int width = scanner.nextInt();
			System.out.println("");
			if(board.validMove(depth, width, player))
			{
				board.makeMove(depth, width, player);
				swapTurns();
			}
			else
			{
				System.out.println("Not a valid move");
			}
		}
		*/
		
		public void swapTurns()
		{
			player1Turn = !player1Turn;
			player2Turn = !player2Turn;
		}
		
		public void printScores()
		{
			board.checkScore(1);
			board.checkScore(2);
			System.out.println("");
		}
		
		
}
