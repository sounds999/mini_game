package Games;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

public interface MainInterface extends ActionListener{
	ImageIcon titleImg = new ImageIcon("src/Games/InterfaceIcon/MainImg.png");
	ImageIcon omokImg = new ImageIcon("src/Games/InterfaceIcon/MainOmokImg.png");
	ImageIcon othelloImg = new ImageIcon("src/Games/InterfaceIcon/MainOthelloImg.png");
	ImageIcon quoridoImg = new ImageIcon("src/Games/InterfaceIcon/MainQuoridoImg.png");
	ImageIcon omokCheck = new ImageIcon("src/Games/InterfaceIcon/omokCheck.jpg");
	ImageIcon othelloCheck = new ImageIcon("src/Games/InterfaceIcon/othelloCheck.jpg");
	ImageIcon quoridoCheck = new ImageIcon("src/Games/InterfaceIcon/quoridoCheck.jpg");	
	
	void GameStart(); // abstract
	
	default void GameSelect(int omokSelect, int othelloSelect, int quoriSelect, String[] player) {
		if (omokSelect == 1) {
			OmokDemo omokTest = new OmokDemo(player);
		}
		else if (othelloSelect == 1) {
			OthelloDemo othelloTest = new OthelloDemo(player);
		}
		else if (quoriSelect == 1) {
			QuoridoDemo quoriTest = new QuoridoDemo(player);
		}
		omokSelect = -1; othelloSelect = -1; quoriSelect = -1;
	}
}
