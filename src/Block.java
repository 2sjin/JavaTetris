import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class Block extends JLabel {
	private static final long serialVersionUID = 1L;
	private boolean filled;		// ����� ä�������� ����
	
	// ������
	public Block() {
		this.filled = false;
		setSize(20, 20);
		setBorder(new LineBorder(Color.BLACK, 1));
		setOpaque(true);
		setBackground(Color.BLACK);
		setVisible(true);
	}
	
	// �޼ҵ�: ��� ä�� ���� ����
	public boolean getFilled() {
		return filled;
	}	
	
	// �޼ҵ�: ��� ä�� ���� ����
	public void setFilled(boolean b) {
		this.filled = b;
	}	

	// �޼ҵ�: ��� ���� ����
	public void setBlockColor(int type) {
		Color [] color = {Color.RED, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.ORANGE, Color.CYAN, Color.GREEN};		
		if(type == -1) setBackground(Color.BLACK);	// Ÿ���� -1�̸� ������
		else setBackground(color[type]);	// �� ���� ��쿡�� Ÿ�Կ� ���� ���� ����
	}
}
