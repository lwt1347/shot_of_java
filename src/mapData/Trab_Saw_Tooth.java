package mapData;

import java.awt.Point;

//����: ��Ϲ���
public class Trab_Saw_Tooth {
	
	//��Ϲ��� ��ġ
	Point saw_Tooth_Point;
	//������
	int radius;
	
	
	//��Ϲ��� ��ġ������ ������ ������ ��ŭ �׸���.
	public Trab_Saw_Tooth(int x, int y){
		//��ġ ����
		saw_Tooth_Point = new Point(x, y);
		//������
		radius = 30;
	}
	
	//��ǥ ���
	public Point Get_Trab_Saw_tooth_Point(){
		return saw_Tooth_Point;
	}
	
	//������ ��ȯ
	public int get_Radius(){
		return radius;
	}
	
	
}
