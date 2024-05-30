package Games.GUI;

import javax.swing.*;
import java.awt.*;

public class BarDirectionSelectWindow extends JFrame {
    private JTextField DirectionSelectMsg = new JTextField("방향을 선택하세요");
    private JRadioButton[] Directions = new JRadioButton[2]; // 0번에 "가로", 1번에 "세로"
    private ButtonGroup buttonGroup = new ButtonGroup();
    private JButton[] checkCancelButtons = new JButton[2]; // 0번에 "확인", 1번에 "취소"
    private JTextField warning = new JTextField();


    public BarDirectionSelectWindow() {
        setBounds(225,250,210,300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 삭제해도 메인 창은 안 꺼짐
        setLayout(null); // 세밀하게 좌표 제어
        setVisible(true);
        componentPosition();

    }

    public JTextField getWarning() {
        return warning;
    }

    // 창 종료
    public void closeWindow() {
        dispose();
    }

    // 방향을 선택하는 창
    public static void main(String[] args) {
        BarDirectionSelectWindow window = new BarDirectionSelectWindow();
    }

    // 방향 선택 창 GUI구축
    // 방향 미 선택 후 확인을 누른 경우 (밑에 TextField에 경고 문구 띄우기)
    // 방향 선택 후 확인을 눌렀으나 이미 해당 방향으로 막대가 있는 경우 (및에 TextField에 경고 문구 띄우기)
    public void componentPosition() {
        DirectionSelectMsg.setBounds(0,0,210,50);
        DirectionSelectMsg.setHorizontalAlignment(SwingConstants.CENTER);
        add(DirectionSelectMsg);

        Directions[0] = getJRadioButton("가로",30,50, 50,50);
        Directions[1] = getJRadioButton("세로",105,50,50,50);

        checkCancelButtons[0] = getButton("확인", 15, 120, 80, 80);
        checkCancelButtons[1] = getButton("취소", 105, 120, 80, 80);

        warning.setBounds(0,200,210,100);
        warning.setBackground(Color.RED);
        add(warning);
    }

    // RadioButton생성
    public JRadioButton getJRadioButton(String text, int x, int y, int width, int height) {
        JRadioButton jRadioButton = new JRadioButton();
        jRadioButton.setText(text);
        jRadioButton.setBounds(x, y, width, height);
        add(jRadioButton);
        buttonGroup.add(jRadioButton);
        return jRadioButton;
    }

    public JButton getButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        add(button);
        return button;
    }

    public JButton[] getCheckCancelButtons() {
        return checkCancelButtons;
    }

    public JRadioButton[] getDirections() {
        return Directions;
    }
}

