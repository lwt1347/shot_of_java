package boss;

import java.awt.Point;

public class Boss extends Thread{

	//보스의 기본 위치
		protected Point boss_Point;
		protected Point hero_Point;
		
		
		//보스 1차 크기 비교 1차에서 
		protected int width;
		protected int height;
		
		//보스 얼굴 크기 대미지 3씩 
		protected int head_Width;
		protected int head_Height;
		int[] head_Info = new int[4]; //0=x 좌표, 1=y 좌표, 2=넓이, 3=높이
		
		//보스의 몸 크기 대미지 2씩
		protected int body_Width;
		protected int body_Height;
		int[] body_Info = new int[4]; //0=x 좌표, 1=y 좌표, 2=넓이, 3=높이
		
		//보스 다리 크기 대미지 1씩
		protected int leg_Width;
		protected int leg_Height;
		int[] leg_Info = new int[4]; //0=x 좌표, 1=y 좌표, 2=넓이, 3=높이
		
		//보스 Hp
		protected int HP;
	
		//보스가 달려갈 방향
		boolean Direction; //보스가 영웅 보다 왼쪽에 있을때 true
		boolean boss_Move_Direction;
		boolean boss_Move_Direction_Flag = true; //도달할때 까지 변화 하지 않도록
		boolean boss_Move_Direction_Pattern_3 = true; //보스 마법 발사할 방향
		
	//오버라이딩 함수
	public void boss_Move(){
		
	}
	
		//보스 패턴
		int pattern = 1;
		int pattern_Change_Time = 1;
		
		//보스 패턴 반환
		public int get_Boss_pattern(){
			return pattern;
		}
		//보스가 보는 방향
		public boolean get_Boss_Move_Direction(){
			return boss_Move_Direction;
		}
		
		//보스가 영웅을 바라봄
		public boolean get_Boss_Hero_Look(){
			return Direction;
		}
		
		//보스의 움직이는 방향 반환
		public boolean get_Boss_Move_Direction_Flag(){
			return boss_Move_Direction_Flag;
		}
		//보스의 좌표 가져오기
		public Point get_Boss_Point(){
			return boss_Point;
		}
		//보스 넓이 가져오기
		public int get_Boss_Width(){
			return width;
		}
		//보스 높이 가져오기
		public int get_Boss_Height(){
			return height;
		}

		//보스 머리 크기와 위치 가져오기
		public int[] get_Head_Info(){
			
			return head_Info; 
		}
		//보스 몸통의 크기와 위치 가져오기
		public int[] get_Body_Info(){
			
			return body_Info;
		}
		
		//보스 다리의 크기와 위치 가져오기
		public int[] get_Leg_Info(){
			return leg_Info;
		}
		
	//머리 맞출때
	public void set_Hp_Minus_Head(){
		HP-=3; 
	}
	//머리 맞출때
	public void set_Hp_Minus_Body(){
		HP-=2; 
	}
	//다리 맞출때
	public void set_Hp_Minus_Leg(){
		HP--; 
	}
	
	int Hp_temp = 1;
	public int get_Boss_HP(){
		if(HP >= 500){
			Hp_temp = 1;
		}else if(HP >= 450){
			Hp_temp = 2;
		}else if(HP >= 400){
			Hp_temp = 3;
		}else if(HP >= 350){
			Hp_temp = 4;
		}else if(HP >= 300){
			Hp_temp = 5;
		}else if(HP >= 250){
			Hp_temp = 6;
		}else if(HP >= 150){
			Hp_temp = 7;
		}else if(HP >= 100){
			Hp_temp = 8;
		}else if(HP >= 50){
			Hp_temp = 9;
		}else if(HP >= 1){
			Hp_temp = 10;
		}else Hp_temp = 0;
		
		return Hp_temp;
	}
	
	public void run(){
		while(true){
			
			try {
				//보스 움직임
				Thread.sleep(20);
				
			
				boss_Move();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	
}
