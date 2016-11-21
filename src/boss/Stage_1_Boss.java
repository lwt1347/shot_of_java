package boss;

import java.awt.Point;
import java.util.ArrayList;

public class Stage_1_Boss extends Boss{

	//3번 패턴 반환
	Pattern_3 pattern_3;
	
	//4번 패턴 반환
	Pattern_4 pattern_4;
	
	//번개 공격은 여러 차례 나타나기 떄문에 여러개 생성
	ArrayList<Pattern_4> pattern_4_List = new ArrayList<Pattern_4>();
	
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
		
		HP = 500;
		start(); //상속받은 런을 여기서 실행 시킨다.
	}

	
	public void boss_Move(){
		
		//패턴 정하는 공간 보스가 영웅 보다 왼쪽에 있을때 true
		if(boss_Point.x <= hero_Point.x){
			Direction = true;
		}else {
			Direction = false;
		}
		
		//보스패턴 변화 시간
		//패턴 변화 시간
		pattern_Change_Time++;
		if(pattern_Change_Time >= 300){
			pattern_Change_Time = 0;
			set_get_Boss_1_Patter4_Clear(); //패턴이 변할때 번개 삭제
			
			raser_StandBy = 0;//레이저 스탠바이 내부 클래스 에서 증가하는 값을 초기화 시킨다.
			Image_Rotation_num_Pattern_3 = 0; //마찬가지로 레이저 스탠바이 표출
			
			if(!(boss_Point.y+20 > hero_Point.y && (boss_Point.y + height - 20) < hero_Point.y)){ //보스 보다 영웅이 많이 벗어나있을때 보스를 y축 이동함
				boss_Point.y = hero_Point.y - 100;//보스 y축 이동
				//boss_Point.x = 100; //x 축 초기화 x 는 좌측에서 나올지 우측에서 나올지 랜덤으로 결정한다.
			}
			
			
			
			 // 3; //랜덤값 입력
			 pattern = 1 + (int) (Math.random()*4);
			 System.out.println(pattern);
			 
			 
			 //pattern = 4; //임시로 번개 생성
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
				
			case 4: //번개 발사
				
				//보스 위치 가운데 지정
				boss_Point.x = 500; 
				boss_Point.y = 1500;
				
				pattern_4_Delay++;
				pattern_4_Delet_Delay++; //번개 삭제 시간
				if(pattern_4_Delay >= 30){
				//4번 패턴일때 생성 해서 추가 50당 하나씩 추가
				
				pattern_4 = new Pattern_4(1 + (int) (Math.random()*1000), this.boss_Point.y - 500 + (int) (Math.random()*300), 100); //랜덤으로 x 좌표 설정 해야함
				
				
				
				pattern_4_Delay = 0;
				
				pattern_4_List.add(pattern_4); //4번 패턴 번개 삽입
				System.out.println("a");
				}
				
				else if(pattern_4_List.size() > 0 && pattern_4_Delet_Delay >= 50){
					pattern_4_List.remove(0); //0번째 부터 차례로 삭제 시켜야함
					pattern_4_Delet_Delay = 0;
				}
				
				break;
				
		}
		
		

		//보스 머리 몸통 다리 설정    //0=x 좌표, 1=y 좌표, 2=넓이, 3=높이
				//오른쪽볼때
				if(Direction){
				head_Info[0] = boss_Point.x + 15; 
				head_Info[1] = boss_Point.y + 25;
				head_Info[2] = 45;
				head_Info[3] = 30;
					
				body_Info[0] =  boss_Point.x + 10;
				body_Info[1] =  head_Info[1] + head_Info[3]; //몸통은 머리 바로 아래 달림
				body_Info[2] =  25;
				body_Info[3] =  70;
				
				leg_Info[0] =   boss_Point.x + 5;
				leg_Info[1] =   body_Info[1] + body_Info[3];
				leg_Info[2] =   50;
				leg_Info[3] =   40;
				}else{
					head_Info[0] = boss_Point.x + 15; 
					head_Info[1] = boss_Point.y + 25;
					head_Info[2] = 45;
					head_Info[3] = 30;
						
					body_Info[0] =  boss_Point.x + 35;
					body_Info[1] =  head_Info[1] + head_Info[3]; //몸통은 머리 바로 아래 달림
					body_Info[2] =  25;
					body_Info[3] =  70;
					
					leg_Info[0] =   boss_Point.x + 15;
					leg_Info[1] =   body_Info[1] + body_Info[3];
					leg_Info[2] =   50;
					leg_Info[3] =   40;
				}
				

		
		
		
	}
	
	//보스 3번 패턴 상태 반환
	public Pattern_3 get_Boss_1_Patter3(){
		return pattern_3;
	}
	
	
	//보스 4번 패턴 리스트 반환
	public ArrayList<Pattern_4> get_Boss_1_Patter4(){
		return pattern_4_List;
	}
	
	//보스 4번 패턴 공격 끝나면 클리어 -> 시간이 지나면 하나씩 소멸해야함
	public void set_get_Boss_1_Patter4_Clear(){
		pattern_4_List.clear();
	}
	
	
	//보스 3번 마법 딜레이
	int pattern_3_Delay = 0;
	
	//보스 4번 번개 딜레이
	int pattern_4_Delay = 0;
	
	//보스 4번 패턴 번개 삭제 시간
	int pattern_4_Delet_Delay;
	
	

	public void set_Hero_Point(int x, int y){
		//영웅의 좌표를 가져온다. 추적 하기 위해서
		hero_Point.x = x;
		hero_Point.y = y;
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
	
	

	//보스 4번 패턴 보스 이미지 변경
	int boss_Image_Rotation_Num = 0; 
	int Image_Rotation_Delay_Pattern_4=0;
	public int set_Boss_Image_Rotation_Pattern_4(){
			
		Image_Rotation_Delay_Pattern_4++;
			if(Image_Rotation_Delay_Pattern_4 % 10 == 0){
				Image_Rotation_Delay_Pattern_4 = 0;
				boss_Image_Rotation_Num++;
			}
			
			if(boss_Image_Rotation_Num > 5){
				boss_Image_Rotation_Num = 1;
			}
			
			
			return boss_Image_Rotation_Num;
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
		
		
		//보스 3번 패턴
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
		
		
		
		
		
		
		//레이저 대기 이미지 변경
		public int raser_StandBy(){
			return raser_StandBy%9;
		}
		
	}
	
	//보스의 4번째 공격 패턴 
	public class Pattern_4{
		//번개 공격
		int point_X; //위치 설정
		int point_y;
		
		//번개 크기
		int width;
		int hegith;
		int img_Color;
		
		//번개 모양
		String shape;
		
		public Pattern_4(int x, int y, int img_Color){ //생성될 위치 반환
			this.point_X = x;
			this.point_y = y;
			width = 150;
			hegith = 1500;
			this.img_Color = (int) (1 + Math.random() * 3); //1이면 노랑 2면 빨강 3이면 파랑
			if(this.img_Color == 1){
				shape = "Yellow";
			}else if(this.img_Color == 2){
				shape = "Red";
			}else if(this.img_Color == 3){
				shape = "Blue";
			}
		}
		
		int Image_Rotation_num_Pattern_4 = 0; //번개 이미지 변경
		//번개가 어느 순간 이후 부터 대미지를 입힘
		int Image_Rotation_num_Pattern_4_Delay = 0;
		
		public int set_Image_Rotation_Pattern_4(){
			
			Image_Rotation_num_Pattern_4_Delay++;
			
			if(Image_Rotation_num_Pattern_4_Delay > 25){
			Image_Rotation_num_Pattern_4++; //일단 1
			if(Image_Rotation_num_Pattern_4 > 5){
				Image_Rotation_num_Pattern_4 = 1;
			}
			}
			return Image_Rotation_num_Pattern_4;
		}
		
		//번개 이미지 선택 노 붉 파
		public String get_Lightning_Shape(){
			return shape;
		}
		
		public int get_Point_X(){
			return point_X;
		}
		
		public int get_Point_Y(){
			return point_y;
		}
		
		
		
	}
	
	
	
	

}


