package Games.GUI;

import javax.swing.*;
import java.awt.*;

public class QuoridorTextField extends JFrame {
    
    private JTextField topUserField = new JTextField();
    private JTextField bottomUserField = new JTextField();
    
    //유저이름
    private String[] playerNames = new String[2];
    // TextFields
    private JTextField[] userTextFields = new JTextField[2];

    // 생성자 (Text의 크기 및 유저이름 받음)
    public QuoridorTextField(int ButtonPanelWidth, int ButtonPanelHeight, int TextFieldWidth, int TextFieldHeight, String[] playerNames) {
        
        this.playerNames[0] = playerNames[0];
        this.playerNames[1] = playerNames[1];
        
        createTextFields();
        userTextFields[0].setBounds(0, 0, TextFieldWidth, TextFieldHeight);
        userTextFields[0].setText("USER: " + this.playerNames[1] + "(●)                                                    막대 수 10개");
        userTextFields[1].setBounds(0, ButtonPanelHeight + TextFieldHeight, TextFieldWidth, TextFieldHeight);
        userTextFields[1].setText("USER: " + this.playerNames[0] + "(○)                                                    막대 수 10개");
    }

    // userTextField를 생성함
    public void createTextFields() {
        userTextFields[0] = topUserField;
        userTextFields[1] = bottomUserField;

        for (int i = 0; i < userTextFields.length; i++) {
            userTextFields[i].setBackground(Color.LIGHT_GRAY);
            userTextFields[i].setForeground(Color.BLACK);
            // setSize는 absolute하게 사이즈를 설정한다 setPreferredSize는 Layout manager에게 size를 제안함
            userTextFields[i].setFont(new Font("맑은 고딕", Font.BOLD, 20));
            userTextFields[i].setText("USER");
        }
    }

    public String[] getPlayerNames() {
        return playerNames;
    }

    public JTextField[] getUserTextFields() {
        return userTextFields;
    }
}
