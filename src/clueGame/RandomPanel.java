package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class RandomPanel extends JPanel {

	public RandomPanel(String borderStr) {
		setBorder(new TitledBorder (new EtchedBorder(), borderStr));
	}

}
