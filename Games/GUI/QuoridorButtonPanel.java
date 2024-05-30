package Games.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/*
패널을 생성한 뒤, 생성된 패널에 버튼을 넣음
버튼이 넣어진 패널을 QuoridorMainFrame에 넘겨줌
*/
public class QuoridorButtonPanel extends JPanel {

    // 버튼 크기
    private final int userMoveButtonWidth = 60;
    private final int userMoveButtonHeight = 60;
    private final int barCanPutButtonWidth = 15;
    private final int barCanPutButtonHeight = 15;
    private final int horizontalBarButtonWidth = 60;
    private final int horizontalBarButtonHeight = 15;
    private final int verticalBarButtonWidth = 15;
    private final int verticalBarButtonHeight = 60;

    // button Icon
    private ImageIcon userMoveImg = new ImageIcon("src/Games/QuoridorImg/userMoveImg.jpg"); // 말이 이동할 수 있는 Button 이미지
    private ImageIcon userCanMoveImg = new ImageIcon("src/Games/QuoridorImg/userCanMoveImg.jpg"); // 유저 턴에 실제 이동 가능한 mark 이미지
    private ImageIcon player1LocationImg = new ImageIcon("src/Games/QuoridorImg/player1LocationImg.jpg"); // 백색말
    private ImageIcon player2LocationImg = new ImageIcon("src/Games/QuoridorImg/player2LocationImg.jpg"); // 흑색말
    private ImageIcon barCanPutImg = new ImageIcon("src/Games/QuoridorImg/barCanPutImg.jpg"); // 선택해서 바를 선택할 수 있는 이미지
    private ImageIcon verticalBarImg = new ImageIcon("src/Games/QuoridorImg/verticalBarImg.jpg"); // 바를 설치할 수 있는 세로 막대 이미지
    private ImageIcon horizontalBarImg = new ImageIcon("src/Games/QuoridorImg/horizontalBarImg.jpg"); // 바를 설치할 수 있는 가로 막대 이미지
    private ImageIcon installedVerticalBarImg = new ImageIcon("src/Games/QuoridorImg/installedVerticalBarImg.jpg"); // 설치된 세로 막대 이미지
    private ImageIcon installedHorizontalBarImg = new ImageIcon("src/Games/QuoridorImg/installedHorizontalBarImg.jpg"); // 설치된 가로 막대 이미지
    private ImageIcon installedCentalBarImg = new ImageIcon("src/Games/QuoridorImg/button.png"); // 바가 설치된 중앙 막대 이미지

    // 버튼 가로 세로 갯수
    private final int verticalButtonNumber = 17;
    private final int horizontalButtonNumber = 17;

    private JButton[][] quoridorButtons = new JButton[verticalButtonNumber][horizontalButtonNumber];
    JPanel buttonPanel = new JPanel();

    // 생성자
    public QuoridorButtonPanel(int ButtonPanelWidth, int ButtonPanelHeight, int TextFieldWidth, int TextFieldHeight) {
        buttonPanel.setBounds(0,TextFieldHeight, ButtonPanelWidth,ButtonPanelHeight);
        createButtons();
        /*quoridorButtons[0][0].setIcon(player1LocationImg);
        quoridorButtons[0][2].setIcon(player2LocationImg);
        quoridorButtons[0][4].setIcon(userCanMoveImg);
        quoridorButtons[0][1].setIcon(installedVerticalBarImg);
        quoridorButtons[2][1].setIcon(installedVerticalBarImg);
        quoridorButtons[1][1].setIcon(installedCentalBarImg);
        quoridorButtons[1][0].setIcon(installedHorizontalBarImg);
        quoridorButtons[1][2].setIcon(installedHorizontalBarImg);*/
    }

    public JButton[][] getQuoridorButtons() {
        return quoridorButtons;
    }

    // 패널 QuoridorMainJFrame으로 반환
    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    // button 생성
    private void createButtons() {
        buttonPanel.setLayout(null);

        int coordinateY=0;
        for (int i = 0; i < quoridorButtons.length ; i++) { // button index가 0부터 시작함
            int coordinateX=0;
            for (int j = 0; j < quoridorButtons[i].length; j++) {
                JButton newButton = new JButton();
                buttonPanel.add(newButton);
                quoridorButtons[i][j] = newButton;

                if(i % 2 == 0 && j % 2 == 0) { // 유저 말이 이동할 수 있는 button
                    newButton.setIcon(userMoveImg);
                    newButton.setBounds(coordinateX,coordinateY,userMoveButtonWidth,userMoveButtonHeight);
                } else if (i % 2 == 0 && j % 2 == 1) { // 세로 방향 막대 위치 button
                    newButton.setIcon(verticalBarImg);
                    newButton.setBounds(coordinateX,coordinateY,verticalBarButtonWidth,verticalBarButtonHeight);
                } else if (i % 2 == 1 && j % 2 == 0) { // 가로 방향 막대 위치 button
                    newButton.setIcon(horizontalBarImg);
                    newButton.setBounds(coordinateX,coordinateY,horizontalBarButtonWidth,horizontalBarButtonHeight);
                } else if (i % 2 == 1 && j % 2 == 1) { // 막대 put 버튼
                    newButton.setIcon(barCanPutImg);
                    newButton.setBounds(coordinateX,coordinateY,barCanPutButtonWidth,barCanPutButtonHeight);
                }
                coordinateX += (j % 2 == 0 ? userMoveButtonWidth : verticalBarButtonWidth);
            }
            coordinateY += (i % 2 == 0 ? userMoveButtonHeight : horizontalBarButtonHeight);
        }
    }

    // 이미지 getter들(로직에서 icon 비교를 할 때 사용한다)
    public ImageIcon getUserMoveImg() {
        return userMoveImg;
    }

    public ImageIcon getUserCanMoveImg() {
        return userCanMoveImg;
    }

    public ImageIcon getPlayer1LocationImg() {
        return player1LocationImg;
    }

    public ImageIcon getPlayer2LocationImg() {
        return player2LocationImg;
    }

    public ImageIcon getBarCanPutImg() {
        return barCanPutImg;
    }

    public ImageIcon getVerticalBarImg() {
        return verticalBarImg;
    }

    public ImageIcon getHorizontalBarImg() {
        return horizontalBarImg;
    }

    public ImageIcon getInstalledVerticalBarImg() {
        return installedVerticalBarImg;
    }

    public ImageIcon getInstalledHorizontalBarImg() {
        return installedHorizontalBarImg;
    }

    public ImageIcon getInstalledCentalBarImg() {
        return installedCentalBarImg;
    }

}
