import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RotationTest extends JFrame{

	public RotationTest() throws IOException {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setLayout(new BorderLayout());
		
		
		 
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JLabel addLabel = getRotatedJLabelFromLabel(getJLabelFromPath());
		
		addLabel.setBounds(0, 0, 900, 600);
		
		panel.add(addLabel);

		
		this.add(panel, BorderLayout.CENTER);

		this.setSize(900, 600);

		this.setVisible(true);
	}

	
	public JLabel getJLabelFromPath() throws MalformedURLException, IOException {

		BufferedImage img = ImageIO.read(new URL(
				"https://upload.wikimedia.org/wikipedia/commons/thumb/e/e7/Instagram_logo_2016.svg/1200px-Instagram_logo_2016.svg.png"));

		ImageIcon imageIconBig = new ImageIcon(img);
		JLabel labelBig = new JLabel(imageIconBig);

		return labelBig;
		

	}
	
	
	public JLabel getRotatedJLabelFromLabel(JLabel label) throws MalformedURLException, IOException {
		
		
		Image image = ((ImageIcon) label.getIcon()).getImage();

		BufferedImage bi = (BufferedImage) image;
		
		JLabel rotated = new JLabel() {
			
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(bi.getWidth(null), bi.getHeight(null));
			}
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.rotate(Math.PI / 2, bi.getWidth(null) / 2, bi.getHeight(null) / 2);
				g2.drawImage(bi, 0, 0, null);
			}
		};
		
		return rotated;

	}

	public static void main(String args[]) throws Exception {
		new RotationTest();
	}
}