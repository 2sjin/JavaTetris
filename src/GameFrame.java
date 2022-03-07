import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private Container contentPane = getContentPane();	// 컨텐트팬 필드
	private GamePanel pBlock = new GamePanel();		// 패널 필드
	private JLabel label[] = new JLabel[4];			// 레이블 필드
	
	// 생성자
	public GameFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// JFrame 종료 시 프로그램 종료
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(16, 16, 96));		
		contentPane.add(pBlock);
		setTitle("Tetris");
		setSize(330, 500);
		setVisible(true);
		setLabel();
		setThread();
	}
	
	// 메소드: 컨텐트팬에 들어갈 레이블 설정
	public void setLabel() {
		// 레이블 생성 및 글꼴 설정
		for(int i=0; i<label.length; i++) {
			label[i] = new JLabel();
			label[i].setFont(new Font("맑은 고딕", Font.BOLD, 20));
			label[i].setForeground(Color.WHITE);
			contentPane.add(label[i]);
		}
		
		// 레이블 텍스트 설정
		label[0].setText("LINES");
		label[1].setText("0");
		label[2].setText("NEXT");
	
		// 레이블 위치 설정
		label[0].setLocation(235, 0);
		label[1].setLocation(235, 25);
		label[2].setLocation(235, 75);
		label[3].setLocation(235, 135);
		
		// 레이블 크기 설정
		label[0].setSize(100, 100);
		label[1].setSize(100, 100);
		label[2].setSize(100, 100);
		label[3].setSize(78, 103);
	}
		
	// 메소드: 스레드의 생성 및 실행
	public void setThread() {
		TetrisThread th = new TetrisThread(pBlock, label);	// 스레드 객체 생성
		th.start();	// 스레드 실행
	}
	
	// 메소드: main
	public static void main(String[] args) {
		new GameFrame();	// 프레임 객체 생성
	}
}
