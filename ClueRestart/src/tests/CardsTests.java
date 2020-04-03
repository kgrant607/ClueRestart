package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

public class CardsTests {
<<<<<<< HEAD
	private static Board board;
	@BeforeClass
	public static void Setup() throws FileNotFoundException, BadConfigFormatException, SecurityException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException{
	Board board = Board.getInstance();
=======
	private Board board;
	
	@Before
	public void Setup() throws FileNotFoundException, BadConfigFormatException, SecurityException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException{
	board = Board.getInstance();
>>>>>>> d68a82fc085dc325b34339c6761bd189d08d265b
	board.setConfigFiles("data/Layout1.csv", "data/Legend.txt", "data/Weapons.txt", "data/Players.txt");
	board.loadCardsConfig();
	}
	
	//check that the deck has the appropriate amount of cads
	@Test
	public void cardNumberTest() {
		int deckSize = 22;
		assert(deckSize == board.deck.size());
	}
	
	
	//make sure that each card has a type, and that the appropriate amounts of each card type exist
	@Test
	public void cardTypeTest() {
		int weapon=0;
		int player=0;
		int room=0;
		for(int i=0;i<board.deck.size();i++) {
			if(board.deck.get(i).type == CardType.PERSON) {
				player++;
			}
			else if(board.deck.get(i).type == CardType.WEAPON) {
				weapon++;
			}
			else if(board.deck.get(i).type == CardType.ROOM) {
				room++;
			}
		}
		assertEquals(room,10);
		assertEquals(weapon,6);
		assertEquals(player,6);
	}
	
	
	//check that one of each type of card is correct and contained in the deck
	@Test
	public void cardTest() {
		Card gun = new Card();
		gun.cardName="Gun";
		gun.type=CardType.WEAPON;
		if(!board.deck.contains(gun)){
			assert(false);
		}
		Card cpw = new Card();
		cpw.cardName = "CPW";
		cpw.type = CardType.PERSON;
		if(!board.deck.contains(cpw)) {
			assert(false);
		}
		Card green = new Card();
		green.cardName = "Green Center";
		green.type = CardType.ROOM;
		if(!board.deck.contains(green)) {
			assert(false);
		}
	}
	
	
	//check that all cards are dealt by adding all hands and comparing with appropriate size of deck
	@Test
	public void allCardsDealt() {
		board.deal();
		int cardCount = 0;
		for(Player player : board.players) {
			cardCount += player.myCards.size();
		}
		assertEquals(22, cardCount);
	}
	
	
	//compare all players hands to make sure they are within +/- 1 card of each other
	@Test
	public void cardsPerPlayer() {
		board.deal();
		for(Player player1 : board.players) {
			for(Player player2 : board.players) {
				assert(Math.abs(player1.myCards.size()-player2.myCards.size())<=1);
			}
		}
	}
	
	//make sure no card is duplicated when dealt
	@Test
	public void noDuplicateCards() {
		board.deal();
		int playerCount;
		for(Card card : board.deck) {
			playerCount=0;
			for(Player player : board.players) {
				if(player.myCards.contains(card)) {
				playerCount++;
				}
			}
			assert(playerCount<=1);
		}
	}
	
	//check that random players in the list are properly assigned 
	@Test
	public void checkPeople() {
		assert(board.players.get(0).name.equals("Baldwin"));
		assert(board.players.get(2).name.equals("CPW"));
		assert(board.players.get(5).name.equals("Paone"));
		
		System.out.println(board.players.get(0).color.toString());
		assert(board.players.get(0).color.equals(java.awt.Color.blue));
		assert(board.players.get(2).color.equals(java.awt.Color.red));
		assert(board.players.get(5).color.equals(java.awt.Color.pink));
		
		assert(board.players.get(0).row == 0);
		assert(board.players.get(0).column == 5);
		assert(board.players.get(5).row == 23);
		assert(board.players.get(5).column == 19);
	}
}
