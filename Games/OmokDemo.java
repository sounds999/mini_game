package Games;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OmokDemo extends JFrame implements ActionListener {
	private JTextField msgField; // 메시지 필드
	private String[][] board = new String[15][15]; // 오목판 현황
	private String[] player = new String[2]; // 선수 이름
	private String[] stone = { "●", "○" }; // 오목알
	private int turn = 0; // 현재 순서
	private JButton[][] button;
	private boolean continueGame = true;
	private ImageIcon[] stoneImg = {new ImageIcon("src/Games/OmokIcon/BlackStone.jpg"),
			new ImageIcon("src/Games/OmokIcon/WhiteStone.jpg")};
	private ImageIcon boardImg = new ImageIcon("src/Games/OmokIcon/Board.jpg");
	private ImageIcon boardCenter = new ImageIcon("src/Games/OmokIcon/BoardCenter.jpg");

	public OmokDemo(String[] player) {
		this.player = player;
		
		setTitle("Omok");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 900);
		setLayout(new GridLayout(16, 1));

		JPanel[] panel = new JPanel[16];
		for (int i = 0; i < 15; i++) {
			panel[i] = new JPanel();
			panel[i].setLayout(new GridLayout(1, 15));
		}
		panel[15] = new JPanel();
		panel[15].setLayout(new GridLayout(1, 1));

		button = new JButton[15][15];
		
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button.length; j++) {
				button[i][j] = new JButton(boardImg);
				button[i][j].setBorderPainted(false);
				button[i][j].addActionListener(this);
				board[i][j] = ""; // 오목판 설정
				panel[i].add(button[i][j]);
			}
			add(panel[i]);
		}
		
		button[7][7].setIcon(boardCenter);
		button[3][3].setIcon(boardCenter); button[3][11].setIcon(boardCenter);
		button[11][3].setIcon(boardCenter); button[11][11].setIcon(boardCenter);

		JPanel msgPanel = new JPanel();
		msgField = new JTextField();
		msgField.setBackground(Color.WHITE);
		msgField.setForeground(Color.BLACK);
		msgField.setPreferredSize(new Dimension(800, 40));
		msgField.setHorizontalAlignment(JTextField.CENTER);
		msgField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		msgPanel.add(msgField);
		add(msgPanel);
		msgField.setText(player[turn] + "[" + stone[turn] + "]님의 순서입니다. 오목알을 옮겨주세요.");
		
		setVisible(true);
		setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (continueGame) {
				OmokGameStart(e);
			}
		} catch (NumberFormatException e2) {
			msgField.setText("Error : Reenter Button.");
		}
	}

	public void OmokGameStart(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		int row = -1, col = -1;

		if (btn.getIcon().equals(boardImg) || btn.getIcon().equals(boardCenter)) {
			for (int i = 0; i < button.length; i++) { // 클릭 위치 좌표 찾기
				for (int j = 0; j < button[i].length; j++) {
					if (button[i][j] == btn) {
						row = i;
						col = j;
						break;
					}
				}
				if (row != -1 && col != -1)
					break;
			}
			if (turn == 0) {
				if (black33(row, col, stone[turn])) { // 흑돌일 경우 33인지 확인
					msgField.setText("※흑돌 33불가※ " + player[turn] + "[" + stone[turn] + "]님, 해당 위치에는 오목알을 놓을 수 없습니다.");
				} else {
					board[row][col] = stone[turn]; // 좌표에 오목알 위치
					btn.setIcon(stoneImg[turn]); // 좌표에 오목알 위치(display)
					
					if (!complete(row, col, board)) {
						msgField.setText(player[(turn == 0) ? 1 : 0] + "[" + stone[(turn == 0) ? 1 : 0] + "]님의 순서입니다. 오목알을 옮겨주세요.");
						turn = (turn == 0) ? 1 : 0;
					} else {
						msgField.setText(winnerPrint(player, stone, turn));
						continueGame = false;
					}
				}
			} else if (turn == 1) {
				board[row][col] = stone[turn]; // 좌표에 오목알 위치
				btn.setIcon(stoneImg[turn]); // 좌표에 오목알 위치(display)
				
				if (!complete(row, col, board)) {
					msgField.setText(player[(turn == 0) ? 1 : 0] + "[" + stone[(turn == 0) ? 1 : 0] + "]님의 순서입니다. 오목알을 옮겨주세요.");
					turn = (turn == 0) ? 1 : 0;
				} else {
					msgField.setText(winnerPrint(player, stone, turn));
					continueGame = false;
				}
			}
		} else {
			msgField.setText(player[turn] + "[" + stone[turn] + "]님, 다른 오목알이 이미 놓여있습니다. 다시 놓아 주세요.");
		}

	}
	
	// 흑돌 33 확인
	public boolean black33(int row, int col, String stone) {
        if (!board[row][col].isEmpty()) {
            return false;
        }
        
        board[row][col] = stone;
        int countThree = 0; // 열린 삼의 개수

        for (int dx = -1; dx <= 1; dx++) { // 좌우
            for (int dy = -1; dy <= 1; dy++) { // 상하
                if (dx == 0 && dy == 0) {
                    continue; // 본인 방향은 제외
                }
                if (checkOpen(row, col, dx, dy, stone)) {
                    countThree++;
                }
            }
        }
        board[row][col] = "";

        return countThree >= 3;
    }
	// 흑돌 33 확인2
	private boolean checkOpen(int row, int col, int dx, int dy, String stone) {
        int count = 1; // 현재 돌을 포함하여 연속된 돌의 개수
        boolean leftEnds = false; // 왼쪽 끝이 비어있는지 여부
        boolean rightEnds = false; // 오른쪽 끝이 비어있는지 여부

        int nx = row + dx;
        int ny = col + dy;
        
        while (nx >= 0 && nx < 15 && ny >= 0 && ny < 15 && board[nx][ny].equals(stone)) {
            count++;
            nx += dx;
            ny += dy;
        }
        if (nx >= 0 && nx < 15 && ny >= 0 && ny < 15 && board[nx][ny].isEmpty()) {
        	rightEnds = true;
        }

        nx = row - dx;
        ny = col - dy;
        while (nx >= 0 && nx < 15 && ny >= 0 && ny < 15 && board[nx][ny].equals(stone)) {
            count++;
            nx -= dx;
            ny -= dy;
        }
        if (nx >= 0 && nx < 15 && ny >= 0 && ny < 15 && board[nx][ny].isEmpty()) {
        	leftEnds = true;
        }
        
        return count == 3 && leftEnds && rightEnds;
    }
	
	// 오목 완성 확인
	public static boolean complete(int row, int col, String[][] board) {
		if ((seroComplete(row, col, board)) || (garoComplete(row, col, board)) 
				|| rightUpComplete(row, col, board) || rightDownComplete(row, col, board))
			return true;
		else
			return false;
	}
	
	// 세로 오목 완성 확인
	public static boolean seroComplete(int row, int col, String[][] board) {
		boolean pass = false;
		int cnt = 0;
		int startRow = 0;
		int endRow = 14;

		for (int i = startRow; i <= endRow; i++) {
			if (board[i][col].equals(board[row][col])) {
				cnt++;
				// 육목 확인
				if (cnt > 5) {
					pass = false;
					cnt = 0;
				} else if (cnt == 5)
					pass = true;
			} else
				cnt = 0;
		}
		return pass;
	}

	// 가로 오목 완성 확인
	public static boolean garoComplete(int row, int col, String[][] board) {
		boolean pass = false;
		int cnt = 0;
		int startCal = 0;
		int endCal = 14;

		for (int i = startCal; i <= endCal; i++) {
			if (board[row][i].equals(board[row][col])) {
				cnt++;
				// 육목 확인
				if (cnt > 5) {
					pass = false;
					cnt = 0;
				} else if (cnt == 5)
					pass = true;
			} else
				cnt = 0;
		}
		return pass;
	}

	// 우상향 대각선 오목 완성 확인
	public static boolean rightUpComplete(int row, int col, String[][] board) {
		boolean pass = false;
		int cnt = 0, move = -1;
		int startRow = row, startCal = col;
		int endRow = row, endCal = col;

		while (startRow < 14 && startCal > 0) {
			startRow++;
			startCal--;
		}

		while (endRow > 0 && endCal < 14) {
			endRow--;
			endCal++;
		}

		for (int i = startRow; i >= endRow; i--) {
			move++;
			if ((startCal + move) > endCal)
				break;
			else if (board[i][startCal + move].equals(board[row][col])) {
				cnt++;
				// 육목 확인
				if (cnt > 5) {
					pass = false;
					cnt = 0;
				} else if (cnt == 5)
					pass = true;
			} else
				cnt = 0;
		}
		return pass;
	}

	// 우하향 대각선 오목 완성 확인
	public static boolean rightDownComplete(int row, int col, String[][] board) {
		boolean pass = false;
		int cnt = 0, move = -1;
		int startRow = row, startCal = col;
		int endRow = row, endCal = col;

		while (startRow > 0 && startCal > 0) {
			startRow--;
			startCal--;
		}

		while (endRow < 14 && endCal < 14) {
			endRow++;
			endCal++;
		}

		for (int i = startRow; i <= endRow; i++) {
			move++;
			if ((startCal + move) > endCal)
				break;
			else if (board[i][startCal + move].equals(board[row][col])) {
				cnt++;
				// 육목 확인
				if (cnt > 5) {
					pass = false;
					cnt = 0;
				} else if (cnt == 5)
					pass = true;
			} else
				cnt = 0;
		}
		return pass;
	}
	
	public static String winnerPrint(String[] player, String[] stone, int turn) {
		return "☆★☆★☆★☆★   " + player[turn] + "[" + stone[turn] + "]님께서 오목을 완성하여 승리하셨습니다!   ☆★☆★☆★☆★";
	}

//	public static void main(String[] args) {
//		OmokDemo test = new OmokDemo();
//	}

}
