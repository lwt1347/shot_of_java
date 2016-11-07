package mainframe;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;

public class MainFrame extends JFrame{
	//메인 패널 생성
	MainPanel mainPanel;
	//컨테이너 생성
	Container contentPane;
	
	public MainFrame() {
		
		//메인 패널 할당 및 크기 조정
		mainPanel = new MainPanel();
		mainPanel.setForeground(Color.white);
		//mainPanel.setBounds(500, 100, 1000, 1000);
		mainPanel.setFocusable(true); // 패널에 키보드 입력 가능
		
		//프로그렘이 정상적으로 종료하도록 만들어 줍니다.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(mainPanel);
		setSize(1000, 2000);
		setVisible(true);
	}
	
}
