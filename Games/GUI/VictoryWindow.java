package Games.GUI;

import javax.swing.*;
import java.awt.*;

public class VictoryWindow extends JFrame {

    public VictoryWindow(String[] playersNames, int turn) {
        String currnetPlayer = "";

        if(turn == 1)
            currnetPlayer = playersNames[1];
        else if (turn == 0)
            currnetPlayer = playersNames[0];

        JTextArea victoryText = new JTextArea();
        victoryText.setRows(3);
        victoryText.setLineWrap(true);
        victoryText.setText("★★★★★★★★★★★★★★★★★★★★★★★★★★★★"
                            + currnetPlayer + "님이 승리하셨습니다   ★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
        victoryText.setFont(new Font("맑은 고딕", Font.BOLD, 20));

        add(victoryText);
        setBounds(225,250,300,180);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
