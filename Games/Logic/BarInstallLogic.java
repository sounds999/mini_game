package Games.Logic;

import Games.GUI.BarDirectionSelectWindow;
import Games.GUI.QuoridorButtonPanel;
import Games.GUI.QuoridorTextField;

import javax.swing.*;


public class BarInstallLogic extends JFrame {

    // BarDirectionSelectWindow에서 받아온 button과 RadioButton
    private JRadioButton[] directionRadioButtons; // 0번에 "가로" 버튼, 1번에 "세로" 버튼
    private int dircetion = -1; // 값이 -1이면 선택 안 함, 0이면 "가로", 1이면 "세로"
    private JButton[] checkCancelButtons; // 0번에 "확인" 버튼, 1번에 "취소" 버튼
    private int checkCancel; // 값이 0이면 "확인", 1이면 "취소"

    private int[] barNumber = {10, 10}; // 0번에 백색돌 막대 수, 1번에 흑색 돌 막대 수 <- 다시 만든다면 백과 흑 따로 만들었을 듯 하다
    private String[][] barLocation; // bar가 설치되어 있으면 "bar", 없으면 ""

    // 사용 객체들
    private JTextField[] userTextFields; // 0번에 topTextField(검은말), 1번에 bottomTextField(흰말)
    private QuoridorButtonPanel ButtonPanelObject; // GUI 꺼낼 때 사용할 목적
    private JButton[][] quoridorButtons; // 버튼 reference 저장
    private String[] playerName; // 0번에 백색 말 유저 이름, 1번에 흑색 말 유저 이름

    // 생성자
    public BarInstallLogic(QuoridorButtonPanel ButtonPanelObject,
                           JButton[][] quoridorButtons, QuoridorTextField TextFieldObject, String[][] barLocation) {
        this.ButtonPanelObject = ButtonPanelObject;
        this.quoridorButtons = quoridorButtons;
        this.userTextFields = TextFieldObject.getUserTextFields();
        this.playerName = TextFieldObject.getPlayerNames();
        this.barLocation = barLocation;
    }

    // 실시간 막대 설치 버튼 누른 좌표
    private int[] barInstallButtonCoordinate = new int[2];

    // 막대 설치 로직
    // 작은 button이 눌러졌을 때 호출됨
    public void installBar(int turn, int clickedButtonRow, int clickedButtonCol, PlayerMoveLogic playerMoveLogicObject) {
        barInstallButtonCoordinate[0] = clickedButtonRow;
        barInstallButtonCoordinate[1] = clickedButtonCol;

        // 누른 곳에 막대가 없는지 확인
        if (quoridorButtons[clickedButtonRow][clickedButtonCol].getIcon() == ButtonPanelObject.getBarCanPutImg()) {
            // 방향 선택할 수 있는 window 창을 띄움
            BarDirectionSelectWindow barDirectionSelectWindow = new BarDirectionSelectWindow();

            // 방향 선택 창의 가로,세로 버튼 및 확인 취소 버튼 가져옴
            directionRadioButtons = barDirectionSelectWindow.getDirections(); // 0번 가로, 1번 세로
            checkCancelButtons = barDirectionSelectWindow.getCheckCancelButtons(); // 0번 확인, 1번 취소

            eventListenerSet(turn, barDirectionSelectWindow, playerMoveLogicObject);
        }
    }

    // 가져온 Button과 JRadioButton에 EventListener전달
    public void eventListenerSet(int turn, BarDirectionSelectWindow barDirectionSelectWindow, PlayerMoveLogic playerMoveLogicObject) {
        for (int i = 0; i < directionRadioButtons.length; i++) {
            directionRadioButtons[i].addItemListener(e2 -> {
                if (directionRadioButtons[0].isSelected()) dircetion = 0; // 가로 선택
                else if (directionRadioButtons[1].isSelected()) dircetion = 1; // 세로 선택
            });
            checkCancelButtons[i].addActionListener(e3 -> {
                if (checkCancelButtons[0] == e3.getSource()) checkCancel = 0; // 확인 선택
                if (checkCancelButtons[1] == e3.getSource()) checkCancel = 1; // 취소 선택
                barInstallLogic(turn, barDirectionSelectWindow, playerMoveLogicObject);
            });
        }
    }

    // bar을 설치
    public void barInstallLogic(int turn, BarDirectionSelectWindow barDirectionSelectWindow, PlayerMoveLogic playerMoveLogicObject) {
        if (checkCancel == 1) { // 취소버튼 누름
            barDirectionSelectWindow.closeWindow(); // 창 종료
        } else if (checkCancel == 0) { //  확인버튼 누름
            // 막대 갯수 확인
            for (int i = 0; i < 2; i++) {
                if ((turn == i) && (barNumber[i] > 0)) {

                    // TODO 만약 해당 bar가 유저의 경로를 차단하지 않는 경우에 if문이 true가 되어서 barinstall이 실행되게 하는 로직 구현
                    barinstall(turn, barDirectionSelectWindow, playerMoveLogicObject);

                } else if ((turn == i) && (barNumber[i] <= 0)) {
                    barDirectionSelectWindow.getWarning().setText("막대를 모두 사용하셨습니다");
                }
            }
        }
    }

    // 바 설치
    //turn 값이 0이면 백색 턴, 1이면 흑색
    private void barinstall(int turn, BarDirectionSelectWindow barDirectionSelectWindow, PlayerMoveLogic playerMoveLogicObject) {
        int row = barInstallButtonCoordinate[0];
        int col = barInstallButtonCoordinate[1];

        // 막대 가로 방향 선택
        if ((dircetion == 0) &&
                barLocation[row][col - 1].equals("") && barLocation[row][col + 1].equals("")) {
            quoridorButtons[row][col - 1].setIcon(ButtonPanelObject.getInstalledHorizontalBarImg());
            quoridorButtons[row][col].setIcon(ButtonPanelObject.getInstalledCentalBarImg());
            quoridorButtons[row][col + 1].setIcon(ButtonPanelObject.getInstalledHorizontalBarImg());
            for (int i = 0; i < 3; i++) barLocation[row][col + 1 - i] = "bar"; // 막대 위치 삽입

            // 막대 숫자 감소
            if (turn == 0) {
                barNumber[0]--;
                userTextFields[1].setText("USER: " + playerName[0] + "(○)                                                     막대 수 " + barNumber[0] + "개");
            } else if (turn == 1) {
                barNumber[1]--;
                userTextFields[0].setText("USER: " + playerName[1] + "(●)                                                     막대 수 " + barNumber[1] + "개");
            }

            moveTurn(turn, playerMoveLogicObject);

            barDirectionSelectWindow.closeWindow(); // 창 종료

            // 가로 막대 설치 영역에 이미 막대가 있는 경우
        } else if ((dircetion == 0) &&
                !(barLocation[row][col - 1].equals("")) || !(barLocation[row][col + 1].equals(""))) {
            barDirectionSelectWindow.getWarning().setText("이미 막대가 설치된 구역입니다");
        }

        // 막대 세로 방향 선택
        if ((dircetion == 1) &&
                barLocation[row - 1][col].equals("") && barLocation[row + 1][col].equals("")) {
            quoridorButtons[row + 1][col].setIcon(ButtonPanelObject.getInstalledVerticalBarImg());
            quoridorButtons[row][col].setIcon(ButtonPanelObject.getInstalledCentalBarImg());
            quoridorButtons[row - 1][col].setIcon(ButtonPanelObject.getInstalledVerticalBarImg());
            for (int i = 0; i < 3; i++) barLocation[row + 1 - i][col] = "bar"; // 막대 위치 삽입

            if (turn == 0) {
                barNumber[0]--;
                userTextFields[1].setText("USER: " + playerName[0] + "(○)                                                     막대 수 " + barNumber[0] + "개");
            } else if (turn == 1) {
                barNumber[1]--;
                userTextFields[0].setText("USER: " + playerName[1] + "(●)                                                     막대 수 " + barNumber[1] + "개");
            }

            moveTurn(turn, playerMoveLogicObject);

            barDirectionSelectWindow.closeWindow(); // 창 종료


            // 가로 막대 설치 영역에 이미 막대가 있는 경우
        } else if ((dircetion == 1) &&
                !(barLocation[row - 1][col].equals("")) || !(barLocation[row + 1][col].equals(""))) {
            barDirectionSelectWindow.getWarning().setText("이미 막대가 설치된 구역입니다");
        }
    }

    // 턴 이동
    public void moveTurn(int turn, PlayerMoveLogic playerMoveLogicObject) {
        // 현재 mark 삭제
        playerMoveLogicObject.removeMark();
        // 턴 변경
        if (turn == 0) {
            playerMoveLogicObject.setTurn(1);
        }
        if (turn == 1) {
            playerMoveLogicObject.setTurn(0);
        }
        playerMoveLogicObject.canMoveMark();
    }
}

