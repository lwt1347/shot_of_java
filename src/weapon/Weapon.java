package weapon;

import java.awt.Point;


//기본 총알의 부모 클래스
public class Weapon implements Runnable{
	
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
	
	//총알의 두께
	protected int weapon_Width;
	protected int weapon_Height;
	
	//스레드 생성
	Thread th;
	

	
	
	//생성시 초기화, 총알 좌표 초기화	
	Weapon(Point character_Point, boolean bullet_Side_LEFT_RIGHT) {
		//총알 의 시작 좌쵸를 전달 받는 캐릭터의 x 좌쵸로 설정
		//총알 의 시작 좌쵸를 전달 받는 캐릭터의 y 좌쵸로 설정
		bullet_Point = new Point(character_Point.x, character_Point.y);
		
		
		th = new Thread(this); 	  //스레드 생성
		th.start(); 		  //스레드 시작
		
		
		//총알의 기본 속도 10으로 생성
		bullet_Speed = 10;
		
		//총알의 기본 파워
		bullet_Power = 1;
		
		this.bullet_Side_LEFT_RIGHT = bullet_Side_LEFT_RIGHT;
		
		bullet_Remove_Bollean = false;
		
	}
	//총알 두께 가져오기
	public int get_Weapon_Width(){
		return weapon_Width;
	}
	public int get_Weapon_Height(){
		return weapon_Height;
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
	
	
	//총알 속도를 올리고 객체지향을위해 총알이 나가는 것을 쓰레드로 돌린다.
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			
			while(true){
				pistol_Move(this.bullet_Side_LEFT_RIGHT);
				Thread.sleep(4);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//총알이 날아갈 방향 정하기
	public void set_Bullet_Side_LEFT(){ //왼쪽으로 진행
		bullet_Side_LEFT_RIGHT = true;
	}
	public void bullet_Side_RIGHT(){ //오른쪽 으로 진행
		bullet_Side_LEFT_RIGHT = false;
	}
	
	//권총의 날아가는 방향을 계산 좌축 또는 우측
			public synchronized void pistol_Move(boolean direction){
				//참이면 왼쪽, 거짓이면 오른쪽 으로 이동
				
				if(direction){ //좌측
					bullet_Point.x -= 8;
				}else{// 우측
					bullet_Point.x += 8;
				}
				
			}
	
	
}



