package clueGame;

public class Card {
	public String cardName;
	public CardType type;
	@Override
	public boolean equals(Object o) {
		if(o instanceof Card) {
			if((((Card) o).cardName.contentEquals(cardName)) && ((Card) o).type== this.type){
				return true;
			}
		}
		return false;
	}
}
