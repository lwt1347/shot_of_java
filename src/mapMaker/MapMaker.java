package mapMaker;

import java.awt.Color;

import javax.swing.JFrame;

public class MapMaker extends JFrame{
	
	public MapMaker() {
		MapMakerPanel mapMakerPanel = new MapMakerPanel();
		mapMakerPanel.setFocusable(true);// �гο� Ű���� �Է� ����
		mapMakerPanel.setForeground(Color.white);
		setTitle("�� �����");
		
		//���α׷��� ���������� �����ϵ��� ����� �ݴϴ�.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(mapMakerPanel);
		setSize(1000, 1900);//���� 1/2
		setVisible(true);
	}
}