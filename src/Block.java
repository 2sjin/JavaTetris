import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class Block extends JLabel {
	private static final long serialVersionUID = 1L;
	private boolean filled;		// 블록이 채워졌는지 여부
	
	// 생성자
	public Block() {
		this.filled = false;
		setSize(20, 20);
		setBorder(new LineBorder(Color.BLACK, 1));
		setOpaque(true);
		setBackground(Color.BLACK);
		setVisible(true);
	}
	
	// 메소드: 블록 채움 여부 리턴
	public boolean getFilled() {
		return filled;
	}	
	
	// 메소드: 블록 채움 여부 설정
	public void setFilled(boolean b) {
		this.filled = b;
	}	

	// 메소드: 블록 색상 설정
	public void setBlockColor(int type) {
		Color [] color = {Color.RED, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.ORANGE, Color.CYAN, Color.GREEN};		
		if(type == -1) setBackground(Color.BLACK);	// 타입이 -1이면 검정색
		else setBackground(color[type]);	// 그 외의 경우에는 타입에 따라 색상 결정
	}
}
