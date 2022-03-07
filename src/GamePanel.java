import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	protected static final int PANEL_X = 10;	// �г��� ���� ����
	protected static final int PANEL_Y = 21;	// �г��� ���� ����
	protected static final int BLOCK_LEN = 20;	// ����� ���� ����(pixel)
	protected Block [][] block = new Block[PANEL_X][PANEL_Y];	// �г� �ȿ� ���� ��� �迭
	protected int [] nowX = new int[4];		// ���� �������� ��Ʈ���̳� �߽��� Y ��ǥ
	protected int [] nowY = new int[4];		// ���� �������� ��Ʈ���̳� �߽��� X ��ǥ
	protected Tetromino mino;				// ��Ʈ���̳� ��ü
	protected int lines;			// ������ ���� ��
		
	// ������
	public GamePanel() {				
		Tetromino.type[1] = (int)(Math.random() * 7);		// ���� ����
		setLayout(null);
		setSize(BLOCK_LEN*PANEL_X, BLOCK_LEN*PANEL_Y);
		setLocation(20, 20);
		setBackground(Color.BLACK);
		setSpace();
		addKeyListener(new MyKeyAdapter());	// �̺�Ʈ ������ ���	
		setFocusable(true);	// ����Ʈ���� ��Ŀ���� ���� �� �ִ� ���¸� true�� ����
		requestFocus();		// ����Ʈ�ҿ� ��Ŀ�� ����
		mino = new Tetromino(this);		// ���ο� ��Ʈ���̳� ����
	}
	
	// �޼ҵ�: ��� ���� �����
	public void setSpace() {
		for(int i=0; i<block.length; i++) {
			for(int j=0; j<block[i].length; j++) {
				block[i][j] = new Block();		// ��� ��ü ����
				block[i][j].setLocation(i*BLOCK_LEN, j*BLOCK_LEN);	// ��� ��ü ��ġ ����
				add(block[i][j]);	// �гο� ��� �߰�
			}
		}
	}	
	
	// �޼ҵ�: ��Ʈ���̳밡 �ٴڿ� ������ true(�����忡�� �� ��� ����)
	public boolean checkBottom() {
		nowBlockFunction("empty");
		for(int i=0; i<nowY.length; i++)
			if(nowY[i] >= PANEL_Y-1 || block[nowX[i]][nowY[i]+1].getFilled()) {
				nowBlockFunction("fill");
				return true;
			}
		nowBlockFunction("fill");	// ����� ä���
		return false;				// false ����
	}

	// �޼ҵ�: ���� �ϼ� Ȯ��
	public void checkLine() {
		for(int j=0; j<PANEL_Y; j++) {
			int count = 0;
			for(int i=0; i<PANEL_X; i++) {				// X��ǥ ���� ������ Ȯ��
				if(block[i][j].getFilled()) count++;	// ����� ä���� ������ ī��Ʈ ����
				else break;
			}
			if(count >= 10)  removeLine(j);		// ��� ����� ä���� ������ ���� �� ��� ����
		}			
	}
	
	// �޼ҵ�: ���� �� ��� ����
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
	
	// �޼ҵ�: ���� ���� Ȯ��
	public boolean checkGameOver() {
		for(int i=0; i<PANEL_X; i++) {
			if(block[i][0].getFilled()) {	// �� ������ �ϳ��� ä���� ������
				return true;				// true ����
			}
		}
		return false;
	}
	
	
	// �޼ҵ�: ��� now ���鿡 ���� �޼ҵ� ����
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
	
	// �޼ҵ�: ��� �̵�
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
	
	// ���� Ŭ����: �̺�Ʈ ������ �ۼ�
	private class MyKeyAdapter extends KeyAdapter {	// MouseAdapter �������̽��� ��ӹ���
		public void keyPressed(KeyEvent e) {	// �޼ҵ� �������̵�(Ű �Է� �� ȣ��Ǵ� �޼ҵ�)
			int keyCode = e.getKeyCode();	// �Է��� Ű�� ������ Ű �ڵ� ����
			switch(keyCode) {	// �Է��� Ű�� �ڵ�� ����Ű�� ���� Ű ���� ������, ���̺� �ؽ�Ʈ�� ��ġ ����
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
