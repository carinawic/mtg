import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/*
 * SYNTAX FOR DECK STORAGE 
 * deckName1:cardName1,cardUrl1.cardName2,cardUrl2...cardNameN,cardUrlN
 * deckName2:cardName1,cardUrl1.cardName2,cardUrl2...cardNameN,cardUrlN
 * .
 * .
 * .
 * deckNameN:cardName1,cardUrl1.cardName2,cardUrl2...cardNameN,cardUrlN
 */
public class FileHandler {

	private final static String fileName = "decks.txt";

	public static void storeDeck(Deck deck) {
		try {
			PrintWriter pw = new PrintWriter(fileName);

			ArrayList<Deck> oldDecks = fetchAllDecks();
			if (oldDecks != null) {
				StringBuilder sb = new StringBuilder();
				int i = 0;
				int numberOfDecks = oldDecks.size();
				for (Deck d : oldDecks) {
					sb.append(d.toString());
					i++;
					if (i < numberOfDecks) {
						sb.append("\n");
					}
				}
				sb.append(deck.toString());
				pw.write(sb.toString());
			} else {
				pw.write(deck.toString());
			}
			pw.close();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public static void storeDecks(Deck[] decks) {
		try {
			StringBuilder sb = new StringBuilder();
			PrintWriter pw = new PrintWriter(fileName);

			ArrayList<Deck> oldDecks = fetchAllDecks();
			if (oldDecks != null) {
				int i = 0;
				int numberOfOldDecks = oldDecks.size();
				for (Deck deck : oldDecks) {
					sb.append(deck.toString());
					i++;
					if (i < numberOfOldDecks) {
						sb.append("\n");
					}
				}
			}
			int numberOfNewDecks = decks.length;
			int i = 0;
			for (Deck deck : decks) {
				sb.append(deck.toString());
				i++;
				if (i < numberOfNewDecks) {
					sb.append(",");
				}
			}
			pw.write(sb.toString());
			pw.close();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public static Deck fetchDeck(String deckName) {
		ArrayList<Deck> allDecks = fetchAllDecks();
		if (allDecks != null) {
			for(Deck deck:allDecks) {
				if(deck.getDeckName().equals(deckName)) {
					return deck;
				}
			}
		}
		return null;
	}

	public static ArrayList<Deck> fetchAllDecks() {
		try {
			ArrayList<Deck> decks = new ArrayList<Deck>();
			
			ArrayList<String> stringDecks = new ArrayList<String>();
			
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String deckString = br.readLine();
		    while (deckString != null) {
		        stringDecks.add(deckString);
		        deckString = br.readLine();
		    }
		    br.close();
		    
		    for(String d:stringDecks) {
		    	String[] split = d.split(":");
		    	
		    	String deckName = split[0];
		    	
		    	ArrayList<Card> cards = new ArrayList<Card>();
		    	String[] stringCards = split[1].split(".");
		    	for(String c: stringCards) {
		    		split = c.split(",");
		    		cards.add(new Card(split[0],split[1]));
		    	}
		    	
		    	Deck deck = new Deck(deckName);
		    	for(Card card:cards) {
		    		deck.addCard(card);
		    	}
		    	decks.add(deck);
		    }
		    return decks;
		} catch (IOException e) {
			System.err.println(e);
		}
		return null;
	}
}
