package character;

import javax.swing.JPanel;

public class Hero extends JPanel{
	//히어로 기본 좌표생성
	private int x_Point;
	private int y_Point;
	
	//히어로의 두께
	private int hero_Height;
	private int hero_Width;
	

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
	
	//주인공 앉기 서기 플래그 true = 서있을때
	private boolean set_Hero_Sit_Stand;
	
	//주인공 상태에 따라 속도 변화 ex) 앉기 / 서기
	private int hero_Speed;
	
	//캐릭터가 보고있는 방향 좌측일때 true 우측 일때 false
	boolean face_Side_LEFT_RIGHT;
	
	//캐릭터 점프시 중력가속도
	int g;
	int gSum;
	int dgSum;
	
	//영웅 HP
	int hp;
	
	//점프 스케줄링 점프 스케줄링 이 어느정도 도달해야 다시 띌수 있도록 조정
	int jump_Time_Schedule;
	
	
	//영웅 시점 변경 명령 boolean
	private boolean view_Temp = false;
	//영웅 시점 단계변화 9단계 까지
	private int view_Temp_Int=0;
	
	

	
	
	
	//히어로 기본 생성자
	public Hero() {
		x_Point = 100;
		y_Point = 1700;
		
		//히어로 폭
		hero_Width = 30;
		hero_Height = 45;
		
		//히어로 피
		hp = 1;
		
		x_Flag_Left = false;
		x_Flag_Right = false;
		y_Flag_Left = false;
		y_Flag_Right = false;
		
		face_Side_LEFT_RIGHT = false; //캐릭터 생성과 동시에 우측 방향
		
		jump_Hero = false;//점프키 누르면 점프 하는 명령어 실행
		jump_Hero_UP_DOWN = true; //올라가는중
		jump_Hero_Stop_Point = true; //true 일때 한번 실행
		
		//영웅 서있기
		set_Hero_Sit_Stand = true;
		
		g = 1;//중력가속도
		gSum = 0;
		dgSum = 0;//낙하 가속도
		
		//영웅의 기본속도 = 5
		hero_Speed = 5;
		
		//점프 스케줄링 0 으로 초기화
		jump_Time_Schedule = 0;
	}
	
	
	//영웅 피딸기 //영웅 피가 0 일때 못움직임
	
	public void set_Hero_Hp_Minus(){
		hp--;
		if(hp <= 0){
			hero_Speed = 0;
		}
	}
	//영웅 피 반환
	public int get_Hero_Hp(){
		return hp;
	}
	
	
	
	//히어로 폭 반환
	public int get_Hero_Width(){
		return hero_Width; 
	}
	//히어로 높이 반환
	public int get_Hero_Height(){
		return hero_Height;
	}
	
	//히어로 넉백 적군이 영웅을 피격했을때. 좌측으로 넉백
	public void left_Knock_Back(){
		x_Point -= 5;
	}
	//우측으로 넉백
	public void right_Knock_Back(){
		x_Point += 5;
	}
	
	public synchronized void move(){  //synchronized 해당 함수가 작동하는 동안 동기화를 수행한다.
		//좌우 움직임 컨트롤
		if(x_Flag_Left){
			//벽보다 왼쪽으로 가지 못하도록 
			if(x_Point > 0){
			x_Point -= hero_Speed;
			}
		}
		if(x_Flag_Right){
			if(x_Point < 950){ //벽보다 오른쪽으로 가지 못하도록
			x_Point += hero_Speed;
			}
		}
	}
	
	
	
	//총 발사 이미지 변경 위한 변수 hero 에 있는 이유는 총 발사 이벤트는 캐릭터가 하는 것이기때문에
	private int trigger_State = 1;
	
	public int get_Trigger_State(){
		return trigger_State;
	}
	public void set_Trigger_State(){
		trigger_State++;
		if(trigger_State == 8){
			trigger_State = 1;
		}
	}
	
	
	//오른쪽으로 걸을때 3~4 
	private int right_Walk=0;
	private int right_Walk_Delay = 0;
	private int walk_Image_Temp = 13; //일어나있을때 걷는 모습은 13장 앉았을때는 6장
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
	
	//오른쪽으로 움직이는중인지 체크한다
	public boolean get_X_Flag_Right(){
		return x_Flag_Right;
	}
	
	//왼쪽으로 걸을때 3~4 
		private int left_Walk=0;
		private int left_Walk_Delay = 0;
		//왼쪽으로 증가
		public int set_Left_Walk_Plus(){
			if(left_Walk_Delay%2==0){
				left_Walk++;
				left_Walk_Delay = 0;
			}
			left_Walk_Delay++;
			if(left_Walk >= walk_Image_Temp){
				left_Walk = 1;
			}
			return left_Walk;
		}
		
		//왼쪽으로 움직이는중인지 체크한다
		public boolean get_X_Flag_Left(){
			return x_Flag_Left;
		}
	
	
	
	
	//캐릭터 점프 무빙
	public void jump_Move(){ //캐릭터의 Y축 값을 가져와서 Y축과 동일 해지거나 작아질때까지 감소시키고 Y축 값을 리턴 또는 중간에 블럭을 밟으면 블럭의 값을 가져와 일치시킨다.
		
		//jump_Time_Schedule 가 일정 수 이상일때 점프 가능
		jump_Time_Schedule++;
		
		
		if(jump_Hero){
			//스톱 지정 지정하기
			if(jump_Hero_Stop_Point){
				jump_Hero_Stop_Point = false;
			}
			
			//gSum이 특정 숫자에 도달했을때 방향 역전
			 if(gSum == 0){
				 jump_Hero_UP_DOWN = !jump_Hero_UP_DOWN;
			 }
			 
			 //낙하 과속 방지
			 if(gSum >= 15){
				 gSum = 15;
			 }
			 
			 //올라가는중
			 if(jump_Hero_UP_DOWN){
				 gSum -= g;
				 y_Point -= gSum;
			 }else{ //내려가는중
				 gSum += g;
				 y_Point += gSum;
			 }
			 hero_Height = 35; //점프중일때는 다리를 올리기 때문에 크기가 10 줄어든다.
		}else{
			hero_Height = 45; //점프중이 아닐때는 다리가 펴져 있기 때문에 크기가 원래대로 돌아온다.
		}
	}
	
	
	//영웅 시점 반환
	public int get_View_Temp_Int_Plus(){
		if(view_Temp_Int < 5 ){ //1부터 ~ 4까지
			view_Temp_Int++; 
		}
		return view_Temp_Int;
	}
	//영웅 시점 반환
	public int get_View_Temp_Int_Minus(){
		if(view_Temp_Int > 1 ){ //4부터 ~ 1까지
			view_Temp_Int--;
		}
		return view_Temp_Int;
	}
	
	
	
		
	
	
	
	//점프를 멈출 시점을 정해준다.
	public void jump_Move_Stop(int y_Stop){
		jump_Hero_Stop_Point_Y = y_Stop; //맨 처음 캐릭터가 점프후 도착할 Y좌표
		//점프가 중지되어야 할 시점 //점프가 다시 가능하도록 초기화
		 if(y_Point >= jump_Hero_Stop_Point_Y){			//초기값으로 받은 y 포인터가 캐릭터보다 아래이면 중지
			 y_Point = jump_Hero_Stop_Point_Y;		
			 jump_Hero = false;							//Jump_Move 매소드 안으로 못들어오게 한다. 점프 중지
			 jump_Hero_Stop_Point = true;				//점프 중지후 다시 멈출 Y 포인터를 받을수 있도록 초기화
			 jump_Hero_UP_DOWN = true; 	//중력 가속도 증감 조절 하는 불린
			 g = 1;
			 gSum = 15;
		 }
	}
	
	//벽을 밝고 있지 않으면 땅으로 떨어져야 한다.
	public void auto_Jump_Down(){
		
		//점프하는 순간이 아니면서 캐릭터가 블럭과 맞닿아있지 않을때
		if(!jump_Hero){ //jump_Hero = 점프 중일때
			if(dgSum>=15)dgSum = 15;
			
			 y_Point += dgSum;
			 dgSum += g;
			 //System.out.println(dgSum);
			 jump_Hero_UP_DOWN = false; //점프 하지않고 떨어지는 중에도 안착할 수 있도록 함
		}else dgSum = 0;
	}
	
	//머리가 끼어있으면 아래로 떨어뜨린다.
	public void auto_Jump_Down_Head(int minus_Num){
		 y_Point += minus_Num+1;
	}
	
	//우측으로 못들가게함
	public void stop_Move_Right(int x_Temp){
		//x_Point = x_Temp;
		//우측 벽 에 부딪혀서 오른쪽으로 제어 불가능
		x_Flag_Right = false;
		//왼쪽으로 -5 만큼씩 밀어줘야 벽으로 안들어가짐
		x_Point -= 6;
		//y_Point 는 약간의 버그 때문에 추가하였음, 몹한테 부딛히면 벽에 붙을 수 있기때문에 미세하게 움직이도록 함
		//y_Point += 5;
		
	}
	
	//좌측으로 못들가게함
		public void stop_Move_Leftt(int y_Temp){
			//x_Point = x_Temp;
			//좌측 벽 에 부딪혀서 오른쪽으로 제어 불가능
			x_Flag_Left = false;
			//오른쪽으로 + 5 만큼씩 밀어줘야 벽으로 안들어가짐
			x_Point += 6;
			//y_Point 는 약간의 버그 때문에 추가하였음, 몹한테 부딛히면 벽에 붙을 수 있기때문에 미세하게 움직이도록 함
			//y_Point -= 5;
		}
		
	
	//캐릭터가 점프를 하지않고 떨어졌을때 중력 가속도를 초기화 한다.
	public void set_dgSum_Zero(){
		dgSum=0;
	}
	
	//벽하고 충돌시 바로 아래로 떨러지도록 하는 함수
	public void set_Jump_Hero_UP_DOWN(){
		dgSum=0;
		jump_Hero_UP_DOWN = false;
	}
	
	
	//점프 키를 눌렀을때 점프 를 실행하도록 설정함
	public void set_Hero_Jumping(){
		
		
		if(jump_Time_Schedule >= 15){	
			if(!jump_Hero){ //점프중일때는 점프가 불가능함
			
			jump_Hero = true;
			//점프 스케줄 초기화
			jump_Time_Schedule = 0;
			}		
		}
	}
	
	//캐릭터가 점프 중인지 아닌지 반환한다
	public boolean get_Jump_State(){
		return jump_Hero_UP_DOWN; //true = 올라가는중
	}
	
	//점프중일때
	public boolean get_Jump_Hero(){ //점프중일때 true
		return jump_Hero;
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
	
	//주인공 점프후 착지하면 블록위에 안착시킨다
	public void set_Hero_Y_Point(int set_Y_Point){
		y_Point = set_Y_Point;
	}
	
	
	
	//주인공 서기/앉기
	public void set_Hero_Sit(){ //주인공 앉기
		if(set_Hero_Sit_Stand){
		hero_Height = 25;
		y_Point += 15;  //15이상하게되면 점프후 착지 앉기 점프를 하면 땅으로 꺼진다.
		set_Hero_Sit_Stand = false;
		//if(!jump_Hero){ //점프 중이 아닐때
		hero_Speed = 2; //영웅 이속 변경
		//}
		walk_Image_Temp = 6; //앉아있을때 사진 6장
		}
	}
	public void set_Hero_Stand(){ //주인공 서기
		set_Hero_Sit_Stand = true;
		y_Point -= 20;
		hero_Height = 45;
		hero_Speed = 5; //영웅 이속 변경
		walk_Image_Temp = 13;
	}
	

	
	//주인공 앉아있을때 상태 반환
	public boolean get_Sit_State(){
		return set_Hero_Sit_Stand;
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
