package weapon;

import java.awt.Point;


//기본 총알의 부모 클래스
public class Weapon {
	
	//총알 좌표
	protected Point bullet_Point;
	//총알 속도
	protected int bullet_Speed;
	//총알의 파워
	protected int bullet_Power; 
	
	//총알이 날아갈 방향  true:좌 false:우   
	protected boolean bullet_Side_LEFT_RIGHT;
	
	//총알 충돌 판정시 삭제 하기 위함
	private boolean bullet_Remove_Bollean; 
	
	//생성시 초기화, 총알 좌표 초기화	
	Weapon(Point character_Point, boolean bullet_Side_LEFT_RIGHT) {
		//총알 의 시작 좌쵸를 전달 받는 캐릭터의 x 좌쵸로 설정
		//총알 의 시작 좌쵸를 전달 받는 캐릭터의 y 좌쵸로 설정
		bullet_Point = new Point(character_Point.x, character_Point.y);
		
		
		//총알의 기본 속도 10으로 생성
		bullet_Speed = 10;
		
		//총알의 기본 파워
		bullet_Power = 1;
		
		this.bullet_Side_LEFT_RIGHT = bullet_Side_LEFT_RIGHT;
		
		bullet_Remove_Bollean = false;
	}
	
	//사라질 총알 
	public void set_Remove_Bullet_Choice(){
		bullet_Remove_Bollean = true;
	}
	//사라질 총알 
	public boolean get_Remove_Bullet_Choice(){
		return bullet_Remove_Bollean;
	}
	
	//총알의 좌표를 리턴
	public Point getPoint(){
		return bullet_Point;
	}
	
	//총알의 좌우측 방향을 린턴
	public boolean get_Bullet_Side_LEFT_RIGHT(){
		return bullet_Side_LEFT_RIGHT;
	}
	
	public int get_Bullet_Power(){
		return bullet_Power;
	}
	
	
	
	
}



