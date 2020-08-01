import java.util.ArrayList;

public class Card {
	private String name;
	private String cardUrl;
	private ArrayList<String> counters;

	public Card(String name, String cardUrl) {
		this.name = name;
		this.cardUrl = cardUrl;
		counters = new ArrayList<String>();
	}

	public String getName() {
		return new String(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return new String(cardUrl);
	}

	public void setUrl(String cardUrl) {
		this.cardUrl = cardUrl;
	}
	
	public void setCounter(String counter) {
		counters.add(counter);
	}
}
