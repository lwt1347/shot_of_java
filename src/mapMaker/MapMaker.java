package mapMaker;

import java.awt.Color;

import javax.swing.JFrame;

public class MapMaker extends JFrame{
	
	public MapMaker() {
		MapMakerPanel mapMakerPanel = new MapMakerPanel();
		mapMakerPanel.setFocusable(true);// 패널에 키보드 입력 가능
		mapMakerPanel.setForeground(Color.white);
		setTitle("맵 만들기");
		
		//프로그렘이 정상적으로 종료하도록 만들어 줍니다.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(mapMakerPanel);
		setSize(1000, 1900);//
		setVisible(true);
	}
}