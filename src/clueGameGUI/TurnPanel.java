package clueGameGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import clueGame.Player;

public class TurnPanel extends JPanel {
	private DiePanel die;
	private JTextField currentPlayer;
	
	public TurnPanel() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		setBorder(new TitledBorder (BorderFactory.createLineBorder(Color.red),"Current Turn", 0, 0, new Font("Arial Narrow", Font.BOLD, 12), Color.WHITE));
		die = new DiePanel();
		currentPlayer = new JTextField();
		currentPlayer.setEditable(false);
		currentPlayer.setFont(new Font("Arial Narrow", Font.BOLD, 23));
		currentPlayer.setPreferredSize(new Dimension(300, 94));
		currentPlayer.setMaximumSize(new Dimension(300, 94));
		
		currentPlayer.setHorizontalAlignment(JTextField.CENTER);
		currentPlayer.setText("...");
		
		add(die);
		add(currentPlayer);
	}


	public void setRoll(int i) {
		die.setFace(i);
	}

	public void setCurrentPlayer(Player p) {
		// TODO Auto-generated method stub
		currentPlayer.setForeground(p.getColor());
		currentPlayer.setBorder(new LineBorder(p.getColor().darker(), 3));
		currentPlayer.setBackground(p.getColor().darker().darker().darker());
		currentPlayer.setHorizontalAlignment(JTextField.CENTER);
		currentPlayer.setText(p.getName());
	}

}
