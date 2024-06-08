package Games;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainClass extends JPanel implements MainInterface{
	private JButton omokBtn, othelloBtn, quoriBtn, checkBtn, check2Btn;
	private JTextField name1TextField, name2TextField;
	private JFrame frame;
	private String[] player = new String[2];
	private int omokSelect = -1, othelloSelect = -1, quoriSelect = -1;
	
	public void GameStart() {
		frame = new JFrame();
		frame.setTitle("Game Land");
		frame.setSize(450, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(3,1));
		
		// 상위 패널 (타이틀)
		JPanel titlePanel = new JPanel();
		JButton titleBtn = new JButton(titleImg);
		titleBtn.setBorderPainted(false);
		titlePanel.add(titleBtn);
		frame.add(titlePanel);
		
		// 중간 패널 (게임 선택)
		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(1,3));
		omokBtn = new JButton(omokImg);
		omokBtn.addActionListener(this);
		othelloBtn = new JButton(othelloImg);
		othelloBtn.addActionListener(this);
		quoriBtn = new JButton(quoridoImg);
		quoriBtn.addActionListener(this);
		
		gamePanel.add(omokBtn);
		gamePanel.add(othelloBtn);
		gamePanel.add(quoriBtn);
		frame.add(gamePanel);
		
		// 하위 패널 (플레이어 이름)
		JPanel playerPanel = new JPanel();
		playerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbxy = new GridBagConstraints();
		gbxy.insets = new Insets(10, 1, 10, 1);
		
		JLabel name1 = new JLabel("Player 1 (선공) : ");
		gbxy.gridx = 1; gbxy.gridy = 0;
		playerPanel.add(name1, gbxy);

		name1TextField = new JTextField(15);
		gbxy.gridx = 2; gbxy.gridy = 0; gbxy.gridwidth = 3;
		playerPanel.add(name1TextField, gbxy);
		
		JLabel name2 = new JLabel("Player 2 (후공) : ");
		gbxy.gridx = 1; gbxy.gridy = 2;
		playerPanel.add(name2, gbxy);
		
		name2TextField = new JTextField(15);
		gbxy.gridx = 2; gbxy.gridy = 2; gbxy.gridwidth = 3;
		playerPanel.add(name2TextField, gbxy);
		
		JPanel checkPanel = new JPanel();
		checkBtn = new JButton("게임 시작");
		checkBtn.addActionListener(this);
		checkPanel.add(checkBtn);
		check2Btn = new JButton("게임 종료");
		check2Btn.addActionListener(this);
		checkPanel.add(check2Btn);
		
		gbxy.gridx = 1; gbxy.gridy = 3; gbxy.gridwidth = 4;
		playerPanel.add(checkPanel, gbxy);
		
		frame.add(playerPanel);
		
		frame.setVisible(true);
		frame.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (omokBtn == e.getSource() && !name1TextField.getText().isEmpty() && !name2TextField.getText().isEmpty()) {
			omokSelect = 1; othelloSelect = -1; quoriSelect = -1;
			omokBtn.setIcon(omokCheck); othelloBtn.setIcon(othelloImg); quoriBtn.setIcon(quoridoImg);
		} 
		else if (othelloBtn == e.getSource() && !name1TextField.getText().isEmpty() && !name2TextField.getText().isEmpty()) {
			omokSelect = -1; othelloSelect = 1; quoriSelect = -1;
			omokBtn.setIcon(omokImg); othelloBtn.setIcon(othelloCheck); quoriBtn.setIcon(quoridoImg);
		} 
		else if (quoriBtn == e.getSource() && !name1TextField.getText().isEmpty() && !name2TextField.getText().isEmpty()) {
			omokSelect = -1; othelloSelect = -1; quoriSelect = 1;
			omokBtn.setIcon(omokImg); othelloBtn.setIcon(othelloImg); quoriBtn.setIcon(quoridoCheck);
		} 
		else if ( (omokSelect == -1) && (othelloSelect == -1) && (quoriSelect == -1) 
				&& (checkBtn == e.getSource())) {
		} 
		else if (check2Btn == e.getSource()) {
			frame.setVisible(false);
		}
		else if ( checkBtn == e.getSource() ) {
			player[0] = name1TextField.getText();
			player[1] = name2TextField.getText();
			GameSelect(omokSelect, othelloSelect, quoriSelect, player);
		}
	}

	public static void main(String[] args) {
		MainClass game = new MainClass();
		game.GameStart();
	}
}
