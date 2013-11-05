package clueGameGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import clueGame.Card;
import clueGame.Player;
import clueGame.Solution;

public class SuggestionLogPanel extends JPanel {
	private SuggestionLog log;
	
	SuggestionLogPanel(){
		//We add a scrolling text area for
		//all previous suggestions and
		//their results
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		setBorder(new TitledBorder (BorderFactory.createLineBorder(Color.red),"Previous Suggestions", 0, 0, new Font("Arial Narrow", Font.BOLD, 12), Color.WHITE));
		log = new SuggestionLog();
		
		setSize(300,getSize().height);
		//log.setWrapStyleWord(true);
		//log.setLineWrap(true);
		setLayout(new BorderLayout());
		
		add(log, BorderLayout.CENTER);
		//the scrolling part:
		

		//and a border to explain the purpose of this
		//setBorder(new TitledBorder (new EtchedBorder(), "Previous Suggestions"));	
	}

	public void addSuggestion(Player accuser, Solution suggestion,
			Card disprovedBy) {
		// TODO Auto-generated method stub
		 log.addSuggestion(accuser, suggestion, disprovedBy);

	}

	

}

