package com.Reversi.main;

public class GameBoard {

	private int [][] board;
	
	//--------------------------Public methods-------------------------------
	
	//Instantiates a new 8x8 gameboard with empty cells (0).
	public GameBoard() {
		
		board = new int[8][8];
		for(int x = 0; x<8; x++)
		{
			for(int y=0; y<8; y++)
			{
				board[x][y] = 0;
			}
		}
		board[3][3] = 1;
		board[3][4] = 1;
		board[4][3] = 2;
		board[4][4] = 2;
	}
	
	//Copy constructor
	public GameBoard(GameBoard gb)
	{
		for(int x = 0; x<8; x++)
		{
			for(int y=0; y<8; y++)
			{
				board[x][y] = gb.getCell(x, y);
			}
		}
	}
	
	
	//Prints the current state of the gameboard.
	public void printBoard() {
		for(int x = 0; x<8; x++)
		{
			String tempRow = "";
			for(int y=0; y<8; y++)
			{
				tempRow += board[x][y];
			}
			System.out.println(tempRow);
		}
		System.out.println("");
	}
	
	
	//Checks to see if the selected cell is a valid move for the provided player.
	public boolean validMove(int x, int y, int player)
	{
		Cell temp = new Cell(x,y);
		if(board[x][y] == 0 && distanceCheck(temp,player) > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//Access to process move method.
	public void makeMove(int x, int y, int player)
	{
		Cell move = new Cell(x,y);
		processMove(move,player);
	}
	
	//Returns the current value of the selected cell.
	public int getCell(int x, int y)
	{
		return board[x][y];
	}
	
	//Checks to see if game is over.
	public boolean gameOverCheck()
	{
		if(checkBlackout(1))
		{
			return true;
		}
		else if(checkBlackout(2))
		{
			return true;
		}
		else if(checkFull())
		{
			return true;
		}
		else if(noValidMoves())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//Checks for any valid moves
	public boolean noValidMoves()
	{
		boolean noValidMoves = true;
		for(int x = 0; x<=7;x++)
		{
			for(int y=0;y<=7;y++)
			{
				if(validMove(x,y,1))
				{
					noValidMoves = false;
				}
				if(validMove(x,y,2))
				{
					noValidMoves = false;
				}
			}
		}
		return noValidMoves;
	}
	
	//Calculates and prints out score for selected player
	public void checkScore(int player)
	{
		int score = 0;
		for(int x = 0; x<=7;x++)
		{
			for(int y=0;y<=7;y++)
			{
				if(getCell(x,y) == player)
				{
					score++;
				}
			}
		}
		System.out.println("Player "+player+"'s Score: " +score);
	}
	
	//--------------------------------Private methods-------------------------------------------
	
	//Makes the selected move for the player. Checks for other tiles to be flipped and flips them.
	private void processMove(Cell move, int player)
	{
		changeCell(move,player);
		processUp(move,player);
		processDown(move,player);
		processLeft(move,player);
		processRight(move,player);
		processLowerLeft(move,player);
		processUpperLeft(move,player);
		processLowerRight(move,player);
		processUpperRight(move,player);
	}
	
	//Checks the upward direction and flips required tiles.
	private void processUp(Cell move, int player)
	{
		processDirection(move,player,-1,0);
	}
	
	//Checks the downward direction and flips required tiles.
	private void processDown(Cell move, int player)
	{
		processDirection(move,player,1,0);
	}
	
	//Checks the leftward direction and flips required tiles.
	private void processLeft(Cell move, int player)
	{
		processDirection(move,player,0,-1);
	}
	
	//Checks the rightward direction and flips required tiles.
	private void processRight(Cell move, int player)
	{
		processDirection(move,player,0,1);
	}
	
	//Checks the lowerleft direction and flips required tiles.
	private void processLowerLeft(Cell move, int player)
	{
		processDirection(move,player,1,-1);
	}
	
	//Checks the upperleft direction and flips required tiles.
	private void processUpperLeft(Cell move, int player)
	{
		processDirection(move,player,-1,-1);
	}
	
	//Checks the lowerright direction and flips required tiles.
	private void processLowerRight(Cell move, int player)
	{
		processDirection(move,player,1,1);
	}
	
	//Checks the upperright direction and flips required tiles.
	private void processUpperRight(Cell move, int player)
	{
		processDirection(move, player, -1, 1);
	}
	
	private void processDirection(Cell move, int player, int x_change, int y_change)
	{
		Cell foundCell = null;
		boolean cellFound = false;
		boolean noPath = false;
		int tempX = move.getX()+x_change;
		int tempY = move.getY()+y_change;
		while(!cellFound && !noPath && (tempY <= 7 && tempY >=0) && (tempX >= 0 && tempX <= 7))
		{
			if(board[tempX][tempY] == 0)
			{
				noPath = true;
			}
			else if(board[tempX][tempY] == player)
			{
				foundCell = new Cell(tempX,tempY);
				cellFound = true;
			}
			else
			{
				tempX = tempX + x_change;
				tempY = tempY + y_change;
			}
		}
		tempX = move.getX()+x_change;
		tempY = move.getY()+y_change;
		if(foundCell != null)
		{
			while(tempY != foundCell.getY() && tempX != foundCell.getX())
			{
				Cell temp = new Cell(tempX,tempY);
				changeCell(temp,player);
				tempX = tempX + x_change;
				tempY = tempY + y_change;
			}
		}
	}
	
	//Checks the leftward direction for a valid move.
	private int checkLeft(Cell move, int player)
	{
		Cell foundCell = null;
		boolean cellFound = false;
		boolean noPath = false;
		boolean loopEntered = false;
		int tempX = move.getX();
		int tempY = move.getY()-1;
		while(!cellFound && !noPath && tempY >= 0)
		{
			loopEntered = true;
			if(board[tempX][tempY] == 0)
			{
				noPath = true;
			}
			else if(board[tempX][tempY] == player)
			{
				foundCell = new Cell(tempX,tempY);
				cellFound = true;
			}
			else
			{
				tempY--;
			}
		}
		if(noPath)
		{
			return 0;
		}
		else if(!loopEntered)
		{
			return 0;
		}
		else if(foundCell != null)
		{
			int distance = move.getY() - foundCell.getY();
			if(distance > 1)
			{
				return distance;
			}
			return 0;
		}
		else
		{
			return 0;
		}
	}
	
	//Checks the upperleft direction for a valid move.
	private int checkUpperLeft(Cell move, int player)
	{
		Cell foundCell = null;
		boolean cellFound = false;
		boolean noPath = false;
		boolean loopEntered = false;
		int tempX = move.getX()-1;
		int tempY = move.getY()-1;
		while(!cellFound && !noPath && tempY >= 0 && tempX >= 0)
		{
			loopEntered = true;
			if(board[tempX][tempY] == 0)
			{
				noPath = true;
			}
			else if(board[tempX][tempY] == player)
			{
				foundCell = new Cell(tempX,tempY);
				cellFound = true;
			}
			else
			{
				tempX--;
				tempY--;
			}
		}
		if(noPath)
		{
			return 0;
		}
		else if(!loopEntered)
		{
			return 0;
		}
		else if (foundCell != null)
		{
			int distance = move.getY() - foundCell.getY();
			if(distance > 1)
			{
				return distance;
			}
			return 0;
		}
		else
		{
			return 0;
		}
	}
	
	//Checks the lowerleft direction for a valid move.
	private int checkLowerLeft(Cell move, int player)
	{
		Cell foundCell = null;
		boolean cellFound = false;
		boolean noPath = false;
		boolean loopEntered = false;
		int tempX = move.getX()+1;
		int tempY = move.getY()-1;
		while(!cellFound && !noPath && tempY >= 0 && tempX <= 7)
		{
			loopEntered = true;
			if(board[tempX][tempY] == 0)
			{
				noPath = true;
			}
			else if(board[tempX][tempY] == player)
			{
				foundCell = new Cell(tempX,tempY);
				cellFound = true;
			}
			else
			{
				tempX++;
				tempY--;
			}
		}
		if(noPath)
		{
			return 0;
		}
		else if(!loopEntered)
		{
			return 0;
		}
		else if(foundCell != null)
		{
			int distance = move.getY() - foundCell.getY();
			if(distance > 1)
			{
				return distance;
			}
			return 0;
		}
		else
		{
			return 0;
		}
	}
	
	//Checks the rightward direction for a valid move.
	private int checkRight(Cell move, int player)
	{
		Cell foundCell = null;
		boolean cellFound = false;
		boolean noPath = false;
		boolean loopEntered = false;
		int tempX = move.getX();
		int tempY = move.getY()+1;
		while(!cellFound && !noPath && tempY <= 7)
		{
			loopEntered = true;
			if(board[tempX][tempY] == 0)
			{
				noPath = true;
			}
			else if(board[tempX][tempY] == player)
			{
				foundCell = new Cell(tempX,tempY);
				cellFound = true;
			}
			else
			{
				tempY++;
			}
		}
		if(noPath)
		{
			return 0;
		}
		else if(!loopEntered)
		{
			return 0;
		}
		else if(foundCell != null)
		{
			int distance = foundCell.getY()-move.getY();
			if(distance > 1)
			{
				return distance;
			}
			return 0;
		}
		else
		{
			return 0;
		}
	}
	
	//Checks the upperright direction for a valid move.
	private int checkUpperRight(Cell move, int player)
	{
		Cell foundCell = null;
		boolean cellFound = false;
		boolean noPath = false;
		boolean loopEntered = false;
		int tempX = move.getX()-1;
		int tempY = move.getY()+1;
		while(!cellFound && !noPath && tempY <= 7 && tempX >= 0)
		{
			loopEntered = true;
			if(board[tempX][tempY] == 0)
			{
				noPath = true;
			}
			else if(board[tempX][tempY] == player)
			{
				foundCell = new Cell(tempX,tempY);
				cellFound = true;
			}
			else
			{
				tempX--;
				tempY++;
			}
		}
		if(noPath)
		{
			return 0;
		}
		else if(!loopEntered)
		{
			return 0;
		}
		else if (foundCell != null)
		{
			int distance = move.getX() - foundCell.getX();
			if(distance > 1)
			{
				return distance;
			}
			return 0;
		}
		else
		{
			return 0;
		}
	}
	
	//Checks the lowerright direction for a valid move.
	private int checkLowerRight(Cell move, int player)
	{
		Cell foundCell = null;
		boolean cellFound = false;
		boolean noPath = false;
		boolean loopEntered = false;
		int tempX = move.getX()+1;
		int tempY = move.getY()+1;
		while(!cellFound && !noPath && tempY <= 7 && tempX <= 7)
		{
			loopEntered = true;
			if(board[tempX][tempY] == 0)
			{
				noPath = true;
			}
			else if(board[tempX][tempY] == player)
			{
				foundCell = new Cell(tempX,tempY);
				cellFound = true;
			}
			else
			{
				tempX++;
				tempY++;
			}
		}
		if(noPath)
		{
			return 0;
		}
		else if(!loopEntered)
		{
			return 0;
		}
		else if(foundCell != null)
		{
			int distance =  foundCell.getX() - move.getX();
			if(distance > 1)
			{
				return distance;
			}
			return 0;
		}
		else
		{
			return 0;
		}
	}
	
	//Checks the upward direction for a valid move.
	private int checkUp(Cell move, int player)
	{
		Cell foundCell = null;
		boolean cellFound = false;
		boolean noPath = false;
		boolean loopEntered = false;
		int tempX = move.getX()-1;
		int tempY = move.getY();
		while(!cellFound && !noPath && tempX >= 0)
		{
			loopEntered = true;
			if(board[tempX][tempY] == 0)
			{
				noPath = true;
			}
			else if(board[tempX][tempY] == player)
			{
				foundCell = new Cell(tempX,tempY);
				cellFound = true;
			}
			else
			{
				tempX--;
			}
		}
		if(noPath)
		{
			return 0;
		}
		else if(!loopEntered)
		{
			return 0;
		}
		else if(foundCell != null)
		{
			int distance = move.getX()-foundCell.getX();
			if(distance > 1)
			{
				return distance;
			}
			return 0;
		}
		else
		{
			return 0;
		}
			
	}
	
	//Checks the downward direction for a valid move.
	private int checkDown(Cell move, int player)
	{
		Cell foundCell = null;
		boolean cellFound = false;
		boolean noPath = false;
		boolean loopEntered = false;
		int tempX = move.getX()+1;
		int tempY = move.getY();
		while(!cellFound && !noPath && tempX <= 7)
		{
			loopEntered = true;
			if(board[tempX][tempY] == 0)
			{
				noPath = true;
			}
			else if(board[tempX][tempY] == player)
			{
				foundCell = new Cell(tempX,tempY);
				cellFound = true;
			}
			else
			{
				tempX++;
			}
		}
		if(noPath)
		{
			return 0;
		}
		else if(!loopEntered)
		{
			return 0;
		}
		else if(foundCell != null)
		{
			int distance = foundCell.getX()-move.getX();
			if(distance > 1)
			{
				return distance;
			}
			return 0;
		}
		else
		{
			return 0;
		}
			
	}
	
	//Checks for a valid move if any direction has a distance > 1.
	private int distanceCheck(Cell move, int player)
	{
		return checkLeft(move,player) + checkRight(move,player) + checkLowerLeft(move,player) + checkLowerRight(move,player) + checkUpperRight(move,player) + checkUpperLeft(move,player)
		 + checkUp(move,player) + checkDown(move,player);
	}
	
	//Changes the value of the specified cell to the players value.
	private void changeCell(Cell move, int player)
	{
		board[move.getX()][move.getY()] = player;
	}
	
	//Checks for a blackout for the selected player
	private boolean checkBlackout(int player)
	{
		boolean blackout = true;
		for(int x = 0;x<=7; x++)
		{
			for(int y = 0; y <= 7; y++)
			{
				if(board[x][y] == player || board[x][y] == 0)
				{
					
				}
				else
				{
					blackout = false;
				}
			}
		}
		return blackout;
	}
	
	//Check if board is fully populated
	private boolean checkFull()
	{
		boolean full = true;
		for(int x = 0; x <= 7; x++)
		{
			for(int y = 0; y<=7;y++)
			{
				if(board[x][y] == 0)
				{
					full = false;
				}
			}
		}
		return full;
	}
	
}
