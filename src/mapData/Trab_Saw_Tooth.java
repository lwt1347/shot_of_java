package mapData;

import java.awt.Point;

//함정: 톱니바퀴
public class Trab_Saw_Tooth {
	
	//톱니바퀴 위치
	Point saw_Tooth_Point;
	//반지름
	int radius;
	
	
	//톱니바퀴 위치정보를 가져와 반지름 만큼 그린다.
	public Trab_Saw_Tooth(int x, int y){
		//위치 정보
		saw_Tooth_Point = new Point(x, y);
		//반지름
		radius = 30;
	}
	
	//좌표 출력
	public Point Get_Trab_Saw_tooth_Point(){
		return saw_Tooth_Point;
	}
	
	//반지름 반환
	public int get_Radius(){
		return radius;
	}
	
	
}
