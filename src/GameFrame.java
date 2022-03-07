import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private Container contentPane = getContentPane();	// ����Ʈ�� �ʵ�
	private GamePanel pBlock = new GamePanel();		// �г� �ʵ�
	private JLabel label[] = new JLabel[4];			// ���̺� �ʵ�
	
	// ������
	public GameFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// JFrame ���� �� ���α׷� ����
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(16, 16, 96));		
		contentPane.add(pBlock);
		setTitle("Tetris");
		setSize(330, 500);
		setVisible(true);
		setLabel();
		setThread();
	}
	
	// �޼ҵ�: ����Ʈ�ҿ� �� ���̺� ����
	public void setLabel() {
		// ���̺� ���� �� �۲� ����
		for(int i=0; i<label.length; i++) {
			label[i] = new JLabel();
			label[i].setFont(new Font("���� ���", Font.BOLD, 20));
			label[i].setForeground(Color.WHITE);
			contentPane.add(label[i]);
		}
		
		// ���̺� �ؽ�Ʈ ����
		label[0].setText("LINES");
		label[1].setText("0");
		label[2].setText("NEXT");
	
		// ���̺� ��ġ ����
		label[0].setLocation(235, 0);
		label[1].setLocation(235, 25);
		label[2].setLocation(235, 75);
		label[3].setLocation(235, 135);
		
		// ���̺� ũ�� ����
		label[0].setSize(100, 100);
		label[1].setSize(100, 100);
		label[2].setSize(100, 100);
		label[3].setSize(78, 103);
	}
		
	// �޼ҵ�: �������� ���� �� ����
	public void setThread() {
		TetrisThread th = new TetrisThread(pBlock, label);	// ������ ��ü ����
		th.start();	// ������ ����
	}
	
	// �޼ҵ�: main
	public static void main(String[] args) {
		new GameFrame();	// ������ ��ü ����
	}
}
