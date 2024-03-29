import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.FileOutputStream;

public class FileHandler {

	// Separate deck name and cards.
	public static final String separator1 = "separator1";
	// Separate cards.
	public static final String separator2 = "separator2";
	// Separate card name and url.
	public static final String separator3 = "separator3";
	// Separate decks.
	public static final String separator4 = "separator4";

	private final static String fileName = "decks.txt";

	public static void storeDeck(Deck deck) {

		try {
			ArrayList<Deck> oldDecks = fetchAllDecks();

			PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));

			if (!oldDecks.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				int i = 0;
				for (Deck d : oldDecks) {
					sb.append(d.toString());
					if (i < oldDecks.size()) {
						sb.append(separator4);
					}
					i++;
				}
				sb.append(deck.toString());
				pw.write(sb.toString());
			} else {
				pw.write(deck.toString());
			}
			System.out.println("FileHandler stored: " + deck.toString() + "\n");
			pw.close();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public static void deleteDeck(String deckName) {
		try {
			ArrayList<Deck> allDecks = fetchAllDecks();
			for (Deck deck : allDecks) {
				if (deck.getDeckName().equals(deckName)) {
					allDecks.remove(deck);
					// DEBUG
					System.out.println("FileHandler deleted: " + deck.toString() + "\n");
					break;
				}
			}
			PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));

			if (!allDecks.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				int i = 0;
				for (Deck d : allDecks) {
					sb.append(d.toString());
					if (i < allDecks.size()) {
						sb.append(separator4);
					}
					i++;
				}
				pw.write(sb.toString());
			} else {
				pw.write("");
			}
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static void deleteAllDecks() {
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
			pw.write("");
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static Deck fetchDeck(String deckName) {
		ArrayList<Deck> allDecks = fetchAllDecks();
		for (Deck deck : allDecks) {
			if (deck.getDeckName().equals(deckName)) {
				System.out.println("FileHandler fetched:" + deck.toString());
				return deck;
			}
		}

		return null;
	}

	public static ArrayList<Deck> fetchAllDecks() {
		try {
			ArrayList<Deck> decks = new ArrayList<Deck>();

			BufferedReader br = new BufferedReader(new FileReader(fileName));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			br.close();
			
			if(sb.toString().equals("")){
				return new ArrayList<Deck>();
			}

			String[] stringDecks = sb.toString().split(FileHandler.separator4);

			for (String d : stringDecks) {
				String[] split = d.split(separator1);

				String deckName = split[0];

				ArrayList<Card> cards = new ArrayList<Card>();

				String[] stringCards = split[1].split(separator2);

				for (String c : stringCards) {
					split = c.split(separator3);
					cards.add(new Card(split[0], split[1]));

				}

				Deck deck = new Deck(deckName);
				for (Card card : cards) {
					deck.addCard(card);
				}
				decks.add(deck);
			}

			return decks;
		} catch (IOException e) {
			System.err.println(e);
		}
		return new ArrayList<Deck>();
	}
}
