import java.awt.*;
import javax.swing.*;

public class Tetromino {

	static int [] type = new int[2];	// 테트리미노의 타입을 나타내는 배열(현재 타입, 다음 타입)
	private int rotate;			// 회전 상태를 분기하는 정수
	private GamePanel p;		// 패널 필드
	private ImageIcon [] img = new ImageIcon[7];	// 이미지아이콘 필드

	// 생성자
	public Tetromino(GamePanel panel) {
		this.p = panel;
		this.rotate = 0;
		setType();
		create(p.nowX, p.nowY);
	}

	// 메소드: 테트로미노 타입 설정
	public void setType() {
		type[0] = type[1];						// 현재 타입에 다음 타입을 대입
		type[1] = (int)(Math.random() * 7);		// 다음 타입은 난수에 의해 결정
		if(type[0] == type[1])		// 만약 현재 타입과 다음 타입이 같으면
			setType();				// 타입 재설정		
	}
	
	// 메소드: NEXT 이미지 리턴
	public ImageIcon getNextImage() {
		for(int i=0; i<img.length; i++) {
			img[i] = new ImageIcon("Image/Tetromino_" + i + ".png");	// Image 파일을 ImageIcon 객체로 생성 
			Image tempImage = img[i].getImage();									// ImageIcon을 Image로 변환
			tempImage = tempImage.getScaledInstance(63, 83, Image.SCALE_SMOOTH);	// Image 크기 재설정
			img[i] = new ImageIcon(tempImage);										// 재설정한 Image를 ImageIcon 객체로 재생성
		}
		return img[type[1]];	// 다음 타입 이미지 리턴
	}
	
	// 메소드: 테트로미노 만들기
	public void create(int[] nowX, int[] nowY) {
		for(int i=0; i<nowX.length; i++) {
			nowX[i] = 5; nowY[i] = 1;		// 테트로미노가 생성되는 위치 설정
		}
		nowY[1] += 1;
		switch(type[0]) {
			case 0:		// I 미노
				nowY[2] += 2;
				nowY[3] += -1;		
				break;
			case 1:		// O 미노
				nowX[2] -= 1;
				nowX[3] -= 1; nowY[3] += 1;
				break;
			case 2:		// T 미노
				nowX[2] -= 1;
				nowX[3] += 1;
				break;
			case 3:		// J 미노
				nowY[2] -= 1;
				nowY[3] += 1; nowX[3] -= 1;
				break;
			case 4:		// L 미노
				nowY[2] -= 1;
				nowY[3] += 1; nowX[3] += 1;
				break;
			case 5:		// Z 미노
				nowX[2] += 1;
				nowX[3] += 1; nowY[3] -= 1;				
				break;
			case 6:		// S 미노
				nowX[2] -= 1;
				nowX[3] -= 1; nowY[3] -= 1;				
				break;
		}
	}
		
	// 메소드: 블록 회전
	public void rotate() {
		int nowX[] = p.nowX;	// 현재 조작중인 테트리미노 중심의 X 좌표
		int nowY[] = p.nowY;	// 현재 조작중인 테트리미노 중심의 Y 좌표
		p.nowBlockFunction("empty");	// 블록를 이동 가능한 상태로 만듦
		
		// 현재 테트리미노의 타입 및 회전 상태에 따라 회전 실행
		switch(type[0]) {
		case 0:		// I 미노
			if(rotate == 0) {
				nowX[1] += 1; nowY[1] -= 1;
				nowX[2] += 2; nowY[2] -= 2;
				nowX[3] -= 1; nowY[3] += 1;
				rotate++;
			}
			else if(rotate == 1) {
				nowX[1] -= 1; nowY[1] += 1;
				nowX[2] -= 2; nowY[2] += 2;
				nowX[3] += 1; nowY[3] -= 1;
				rotate = 0;
			}
			break;
		case 1:		// O 미노
			rotate = 0;
			break;
		case 2:		// T 미노
			if(rotate == 0) { nowX[3] -= 1; nowY[3] -= 1; rotate++; }
			else if(rotate == 1) { nowX[1] += 1; nowY[1] -= 1; rotate++; }
			else if(rotate == 2) { nowX[2] += 1; nowY[2] += 1; rotate++; }
			else if(rotate == 3) {
				nowX[1] -= 1; nowY[1] += 1;
				nowX[2] -= 1; nowY[2] -= 1;
				nowX[3] += 1; nowY[3] += 1;
				rotate = 0;
			}
			break;
		case 3:		// J 미노
			if(rotate == 0) {
				nowX[1] -= 1; nowY[1] -= 1;
				nowX[2] += 1; nowY[2] += 1;
				nowX[3] += 0; nowY[3] -= 2;
				rotate++;
			}
			else if(rotate == 1) {
				nowX[1] += 1; nowY[1] -= 1;
				nowX[2] -= 1; nowY[2] += 1;
				nowX[3] += 2; nowY[3] += 0;
				rotate++;
			}
			else if(rotate == 2) {
				nowX[1] += 1; nowY[1] += 1;
				nowX[2] -= 1; nowY[2] -= 1;
				nowX[3] += 0; nowY[3] += 2;
				rotate++;
			}
			else if(rotate == 3) {
				nowX[1] -= 1; nowY[1] += 1;
				nowX[2] += 1; nowY[2] -= 1;
				nowX[3] -= 2; nowY[3] += 0;
				rotate = 0;
			}
			break;
		case 4:		// L 미노
			if(rotate == 0) {
				nowX[1] -= 1; nowY[1] -= 1;
				nowX[2] += 1; nowY[2] += 1;
				nowX[3] -= 2; nowY[3] += 0;
				rotate++;
			}
			else if(rotate == 1) {
				nowX[1] += 1; nowY[1] -= 1;
				nowX[2] -= 1; nowY[2] += 1;
				nowX[3] += 0; nowY[3] -= 2;
				rotate++;
			}
			else if(rotate == 2) {
				nowX[1] += 1; nowY[1] += 1;
				nowX[2] -= 1; nowY[2] -= 1;
				nowX[3] += 2; nowY[3] += 0;
				rotate++;
			}
			else if(rotate == 3) {
				nowX[1] -= 1; nowY[1] += 1;
				nowX[2] += 1; nowY[2] -= 1;
				nowX[3] += 0; nowY[3] += 2;
				rotate = 0;
			}
			break;
		case 5:		// Z 미노
			if(rotate == 0) {
				nowX[1] -= 1; nowY[1] -= 1;
				nowX[2] -= 1; nowY[2] += 1;
				nowX[3] += 0; nowY[3] += 2;
				rotate++;
			}
			else if(rotate == 1) {
				nowX[1] += 1; nowY[1] += 1;
				nowX[2] += 1; nowY[2] -= 1;
				nowX[3] += 0; nowY[3] -= 2;
				rotate = 0;
			}
			break;
		case 6:		// S 미노
			if(rotate == 0) {
				nowX[1] -= 1; nowY[1] -= 1;
				nowX[2] -= 1; nowY[2] += 1;
				nowX[3] += 0; nowY[3] += 2;
				rotate++;
			}
			else if(rotate == 1) {
				nowX[1] += 1; nowY[1] += 1;
				nowX[2] += 1; nowY[2] -= 1;
				nowX[3] += 0; nowY[3] -= 2;
				rotate = 0;
			}
			break;
		}
		
		// 회전 후 좌표값 보정
		for(int i=0; i<nowX.length; i++) {
			if(nowX[i] < 0) {	// 테트로미노가 왼쪽 벽을 뚫었을 경우
				for(int j=0; j<nowX.length; j++)
					nowX[j]++;	// 오른쪽으로 이동
			}
			else if(nowX[i] > GamePanel.PANEL_X-1) {	// 테트로미노가 오른쪽 벽을 뚫었을 경우
				for(int j=0; j<nowX.length; j++)
					nowX[j]--;							// 왼쪽으로 이동
			}
		}	
	}
}
