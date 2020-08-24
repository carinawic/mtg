import java.util.ArrayList;

import javax.swing.JLabel;

public class Card {
	private String name;
	private String cardUrl;
	private ArrayList<String> counters;
	private JLabel label;
	private JLabel rotatedLabel;
	private JLabel zoomedLabel;

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
	
	public String getId() {
		return Integer.toString(this.hashCode());
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public void setRotatedLabel(JLabel label) {
		this.rotatedLabel = label;
	}

	public void setZoomedLabel(JLabel label) {
		this.zoomedLabel = label;
	}

	public JLabel getLabel() {
		return this.label;
	}

	public JLabel getRotatedLabel() {
		return this.rotatedLabel;
	}

	public JLabel getZoomedLabel() {
		return this.zoomedLabel;
	}
}
