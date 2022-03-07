import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	protected static final int PANEL_X = 10;	// 패널의 가로 개수
	protected static final int PANEL_Y = 21;	// 패널의 세로 개수
	protected static final int BLOCK_LEN = 20;	// 블록의 변의 길이(pixel)
	protected Block [][] block = new Block[PANEL_X][PANEL_Y];	// 패널 안에 들어가는 블록 배열
	protected int [] nowX = new int[4];		// 현재 조작중인 테트리미노 중심의 Y 좌표
	protected int [] nowY = new int[4];		// 현재 조작중인 테트리미노 중심의 X 좌표
	protected Tetromino mino;				// 테트리미노 객체
	protected int lines;			// 제거한 라인 수
		
	// 생성자
	public GamePanel() {				
		Tetromino.type[1] = (int)(Math.random() * 7);		// 난수 생성
		setLayout(null);
		setSize(BLOCK_LEN*PANEL_X, BLOCK_LEN*PANEL_Y);
		setLocation(20, 20);
		setBackground(Color.BLACK);
		setSpace();
		addKeyListener(new MyKeyAdapter());	// 이벤트 리스너 등록	
		setFocusable(true);	// 컨텐트팬이 포커스를 받을 수 있는 상태를 true로 설정
		requestFocus();		// 컨텐트팬에 포커스 설정
		mino = new Tetromino(this);		// 새로운 테트리미노 생성
	}
	
	// 메소드: 블록 공간 만들기
	public void setSpace() {
		for(int i=0; i<block.length; i++) {
			for(int j=0; j<block[i].length; j++) {
				block[i][j] = new Block();		// 블록 객체 생성
				block[i][j].setLocation(i*BLOCK_LEN, j*BLOCK_LEN);	// 블록 객체 위치 설정
				add(block[i][j]);	// 패널에 블록 추가
			}
		}
	}	
	
	// 메소드: 테트리미노가 바닥에 있으면 true(스레드에서 새 블록 생성)
	public boolean checkBottom() {
		nowBlockFunction("empty");
		for(int i=0; i<nowY.length; i++)
			if(nowY[i] >= PANEL_Y-1 || block[nowX[i]][nowY[i]+1].getFilled()) {
				nowBlockFunction("fill");
				return true;
			}
		nowBlockFunction("fill");	// 블록을 채우고
		return false;				// false 리턴
	}

	// 메소드: 라인 완성 확인
	public void checkLine() {
		for(int j=0; j<PANEL_Y; j++) {
			int count = 0;
			for(int i=0; i<PANEL_X; i++) {				// X좌표 마다 별도로 확인
				if(block[i][j].getFilled()) count++;	// 블록이 채워져 있으면 카운트 증가
				else break;
			}
			if(count >= 10)  removeLine(j);		// 모든 블록이 채워져 있으면 라인 내 블록 제거
		}			
	}
	
	// 메소드: 라인 내 블록 제거
	public void removeLine(int remove_Y) {
		lines++;
		for(int i=0; i<PANEL_X; i++) {
			block[i][remove_Y].setFilled(false);
			block[i][remove_Y].setBlockColor(-1);
			for(int j=remove_Y; j>0; j--) {
				block[i][j].setFilled(block[i][j-1].getFilled());
				block[i][j].setBackground(block[i][j-1].getBackground());
			}
		}
	}
	
	// 메소드: 게임 오버 확인
	public boolean checkGameOver() {
		for(int i=0; i<PANEL_X; i++) {
			if(block[i][0].getFilled()) {	// 맨 윗줄이 하나라도 채워져 있으면
				return true;				// true 리턴
			}
		}
		return false;
	}
	
	
	// 메소드: 모든 now 블럭들에 관한 메소드 정리
	public void nowBlockFunction(String func) {
		for(int i=0; i<nowX.length; i++) {
			Block b = block[nowX[i]][nowY[i]];
			switch(func) {
				case "fill":
					b.setFilled(true);
					b.setBlockColor(Tetromino.type[0]);
					break;
				case "empty":
					b.setFilled(false);
					b.setBlockColor(-1);
					break;
				case "moveLeft": nowX[i]--; break;
				case "moveRight": nowX[i]++; break;
				case "moveDown": nowY[i]++; break;
			}
		}
	}
	
	// 메소드: 블록 이동
	public void moveBlock(char direction) {
		nowBlockFunction("empty");
		switch(direction) {
			case 'L':
				for(int i=0; i<nowY.length; i++) {	
					if(nowX[i] <= 0 || block[nowX[i]-1][nowY[i]].getFilled() ) {
						nowBlockFunction("fill");
						return;
					}
				}
				nowBlockFunction("moveLeft");
				break;
			case 'R':
				for(int i=0; i<nowY.length; i++)	
					if(nowX[i] >= PANEL_X-1 || block[nowX[i]+1][nowY[i]].getFilled()) {
						nowBlockFunction("fill");
						return;
					}
				nowBlockFunction("moveRight");
				break;
			case 'D':
				for(int i=0; i<nowY.length; i++)	
					if(nowY[i] >= PANEL_Y-1 || block[nowX[i]][nowY[i]+1].getFilled()) {
						nowBlockFunction("fill");
						return;
					}
				nowBlockFunction("moveDown");
				break;
		}
		nowBlockFunction("fill");
	}
	
	// 내부 클래스: 이벤트 리스너 작성
	private class MyKeyAdapter extends KeyAdapter {	// MouseAdapter 인터페이스를 상속받음
		public void keyPressed(KeyEvent e) {	// 메소드 오버라이딩(키 입력 시 호출되는 메소드)
			int keyCode = e.getKeyCode();	// 입력한 키의 정수형 키 코드 리턴
			switch(keyCode) {	// 입력한 키의 코드와 방향키의 가상 키 값이 같으면, 레이블 텍스트와 위치 변경
				case KeyEvent.VK_LEFT: moveBlock('L'); break;
				case KeyEvent.VK_RIGHT: moveBlock('R'); break;
				case KeyEvent.VK_DOWN: moveBlock('D'); break;
				case KeyEvent.VK_UP: mino.rotate(); break;
				case KeyEvent.VK_SPACE:
					for(int i=0; i<PANEL_Y; i++)
						moveBlock('D');
					break;
			}
		}
	}	
}
