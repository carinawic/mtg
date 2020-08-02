import java.util.Collections;
import java.util.Stack;

public class Deck {
	private String deckName;
	private Stack<Card> cards;
	private int numberOfCards;

	public Deck(String deckName) {
		this.deckName = deckName;
		this.cards = new Stack<Card>();
		numberOfCards = 0;
	}
	
	public void addCard(Card card) {
		cards.push(card);
		numberOfCards++;
	}
	
	public Card DrawTopCard() {
		if(!cards.isEmpty())
		{
			numberOfCards--;
			return cards.pop();
		}
		else {
			return null;
		}
	}
	
	public void setDeckName(String deckName) {
		this.deckName = deckName;
	}
	
	public String getDeckName() {
		return new String(deckName);
	}
	
	public void removeOneInstancesOfGivenCard(String cardName) {
		for(Card card : cards) {
			if(card.getName().equals(cardName)) {
				cards.remove(card);
				break;
			}
		}
	}
	public void removeAllInstancesOfGivenCard(String cardName) {
		for(Card card : cards) {
			if(card.getName().equals(cardName)) {
				cards.remove(card);
			}
		}
	}
	
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	//SYNTAX deckName:cardName1,cardUrl1.cardName2,cardUrl2...cardNameN,cardUrlN
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(deckName+":");
		int i = 0;
		for(Card card:cards) {
			sb.append(card.getName()+","+card.getUrl());
			i++;
			if(i < numberOfCards) {
				sb.append(".");
			}
		}
		return sb.toString();
	}
}
