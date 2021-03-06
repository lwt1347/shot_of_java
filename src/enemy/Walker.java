package enemy;

import character.Hero;

//워커 클래스
public class Walker extends Enemy{

	//lock_Trans = 태세 변환을 위한 불린 공격/경계 등
	private boolean lock_Trans;
	
	
	
	
	public Walker(int left_Site, int right_Site, int bottom_Site) {
		super(left_Site, right_Site, bottom_Site);
		// TODO Auto-generated constructor stub
		
		//적 영웅 탐지 사각형 범위 지정
		range_Site_Width_Right_Point = 250;
		range_Site_Height_Bottom_Point = 60;
		
		//워커 둘레
		width = 30;
		height = 70;
		
		lock_Trans = true;
		
		//워커의 기본 생명력 50
		enemy_HP = 30;
		
	}
	
	
	
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@오버 라이딩
		//경계 구역 사각형 값 리턴
		public int get_Range_Site_Width_Right_Point(){
			return range_Site_Width_Right_Point;
		}
		
		//경계 구역 사각형 값 리턴
		public int get_Range_Site_Height(){
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
		
			//몬스터가 히어로 쫒는 것을 종료했을때 탐색 범위를 원위치 시킨다.
			public void init_Range_Site(){
				range_Site_Width_Right_Point = 250;
				range_Site_Height_Bottom_Point = 60;
				
				range_Site_Width_Left_Point = enemy_Point.x;
				range_Site_Height_Top_Point = enemy_Point.y;
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
						}
						*/
						//경계 구역 사각형 값 을 증가시킨다. 영웅을 발견했을때 범위를 증가시킨다.
						/*
						public void set_Range_Site_Height_Top_Point(int range_Site_Top_Height){
							this.range_Site_Height_Top_Point = range_Site_Top_Height;
						}*/
				

		
		
		//경계값 한번만 증가 시키기위한 플래그
		boolean plusOnce = true;
						

		//영웅을 발견했을때
		public void set_Find_Hero(int hero_X_Point){
			find_Hero = true;
			if(hero_X_Point > enemy_Point.x){
				move_Site = false;
			}else {
				move_Site = true;
			}
		}
		
		//영웅을 놓쳤을때
		public void set_Not_Find_Hero(){
			find_Hero = false;
		}
		
		//적군의 공격 상태확인
		public boolean get_Find_Hero(){
			return find_Hero;
		}
		
		
		
		
					
					
		//피격 판정시 뒤로 밀려남 효과
		public void knockback(boolean flag){
			//flag 가 ture 이면 왼쪽으로 전환
			if(flag){
				
				enemy_Point.x += 15;
				
			}else {
				
				enemy_Point.x -= 15;
				
			}
			//넉백위치에서 다시금 경계 사각형을 그린다.
			//range_Site_Width_Left_Point = enemy_Point.x;
			//range_Site_Height_Top_Point = enemy_Point.y;
		}
		
		
		
		//오른쪽으로 걸을때 3~4 
		private int right_Walk=0;
		private int right_Walk_Delay = 0;
		private int walk_Image_Temp = 9; //일어나있을때 걷는 모습은 9장 앉았을때는 6장
		//오른쪽으로 증가
		public int set_Right_Walk_Plus(){
			if(right_Walk_Delay%2==0){
			right_Walk++;
			right_Walk_Delay = 0;
			}
			right_Walk_Delay++;
			
			if(right_Walk >= walk_Image_Temp){ //일어 나있을때 앉아있을때 사진의 개수가 다르다.
				right_Walk = 1;
			}
			
			return right_Walk;
		}
		
		
		
		
		
		
		public synchronized void enemy_Move(){ //적의 경계 범위는 바닥 정보의 범위 값을 가져와서 계산한다.
			
			//적영웅을 발견하지 못했을때 기본 경계 구역을 돌아다닌다.
			if(!find_Hero){
					
					
					if(move_Site){	//좌측으로 이동
						enemy_Point.x -= 2;
					}else {			//우측으로 이동
						enemy_Point.x += 2;
					}
					
					//좌우로 움직일 범위 설정 적군이 영웅을 발견 못했을때 경계 범위를 블럭 전체로 잡는다.
					if((left_Bound_Site - 10) >= enemy_Point.x){
						move_Site = false;
					}else if((right_Bound_Site - 20) <= enemy_Point.x){
						move_Site = true;
					}
					
					if(!lock_Trans){
						init_Range_Site(); //적 영웅 공격 상태가 아닐때 원상 복귀
						lock_Trans = true; //태세 변환을 위한 불린
					}
					
					//탐색 범위를 계속 해서 동기화 시켜 주어야한다.
					
					range_Site_Width_Left_Point = enemy_Point.x + width/2; //+width/2는 워커의 두께의 반 = 경계지역 확실시 하기 위해서
					//range_Site_Height_Top_Point = enemy_Point.y;
				
					
			}else { //적 영웅을 발견하게 되면 적 영웅을 추격한다.
				
				//끝까지 추격해야한다 방향 전환 
				range_Site_Width_Left_Point = enemy_Point.x + width/2;  //+width/2는 워커의 두께의 반 = 경계지역 확실시 하기 위해서
				
				
				
				
				
				range_Site_Height_Top_Point = enemy_Point.y-100;	//에서 
				range_Site_Height_Bottom_Point = 250; //까지 600- 450  500 - 450 enemy_Point.y - 450
				//System.out.println(range_Site_Height_Bottom_Point);
				//탐색 범위를 계속 해서 동기화 시켜 주어야한다.
				//range_Site_Width_Left_Point = enemy_Point.x;
				
				//태세 변환시 초기화 한번
				if(lock_Trans){
					set_Range_Site_Width_Right_Point(300); //공격 태세일때 경계값 범위가 늘어난다.
					
					//set_Range_Site_Height(100); //동문
					lock_Trans = false;
				}
					//이동 속도 증가
					if(move_Site){	//좌측으로 이동
						if(hero.get_Hero_X_Point()+30 <= enemy_Point.x || hero.get_Hero_Y_Point() >= (enemy_Point.y + height)){ //좌측으로 캐릭터 보다 전진 하지 못하도록 = 캐릭터를 쫓아 가도록
						enemy_Point.x -= 5;
						}
					}else {			//우측으로 이동
						if(hero.get_Hero_X_Point()+3 >= enemy_Point.x + width){
						enemy_Point.x += 5;
						}
					}
					
					//좌우로 움직일 범위 설정 적군의 공격범위에서는 끝까지 따라오도록
					if((left_Bound_Site) >= enemy_Point.x + 30){
						//move_Site = false; //오른쪽
						enemy_Point.x -= 3; //위의 코드로 하면 방향이 바뀐다.
					}else if((right_Bound_Site) <= enemy_Point.x){
						//move_Site = true;//왼쪽
						enemy_Point.x += 3;
					}
					
				
				
			}
		}
}











