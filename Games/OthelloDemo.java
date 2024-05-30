package Games;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OthelloDemo extends JFrame implements ActionListener {
	private JTextField msgField; // 메시지 필드
	private String[][] board = new String[10][10]; // 오목판 현황
	private String[] player = new String[2]; // 선수 이름
	private String[] stone = { "●", "○" }; // 오목알
	private int turn = 0; // 현재 순서
	private JButton[][] button;
	private boolean continueGame = true;
	private ImageIcon[] stoneImg = {new ImageIcon("src/Games/OthelloIcon/BlackStone.jpg"), 
									new ImageIcon("src/Games/OthelloIcon/WhiteStone.jpg")};
	private ImageIcon boardImg = new ImageIcon("src/Games/OthelloIcon/Board.jpg");
	
	public OthelloDemo(String[] player) {
		this.player = player;
		
		setTitle("Othello Test");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(590, 680);
		setLayout(new GridLayout(9, 1));

		JPanel[] panel = new JPanel[21];
		for (int i = 0; i < 20; i++) {
			panel[i] = new JPanel();
			panel[i].setLayout(new GridLayout(1, 20));
		}
		panel[20] = new JPanel();
		panel[20].setLayout(new GridLayout(1, 1));

		button = new JButton[8][8];

		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button.length; j++) {
				button[i][j] = new JButton(boardImg);
				button[i][j].setBorderPainted(false);
				button[i][j].addActionListener(this);
				panel[i].add(button[i][j]);
			}
			add(panel[i]);
		}

		// 오셀로판 설정
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = "";
			}
		}

		board[4][5] = board[5][4] = stone[0];
		board[4][4] = board[5][5] = stone[1];

		button[3][4].setIcon(stoneImg[0]);
		button[4][3].setIcon(stoneImg[0]);
		button[3][3].setIcon(stoneImg[1]);
		button[4][4].setIcon(stoneImg[1]);

		JPanel msgPanel = new JPanel();
		msgField = new JTextField();
		msgField.setBackground(Color.WHITE);
		msgField.setForeground(Color.BLACK);
		msgField.setPreferredSize(new Dimension(600, 40));
		msgField.setHorizontalAlignment(JTextField.CENTER);
		msgField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		msgPanel.add(msgField);
		msgField.setText(player[turn] + "님[" + stone[turn] + "] 의 차례입니다.");

		add(msgPanel);
		
		setVisible(true);
		setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (continueGame) {
				OthelloGameStart(e);
			}
		} catch (NumberFormatException e2) {
			msgField.setText("Error : Reenter Button.");
		}
	}

	public void OthelloGameStart(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		int row = -1, col = -1;
		boolean nextTurn = true;

		if (btn.getIcon().equals(boardImg)) {
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
			if (canComplete2(board, row + 1, col + 1, stone, turn)) {
				board[row + 1][col + 1] = stone[turn]; // 좌표에 오델로알 위치
				board = complete(board, row + 1, col + 1, stone, turn);
				if (canComplete1(board, stone, turn == 0 ? 1 : 0)) {
					turn = turn == 0 ? 1 : 0;
				} else {
					nextTurn = false;
				}
			} else {
				msgField.setText("뒤집을 수 있는 오델로알이 없습니다. 다시 놓아 주세요. " + stone[turn]);
				nextTurn = false;
			}
		} else {
			msgField.setText(player[turn] + "[" + stone[turn] + "]님, 다른 오셀로알이 이미 놓여있습니다. 다시 놓아 주세요.");
			nextTurn = false;
		}

		// 오델로판 display
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				if (board[i + 1][j + 1].equals(stone[0])) {
					button[i][j].setIcon(stoneImg[0]);
				} else if (board[i + 1][j + 1].equals(stone[1])) {
					button[i][j].setIcon(stoneImg[1]);
				}
			}
		}
		if (nextTurn) {
			msgField.setText(player[turn] + "님[" + stone[turn] + "] 의 차례입니다.");
		}
		
		if (!canComplete1(board, stone, 0) && !canComplete1(board, stone, 1)) {
			continueGame = false;
			msgField.setText(winnerPrint(board, stone, player));
		}
	}

	// 오델로 뒤집기 가능 여부
	public static boolean canComplete1(String[][] board, String[] stone, int turn) {
		String[][] copyBoard = new String[board.length][board[0].length];
		for (int i = 0; i < board.length; i++)
			copyBoard[i] = Arrays.copyOf(board[i], board[i].length);

		for (int i = 1; i < 9; i++) {
			for (int j = 1; j < 9; j++)
				if (board[i][j].equals("") && !Arrays.deepEquals(board, complete(copyBoard, i, j, stone, turn)))
					return true;
		}
		return false;
	}

	// 지점 오델로 뒤집기 가능 여부
	public static boolean canComplete2(String[][] board, int row, int col, String[] stone, int turn) {
		String[][] copyBoard = new String[board.length][board[0].length];
		for (int i = 0; i < board.length; i++)
			copyBoard[i] = Arrays.copyOf(board[i], board[i].length);

		if (!Arrays.deepEquals(board, complete(copyBoard, row, col, stone, turn)))
			return true;
		else
			return false;
	}

	// 오델로 종합 뒤집기
	public static String[][] complete(String[][] board, int row, int col, String[] stone, int turn) {
		board = rightComplete(board, row, col, stone, turn);
		board = leftComplete(board, row, col, stone, turn);
		board = downComplete(board, row, col, stone, turn);
		board = upperComplete(board, row, col, stone, turn);
		board = rigthUpComplete(board, row, col, stone, turn);
		board = rigthDownComplete(board, row, col, stone, turn);
		board = leftUpComplete(board, row, col, stone, turn);
		board = leftDownComplete(board, row, col, stone, turn);
		return board;
	}

	// 오델로 오른쪽 뒤집기
	public static String[][] rightComplete(String[][] board, int row, int col, String[] stone, int turn) {
		int cnt = 1;
		for (int i = col + 1; i < board.length; i++) {
			if (board[row][i].equals(stone[turn == 0 ? 1 : 0]))
				cnt++;
			else if (board[row][i].equals(stone[turn == 0 ? 0 : 1])) {
				for (int j = col + 1; j < col + cnt; j++)
					board[row][j] = stone[turn];
				break;
			} else
				break;
		}
		return board;
	}

	// 오델로 왼쪽 뒤집기
	public static String[][] leftComplete(String[][] board, int row, int col, String[] stone, int turn) {
		int cnt = -1;
		for (int i = col - 1; i >= 0; i--) {
			if (board[row][i].equals(stone[turn == 0 ? 1 : 0]))
				cnt--;
			else if (board[row][i].equals(stone[turn == 0 ? 0 : 1])) {
				for (int j = col - 1; j > col + cnt; j--)
					board[row][j] = stone[turn];
				break;
			} else
				break;
		}
		return board;
	}

	// 오델로 아래쪽 뒤집기
	public static String[][] downComplete(String[][] board, int row, int col, String[] stone, int turn) {
		int cnt = 1;
		for (int i = row + 1; i < board.length; i++) {
			if (board[i][col].equals(stone[turn == 0 ? 1 : 0]))
				cnt++;
			else if (board[i][col].equals(stone[turn == 0 ? 0 : 1])) {
				for (int j = row + 1; j < row + cnt; j++)
					board[j][col] = stone[turn];
				break;
			} else
				break;
		}
		return board;
	}

	// 오델로 위쪽 뒤집기
	public static String[][] upperComplete(String[][] board, int row, int col, String[] stone, int turn) {
		int cnt = -1;
		for (int i = row - 1; i > 0; i--) {
			if (board[i][col].equals(stone[turn == 0 ? 1 : 0]))
				cnt--;
			else if (board[i][col].equals(stone[turn == 0 ? 0 : 1])) {
				for (int j = row - 1; j > row + cnt; j--)
					board[j][col] = stone[turn];
				break;
			} else
				break;
		}
		return board;
	}

	// 오델로 우상향 뒤집기
	public static String[][] rigthUpComplete(String[][] board, int row, int col, String[] stone, int turn) {
		int cnt = 1;
		int endRow = row, endCol = col;

		while (endRow > 0 && endCol < 9) {
			endRow--;
			endCol++;
		}

		while ((row - cnt) >= endRow && ((col + cnt) <= endCol)) {
			if (board[row - cnt][col + cnt].equals(stone[turn == 0 ? 1 : 0]))
				cnt++;
			else if (board[row - cnt][col + cnt].equals(stone[turn == 0 ? 0 : 1])) {
				for (int i = 1; i < cnt; i++)
					board[row - i][col + i] = stone[turn];
				break;
			} else
				break;
		}
		return board;
	}

	// 오델로 우하향 뒤집기
	public static String[][] rigthDownComplete(String[][] board, int row, int col, String[] stone, int turn) {
		int cnt = 1;
		int endRow = row, endCol = col;

		while (endRow < 9 && endCol < 9) {
			endRow++;
			endCol++;
		}

		while ((row + cnt) <= endRow && ((col + cnt) <= endCol)) {
			if (board[row + cnt][col + cnt].equals(stone[turn == 0 ? 1 : 0]))
				cnt++;
			else if (board[row + cnt][col + cnt].equals(stone[turn == 0 ? 0 : 1])) {
				for (int i = 1; i < cnt; i++)
					board[row + i][col + i] = stone[turn];
				break;
			} else
				break;
		}
		return board;
	}

	// 오델로 좌상향 뒤집기
	public static String[][] leftUpComplete(String[][] board, int row, int col, String[] stone, int turn) {
		int cnt = 1;
		int endRow = row, endCol = col;

		while (endRow > 1 && endCol > 1) {
			endRow--;
			endCol--;
		}

		while ((row + cnt) >= endRow && ((col + cnt) >= endCol)) {
			if (board[row - cnt][col - cnt].equals(stone[turn == 0 ? 1 : 0]))
				cnt++;
			else if (board[row - cnt][col - cnt].equals(stone[turn == 0 ? 0 : 1])) {
				for (int i = 1; i < cnt; i++)
					board[row - i][col - i] = stone[turn];
				break;
			} else
				break;
		}
		return board;
	}

	// 오델로 좌하향 뒤집기
	public static String[][] leftDownComplete(String[][] board, int row, int col, String[] stone, int turn) {
		int cnt = 1;
		int endRow = row, endCol = col;

		while (endRow < 9 && endCol > 1) {
			endRow++;
			endCol--;
		}

		while ((row + cnt) <= endRow && ((col + cnt) >= endCol)) {
			if (board[row + cnt][col - cnt].equals(stone[turn == 0 ? 1 : 0]))
				cnt++;
			else if (board[row + cnt][col - cnt].equals(stone[turn == 0 ? 0 : 1])) {
				for (int i = 1; i < cnt; i++)
					board[row + i][col - i] = stone[turn];
				break;
			} else
				break;
		}
		return board;
	}

	// 오델로 승패
	public static String winnerPrint(String[][] board, String[] stone, String[] player) {
		int[] cnt = { 0, 0 };
		for (int i = 1; i < 9; i++) {
			for (int j = 1; j < 9; j++) {
				if (board[i][j].equals(stone[0]))
					cnt[0] += 1;
				else if (board[i][j].equals(stone[1]))
					cnt[1] += 1;
			}
		}
		int winner = cnt[0] > cnt[1] ? 0 : 1;

		return "☆승리☆   " + player[winner] + "(" + stone[winner] + ") : " + cnt[winner] + " - "
				+ cnt[winner == 0 ? 1 : 0] + " : " + player[winner == 0 ? 1 : 0] + "(" + stone[winner == 0 ? 1 : 0] + ")   ★패배★";
	}

//	public static void main(String[] args) {
//		OthelloDemo test = new OthelloDemo();
//	}
}
