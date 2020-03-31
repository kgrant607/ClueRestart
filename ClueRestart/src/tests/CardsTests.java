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
	private static Board board;
	@BeforeClass
	public void Setup() throws FileNotFoundException, BadConfigFormatException{
	Board board = Board.getInstance();
	board.setConfigFiles("data/Layout1.csv", "data/Legend.txt", "data/Weapons.txt", "data/Players.txt");
	board.loadCardsConfig();
	}
	@Test
	public void cardNumberTest() {
		assertEquals(24, board.deck.length());
	}
	
	public void cardTypeTest() {
		int weapon=0;
		int player=0;
		int room=0;
		for(int i=0;i<board.deck.length(),i++) {
			if(board.deck.at(i).type == CardType.PERSON) {
				player++;
			}
			else if(board.deck.at(i).type == CardType.WEAPON) {
				weapon++;
			}
			else if(board.deck.at(i).type == CardType.ROOM) {
				room++;
			}
		}
		assertEquals(room,12);
		assertEquals(weapon,6);
		assertEquals(player,6);
	}
	
	
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
	
	public void allCardsDealt() {
		board.deal();
		int cardCount;
		for(Player player : board.Players) {
			cardCount += player.myCards.length();
		}
		cardCount+=3;
		assertEquals(24, cardCount);
	}

	public void cardsPerPlayer() {
		board.deal();
		for(Player player1 : board.players) {
			for(Player player2 : board.players) {
				assert(Math.abs(player1.myCards.length()-player2.myCards.length)<=1);
			}
		}
	}
	
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
	
	public void checkPeople() {
		assert(board.players.at(0).name.equals("Baldwin"));
		assert(board.players.at(2).name.equals("CPW"));
		assert(board.players.at(5).name.equals("Paone"));
		
		assert(board.players.at(0).color.toString().equals("Blue"));
		assert(board.players.at(2).color.toString().equals("Red"));
		assert(board.players.at(5).color.toString().equals("Pink"));
		
		assert(board.players.at(0).row == 0);
		assert(board.players.at(1).column == 5);
		assert(board.players.at(5).row == 23);
		assert(board.players.at(5).column == 19);
	}
}
