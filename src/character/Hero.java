package character;

public class Hero {
	//히어로 기본 좌표생성
	private int x_Point;
	private int y_Point;
	
	//히어로 무빙(부드러운 움직임 표현)
	boolean x_Flag_Left;
	boolean y_Flag_Left;
	boolean x_Flag_Right;
	boolean y_Flag_Right;
	
	//점프시
	boolean jump_Hero;
	//점프시 올라가는중인지 가라 앉는 중 인지 확인 true 이면 점프중
	boolean jump_Hero_UP_DOWN;
	//점프 시작과 함께 기본 캐릭터의 도착지점 Y를 체크하기위한 플래그
	boolean jump_Hero_Stop_Point;
	//도착지점의 y좌표를 지정할 변수
	int jump_Hero_Stop_Point_Y;
	
	
	//캐릭터가 보고있는 방향 좌측일때 true 우측 일때 false
	boolean face_Side_LEFT_RIGHT;
	
	//캐릭터 점프시 중력가속도
	int g;
	int gSum;
	
	//히어로 기본 생성자
	public Hero() {
		x_Point = 100;
		y_Point = 620;
		
		x_Flag_Left = false;
		x_Flag_Right = false;
		y_Flag_Left = false;
		y_Flag_Right = false;
		
		face_Side_LEFT_RIGHT = false; //캐릭터 생성과 동시에 우측 방향
		
		jump_Hero = false;//점프키 누르면 점프 하는 명령어 실행
		jump_Hero_UP_DOWN = true; //올라가는중
		jump_Hero_Stop_Point = true; //true 일때 한번 실행
		
		g = 1;//중력가속도
		gSum = 15;
	}
	
	public synchronized void move(){  //synchronized 해당 함수가 작동하는 동안 동기화를 수행한다.
		//좌우 움직임 컨트롤
		if(x_Flag_Left){
			x_Point -= 5;
		}
		if(x_Flag_Right){
			x_Point += 5;
		}
	}
	
	//캐릭터 점프 무빙
	public void jump_Move(int y_Stop){ //캐릭터의 Y축 값을 가져와서 Y축과 동일 해지거나 작아질때까지 감소시키고 Y축 값을 리턴 또는 중간에 블럭을 밟으면 블럭의 값을 가져와 일치시킨다.
		
		if(jump_Hero){
			//스톱 지정 지정하기
			if(jump_Hero_Stop_Point){
				jump_Hero_Stop_Point_Y = y_Stop; //맨 처음 캐릭터가 점프후 도착할 Y좌표
				jump_Hero_Stop_Point = false;
			}
			
			//gSum이 특정 숫자에 도달했을때 방향 역전
			 if(gSum == 0){
				 jump_Hero_UP_DOWN = !jump_Hero_UP_DOWN;
			 }
			 
			 //올라가는중
			 if(jump_Hero_UP_DOWN){
				 gSum -= g;
				 y_Point -= gSum;
			 }else{ //내려가는중
				 gSum += g;
				 y_Point += gSum;
			 }
			 
			 //점프가 중지되어야 할 시점 //점프가 다시 가능하도록 초기화
			 if(y_Point >= jump_Hero_Stop_Point_Y){			//초기값으로 받은 y 포인터가 캐릭터보다 아래이면 중지
				 y_Point = jump_Hero_Stop_Point_Y;		
				 jump_Hero = false;							//Jump_Move 매소드 안으로 못들어오게 한다. 점프 중지
				 jump_Hero_Stop_Point = true;				//점프 중지후 다시 멈출 Y 포인터를 받을수 있도록 초기화
				 jump_Hero_UP_DOWN = !jump_Hero_UP_DOWN; 	//중력 가속도 증감 조절 하는 불린
				 g = 1;
				 gSum = 15;
			 }
			 
		}
	}
	
	//점프 키를 눌렀을때 점프 를 실행하도록 설정함
	public void set_Hero_Jumping(){
		if(!jump_Hero){ //점프중일때는 점프가 불가능함
		jump_Hero = true;
		}
	}
	
	//주인공 x 축 리턴
	public int get_Hero_X_Point(){
		return x_Point;
	}
	//주인공 y 축 리턴
	public int get_Hero_Y_Point(){
		return y_Point;
	}
	
	//캐릭터가 처다보고 있는 방향 리턴
	public boolean get_Face_Side_LFET_RIGHT(){
		return face_Side_LEFT_RIGHT;
	}
	
	//주인공 좌측이동
	public void set_Hero_X_Left(){
		x_Flag_Left = true;
		face_Side_LEFT_RIGHT = true; //좌측 방향
	}
	
	//주인공 우측 이동
	public void set_Hero_X_Right(){
		x_Flag_Right = true;
		face_Side_LEFT_RIGHT = false; //우측 방향
	}
	//주인공 정지
	public void set_Hero_X_Left_Stop(){
		x_Flag_Left = false;
	}
	//주인공 정지
	public void set_Hero_X_Right_Stop(){
		x_Flag_Right = false;
	}
	
	
	
}
