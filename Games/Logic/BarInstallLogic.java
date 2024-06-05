package Games.Logic;

import Games.GUI.BarDirectionSelectWindow;
import Games.GUI.QuoridorButtonPanel;
import Games.GUI.QuoridorTextField;

import javax.swing.*;
import java.util.Stack;


public class BarInstallLogic extends JFrame {

    // BarDirectionSelectWindow에서 받아온 button과 RadioButton
    private JRadioButton[] directionRadioButtons; // 0번에 "가로" 버튼, 1번에 "세로" 버튼
    private int dircetion = -1; // 값이 -1이면 선택 안 함, 0이면 "가로", 1이면 "세로"
    private JButton[] checkCancelButtons; // 0번에 "확인" 버튼, 1번에 "취소" 버튼
    private int checkCancel; // 값이 0이면 "확인", 1이면 "취소"

    private int[] barNumber = {10, 10}; // 0번에 백색돌 막대 수, 1번에 흑색 돌 막대 수 <- 다시 만든다면 백과 흑 따로 만들었을 듯 하다
    private String[][] barLocation; // bar가 설치되어 있으면 "bar", 없으면 ""
    private int[][] currentPlayerCoodinate; // 현재 말들의 좌표 <- 이런 식으로 다른 객체의 변수를 가져와서 저장하는 방식의 코딩은 좋은 방식은 아닌 것 같다
                                            // 차라리 객체를 가져와서 객체의 getter을 사용해서 변수에 접근하는 것이 더 좋은 코드 작성법이라고 생각한다 (관리할 변수가 느니까)

    // 사용 객체들
    private JTextField[] userTextFields; // 0번에 topTextField(검은말), 1번에 bottomTextField(흰말)
    private QuoridorButtonPanel ButtonPanelObject; // GUI 꺼낼 때 사용할 목적
    private JButton[][] quoridorButtons; // 버튼 reference 저장
    private String[] playerName; // 0번에 백색 말 유저 이름, 1번에 흑색 말 유저 이름

    // 생성자
    public BarInstallLogic(QuoridorButtonPanel ButtonPanelObject,
                           JButton[][] quoridorButtons, QuoridorTextField TextFieldObject, String[][] barLocation, int[][] currentPlayerCoordinate) {
        this.ButtonPanelObject = ButtonPanelObject;
        this.quoridorButtons = quoridorButtons;
        this.userTextFields = TextFieldObject.getUserTextFields();
        this.playerName = TextFieldObject.getPlayerNames();
        this.barLocation = barLocation;
        this.currentPlayerCoodinate = currentPlayerCoordinate;
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
                    if(!pasthBlocking()) { // 바를 두면 경로가 차단되는지 확인한다
                        barinstall(turn, barDirectionSelectWindow, playerMoveLogicObject);
                    }

                } else if ((turn == i) && (barNumber[i] <= 0)) {
                    barDirectionSelectWindow.getWarning().setText("막대를 모두 사용하셨습니다");
                }
            }
        }
    }

    // 해당 위치에 bar을 두면 말 경로가 차단되는지 유무 검사
    private boolean pasthBlocking() {
        int[] whitePiecesCoordinate = currentPlayerCoodinate[0]; // 흰 말 현재좌표
        int[] blackPiecesCoordinate = currentPlayerCoodinate[1]; // 검 말 현재좌표

        String[][] whiteQuridor = deepCopy(barLocation);
        String[][] blackQuridor = deepCopy(barLocation);

        // button 기준으로 작성한 좌표를 String기준으로 변경함
        for (int i = 0; i < whitePiecesCoordinate.length ; i++) {
            whitePiecesCoordinate[i] += 1;
            blackPiecesCoordinate[i] += 1;
        }

        whitePathBlocking(whiteQuridor, whitePiecesCoordinate);
        blackPathBlocking(blackQuridor, blackPiecesCoordinate);

        return false;
    }

    // 2차원 String 배열을 딥카피 한다
    private String[][] deepCopy(String[][] barLocation) {
        String[][] temArr = new String[barLocation.length][barLocation[0].length];

        for (int i = 0; i < temArr.length; i++) {
            temArr[i] = barLocation[i].clone();
        }
        return temArr;
    }

    // 흰색 돌 경로 체크하고 이후에 흰색 돌 체크랑 검은색 돌 체크 method를 함칠 수 있는 방법 고안 (체크해야 하는 줄을 매개변수로 건네주기)
    // 흰색 돌 경로 체크
    private boolean whitePathBlocking(String[][] Quridor, int[] currentCoordinate) {
        Stack<int[]> stack = new Stack<int[]>();
        stack.add(currentCoordinate);


        return false;
    }

    // 검은 돌 경로 체크
    private boolean blackPathBlocking(String[][] Quridor, int[] currentCoordinate) {
        Stack<int[]> stack = new Stack<int[]>();
        stack.add(currentCoordinate);


        return false;
    }


    // 바 설치
    //turn 값이 0이면 백색 턴, 1이면 흑색
    private void barinstall(int turn, BarDirectionSelectWindow barDirectionSelectWindow, PlayerMoveLogic playerMoveLogicObject) {
        int row = barInstallButtonCoordinate[0];
        int col = barInstallButtonCoordinate[1];

        // barLocation이 quridorButtons보다 테두리 배열이 한 줄 더 있는 것 감안
        int StringRow = barInstallButtonCoordinate[0] + 1;
        int StringCol = barInstallButtonCoordinate[1] + 1;

        // 막대 가로 방향 선택
        if ((dircetion == 0) &&
                barLocation[StringRow][StringCol - 1].equals("") && barLocation[StringRow][StringCol + 1].equals("")) {
            quoridorButtons[row][col - 1].setIcon(ButtonPanelObject.getInstalledHorizontalBarImg());
            quoridorButtons[row][col].setIcon(ButtonPanelObject.getInstalledCentalBarImg());
            quoridorButtons[row][col + 1].setIcon(ButtonPanelObject.getInstalledHorizontalBarImg());
            for (int i = 0; i < 3; i++) barLocation[StringRow][StringCol + 1 - i] = "bar"; // 막대 위치 삽입

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
                !(barLocation[StringRow][StringCol - 1].equals("")) || !(barLocation[StringRow][StringCol + 1].equals(""))) {
            barDirectionSelectWindow.getWarning().setText("이미 막대가 설치된 구역입니다");
        }

        // 막대 세로 방향 선택
        if ((dircetion == 1) &&
                barLocation[StringRow - 1][StringCol].equals("") && barLocation[StringRow + 1][StringCol].equals("")) {
            quoridorButtons[row + 1][col].setIcon(ButtonPanelObject.getInstalledVerticalBarImg());
            quoridorButtons[row][col].setIcon(ButtonPanelObject.getInstalledCentalBarImg());
            quoridorButtons[row - 1][col].setIcon(ButtonPanelObject.getInstalledVerticalBarImg());
            for (int i = 0; i < 3; i++) barLocation[StringRow + 1 - i][StringCol] = "bar"; // 막대 위치 삽입

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
                !(barLocation[StringRow - 1][StringCol].equals("")) || !(barLocation[StringRow + 1][StringCol].equals(""))) {
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

