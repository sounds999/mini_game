package Games;

import Games.GUI.QuoridorButtonPanel;
import Games.GUI.QuoridorTextField;
import Games.Logic.PlayerMoveLogic;

import javax.swing.*;
import java.util.Scanner;

public class QuoridoDemo extends JFrame {
    // 패널 및 TextField 크기
    private final int ButtonPanelWidth = 660;
    private final int ButtonPanelHeight = 660;
    private final int TextFieldWidth = 660;
    private final int TextFieldHeight = 50;
    private String[] player = new String[2]; // 0에 백돌을 유저 이름, 1에 흑돌 유저 이름

    public QuoridoDemo(String[] player) {
        this.player = player; // main에서 받아온 playerName을 Text에 건네줌

        // 하위 클래스 객체 생성
        QuoridorTextField textField = new QuoridorTextField(ButtonPanelWidth, ButtonPanelHeight, TextFieldWidth, TextFieldHeight, player);
        QuoridorButtonPanel buttonPanel = new QuoridorButtonPanel(ButtonPanelWidth, ButtonPanelHeight, TextFieldWidth, TextFieldHeight);
        PlayerMoveLogic playerMovelogic = new PlayerMoveLogic(buttonPanel, textField);

        // topTextField 생성
        add(textField.getUserTextFields()[0]);

        // 판 생성 및 삽입
        add(buttonPanel.getButtonPanel());

        //bottomTextField 삽입
        add(textField.getUserTextFields()[1]);

        // JFrame 설정
        setLayout(null);
        setSize(680,800);
        setVisible(true);
        setTitle("Quorido");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
