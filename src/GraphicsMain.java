import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class GraphicsMain extends JFrame implements MouseListener, MouseMotionListener, ActionListener {

	private final String cardback = "https://gamepedia.cursecdn.com/mtgsalvation_gamepedia/f/f8/Magic_card_back.jpg";

	private final String forest = "https://static.cardmarket.com/img/20a72e91df381dd044d94597f5f6c7ac/items/1/X2XM/482629.jpg";
	private final String swamp = "https://static.cardmarket.com/img/7972addf6713fd0a67d3f0277f2b4305/items/1/X2XM/482584.jpg";
	private final String plains = "https://static.cardmarket.com/img/140131bb0e90f52a4b2c99e30f0d3c79/items/1/X2XM/482574.jpg";
	private final String island = "https://static.cardmarket.com/img/f2eed6aabee3c4957e7e50a931964820/items/1/X2XM/482579.jpg";
	private final String mountain = "https://static.cardmarket.com/img/4fe33ae1c8f9b428ab3e2c75caa51007/items/1/X2XM/482589.jpg";

	private final String backgroundPath = "/background.jpg";

	private final int cardHeight = 88;
	private final int cardWidth = 63;
	private JLabel cards_in_lib = new JLabel("Cards in library: 0");

	private ArrayList<JLabel> deckImageList = new ArrayList<JLabel>();
	private ArrayList<JLabel> deckBigImageList = new ArrayList<JLabel>();

	private Deck deck;

	private JPopupMenu rightClickLibraryMenu = new JPopupMenu();
	private JPopupMenu rightClickCardMenu = new JPopupMenu();

	private JLabel library;
	private JLabel opp_library;
	JPanel mainPanel = new JPanel();

	JLabel zoomedImage = new JLabel();

	// the component last entered by mouse
	String lastEntered = "";

	JLabel backgroundLabel;

	public GraphicsMain() {

		
		super("Magic the gathering");

		// System.setProperty("http.agent", "Chrome");

		this.setLayout(new BorderLayout());

		mainPanel.setLayout(null);

		// TODO: cleanup
		Image backgroundImage = new ImageIcon(this.getClass().getResource(backgroundPath)).getImage();
		Image scaledBackgroundImage = backgroundImage.getScaledInstance(900, 600, Image.SCALE_SMOOTH);
		ImageIcon backgroundImageIcon = new ImageIcon(scaledBackgroundImage);
		backgroundLabel = new JLabel(backgroundImageIcon);
		backgroundLabel.setBounds(0, 0, 900, 600);
		backgroundLabel.setLocation(0, 0);
		mainPanel.add(backgroundLabel);
		mainPanel.setComponentZOrder(backgroundLabel, 0);

		this.add(mainPanel, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900, 600);

		cards_in_lib.setBounds(600, 600, 100, 40);
		cards_in_lib.setLocation(750, 350);
		mainPanel.add(cards_in_lib);
		mainPanel.setComponentZOrder(cards_in_lib, 0);

		deck = new Deck("testDeck");

		/*
		 * 
		 * Right click on any card: Shuffle card into library, Put on top of library,
		 * Tap/untap, add counter +/+ -/- loyalty custom -> popup
		 * 
		 */

		// menu items

		JMenu Decks, Counters, Extra;
		JMenuItem Build, Edit, Shuffle, Search, Look_top, Look_top_X, Draw_X, Play_revieled, Counterbrick, Experience,
				Poison, Custom, Fetch, Deck_1, Deck_2, Play, Deck_1_Play, Deck_2_Play, Show_card;

		JMenuBar menubar = new JMenuBar();

		Decks = new JMenu("Decks");
		Counters = new JMenu("Counters");
		Extra = new JMenu("Extra");

		Deck_1_Play = new JMenuItem("Deck_1");
		Deck_1_Play.addActionListener(this);

		Deck_2_Play = new JMenuItem("Deck_2");
		Deck_2_Play.addActionListener(this);

		Build = new JMenuItem("Build new deck");
		Build.addActionListener(this);

		Edit = new JMenu("Edit deck");

		Deck_1 = new JMenuItem("Deck_1");
		Deck_1.addActionListener(this);

		Deck_2 = new JMenuItem("Deck_2");
		Deck_2.addActionListener(this);

		Play = new JMenu("Play");

		Shuffle = new JMenuItem("Shuffle deck");
		Shuffle.addActionListener(this);

		Search = new JMenuItem("Search for a card");
		Search.addActionListener(this);

		Look_top = new JMenuItem("Look at top card");
		Look_top.addActionListener(this);

		Look_top_X = new JMenuItem("Look at top X cards");
		Look_top_X.addActionListener(this);

		Draw_X = new JMenuItem("Draw X cards");
		Draw_X.addActionListener(this);

		Play_revieled = new JMenuItem("Play with top card revieled");
		Play_revieled.addActionListener(this);

		Counterbrick = new JMenu("Add a counterbrick");

		Experience = new JMenuItem("Experience");
		Experience.addActionListener(this);

		Poison = new JMenuItem("Poison");
		Poison.addActionListener(this);

		Custom = new JMenuItem("Custom");
		Custom.addActionListener(this);

		Fetch = new JMenuItem("Fetch card from outside game");
		Fetch.addActionListener(this);

		Show_card = new JMenuItem("Show card for opponent");
		Show_card.addActionListener(this);

		rightClickLibraryMenu.addMouseListener(this);
		rightClickCardMenu.addMouseListener(this);

		menubar.add(Play);
		menubar.add(Decks);
		menubar.add(Counters);
		menubar.add(Extra);

		Play.add(Deck_1_Play);
		Play.add(Deck_2_Play);

		Decks.add(Build);
		Decks.add(Edit);

		Edit.add(Deck_1);
		Edit.add(Deck_2);

		rightClickLibraryMenu.add(Shuffle);
		rightClickLibraryMenu.add(Search);
		rightClickLibraryMenu.add(Look_top);
		rightClickLibraryMenu.add(Look_top_X);
		rightClickLibraryMenu.add(Draw_X);
		rightClickLibraryMenu.add(Play_revieled);

		rightClickCardMenu.add(Show_card);

		Counters.add(Counterbrick);
		Counterbrick.add(Experience);
		Counterbrick.add(Poison);
		Counterbrick.add(Custom);

		Extra.add(Fetch);

		this.setJMenuBar(menubar);

		// setup game board

		library = getLabelFromUrl(cardback)[0];
		opp_library = getLabelFromUrl(cardback)[0];

		library.setBounds(770, 400, cardWidth, cardHeight);
		opp_library.setBounds(50, 50, cardWidth, cardHeight);

		library.addMouseMotionListener(this);
		library.addMouseListener(this);

		library.setName("library");
		opp_library.setName("opp_library");
		
		JLabel swampLabel = getLabelFromUrl(swamp)[0];
		JLabel rotatedSwamp = getRotatedJLabelFromLabel(swampLabel);
		
		rotatedSwamp.setBounds(200, 200, cardHeight, cardWidth);
		
		mainPanel.add(rotatedSwamp);
		
		

		mainPanel.add(library);
		mainPanel.add(opp_library);
		// mainPanel.add(zoomedImage);

		mainPanel.setComponentZOrder(library, 0);
		mainPanel.setComponentZOrder(rotatedSwamp, 0);
		mainPanel.setComponentZOrder(opp_library, 0);
		// mainPanel.setComponentZOrder(zoomedImage, 0);

		this.setVisible(true);

	}
	
	public static BufferedImage convertToBufferedImage(Image image)
	{
	    BufferedImage newImage = new BufferedImage(
	        image.getWidth(null), image.getHeight(null),
	        BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = newImage.createGraphics();
	    g.drawImage(image, 0, 0, null);
	    g.dispose();
	    return newImage;
	}
	
	public JLabel getRotatedJLabelFromLabel(JLabel uprightLabel){

		Image image = getImageFromJLabel(uprightLabel);
		
		BufferedImage bi = convertToBufferedImage(image);
		
		JLabel rotated = new JLabel() {
			
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(cardWidth, cardHeight);
			}
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.rotate(Math.PI / 2, cardHeight/2 , cardHeight/2);
				
				
				g2.drawImage(bi, 0, 0, null);
			}
		};
		
		return rotated;

	}
	


	


	public JLabel getLabelFromPath(String imagePath) {

		ImageIcon imageIcon = new ImageIcon(getClass().getResource(imagePath));

		JLabel label = new JLabel(imageIcon);
		return label;

	}
	
	public Image getImageFromJLabel(JLabel jLabel) {

		Image image = ((ImageIcon) jLabel.getIcon()).getImage();

		return image;

	}
	
	

	public JLabel[] getLabelFromUrl(String imageUrl) {
		Image image = null;
		URL url = null;

		// loading image
		try {
			url = new URL(imageUrl);
			image = ImageIO.read(url);
		} catch (MalformedURLException ex) {
			System.err.println("Malformed URL");
		} catch (IOException iox) {
			System.err.println("Can not load file");
		}

		// resizing and placing image

		Image scaledImageBig = image.getScaledInstance(cardWidth * 2, cardHeight * 2, Image.SCALE_SMOOTH);
		ImageIcon imageIconBig = new ImageIcon(scaledImageBig);
		JLabel labelBig = new JLabel(imageIconBig);

		Image scaledImageSmall = image.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
		ImageIcon imageIconSmall = new ImageIcon(scaledImageSmall);
		JLabel labelSmall = new JLabel(imageIconSmall);

		JLabel[] returnLabels = { labelSmall, labelBig };

		return returnLabels;

	}

	public ImageIcon getZoomedIconFromLabel(JLabel smallLabel) {

		Image scaledImage = ((ImageIcon) smallLabel.getIcon()).getImage().getScaledInstance(cardWidth * 2,
				cardHeight * 2, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(scaledImage);

		return imageIcon;

	}

	// changes "sol ring" to "sol-ring", deals with apostrophes
	public String getCorrectedCardName(String originalCardName) {

		return originalCardName;
	}

	public String fetchImageUrlFromCardName(String cardName) {

		// change card spaces to "-" and deal with special characters here

		String imageUrl = "";
		String content = "";
		URLConnection connection = null;
		try {
			connection = new URL("https://www.cardmarket.com/en/Magic/Cards/" + cardName).openConnection();
			Scanner scanner = new Scanner(connection.getInputStream());

			while (scanner.hasNext()) {
				content += scanner.next();
			}

			scanner.close();

			// only if card url was found, we try the regex finding the card link

			Matcher m = Pattern.compile("imgsrc=\"//(static.cardmarket.com/img.*?jpg)").matcher(content);
			if (m.find()) {
				imageUrl = "https://" + m.group(1);

			}

		} catch (Exception ex) {
			System.err.println("card wasn't found");

		}

		return imageUrl;

	}

	public void nameYourDeckPopup() {

		JTextField deckNameField = new JTextField(10);

		JPanel deckNamePanel = new JPanel();
		deckNamePanel.add(new JLabel("Name your deck!"));
		deckNamePanel.add(deckNameField);
		deckNamePanel.add(Box.createHorizontalStrut(15)); // a space

		int nameOk = JOptionPane.showConfirmDialog(null, deckNamePanel, "New Deck", JOptionPane.OK_CANCEL_OPTION);

		if (nameOk == JOptionPane.OK_OPTION && !deckNameField.getText().isEmpty()) {

			// All card fetching threads must have finished before we start initializing cards
			
			deck.setDeckName(deckNameField.getText());
			
			while (Thread.activeCount() > 2) {
				//wait until all cards are fetched
			}
			
			FileHandler.storeDeck(deck);

			System.out.println("finished loading");
			
			initSmallAndBigCards(deckNameField.getText());

			// TODO:
			// save the deck to a file
			// add the deck as playable option in the top menu

		} else if (nameOk == JOptionPane.OK_OPTION && deckNameField.getText().isEmpty()) {

			JOptionPane.showOptionDialog(GraphicsMain.this, "Deck must have a name", "Fail", JOptionPane.PLAIN_MESSAGE,
					JOptionPane.QUESTION_MESSAGE, null, null, "OK");

			nameYourDeckPopup();

		} else if (nameOk == JOptionPane.CANCEL_OPTION) {

			JOptionPane.showOptionDialog(GraphicsMain.this, "Deck will not be saved", "Fail", JOptionPane.PLAIN_MESSAGE,
					JOptionPane.QUESTION_MESSAGE, null, null, "OK");

			// clear the cards that were previously added
			deck.clearDeck();

		}

	}

	public class MyRunnable implements Runnable {

		private String textFromField;
		private int amountFromField;

		// constructor
		public MyRunnable(String textFromField, int amountFromField) {
			this.textFromField = textFromField;
			this.amountFromField = amountFromField;
		}

		public void run() {

			String cardToAddUrl;

			// first we check if it's a basic land, because
			// then we simply load one of our prepared images

			if (textFromField.equals("forest")) {
				cardToAddUrl = forest;
			} else if (textFromField.equals("mountain")) {
				cardToAddUrl = mountain;
			} else if (textFromField.equals("plains")) {
				cardToAddUrl = plains;
			} else if (textFromField.equals("swamp")) {
				cardToAddUrl = swamp;
			} else if (textFromField.equals("island")) {
				cardToAddUrl = island;
			} else {
				// if not a basic land, fetch the card from the Internet.
				// This is where the exception would trigger if card doesn't exist
				cardToAddUrl = fetchImageUrlFromCardName(textFromField);
			}

			// if fetching URL from card name was successful!
			if (!cardToAddUrl.equals("")) {

				// adding the amount of the requested card
				for (int i = 0; i < Integer.valueOf(amountFromField); i++) {

					//deck.addCard(new Card(textFromField + Integer.toString(deck.getNumberOfCards()), cardToAddUrl));

					// Jag tog bort inkrementen på kortnamnet
					// kortet ska istället heta det riktiga namnet t.ex "forest" och vara unikt mha "id" istället.
					deck.addCard(new Card(textFromField, cardToAddUrl));

				}

				// updating cards in library text
				updateCardsInLibText();

			} else {

				JOptionPane.showOptionDialog(GraphicsMain.this, "Could not find card: " + textFromField, "Fail",
						JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, null, "OK");

			}

		}
	}

	public void updateCardsInLibText() {
		cards_in_lib.setText("Cards in library: " + deck.getNumberOfCards());

	}

	public void initSmallAndBigCards(String deckName) {
		
		// add check to see that deck exists
		
		
		deck = FileHandler.fetchDeck(deckName);

		Iterator<Card> iterator = deck.getCards().iterator();

		System.out.println("are there cards in the lib?");

		
		while (iterator.hasNext()) {
			Card cardToAdd = iterator.next();
			
			System.out.println("itinializing " + cardToAdd.getName() + " " + cardToAdd.getUrl());

			deckImageList.add(getLabelFromUrl(cardToAdd.getUrl())[0]);
			deckImageList.get(deckImageList.size() - 1).setBounds(2000, 2000, cardWidth, cardHeight);
			deckImageList.get(deckImageList.size() - 1).addMouseMotionListener(GraphicsMain.this);
			deckImageList.get(deckImageList.size() - 1).addMouseListener(GraphicsMain.this);
			deckImageList.get(deckImageList.size() - 1).setName(cardToAdd.getName());

			// setting properties to the big image
			// we do not set location(), because we don't want the card shown yet
			// despite that, even though the location isn't set, the card is "still there"
			// so the mouse
			// events still triggers it. Therefore we set the bounds coordinates far outside
			// the frame.
			deckBigImageList.add(getLabelFromUrl(cardToAdd.getUrl())[1]);
			deckBigImageList.get(deckBigImageList.size() - 1).setName(cardToAdd.getName());
			deckBigImageList.get(deckBigImageList.size() - 1).setBounds(2000, 2000, cardWidth * 2, cardHeight * 2);

			// adding the zoomed image AFTER small image so that the zoomed card appears on
			// top of the normal card
			mainPanel.add(deckImageList.get(deckImageList.size() - 1));
			mainPanel.add(deckBigImageList.get(deckBigImageList.size() - 1));

			mainPanel.setComponentZOrder(deckImageList.get(deckImageList.size() - 1), 0);
			mainPanel.setComponentZOrder(deckBigImageList.get(deckBigImageList.size() - 1), 0);

		}

		setVisible(true);
	}

	public void addCardsPopup() {

		JTextField cardNameField = new JTextField(10);
		JTextField amountField = new JTextField(5);
		amountField.setText("1");

		JPanel addCardsPanel = new JPanel();
		addCardsPanel.add(new JLabel("Card name:"));
		addCardsPanel.add(cardNameField);
		addCardsPanel.add(Box.createHorizontalStrut(15)); // a spacer
		addCardsPanel.add(new JLabel("Amount:"));
		addCardsPanel.add(amountField);

		int result = JOptionPane.showConfirmDialog(null, addCardsPanel, "Add card to deck",
				JOptionPane.OK_CANCEL_OPTION);

		// if click OK
		if (result == JOptionPane.OK_OPTION && !cardNameField.getText().isEmpty()) {

			Runnable r = new MyRunnable(cardNameField.getText(), Integer.parseInt(amountField.getText()));
			new Thread(r).start();

			// we start thread that searches for card, and continue with asking more cards

			addCardsPopup();

		} else if (result == JOptionPane.OK_OPTION && cardNameField.getText().isEmpty()) {

			JOptionPane.showOptionDialog(GraphicsMain.this, "Card must have a name", "Fail", JOptionPane.PLAIN_MESSAGE,
					JOptionPane.QUESTION_MESSAGE, null, null, "OK");

			addCardsPopup();

		} else if (result == JOptionPane.CANCEL_OPTION) {

			// here we wait for all threads to finish

			

			nameYourDeckPopup();

			// else we just close, there's nothing to save
		}

	}

	public JLabel getImageFromCardName(String cardName) {

		for (JLabel cardImage : deckImageList) {
			if (cardImage.getName().equals(cardName)) {

				return cardImage;
			}
		}

		// if card name wasn't found in deck, return null
		System.err.println("didn't find label from cardname");
		return null;
	}

	public JLabel getBigImageFromCardName(String cardName) {

		for (JLabel cardImage : deckBigImageList) {
			if (cardImage.getName().equals(cardName)) {

				return cardImage;
			}
		}

		// if card name wasn't found in deck, return null
		System.err.println("didn't find label from cardname");
		return null;
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		String enteredCard = e.getComponent().getName();

		if (enteredCard != null) {

			zoomedImage.setLocation(2000, 2000);

			int mouseX = (int) (MouseInfo.getPointerInfo().getLocation().getX() - this.getX());
			int mouseY = (int) (MouseInfo.getPointerInfo().getLocation().getY() - this.getY());

			// drag card from top of library

			if (enteredCard.equals("library") && deck.hasCards()) {

				getImageFromCardName(deck.PeekTopCard().getName()).setLocation(mouseX - 40, mouseY - 70);

			}

			// move a card from outside of library

			if ((!enteredCard.equals("library"))) {

				getImageFromCardName(enteredCard).setLocation(mouseX - 40, mouseY - 70);

			}

			setVisible(true);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		String enteredCard = e.getComponent().getName();

		if (enteredCard != null) {

			if (SwingUtilities.isRightMouseButton(e) && enteredCard.equals("library")) {
				rightClickLibraryMenu.show(e.getComponent(), e.getX(), e.getY());

			}

			if (SwingUtilities.isRightMouseButton(e) && !enteredCard.equals("library")) {
				rightClickCardMenu.show(e.getComponent(), e.getX(), e.getY());

			}

		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

		String enteredCard = e.getComponent().getName();

		if (enteredCard.equals("library") && !lastEntered.equals("library")) {

			// we have drawn the top card from the library, so we remove it from library
			deck.removeTopCard();

			// updating cards in library text
			cards_in_lib.setText("Cards in library: " + deck.getNumberOfCards());

		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

		String enteredCard = e.getComponent().getName();

		if (enteredCard != null) {

			// we're globally interested in what the last entered card was
			lastEntered = e.getComponent().getName();

			// hover card outside of library
			if (!enteredCard.equals("library")) {

				int enteredCardX = e.getComponent().getX();
				int enteredCardY = e.getComponent().getY();

				// for all big labels, we find the one with the right name

				zoomedImage = getBigImageFromCardName(enteredCard);
				zoomedImage.setLocation(enteredCardX, enteredCardY);

				setVisible(true);

			}

		}

	}

	@Override
	public void mouseExited(MouseEvent e) {

		String exitedCard = e.getComponent().getName();

		if (exitedCard != null) {
			zoomedImage.setLocation(2000, 2000);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Build new deck")) {

			addCardsPopup();
		}

	}

	public static void main(String args[]) {

		new GraphicsMain();

	}

}