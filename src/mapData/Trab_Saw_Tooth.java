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
	
	
	
	int rotation = 1;
	int rotation_Delay = 1;
	//ȸ���ϴ� �̹��� ���
	public int set_Rotation(){
		
		if(rotation_Delay%2 == 0){
			rotation++;
			rotation_Delay = 0;
		}
		rotation_Delay++;
		if(rotation >= 5){
			rotation = 1;
		}
		
		return rotation;
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
