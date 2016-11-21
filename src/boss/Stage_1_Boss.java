package boss;

import java.awt.Point;
import java.util.ArrayList;

public class Stage_1_Boss extends Boss{

	//3�� ���� ��ȯ
	Pattern_3 pattern_3;
	
	//4�� ���� ��ȯ
	Pattern_4 pattern_4;
	
	//���� ������ ���� ���� ��Ÿ���� ������ ������ ����
	ArrayList<Pattern_4> pattern_4_List = new ArrayList<Pattern_4>();
	
	public Stage_1_Boss() {
		
		boss_Point = new Point(0, 0);
		hero_Point = new Point(0, 0);
		
		//1�������� �⺻ ���� ��ġ
		boss_Point.x = 100;
		boss_Point.y = 1600;
		
		//3�� ���� ���� ����
		pattern_3 = new Pattern_3();
		
		width = 70;
		height = 170;
		
		HP = 500;
		start(); //��ӹ��� ���� ���⼭ ���� ��Ų��.
	}

	
	public void boss_Move(){
		
		//���� ���ϴ� ���� ������ ���� ���� ���ʿ� ������ true
		if(boss_Point.x <= hero_Point.x){
			Direction = true;
		}else {
			Direction = false;
		}
		
		//�������� ��ȭ �ð�
		//���� ��ȭ �ð�
		pattern_Change_Time++;
		if(pattern_Change_Time >= 300){
			pattern_Change_Time = 0;
			set_get_Boss_1_Patter4_Clear(); //������ ���Ҷ� ���� ����
			
			raser_StandBy = 0;//������ ���Ĺ��� ���� Ŭ���� ���� �����ϴ� ���� �ʱ�ȭ ��Ų��.
			Image_Rotation_num_Pattern_3 = 0; //���������� ������ ���Ĺ��� ǥ��
			
			if(!(boss_Point.y+20 > hero_Point.y && (boss_Point.y + height - 20) < hero_Point.y)){ //���� ���� ������ ���� ��������� ������ y�� �̵���
				boss_Point.y = hero_Point.y - 100;//���� y�� �̵�
				//boss_Point.x = 100; //x �� �ʱ�ȭ x �� �������� ������ �������� ������ �������� �����Ѵ�.
			}
			
			
			
			 // 3; //������ �Է�
			 pattern = 1 + (int) (Math.random()*4);
			 System.out.println(pattern);
			 
			 
			 //pattern = 4; //�ӽ÷� ���� ����
		}
		
		
		
		switch(pattern){
		
		
			
		
		//���� ������ 1�� ����
			case 1:
				
			break;
		// 2�� �¿� ������ ������ ����
			case 2:
				//������ �������� ���ʿ� ������ �������� ���� 
				if(Direction && boss_Move_Direction_Flag){
					boss_Move_Direction = true;
				}else if(boss_Move_Direction_Flag){
					boss_Move_Direction = false;
				}
				
					if(boss_Move_Direction){ //�������� ����
						if(boss_Point.x < 910){ //�������� ���������� ������ ��ȯ
						boss_Point.x+=10;
						boss_Move_Direction_Flag = false;
						}else{
							boss_Move_Direction_Flag = true;
							//���� 1������ ���� ����
							pattern = 1;
						}
					}else {
						if(boss_Point.x > 10){
						boss_Point.x-=10;
						boss_Move_Direction_Flag = false;
						}else{ //�������� ���������� ������ ��ȯ
							boss_Move_Direction_Flag = true;
							//���� 1������ ���� ����
							pattern = 1;
						}
					}
					
			break;
		// 3�� ���� �߻�
			case 3:
				pattern_3_Delay++;
				if(pattern_3_Delay >= 150){ //100 �̻� ���� �Ǿ��ٸ� ����
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
				
				if(boss_Move_Direction_Pattern_3){ //�������� �ٹ� �߻�
					//magic_Direct = 1;
					 pattern_3.set(1, 50); //3�� ���� ���� ���� ��ȯ
				}else{
					//magic_Direct = 2;
					 pattern_3.set(2, 50);
				}
				
				break;
				
			case 4: //���� �߻�
				
				//���� ��ġ ��� ����
				boss_Point.x = 500; 
				boss_Point.y = 1500;
				
				pattern_4_Delay++;
				pattern_4_Delet_Delay++; //���� ���� �ð�
				if(pattern_4_Delay >= 30){
				//4�� �����϶� ���� �ؼ� �߰� 50�� �ϳ��� �߰�
				
				pattern_4 = new Pattern_4(1 + (int) (Math.random()*1000), this.boss_Point.y - 500 + (int) (Math.random()*300), 100); //�������� x ��ǥ ���� �ؾ���
				
				
				
				pattern_4_Delay = 0;
				
				pattern_4_List.add(pattern_4); //4�� ���� ���� ����
				System.out.println("a");
				}
				
				else if(pattern_4_List.size() > 0 && pattern_4_Delet_Delay >= 50){
					pattern_4_List.remove(0); //0��° ���� ���ʷ� ���� ���Ѿ���
					pattern_4_Delet_Delay = 0;
				}
				
				break;
				
		}
		
		

		//���� �Ӹ� ���� �ٸ� ����    //0=x ��ǥ, 1=y ��ǥ, 2=����, 3=����
				//�����ʺ���
				if(Direction){
				head_Info[0] = boss_Point.x + 15; 
				head_Info[1] = boss_Point.y + 25;
				head_Info[2] = 45;
				head_Info[3] = 30;
					
				body_Info[0] =  boss_Point.x + 10;
				body_Info[1] =  head_Info[1] + head_Info[3]; //������ �Ӹ� �ٷ� �Ʒ� �޸�
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
					body_Info[1] =  head_Info[1] + head_Info[3]; //������ �Ӹ� �ٷ� �Ʒ� �޸�
					body_Info[2] =  25;
					body_Info[3] =  70;
					
					leg_Info[0] =   boss_Point.x + 15;
					leg_Info[1] =   body_Info[1] + body_Info[3];
					leg_Info[2] =   50;
					leg_Info[3] =   40;
				}
				

		
		
		
	}
	
	//���� 3�� ���� ���� ��ȯ
	public Pattern_3 get_Boss_1_Patter3(){
		return pattern_3;
	}
	
	
	//���� 4�� ���� ����Ʈ ��ȯ
	public ArrayList<Pattern_4> get_Boss_1_Patter4(){
		return pattern_4_List;
	}
	
	//���� 4�� ���� ���� ������ Ŭ���� -> �ð��� ������ �ϳ��� �Ҹ��ؾ���
	public void set_get_Boss_1_Patter4_Clear(){
		pattern_4_List.clear();
	}
	
	
	//���� 3�� ���� ������
	int pattern_3_Delay = 0;
	
	//���� 4�� ���� ������
	int pattern_4_Delay = 0;
	
	//���� 4�� ���� ���� ���� �ð�
	int pattern_4_Delet_Delay;
	
	

	public void set_Hero_Point(int x, int y){
		//������ ��ǥ�� �����´�. ���� �ϱ� ���ؼ�
		hero_Point.x = x;
		hero_Point.y = y;
	}

	
	//���� �̹��� ����
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
	
	//���� 2�� ����
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
	
	

	//���� 4�� ���� ���� �̹��� ����
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
	
	
	
	int raser_StandBy = 0; //������ ���� ����
	int Image_Rotation_num_Pattern_3 = 1;//������ �̹���
	//������ 3�� ���� ���� ��ȯ
	public class Pattern_3{
		//����
		int magic_Direct = 0; //0�϶� �ȳ���, 1�϶� ������, 2�϶� ����
		//y�� ���� ũ��
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
		
		
		//���� 3�� ����
		int Image_Rotation_Delay_Pattern_3 = 0; 
		public int set_Image_Rotation_Pattern_3(){
			
			
			if(raser_StandBy <= 90){
				raser_StandBy++; //���� ����ɶ� �ʱ�ȭ �ȴ�.
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
		
		
		
		
		
		
		//������ ��� �̹��� ����
		public int raser_StandBy(){
			return raser_StandBy%9;
		}
		
	}
	
	//������ 4��° ���� ���� 
	public class Pattern_4{
		//���� ����
		int point_X; //��ġ ����
		int point_y;
		
		//���� ũ��
		int width;
		int hegith;
		int img_Color;
		
		//���� ���
		String shape;
		
		public Pattern_4(int x, int y, int img_Color){ //������ ��ġ ��ȯ
			this.point_X = x;
			this.point_y = y;
			width = 150;
			hegith = 1500;
			this.img_Color = (int) (1 + Math.random() * 3); //1�̸� ��� 2�� ���� 3�̸� �Ķ�
			if(this.img_Color == 1){
				shape = "Yellow";
			}else if(this.img_Color == 2){
				shape = "Red";
			}else if(this.img_Color == 3){
				shape = "Blue";
			}
		}
		
		int Image_Rotation_num_Pattern_4 = 0; //���� �̹��� ����
		//������ ��� ���� ���� ���� ������� ����
		int Image_Rotation_num_Pattern_4_Delay = 0;
		
		public int set_Image_Rotation_Pattern_4(){
			
			Image_Rotation_num_Pattern_4_Delay++;
			
			if(Image_Rotation_num_Pattern_4_Delay > 25){
			Image_Rotation_num_Pattern_4++; //�ϴ� 1
			if(Image_Rotation_num_Pattern_4 > 5){
				Image_Rotation_num_Pattern_4 = 1;
			}
			}
			return Image_Rotation_num_Pattern_4;
		}
		
		//���� �̹��� ���� �� �� ��
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


