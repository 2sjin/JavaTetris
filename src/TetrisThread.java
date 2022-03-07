import javax.swing.*;

class TetrisThread extends Thread {	// 스레드 클래스 선언
	private GamePanel panel;		// 패널 필드
	private JLabel[] label;			// 레이블 배열 필드
	
	// 생성자
	public TetrisThread(GamePanel panel, JLabel[] label) {
		this.panel = panel;	
		this.label = label;		
	}
	
	// 메소드: 스레드의 실행 메소드 구현(오버라이딩)
	@Override
	public void run() {
		while(true) {
			try {
				label[1].setText(Integer.toString(panel.lines));	// 제거한 라인 수 갱신
				label[3].setIcon(panel.mino.getNextImage());		// NEXT 이미지 갱신
				sleep(12-0);
				panel.moveBlock('D');			// 블록을 아래로 이동
				sleep(120);
				if(panel.checkBottom()) {		// 테트리미노가 바닥에 있을 경우
					if(panel.checkGameOver()) interrupt();	// 게임 오버 조건이면 인터럽트 발생
					panel.checkLine();						// 라인이 완성되었는지 확인(완성되었으면 제거도 실행)
					panel.mino = new Tetromino(panel);		// 새로운 테트리미노 생성
				}
				sleep(120);
			}
			catch(InterruptedException e) {		// 인터럽트 발생 시 메시지 출력 후 프로그램 종료
				JOptionPane.showMessageDialog(panel, "Game Over", "Tetris", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
	}	
}
