package Games;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public interface TestInterface extends ActionListener{
	
//	void printWinner();

	@Override
	default void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
//	default void nameSet() {
//	}
//	
//	default void firstSet() {
//	}
}
