package enemy;

import java.awt.Point;

public class Stage_1_Boss extends Thread{

	//보스의 기본 위치
	Point boss_Point;
	Point hero_Point;
	
	//보스의 크기
	int width;
	int height;
	
	//보스 패턴
	int pattern = 1;
	
	int pattern_Change_Time = 1;
	
	//3번 패턴 반환
	Pattern_3 pattern_3;
	
	
	public Stage_1_Boss() {
		
		boss_Point = new Point(0, 0);
		hero_Point = new Point(0, 0);
		
		//1스테이지 기본 보스 위치
		boss_Point.x = 100;
		boss_Point.y = 1600;
		
		//3번 패턴 공격 생성
		pattern_3 = new Pattern_3();
		
		width = 70;
		height = 170;
		
		start();
	}
	
	//보스가 달려갈 방향
	boolean Direction; //보스가 영웅 보다 왼쪽에 있을때 true
	boolean boss_Move_Direction;
	boolean boss_Move_Direction_Flag = true; //도달할때 까지 변화 하지 않도록
	boolean boss_Move_Direction_Pattern_3 = true; //보스 마법 발사할 방향
	public void boss_Move(){
		
		//보스패턴 변화 시간
		//패턴 변화 시간
		pattern_Change_Time++;
		if(pattern_Change_Time >= 300){
			pattern_Change_Time = 0;
			raser_StandBy = 0;//레이저 스탠바이 내부 클래스 에서 증가하는 값을 초기화 시킨다.
			Image_Rotation_num_Pattern_3 = 0; //마찬가지로 레이저 스탠바이 표출
			
			
			if(!(boss_Point.y+20 > hero_Point.y && (boss_Point.y + height - 20) < hero_Point.y)){ //보스 보다 영웅이 많이 벗어나있을때 보스를 y축 이동함
				boss_Point.y = hero_Point.y - 100;//보스 y축 이동
				//boss_Point.x = 100; //x 축 초기화 x 는 좌측에서 나올지 우측에서 나올지 랜덤으로 결정한다.
			
			}
			
			//패턴 정하는 공간 보스가 영웅 보다 왼쪽에 있을때 true
			if(boss_Point.x <= hero_Point.x){
				Direction = true;
			}else {
				Direction = false;
			}
			
			pattern = 3; //랜덤값 입력
		}
		
		
		
		
		switch(pattern){
		
		
			
		
		//보스 움직임 1번 정지
			case 1:
				
			break;
		// 2번 좌우 움직임 끝까지 돌격
			case 2:
				//보스가 영웅보다 왼쪽에 있을때 우측으로 돌진 
				if(Direction && boss_Move_Direction_Flag){
					boss_Move_Direction = true;
				}else if(boss_Move_Direction_Flag){
					boss_Move_Direction = false;
				}
				
					if(boss_Move_Direction){ //우측으로 돌진
						if(boss_Point.x < 910){ //우측까지 도달했을때 방향을 전환
						boss_Point.x+=10;
						boss_Move_Direction_Flag = false;
						}else{
							boss_Move_Direction_Flag = true;
							//패턴 1번으로 가서 정지
							pattern = 1;
						}
					}else {
						if(boss_Point.x > 10){
						boss_Point.x-=10;
						boss_Move_Direction_Flag = false;
						}else{ //좌측까지 도달했을때 방향을 전환
							boss_Move_Direction_Flag = true;
							//패턴 1번으로 가서 정지
							pattern = 1;
						}
					}
					
			break;
		// 3번 마법 발사
			case 3:
				pattern_3_Delay++;
				if(pattern_3_Delay >= 150){ //100 이상 지속 되었다면 정지
					pattern_3_Delay = 0;
				    pattern = 1; 
				    //magic_Direct = 0;
				    pattern_3.set(0, 50);
				   
				    break;
				}
				
				if(Direction && boss_Move_Direction_Flag){
					boss_Move_Direction_Pattern_3 = true;
				}else if(boss_Move_Direction_Flag){
					boss_Move_Direction_Pattern_3 = false;
				}
				
				if(boss_Move_Direction_Pattern_3){ //우측으로 바법 발사
					//magic_Direct = 1;
					 pattern_3.set(1, 50); //3번 패턴 마법 공격 반환
				}else{
					//magic_Direct = 2;
					 pattern_3.set(2, 50);
				}
				
				break;
		}
		
	}
	
	//보스 3번 패턴 상태 반환
	public Pattern_3 get_Boss_1_Patter3(){
		return pattern_3;
	}
	
	//보스 3번 마법 딜레이
	int pattern_3_Delay = 0;
	
	
	
	//보스 패턴 반환
	public int get_Boss_pattern(){
		return pattern;
	}
	
	
	//보스가 보는 방향
	public boolean get_Boss_Move_Direction(){
		return boss_Move_Direction;
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
	
	
	//보스 이미지 변경
	int Image_Rotation_Delay = 0;
	int Image_Rotation_num = 1;
	public int set_Image_Rotation(){
		Image_Rotation_Delay++;
		if(Image_Rotation_Delay % 10 == 0){
			Image_Rotation_Delay = 0;
			Image_Rotation_num++;
		}
		if(Image_Rotation_num > 7){
			Image_Rotation_num = 1;
		}
		return Image_Rotation_num;
	}
	
	//보스 2번 패턴
	int Image_Rotation_Delay_Pattern_2 = 0;
	int Image_Rotation_num_Pattern_2 = 1;
	public int set_Image_Rotation_Pattern_2(){
		Image_Rotation_Delay_Pattern_2++;
		if(Image_Rotation_Delay_Pattern_2 % 2 == 0){
			Image_Rotation_Delay_Pattern_2 = 0;
			Image_Rotation_num_Pattern_2++;
		}
		if(Image_Rotation_num_Pattern_2 > 18){
			Image_Rotation_num_Pattern_2 = 1;
		}
		return Image_Rotation_num_Pattern_2;
	}
	
	
	public void set_Hero_Point(int x, int y){
		//영웅의 좌표를 가져온다. 추적 하기 위해서
		hero_Point.x = x;
		hero_Point.y = y;
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
	
	int raser_StandBy = 0; //레이저 조준 시점
	int Image_Rotation_num_Pattern_3 = 1;//레이저 이미지
	//보스의 3번 공격 패턴 반환
	public class Pattern_3{
		//방향
		int magic_Direct = 0; //0일때 안나감, 1일때 오른쪽, 2일때 왼쪽
		//y축 범위 크기
		int height = 0;
		public void set(int magic_Direct, int height){
			this.magic_Direct = magic_Direct;
			this.height = height;
		}
		public int get_Magic_Direct(){
			return magic_Direct;
		}
		public int get_Y_Height(){
			return height;
		}
		
		
		//보스 2번 패턴
		
		
		int Image_Rotation_Delay_Pattern_3 = 0; 
		public int set_Image_Rotation_Pattern_3(){
			
			
			if(raser_StandBy <= 90){
				raser_StandBy++; //패턴 변경될때 초기화 된다.
			}else {
			Image_Rotation_Delay_Pattern_3++;
			if(Image_Rotation_Delay_Pattern_3 % 2 == 0){
				Image_Rotation_Delay_Pattern_3 = 0;
				Image_Rotation_num_Pattern_3++;
			}
			
			if(Image_Rotation_num_Pattern_3 > 4){
				Image_Rotation_num_Pattern_3 = 1;
			}
			
			}
			return Image_Rotation_num_Pattern_3;
		}
		
		
		
	}

}


