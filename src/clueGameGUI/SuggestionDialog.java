package clueGameGUI;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import clueGame.ClueGame;

public class SuggestionDialog extends JDialog{
	JButton cancelButton;
	JButton submitButton;

	JComboBox rooms, people, weapons;
	JTextField roomName;

	private ClueGame clueGame;
	public SuggestionDialog(String room, ClueGame clueGame){
		this.clueGame = clueGame;
		setSize(400, 200);
		setLayout(new GridLayout(4,2));

		setVisible(true);

		JLabel roomTitle, personTitle, weaponTitle;
		roomTitle = new JLabel("Murder Location");
		personTitle = new JLabel("Murderer");
		weaponTitle = new JLabel("Murder Weapon");

		//Add Room Title
		// | x    |
		// |      |
		// |      |
		// |[]  []|
		add(roomTitle);

		//Add Room Box
		// |    x |
		// |      |
		// |      |
		// |[]  []|
		if(room == null) {
			//Add a combo box with all options for rooms
			System.out.println(clueGame.getRooms());
			rooms = new JComboBox(clueGame.getRooms().toArray());
			add(rooms);
		} else {
			//Add a text field with the room
			roomName = new JTextField(room);
			roomName.setEditable(false);
			add(roomName);
		}

		//Add Person Title
		// |      |
		// | x    |
		// |      |
		// |[]  []|
		add(personTitle);

		//Add Person Box
		// |      |
		// |    x |
		// |      |
		// |[]  []|
		people = new JComboBox(clueGame.getPeople().toArray());
		add(people);

		//Add Weapon Title
		// |      |
		// |      |
		// | x    |
		// |[]  []|
		add(weaponTitle);

		//Add Weapon Box
		// |      |
		// |      |
		// |    x |
		// |[]  []|
		weapons = new JComboBox(clueGame.getWeapons().toArray());
		add(weapons);

		//Add Submit Button
		// |      |
		// |      |
		// |      |
		// |[x] []|
		submitButton = new JButton();
		add(submitButton);

		//Add Cancel Button
		// |      |
		// |      |
		// |      |
		// |[] [x]|
		cancelButton = new JButton("Cancel");
		add(cancelButton);
	}

	//TODO: Cancel Button should exit.


}
