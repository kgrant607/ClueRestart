package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.Solution;

public class SuggestionTests {
private Board board;
	
	@Before
	public void Setup() throws FileNotFoundException, BadConfigFormatException, SecurityException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException{
	board = Board.getInstance();
	board.setConfigFiles("data/Layout1.csv", "data/Legend.txt", "data/Weapons.txt", "data/Players.txt");
	board.loadCardsConfig();
	}
	@Test
	public void testAccusation() {
		board.solution = new Solution(board.deck.get(0),board.deck.get(6),board.deck.get(20));
		assert(board.testAccusation(board.getSolution()));
		
		Solution test = new Solution(board.deck.get(1),board.deck.get(6),board.deck.get(20));
		assert(!board.testAccusation(test));
		
		test = new Solution(board.deck.get(0),board.deck.get(8),board.deck.get(20));
		assert(!board.testAccusation(test));
		
		test = new Solution(board.deck.get(0),board.deck.get(6),board.deck.get(18));
		assert(!board.testAccusation(test));
	}
	
	
	@Test
	public void createSuggestion() {
		((ComputerPlayer) board.players.get(2)).unseen.add(board.deck.get(0));
		((ComputerPlayer) board.players.get(2)).unseen.add(board.deck.get(1));
		((ComputerPlayer) board.players.get(2)).unseen.add(board.deck.get(2));
		((ComputerPlayer) board.players.get(2)).unseen.add(board.deck.get(6));
		((ComputerPlayer) board.players.get(2)).unseen.add(board.deck.get(7));
		((ComputerPlayer) board.players.get(2)).unseen.add(board.deck.get(8));
		((ComputerPlayer) board.players.get(2)).unseen.add(board.deck.get(18));
		((ComputerPlayer) board.players.get(2)).unseen.add(board.deck.get(19));
		((ComputerPlayer) board.players.get(2)).unseen.add(board.deck.get(20));

		int weapons =0;
		int person = 0;
		for(int i =0;i<100;i++) {
			Solution test = ((ComputerPlayer) board.players.get(2)).createSuggestion();
			if(test.person == board.deck.get(6)) {
				person++;
			}
			if(test.weapon == board.deck.get(0)) {
				weapons++;
			}
		}
		assert(person>20);
		assert(weapons>20);
	}
	
	@Test
	public void disproveSuggestion() {
		board.players.get(2).myCards.add(board.deck.get(0));
		board.players.get(2).myCards.add(board.deck.get(6));
		board.players.get(2).myCards.add(board.deck.get(20));
		
		Solution test = new Solution(board.deck.get(1),board.deck.get(7),board.deck.get(19));
		assert(((ComputerPlayer) board.players.get(2)).disproveSuggestion(test)==null);
		
		test = new Solution(board.deck.get(0),board.deck.get(7),board.deck.get(19));
		assert(((ComputerPlayer) board.players.get(2)).disproveSuggestion(test)==board.deck.get(0));
		
		test = new Solution(board.deck.get(0),board.deck.get(6),board.deck.get(19));
		int counter=0;
		for(int i=0;i<100;i++) {
			if(((ComputerPlayer) board.players.get(2)).disproveSuggestion(test)==board.deck.get(0)) {
				counter++;
			}
		}
		assert(counter>35);
	}
	
	@Test
	public void handleSuggestions() {
		board.players.get(0).myCards.add(board.deck.get(0));
		board.players.get(0).myCards.add(board.deck.get(6));
		board.players.get(0).myCards.add(board.deck.get(20));
		
		board.players.get(1).myCards.add(board.deck.get(2));
		board.players.get(1).myCards.add(board.deck.get(7));
		board.players.get(1).myCards.add(board.deck.get(19));
		
		board.players.get(2).myCards.add(board.deck.get(3));
		board.players.get(2).myCards.add(board.deck.get(8));
		board.players.get(2).myCards.add(board.deck.get(18));
		
		Solution test = new Solution(board.deck.get(4),board.deck.get(9),board.deck.get(17));
		assert(board.handleSuggestion(0, test)==null );
		
		//no one can disprove, return null
		test = new Solution(board.deck.get(0),board.deck.get(6),board.deck.get(20));
		assert(board.handleSuggestion(0, test)==null );
		
		//only human can disprove, human is not accuser. return card 
		test = new Solution(board.deck.get(0),board.deck.get(9),board.deck.get(17));
		assert(board.handleSuggestion(1, test)== board.deck.get(0));
		
		//only human can disprove, human is accuser. return null
		test = new Solution(board.deck.get(0),board.deck.get(9),board.deck.get(17));
		assert(board.handleSuggestion(0, test)== null);
		
		test = new Solution(board.deck.get(2),board.deck.get(8),board.deck.get(17));
		assert(board.handleSuggestion(0, test)== board.deck.get(2));
		
		test = new Solution(board.deck.get(0),board.deck.get(7),board.deck.get(17));
		assert(board.handleSuggestion(2, test)== board.deck.get(0));
	}
}
