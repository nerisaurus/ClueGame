package clueGameGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import clueGame.Card;
import clueGame.Player;
import clueGame.Solution;

public class SuggestionLogPanel extends JPanel {
	private BufferedImage background;
	private SuggestionLog log;
	private JScrollPane scroll;

	SuggestionLogPanel(){
		//We add a scrolling text area for
		//all previous suggestions and
		//their results
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		setBorder(new TitledBorder (BorderFactory.createLineBorder(Color.black),"Previous Suggestions", 0, 0, new Font("Arial Narrow", Font.BOLD, 12), Color.WHITE));
		log = new SuggestionLog();

		setSize(300,getSize().height);
		//log.setWrapStyleWord(true);
		//log.setLineWrap(true);
		setLayout(new BorderLayout());

		//add(log, BorderLayout.CENTER);
		//the scrolling part:
		scroll = new JScrollPane(log);
		scroll.setOpaque(false);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll);

		scroll.getViewport().setOpaque(false);
		//and a border to explain the purpose of this
		//setBorder(new TitledBorder (new EtchedBorder(), "Previous Suggestions"));	
	}

	public void addSuggestion(Player accuser, Solution suggestion, Card disprovedBy) {
		log.addSuggestion(accuser, suggestion, disprovedBy);
	}

	public void setTheme(Player p){
		//borders:
		setBorder(new TitledBorder (BorderFactory.createLineBorder(p.getColor()),"Previous Suggestions", 0, 0, new Font("Arial Narrow", Font.BOLD, 12), p.getColor()));
		//log.setTheme(p);
		
		//background:
		try {
			background = ImageIO.read(new File("Themes/" + p.getName() + "/logBackground.png"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override 
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (background != null) {
			Graphics2D g2 = (Graphics2D) g.create();
			int x = getWidth() - scroll.getWidth();
			int y = getHeight() - scroll.getHeight();
			g2.drawImage(background, x, y, scroll.getViewport().getWidth(), scroll.getViewport().getHeight(), this);
			g2.dispose();
		}
	}

}

