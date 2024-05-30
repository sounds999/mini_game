package Games.Logic;

import Games.GUI.QuoridorButtonPanel;
import Games.GUI.QuoridorTextField;
import Games.GUI.VictoryWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerMoveLogic extends JFrame implements ActionListener {

    // 버튼 GUI 객체
    private QuoridorButtonPanel ButtonPanelObject;

    // 바 설치 로직 객체
    private BarInstallLogic barInstallLogic;

    // Field GUI 객체
    private QuoridorTextField TextFieldObjects;

    private JButton[][] quoridorButtons;
    private boolean continuGame = true; // 게임 판에서 양 쪽 판 위 버튼을 확인하여 반대편 시작 아이콘이 존재하면 종료
    private int[][] currentPlayerCoordinate = new int[2][2]; // 0번에 백색말 (row,col), 1번에 흑색말 (row,col)
    private int turn = 0; // 0은 백색 턴, 1은 흑색 턴이다
    private String[][] barLocation = new String[17][17]; // bar가 설치되어 있으면 "bar", 없으면 ""

    // Constuctor
    // QuoridorDemo에서 quoridorButtonPanel을 받아옴 (Icon 및 Button을 사용하기 위해서) (Icon 클래스를 따로 만드는 것도 괜찮을 듯 함)
    public PlayerMoveLogic(QuoridorButtonPanel quoridorButtonPanel, QuoridorTextField TextFieldObject) {
        this.TextFieldObjects = TextFieldObject;
        this.ButtonPanelObject = quoridorButtonPanel;
        this.quoridorButtons = quoridorButtonPanel.getQuoridorButtons();

        // BarLocation 초기화
        for (int i = 0; i < barLocation.length; i++) {
            for (int j = 0; j < barLocation[i].length; j++) {
                barLocation[i][j] = "";
            }
        }

        // 버튼에 EventListener 주입 및 초기 설정
        insertEventListenerToButton();

        // 초기 설정 끝나고 막대 로직 객체를 생성해 주어야 함
        barInstallLogic = new BarInstallLogic(ButtonPanelObject, quoridorButtons, TextFieldObjects, barLocation);

        // 현재 turn의 버튼 주위로 이동할 수 있는 Icon 띄우는 메서드
        canMoveMark();
    }


    // 버튼에 EventListener 주입 메서드
    private void insertEventListenerToButton() {
        for (int i = 0; i < quoridorButtons.length; i++) {
            for (int j = 0; j < quoridorButtons[i].length; j++) {
                quoridorButtons[i][j].addActionListener(this); // eventHandler을 갖고 있는 eventListener 건네줌
            }
        }
        // 시작 시점 말 좌표
        currentPlayerCoordinate[0][0] = 16;
        currentPlayerCoordinate[0][1] = 8;
        currentPlayerCoordinate[1][0] = 0;
        currentPlayerCoordinate[1][1] = 8;
        quoridorButtons[16][8].setIcon(ButtonPanelObject.getPlayer1LocationImg());
        quoridorButtons[0][8].setIcon(ButtonPanelObject.getPlayer2LocationImg());

    }

    //현재 턴 말이 이동할 수 있는 mark 표시
    public void canMoveMark() {
        // 백색돌 차례인 경우
        if (turn == 0) {
            // 현재 백색 돌 좌표 저장
            int row = currentPlayerCoordinate[0][0];
            int col = currentPlayerCoordinate[0][1];

            // row , col 범위 조건, 해당 방향으로 막대가 설치되어 있는지 검사, 앞에 상대 말이 있거나 비어있으면 thread 통과
            installMark("동", row, col, ButtonPanelObject.getPlayer2LocationImg());
            installMark("서", row, col, ButtonPanelObject.getPlayer2LocationImg());
            installMark("남", row, col, ButtonPanelObject.getPlayer2LocationImg());
            installMark("북", row, col, ButtonPanelObject.getPlayer2LocationImg());
        }
        //흑색돌 차례인 경우
        else if (turn == 1) {
            // 현재 흑색 돌 좌표 저장
            int row = currentPlayerCoordinate[1][0];
            int col = currentPlayerCoordinate[1][1];

            installMark("동", row, col, ButtonPanelObject.getPlayer1LocationImg());
            installMark("서", row, col, ButtonPanelObject.getPlayer1LocationImg());
            installMark("남", row, col, ButtonPanelObject.getPlayer1LocationImg());
            installMark("북", row, col, ButtonPanelObject.getPlayer1LocationImg());
        }
    }

    // 이동할 수 있는 표시 생성
    public void installMark(String direction, int row, int col, ImageIcon competitorIcon) {
        int oneJumpMarkRow = 0;
        int oneJumpMarkCol = 0;
        int twoJumpMarkRow = 0;
        int twoJumpMarkCol = 0;
        int oneJumpBarRow = 0;
        int oneJumpBarCol = 0;
        int towJumpBarRow = 0;
        int twoJumpBarCol = 0;

        if (direction.equals("동")) {
            oneJumpMarkRow = row;
            oneJumpMarkCol = col - 2;
            twoJumpMarkRow = row;
            twoJumpMarkCol = col - 4;
            oneJumpBarRow = row;
            oneJumpBarCol = col - 1;
            towJumpBarRow = row;
            twoJumpBarCol = col - 3;

        }
        else if (direction.equals("서")) {
            oneJumpMarkRow = row;
            oneJumpMarkCol = col + 2;
            twoJumpMarkRow = row;
            twoJumpMarkCol = col + 4;
            oneJumpBarRow = row;
            oneJumpBarCol = col + 1;
            towJumpBarRow = row;
            twoJumpBarCol = col + 3;
        }
        else if (direction.equals("남")) {
            oneJumpMarkRow = row + 2;
            oneJumpMarkCol = col;
            twoJumpMarkRow = row + 4;
            twoJumpMarkCol = col;
            oneJumpBarRow = row + 1;
            oneJumpBarCol = col;
            towJumpBarRow = row + 3;
            twoJumpBarCol = col;
        }
        else if (direction.equals("북")) {
            oneJumpMarkRow = row - 2;
            oneJumpMarkCol = col;
            twoJumpMarkRow = row - 4;
            twoJumpMarkCol = col;
            oneJumpBarRow = row - 1;
            oneJumpBarCol = col;
            towJumpBarRow = row - 3;
            twoJumpBarCol = col;
        }

        // row , col 범위 조건, 해당 방향으로 막대가 설치되어 있는지 검사, 앞에 상대 말이 있거나 비어있으면 thread 통과
        if ((oneJumpMarkRow >= 0 && oneJumpMarkRow <= 16) && (oneJumpMarkCol >= 0 && oneJumpMarkCol <= 16)
                && !(barInstallCheck(oneJumpBarRow, oneJumpBarCol))
                && ((quoridorButtons[oneJumpMarkRow][oneJumpMarkCol].getIcon() == ButtonPanelObject.getUserMoveImg())
                || (quoridorButtons[oneJumpMarkRow][oneJumpMarkCol].getIcon() == competitorIcon)))
        {
            // 해당 방향이 빈 공간인지 검사
            if ((quoridorButtons[oneJumpMarkRow][oneJumpMarkCol].getIcon() == ButtonPanelObject.getUserMoveImg()))
            {
                quoridorButtons[oneJumpMarkRow][oneJumpMarkCol].setIcon(ButtonPanelObject.getUserCanMoveImg());
            }
            // row, col 범위 조건, 상대말 뒤로 막대가 존재하는지 검사, 해당 방향에 상대편 말이 있는지 검사
            else if ((twoJumpMarkRow >= 0 && twoJumpMarkRow <= 16) && (twoJumpMarkCol >= 0 && twoJumpMarkCol <= 16)
                    && !(barInstallCheck(towJumpBarRow, twoJumpBarCol))
                    && (quoridorButtons[oneJumpMarkRow][oneJumpMarkCol].getIcon() == competitorIcon))
            {
                quoridorButtons[twoJumpMarkRow][twoJumpMarkCol].setIcon(ButtonPanelObject.getUserCanMoveImg());
            }
        }
    }

    // 막대가 설치되어 있는지 아닌지 확인
    public boolean barInstallCheck(int row, int col) {
        if (barLocation[row][col] == "") return false;
        if (barLocation[row][col] == "bar") return true;
        return true;
    }

    // TODO 상대편 말을 건너 띄는 상황을 고려하여 4방향의 +-4까지 검사해야 한다 ( 범위 벗어나는거 조건문으로 검사 )
    // Event 발생 이전에 보여졌던 이동 가능 지역 mark 제거
    public void removeMark() {
        if (turn == 0) {
            // 현재 백색 돌 좌표 저장
            int row = currentPlayerCoordinate[0][0];
            int col = currentPlayerCoordinate[0][1];

            // 백색 돌 차례일 때 이동할 수 있는 마크 제거
            if ((row - 2 >= 0 && row - 2 <= 16) && (col >= 0 && col <= 16)) {
                if(quoridorButtons[row - 2][col].getIcon() == ButtonPanelObject.getUserCanMoveImg())
                    quoridorButtons[row - 2][col].setIcon(ButtonPanelObject.getUserMoveImg());
                else if(((row - 4 >= 0 && row - 4 <= 16) && (col >= 0 && col <= 16)))
                    if(quoridorButtons[row - 4][col].getIcon() == ButtonPanelObject.getUserCanMoveImg())
                        quoridorButtons[row - 4][col].setIcon(ButtonPanelObject.getUserMoveImg());
            }

            if ((row + 2 >= 0 && row + 2 <= 16) && (col >= 0 && col <= 16)) {
                if(quoridorButtons[row + 2][col].getIcon() == ButtonPanelObject.getUserCanMoveImg())
                    quoridorButtons[row + 2][col].setIcon(ButtonPanelObject.getUserMoveImg());
                else if(((row + 4 >= 0 && row + 4 <= 16) && (col >= 0 && col <= 16)))
                    if(quoridorButtons[row + 4][col].getIcon() == ButtonPanelObject.getUserCanMoveImg())
                        quoridorButtons[row + 4][col].setIcon(ButtonPanelObject.getUserMoveImg());
            }

            if ((row >= 0 && row <= 16) && (col - 2 >= 0 && col - 2 <= 16)) {
                if(quoridorButtons[row][col - 2].getIcon() == ButtonPanelObject.getUserCanMoveImg())
                    quoridorButtons[row][col - 2].setIcon(ButtonPanelObject.getUserMoveImg());
                else if(((row>= 0 && row <= 16) && (col - 4 >= 0 && col - 4 <= 16)))
                    if(quoridorButtons[row][col - 4].getIcon() == ButtonPanelObject.getUserCanMoveImg())
                        quoridorButtons[row][col - 4].setIcon(ButtonPanelObject.getUserMoveImg());
            }

            if ((row >= 0 && row <= 16) && (col + 2 >= 0 && col + 2 <= 16)) {
                if(quoridorButtons[row][col + 2].getIcon() == ButtonPanelObject.getUserCanMoveImg())
                    quoridorButtons[row][col + 2].setIcon(ButtonPanelObject.getUserMoveImg());
                else if(((row>= 0 && row <= 16) && (col + 4 >= 0 && col + 4 <= 16)))
                    if(quoridorButtons[row][col + 4].getIcon() == ButtonPanelObject.getUserCanMoveImg())
                        quoridorButtons[row][col + 4].setIcon(ButtonPanelObject.getUserMoveImg());
            }

        } else if (turn == 1) {
            // 현재 흑색 돌 좌표 저장
            int row = currentPlayerCoordinate[1][0];
            int col = currentPlayerCoordinate[1][1];

            // 흑색 돌 차례때 이동할 수 있는 마크 제거
            if ((row - 2 >= 0 && row - 2 <= 16) && (col >= 0 && col <= 16)) {
                if(quoridorButtons[row - 2][col].getIcon() == ButtonPanelObject.getUserCanMoveImg())
                    quoridorButtons[row - 2][col].setIcon(ButtonPanelObject.getUserMoveImg());
                else if(((row - 4 >= 0 && row - 4 <= 16) && (col >= 0 && col <= 16)))
                    if(quoridorButtons[row - 4][col].getIcon() == ButtonPanelObject.getUserCanMoveImg())
                        quoridorButtons[row - 4][col].setIcon(ButtonPanelObject.getUserMoveImg());
            }

            if ((row + 2 >= 0 && row + 2 <= 16) && (col >= 0 && col <= 16)) {
                if(quoridorButtons[row + 2][col].getIcon() == ButtonPanelObject.getUserCanMoveImg())
                    quoridorButtons[row + 2][col].setIcon(ButtonPanelObject.getUserMoveImg());
                else if(((row + 4 >= 0 && row + 4 <= 16) && (col >= 0 && col <= 16)))
                    if(quoridorButtons[row + 4][col].getIcon() == ButtonPanelObject.getUserCanMoveImg())
                        quoridorButtons[row + 4][col].setIcon(ButtonPanelObject.getUserMoveImg());
            }

            if ((row >= 0 && row <= 16) && (col - 2 >= 0 && col - 2 <= 16)) {
                if(quoridorButtons[row][col - 2].getIcon() == ButtonPanelObject.getUserCanMoveImg())
                    quoridorButtons[row][col - 2].setIcon(ButtonPanelObject.getUserMoveImg());
                else if(((row>= 0 && row <= 16) && (col - 4 >= 0 && col - 4 <= 16)))
                    if(quoridorButtons[row][col - 4].getIcon() == ButtonPanelObject.getUserCanMoveImg())
                        quoridorButtons[row][col - 4].setIcon(ButtonPanelObject.getUserMoveImg());
            }

            if ((row >= 0 && row <= 16) && (col + 2 >= 0 && col + 2 <= 16)) {
                if(quoridorButtons[row][col + 2].getIcon() == ButtonPanelObject.getUserCanMoveImg())
                    quoridorButtons[row][col + 2].setIcon(ButtonPanelObject.getUserMoveImg());
                else if(((row>= 0 && row <= 16) && (col + 4 >= 0 && col + 4 <= 16)))
                    if(quoridorButtons[row][col + 4].getIcon() == ButtonPanelObject.getUserCanMoveImg())
                        quoridorButtons[row][col + 4].setIcon(ButtonPanelObject.getUserMoveImg());
            }
        }
    }


    // ActionListener interface에 존재하는 eventHandler임
    // this를 통해 해당 eventHandler을 buttons에 건네줬음
    // 이를 통해서 button을 클릭 할 때마다 해당 button에 actionPerformed가 실행됨
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (continuGame) {
                // 이벤트 발생시 게임 진행
                continueLogic(e);
            }
        } catch (NumberFormatException e2) {
            // 에러 발생시 띄울 창
        }
    }

    // 이벤트에 반응해서 말을 움직이게 하는 로직
    private void continueLogic(ActionEvent e) {
        int row = -1;
        int col = -1;

        JButton clickedButton = (JButton) e.getSource(); //ActionEvent 객체는 자신을 생성한 Button의 reference를 가지고 있음

        // 이벤트를 발생시킨 버튼의 row, col 값을 알아냄
        for (int i = 0; i < quoridorButtons.length; i++) {
            for (int j = 0; j < quoridorButtons[i].length; j++) {
                if (quoridorButtons[i][j] == clickedButton) {
                    row = i;
                    col = j;
                    break;
                }
            }
            if (row != -1 && col != -1)
                break;
        }

        // 막대를 설치하는 메서드 (Event Handler)
        // BarInstallLogic으로 스레드가 가는 영역(BarInstallLogic으로 갔다가 BarDirectionSlectWindow로 이동)
        if (row % 2 == 1 && col % 2 == 1 && (quoridorButtons[row][col].getIcon() == ButtonPanelObject.getBarCanPutImg())) {
            // bar설치
            barInstallLogic.installBar(turn, row, col, this);
        }

        // 말을 이동시키는 메서드 (Event Handler)
        // 현재 클릭된 button좌표와 현재 말 위치를 사용해서 이벤트가 발생한 곳으로 이동
        // (현재 턴이 뭔지 상관하지 않고 이동하려는 버튼이 활성화 버튼인지 아닌지만 알면 된다)
        else {
            moveUnit(row, col);
        }
    }

    // 현재 클릭된 button좌표와 현재 말 위치를 사용해서 이벤트가 발생한 곳으로 이동
    private void moveUnit(int clickedButtonRow, int clickedButtonCol) {
        // 클릭한 button에 활성화 mark가 있는지 확인
        if (quoridorButtons[clickedButtonRow][clickedButtonCol].getIcon() == ButtonPanelObject.getUserCanMoveImg()) {
            // 이동 가능 mark 제거
            removeMark();
            // button 위치에 말 이동하고 현재 턴에 해당하는 말 위치 저장
            if (turn == 0) {
                quoridorButtons[clickedButtonRow][clickedButtonCol].setIcon(ButtonPanelObject.getPlayer1LocationImg());
                quoridorButtons[currentPlayerCoordinate[0][0]][currentPlayerCoordinate[0][1]].setIcon(ButtonPanelObject.getUserMoveImg());
                currentPlayerCoordinate[0][0] = clickedButtonRow;
                currentPlayerCoordinate[0][1] = clickedButtonCol;

                victoryCheck();

                turn = 1;
            } else if (turn == 1) {
                quoridorButtons[clickedButtonRow][clickedButtonCol].setIcon(ButtonPanelObject.getPlayer2LocationImg());
                quoridorButtons[currentPlayerCoordinate[1][0]][currentPlayerCoordinate[1][1]].setIcon(ButtonPanelObject.getUserMoveImg());
                currentPlayerCoordinate[1][0] = clickedButtonRow;
                currentPlayerCoordinate[1][1] = clickedButtonCol;

                victoryCheck();

                turn = 0;
            }


            //현재 턴 말이 이동할 수 있는 mark 표시
            canMoveMark();
        }
    }
    public void victoryCheck() {
        for (int i = 0; i < quoridorButtons[0].length; i += 2) {
            if(quoridorButtons[0][i].getIcon() == ButtonPanelObject.getPlayer1LocationImg()) {
                VictoryWindow victoryWindow = new VictoryWindow(TextFieldObjects.getPlayerNames(), turn);
                break;
            } else if(quoridorButtons [16][i].getIcon() == ButtonPanelObject.getPlayer2LocationImg()) {
                VictoryWindow victoryWindow = new VictoryWindow(TextFieldObjects.getPlayerNames(), turn);
                break;
            }
        }
    }


    // 턴 변경
    public void setTurn(int turn) {
        this.turn = turn;
    }

    //해당 턴
    public int getTurn() {
        return turn;
    }
}
