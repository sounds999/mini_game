package Games;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TestMainClass extends JPanel implements TestInterface{
	private JButton omokBtn, othelloBtn, quoriBtn, checkBtn, check2Btn;
	private JTextField name1TextField, name2TextField;
	private JFrame frame;
	private String[] player = new String[2];
	private int omokSelect = -1, othelloSelect = -1, quoriSelect = -1, check = -1;
	
	private ImageIcon titleImg = new ImageIcon("src/Games/InterfaceIcon/MainImg.png");
	private ImageIcon omokImg = new ImageIcon("src/Games/InterfaceIcon/MainOmokImg.png");
	private ImageIcon othelloImg = new ImageIcon("src/Games/InterfaceIcon/MainOthelloImg.png");
	private ImageIcon quoridoImg = new ImageIcon("src/Games/InterfaceIcon/MainQuoridoImg.png");
	
	public TestMainClass() {
		frame = new JFrame();
		frame.setTitle("Main interface TEST");
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
		playerPanel.setLayout(new GridLayout(3, 1));
		
		JPanel NamePanel = new JPanel();
		JLabel name1 = new JLabel("Player 1 (선공) : ");
		name1TextField = new JTextField(15);
		NamePanel.add(name1); NamePanel.add(name1TextField);
		playerPanel.add(NamePanel);
		
		JPanel Name2Panel = new JPanel();
		JLabel name2 = new JLabel("Player 2 (후공) : ");
		name2TextField = new JTextField(15);
		Name2Panel.add(name2); Name2Panel.add(name2TextField);
		playerPanel.add(Name2Panel);
		
		JPanel checkPanel = new JPanel();
		checkBtn = new JButton("게임 시작");
		checkBtn.addActionListener(this);
		check2Btn = new JButton("게임 종료");
		check2Btn.addActionListener(this);
		checkPanel.add(checkBtn, BorderLayout.CENTER);
		checkPanel.add(check2Btn, BorderLayout.CENTER);
		playerPanel.add(checkPanel);
		frame.add(playerPanel);
		
		frame.setVisible(true);
		frame.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (omokBtn == e.getSource() && !name1TextField.getText().isEmpty() && !name2TextField.getText().isEmpty()) {
			omokSelect = 1; othelloSelect = -1; quoriSelect = -1;
		} 
		else if (othelloBtn == e.getSource() && !name1TextField.getText().isEmpty() && !name2TextField.getText().isEmpty()) {
			omokSelect = -1; othelloSelect = 1; quoriSelect = -1;
		} 
		else if (quoriBtn == e.getSource() && !name1TextField.getText().isEmpty() && !name2TextField.getText().isEmpty()) {
			omokSelect = -1; othelloSelect = -1; quoriSelect = 1;
		} 
		else if ( (omokSelect == -1) && (othelloSelect == -1) && (quoriSelect == -1) 
				&& (checkBtn == e.getSource())) {
		} 
		else if (check2Btn == e.getSource()) {
			frame.setVisible(false);
		}
		else if ( checkBtn == e.getSource() ) {
			if (omokSelect == 1) {
				player[0] = name1TextField.getText();
				player[1] = name2TextField.getText();
				OmokDemo omokTest = new OmokDemo(player);
			}
			else if (othelloSelect == 1) {
				player[0] = name1TextField.getText();
				player[1] = name2TextField.getText();
				OthelloDemo othelloTest = new OthelloDemo(player);
			}
			else if (quoriSelect == 1) {
				player[0] = name1TextField.getText();
				player[1] = name2TextField.getText();
				QuoridoDemo quoriTest = new QuoridoDemo(player);
			}
			omokSelect = -1; othelloSelect = -1; quoriSelect = -1;
		}
		
	}

	public static void main(String[] args) {
		TestMainClass test = new TestMainClass();
	}
}
