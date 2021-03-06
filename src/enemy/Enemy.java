package enemy;

import java.awt.Point;

import javax.swing.RepaintManager;

import character.Hero;

//적군 기본 클래스
public class Enemy extends Thread{ //쓰레드를 상속받아서 적군은 적군 알아서 움직이도록 한다.
	
	
	
	//적군의 기본 좌표생성
	protected Point enemy_Point;
	
	//움직일 방향 좌우 랜덤
	protected boolean move_Site; //true 이면 좌측 false이면 우측
	protected double randomValue; //랜덤 값 추출
	
	//경계 구역 범위
	protected int left_Bound_Site;
	protected int right_Bound_Site;
	protected int bottom_Bound_Site;
	
	
	//적을 발견 못했을때는 false, 발견했을때는 true
	protected boolean find_Hero;
	
	//적 영웅 탐지 사각형 왼쪽 위 범위
	protected int range_Site_Width_Left_Point;
	protected int range_Site_Height_Top_Point;
	
	
	//적 영웅 탐지 사각형 오른쪽 밑 아래쪽 범위
	protected int range_Site_Width_Right_Point;
	protected int range_Site_Height_Bottom_Point;
	
	//적군의 가로 두께 및 세로두께
	protected int width;
	protected int height;
	
	//적군의 생명력
	protected int enemy_HP;
	
	//적군 추락 시작 true 일때
	protected boolean down_Start;
	
	//중력 가속도
	private int g = 1;
	private int gSum;
	
	//스레드 생성
	Thread th;
	
	//적군도 히어로의 위치 정보및 정보를 알아야 한다.
	protected Hero hero;
	public void set_Hero_Information(Hero hero){
		this.hero = hero;
	}
	
	public Enemy(int left_Bound_Site, int right_Bound_Site, int bottom_Bound_Site) { //생성될 곳 좌표도 받아와야함
		
		//경계구역 범위 초기 설정
		init_Bound_Site(left_Bound_Site, right_Bound_Site, bottom_Bound_Site);
		
		//적 영웅 탐지 우측 아래쪽 사각형 범위 지정 
		range_Site_Width_Right_Point = 300;
		range_Site_Height_Bottom_Point = 50;
		
		
		//down_Start 가 true 이가 되면 추락 시작
		down_Start = false;
		
		
		//일단 기본적으로 100,600 에 생성
		enemy_Point = new Point(right_Bound_Site, bottom_Bound_Site);
		
		
		//적 영웅 탐지 사각형 범위 생성 위치값 동일
		range_Site_Width_Left_Point = enemy_Point.x;
		range_Site_Height_Top_Point = enemy_Point.y;
		
		
		randomValue = Math.random();
		if(randomValue < 0.5){
			move_Site = true;	//좌측으로 이동
			
		}else {
			move_Site = false; //우측으로 이동
			
		}
		
		find_Hero = false; //적영웅을 발견했을때 true 로 변경
		
		th = new Thread(this); 	  //스레드 생성
		th.start(); 		  //스레드 시작
	}
	
	
	//몬스터가 히어로 쫒는 것을 종료했을때 탐색 범위를 원위치 시킨다, 몬스터 발판 변경후 경계지역 초기화
	public void init_Range_Site(int enemy_Point_X, int enemy_Point_Y){
		//range_Site_Width_Right_Point = 300;
		//range_Site_Height_Bottom_Point = 50;
		
		range_Site_Width_Left_Point = enemy_Point_X + width/2; //+width/2는 워커의 두께의 반 = 경계지역 확실시 하기 위해서
		range_Site_Height_Top_Point = enemy_Point_Y - gSum;
		
		
		//move_Site=!move_Site; //몬스터가 떨어지면서 방향이 바뀌는것을 한 번 더 바꾸어준다.
	}
	
	
	
	public void init_Bound_Site(int left_Bound_Site, int right_Bound_Site, int bottom_Bound_Site){ 
		//몬스터가 튕겨 나가거나 주인공 캐릭을 쫓다가 다른 바닥으로 갔을때 한번더 초기화 해야한다.
		this.left_Bound_Site = left_Bound_Site; //-20 인 이유는 캐릭터를 바깥으로 튕겨내기 위해서
		this.right_Bound_Site = right_Bound_Site;
		this.bottom_Bound_Site = bottom_Bound_Site;
	}
	
	//영웅을 발견했을때
	public void set_Find_Hero(int hero_X_Point){
		find_Hero = true;
	}
	//영웅을 놓쳤을때
	public void set_Not_Find_Hero(){
		find_Hero = false;
	}
	
	
	//기본 영웅을 발견 못했을때 좌우로 경계 서듯 움직이고 있어야함 //synchronized 해당 함수가 작동하는 동안 동기화를 수행한다.
	public synchronized void enemy_Move(){ //적의 경계 범위는 바닥 정보의 범위 값을 가져와서 계산한다.
		
		//적영웅을 발견하지 못했을때 기본 경계 구역을 돌아다닌다.
		if(!find_Hero){
				if(move_Site){	//좌측으로 이동
					enemy_Point.x -= 5;
				}else {			//우측으로 이동
					enemy_Point.x += 5;
				}
				
				//좌우로 움직일 범위 설정
				if((left_Bound_Site) >= enemy_Point.x){
					move_Site = false;
					
				}else if((right_Bound_Site) <= enemy_Point.x){
					move_Site = true;
					
				}
				
		}else { //적 영웅을 발견하게 되면 적 영웅을 추격한다.
			
		}
	}
	
	
	
	
	//경계 구역 사각형 값 리턴
	public int get_Range_Site_Width_Right_Point(){
		return range_Site_Width_Right_Point;
	}
	
	//경계 구역 사각형 값 리턴
	public int get_range_Site_Height_Bottom_Point(){
		return range_Site_Height_Bottom_Point;
	}
	
	//경계 구역 사각형 값 을 증가시킨다. 영웅을 발견했을때 범위를 증가시킨다.
	public void set_Range_Site_Width_Right_Point(int range_Site_Width_Right_Point){
		this.range_Site_Width_Right_Point = range_Site_Width_Right_Point;
	}
	
	//경계 구역 사각형 값 을 증가시킨다. 영웅을 발견했을때 범위를 증가시킨다.
	public void set_Range_Site_Height_Bottom_Point(int range_Site_Height_Bottom_Point){
		this.range_Site_Height_Bottom_Point = range_Site_Height_Bottom_Point;
	}
	
			
				//경계 구역 사각형 좌측 상단 값 반환
				public int get_Range_Site_Width_Left_Point(){
					return range_Site_Width_Left_Point;
				}
				
				//경계 구역 사각형 값 리턴
				public int get_range_Site_Height_Top_Point(){
					return range_Site_Height_Top_Point;
				}
				
				//경계 구역 사각형 값 을 증가시킨다. 영웅을 발견했을때 범위를 증가시킨다.
				/*
				public void set_Range_Site_Width_Left_Point(int range_Site_Width_Left_Point){
					this.range_Site_Width_Left_Point = range_Site_Width_Left_Point;
				}*/
				
				//경계 구역 사각형 값 을 증가시킨다. 영웅을 발견했을때 범위를 증가시킨다.
				/*
				public void set_Range_Site_Height_Up_Point(int range_Site_Top_Height){
					this.range_Site_Height_Top_Point = range_Site_Top_Height;
				}*/
	
	
	
	
	
	
	
	
	
	
	//적군 경계범위 리턴 좌측값
	public int get_Left_Bound_Site(){
		return left_Bound_Site;
	}
	//우측값
	public int get_Right_Bound_Site(){
		return right_Bound_Site;
	}
	public int get_Bottom_Bound_Site(){
		return bottom_Bound_Site;
	}

	
	
	
		
	//적군 가로 세로 반환
	public int get_Enemy_Width(){
		return width;
	}
	
	public int get_Enemy_Height(){
		return height;
	}
	
	//내가 보고 있는 방향 리턴 
	public boolean get_Move_Site(){ //true = 좌측
		return move_Site;
	}
	
	
	
	//저장된 적군의위치를 리턴한다.
	public Point get_enemy_Point(){
		return enemy_Point;
	}
	
	//맵 메이커에서 적군의 y 축 자리를 잡기위해 사용
	public void set_Enemy_Point_Y(){
		enemy_Point.y++;
	}
	
	
	//적군 움직임 쓰레드
	public void run() {
		try{
			while(true){
				
				//자바 멀티 쓰레드 적군의 움직임은 여기서
				enemy_Move();
				
				//적군 발판에서 밀려서 땅으로 떨어지는것
				if(down_Start){
					enemy_Down_Algorithm();
					//System.out.println("1");
				}
				
				
				
				
				Thread.sleep(25); //20milli sec 로 스레드 돌리기
				
			}
		}catch (Exception e) {
			
		}
	}
	
	
	
	//적군 추락 알고리즘
	public void enemy_Down_Algorithm(){
		 //낙하 과속 방지
		 if(gSum >= 15){
			 gSum = 15;
		 }
		 
		 //떨어지면서 확인을 해야한다.
		 gSum += g;
		 enemy_Point.y += gSum;
		 /* 안착 시키기위한 방법 2 
		 for(int i =0;i<gSum;i++){
			 enemy_Point.y ++;			 
		 }*/
		 
		 //다른 발판에 닿았을때 추락을 중지해야 하며 다시금 경계위치를 지정해야한다.
		 if(End_Y_Point < enemy_Point.y){ //이때 중지
			 
			 //적군과 바닥이 맞닿는 지점을 적군의 y 좌표로 설정한다. [적군 착지하면 블록위에 안착시킨다.]
			 enemy_Point.y = End_Y_Point;
			 //추락을 중지하고 중력 가속도를 0 으로 초기화
			 set_Down_Start_False();
			 //닿은 사각형 정보 가져와야함
			 
			
			 
			 //3개 값을 입력 경계시작 위치, 종료위치, y 포인트
			 //init_Bound_Site();
			
			 
			 
		 }
	}
	
	int End_Y_Point = 5000;
	//적군이 새롭게 놓이 y 포인트 받아
	public void get_Enemy_Exit_Yoint(int End_Y_Point){
		this.End_Y_Point = End_Y_Point;
	}

	
	
	//적군이 발판에서 벗어나면 추락시작
	public void set_Down_Start_True(){
		down_Start = true;
		
		//떨어질떄 정지 시켜야함
		if(move_Site){	//좌측으로 이동
			enemy_Point.x += 5;
		}else {			//우측으로 이동
			enemy_Point.x -= 5;
		}
		
	}
	public void set_Down_Start_False(){
		//여기서 발판의 정보를 가져와서 뿌려야함
		End_Y_Point = 5000; //초기화
		gSum = 0;
		down_Start = false;
	}
	public boolean get_Down_Start(){
		return down_Start;
	}
	
	
	
	
	//공격으로 몬스터 방향 전환
	public void set_Move_Site(boolean flag){
		//flag 가 ture 이면 왼쪽으로 전환
		if(flag){
			move_Site = true;
			
		}else {
			
			move_Site = false;
		}
	}

	//총알 효과는 3번
	private int blood_Event_Count = 0;
	//피격시 피흘림 효과
	private boolean blood_Event_Flag = false;
	
	//총알 효과
	public void set_Blood_Event_Count(){
		blood_Event_Count++;
		if(blood_Event_Count==4){ //피흘림효과 3번 되면 멈춤
			blood_Event_Flag = false;
			blood_Event_Count = 0;
		}
	}
	//피효과 그려줄 숫자
	public int get_Blood_Event_Count(){
		return blood_Event_Count;
	}
	public void set_Blood_Event_Flag(){ //true 일때 피흘림시작
		blood_Event_Flag = true;
	}
	public boolean get_Blood_Event_Flag(){
		return blood_Event_Flag; 
	}
	
	//오른쪽으로 움직이는중인지 체크한다
	public boolean get_Right_Flag(){ //false 일때 오른쪽
		return move_Site;
	}
	
	
	//피격 판정시 뒤로 밀려남 효과
	public void knockback(boolean flag){
		

				//flag 가 ture 이면 왼쪽으로 전환
				if(flag){
					
					enemy_Point.x += 15;
					
				}else {
					
					enemy_Point.x -= 15;
					
				}
			}
	
	//적군의 피가 줄어듬
	public void enemy_HP_Down(int gun_Damage){
		enemy_HP--;
		//System.out.println(enemy_HP);
	}
	
	//적군 에너지 체크하기
	public int get_Enemy_HP(){
		return enemy_HP;
	}
			
			
			
}
