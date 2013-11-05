package clueGameGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class TurnPanel extends JPanel {
	private DiePanel die;
	private JTextField currentPlayer;
	
	
	public TurnPanel() {
		super();
		setBorder(new TitledBorder (new EtchedBorder(), "Current Turn"));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		die = new DiePanel();
		currentPlayer = new JTextField();
		currentPlayer.setEditable(false);
		currentPlayer.setText("...");
		add(die);
		add(currentPlayer);
	}


	public void setRoll(int i) {
		die.setFace(i);
	}

	public void setCurrentPlayer(String name) {
		// TODO Auto-generated method stub
		currentPlayer.setText(name);
	}

}