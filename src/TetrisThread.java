import javax.swing.*;

class TetrisThread extends Thread {	// ������ Ŭ���� ����
	private GamePanel panel;		// �г� �ʵ�
	private JLabel[] label;			// ���̺� �迭 �ʵ�
	
	// ������
	public TetrisThread(GamePanel panel, JLabel[] label) {
		this.panel = panel;	
		this.label = label;		
	}
	
	// �޼ҵ�: �������� ���� �޼ҵ� ����(�������̵�)
	@Override
	public void run() {
		while(true) {
			try {
				label[1].setText(Integer.toString(panel.lines));	// ������ ���� �� ����
				label[3].setIcon(panel.mino.getNextImage());		// NEXT �̹��� ����
				sleep(12-0);
				panel.moveBlock('D');			// ����� �Ʒ��� �̵�
				sleep(120);
				if(panel.checkBottom()) {		// ��Ʈ���̳밡 �ٴڿ� ���� ���
					if(panel.checkGameOver()) interrupt();	// ���� ���� �����̸� ���ͷ�Ʈ �߻�
					panel.checkLine();						// ������ �ϼ��Ǿ����� Ȯ��(�ϼ��Ǿ����� ���ŵ� ����)
					panel.mino = new Tetromino(panel);		// ���ο� ��Ʈ���̳� ����
				}
				sleep(120);
			}
			catch(InterruptedException e) {		// ���ͷ�Ʈ �߻� �� �޽��� ��� �� ���α׷� ����
				JOptionPane.showMessageDialog(panel, "Game Over", "Tetris", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
	}	
}
