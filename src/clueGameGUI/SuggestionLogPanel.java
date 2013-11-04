package clueGameGUI;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class SuggestionLogPanel extends JPanel {
	private JTextArea log;
	
	SuggestionLogPanel(){
		//We add a scrolling text area for
		//all previous suggestions and
		//their results
		log = new JTextArea(20, 40);
		log.setEditable(false);
		log.setWrapStyleWord(true);
		log.setLineWrap(true);
		add(log);
		
		//the scrolling part:
		JScrollPane scroll = new JScrollPane(log);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    add(scroll);
	    
	    //and a border to explain the purpose of this
		setBorder(new TitledBorder (new EtchedBorder(), "Previous Suggestions"));	
	}

}

