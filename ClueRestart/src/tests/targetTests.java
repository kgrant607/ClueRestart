package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ComputerPlayer;

public class targetTests {
	private Board board;
	
	@Before
	public void Setup() throws FileNotFoundException, BadConfigFormatException, SecurityException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException{
		board = Board.getInstance();
		board.setConfigFiles("data/Layout1.csv", "data/Legend.txt", "data/Weapons.txt", "data/Players.txt");
		board.loadCardsConfig();
	}
	@Test
	public void noRoomsInList() {
		board.players.get(2).column = 11;
		board.players.get(2).row = 6;
		int roll = 2;
		board.calcTargets(board.players.get(2).row, board.players.get(2).column, roll);
		((ComputerPlayer) board.players.get(2)).move(board.getTargets());
		assert(!board.getBoard()[6][11].isRoom());
	}
	
	@Test
	public void roomTests() {
		board.players.get(2).column = 14;
		board.players.get(2).row = 3;
		int roll = 2;
		char currentRoom = board.getBoard()[3][14].getInitial();
		board.calcTargets(board.players.get(2).row, board.players.get(2).column, roll);
		((ComputerPlayer) board.players.get(2)).move(board.getTargets());
		board.calcTargets(board.players.get(2).row, board.players.get(2).column, roll);
		int probability=0;
		for(int i=0;i<100;i++) {
			((ComputerPlayer) board.players.get(2)).move(board.getTargets());
			if(board.getBoard()[board.players.get(2).row][board.players.get(2).column].getInitial() == currentRoom) {
				probability++;
			}
		}
		assert(.1>Math.abs(((probability/100.0)-1.0/board.getTargets().size())));
	}
	
	@Test
	public void enterRoom() {
		board.players.get(2).column = 14;
		board.players.get(2).row = 17;
		int roll = 2;
		board.calcTargets(board.players.get(2).row, board.players.get(2).column, roll);
		((ComputerPlayer) board.players.get(2)).move(board.getTargets());
		assert(board.getBoard()[board.players.get(2).row][board.players.get(2).column].isRoom());
	}
}
