import java.io.File;
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
	
	public void clearDeck() {
		cards.clear();
	}
	
	public Stack<Card> getCards() {
		return cards;		
	}
	
	public Card PeekTopCard() {
		if(!cards.isEmpty())
		{
			return cards.peek();
		}
		else {
			return null;
		}
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

	public void removeTopCard() {
		if(!cards.isEmpty())
		{
			numberOfCards--;
			cards.pop();
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
	
	
	public int getNumberOfCards() {
		return numberOfCards;
	}
	
	public boolean hasCards() {
		return numberOfCards > 0;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(deckName+"|");
		int i = 0;
		for(Card card:cards) {
			sb.append(card.getName()+";"+card.getUrl());
			i++;
			if(i < numberOfCards) {
				sb.append("?");
			}
		}
		return sb.toString();
	}
}
