import java.awt.*;
import javax.swing.*;

public class Tetromino {

	static int [] type = new int[2];	// ��Ʈ���̳��� Ÿ���� ��Ÿ���� �迭(���� Ÿ��, ���� Ÿ��)
	private int rotate;			// ȸ�� ���¸� �б��ϴ� ����
	private GamePanel p;		// �г� �ʵ�
	private ImageIcon [] img = new ImageIcon[7];	// �̹��������� �ʵ�

	// ������
	public Tetromino(GamePanel panel) {
		this.p = panel;
		this.rotate = 0;
		setType();
		create(p.nowX, p.nowY);
	}

	// �޼ҵ�: ��Ʈ�ι̳� Ÿ�� ����
	public void setType() {
		type[0] = type[1];						// ���� Ÿ�Կ� ���� Ÿ���� ����
		type[1] = (int)(Math.random() * 7);		// ���� Ÿ���� ������ ���� ����
		if(type[0] == type[1])		// ���� ���� Ÿ�԰� ���� Ÿ���� ������
			setType();				// Ÿ�� �缳��		
	}
	
	// �޼ҵ�: NEXT �̹��� ����
	public ImageIcon getNextImage() {
		for(int i=0; i<img.length; i++) {
			img[i] = new ImageIcon("Image/Tetromino_" + i + ".png");	// Image ������ ImageIcon ��ü�� ���� 
			Image tempImage = img[i].getImage();									// ImageIcon�� Image�� ��ȯ
			tempImage = tempImage.getScaledInstance(63, 83, Image.SCALE_SMOOTH);	// Image ũ�� �缳��
			img[i] = new ImageIcon(tempImage);										// �缳���� Image�� ImageIcon ��ü�� �����
		}
		return img[type[1]];	// ���� Ÿ�� �̹��� ����
	}
	
	// �޼ҵ�: ��Ʈ�ι̳� �����
	public void create(int[] nowX, int[] nowY) {
		for(int i=0; i<nowX.length; i++) {
			nowX[i] = 5; nowY[i] = 1;		// ��Ʈ�ι̳밡 �����Ǵ� ��ġ ����
		}
		nowY[1] += 1;
		switch(type[0]) {
			case 0:		// I �̳�
				nowY[2] += 2;
				nowY[3] += -1;		
				break;
			case 1:		// O �̳�
				nowX[2] -= 1;
				nowX[3] -= 1; nowY[3] += 1;
				break;
			case 2:		// T �̳�
				nowX[2] -= 1;
				nowX[3] += 1;
				break;
			case 3:		// J �̳�
				nowY[2] -= 1;
				nowY[3] += 1; nowX[3] -= 1;
				break;
			case 4:		// L �̳�
				nowY[2] -= 1;
				nowY[3] += 1; nowX[3] += 1;
				break;
			case 5:		// Z �̳�
				nowX[2] += 1;
				nowX[3] += 1; nowY[3] -= 1;				
				break;
			case 6:		// S �̳�
				nowX[2] -= 1;
				nowX[3] -= 1; nowY[3] -= 1;				
				break;
		}
	}
		
	// �޼ҵ�: ��� ȸ��
	public void rotate() {
		int nowX[] = p.nowX;	// ���� �������� ��Ʈ���̳� �߽��� X ��ǥ
		int nowY[] = p.nowY;	// ���� �������� ��Ʈ���̳� �߽��� Y ��ǥ
		p.nowBlockFunction("empty");	// ��ϸ� �̵� ������ ���·� ����
		
		// ���� ��Ʈ���̳��� Ÿ�� �� ȸ�� ���¿� ���� ȸ�� ����
		switch(type[0]) {
		case 0:		// I �̳�
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
		case 1:		// O �̳�
			rotate = 0;
			break;
		case 2:		// T �̳�
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
		case 3:		// J �̳�
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
		case 4:		// L �̳�
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
		case 5:		// Z �̳�
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
		case 6:		// S �̳�
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
		
		// ȸ�� �� ��ǥ�� ����
		for(int i=0; i<nowX.length; i++) {
			if(nowX[i] < 0) {	// ��Ʈ�ι̳밡 ���� ���� �վ��� ���
				for(int j=0; j<nowX.length; j++)
					nowX[j]++;	// ���������� �̵�
			}
			else if(nowX[i] > GamePanel.PANEL_X-1) {	// ��Ʈ�ι̳밡 ������ ���� �վ��� ���
				for(int j=0; j<nowX.length; j++)
					nowX[j]--;							// �������� �̵�
			}
		}	
	}
}
