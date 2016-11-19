package enemy;

import java.awt.Point;

public class Stage_1_Boss extends Thread{

	//������ �⺻ ��ġ
	Point boss_Point;
	Point hero_Point;
	
	//������ ũ��
	int width;
	int height;
	
	//���� ����
	int pattern = 1;
	
	int pattern_Change_Time = 1;
	
	//3�� ���� ��ȯ
	Pattern_3 pattern_3;
	
	
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
		
		start();
	}
	
	//������ �޷��� ����
	boolean Direction; //������ ���� ���� ���ʿ� ������ true
	boolean boss_Move_Direction;
	boolean boss_Move_Direction_Flag = true; //�����Ҷ� ���� ��ȭ ���� �ʵ���
	boolean boss_Move_Direction_Pattern_3 = true; //���� ���� �߻��� ����
	public void boss_Move(){
		
		//�������� ��ȭ �ð�
		//���� ��ȭ �ð�
		pattern_Change_Time++;
		if(pattern_Change_Time >= 300){
			pattern_Change_Time = 0;
			raser_StandBy = 0;//������ ���Ĺ��� ���� Ŭ���� ���� �����ϴ� ���� �ʱ�ȭ ��Ų��.
			Image_Rotation_num_Pattern_3 = 0; //���������� ������ ���Ĺ��� ǥ��
			
			
			if(!(boss_Point.y+20 > hero_Point.y && (boss_Point.y + height - 20) < hero_Point.y)){ //���� ���� ������ ���� ��������� ������ y�� �̵���
				boss_Point.y = hero_Point.y - 100;//���� y�� �̵�
				//boss_Point.x = 100; //x �� �ʱ�ȭ x �� �������� ������ �������� ������ �������� �����Ѵ�.
			
			}
			
			//���� ���ϴ� ���� ������ ���� ���� ���ʿ� ������ true
			if(boss_Point.x <= hero_Point.x){
				Direction = true;
			}else {
				Direction = false;
			}
			
			pattern = 3; //������ �Է�
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
		}
		
	}
	
	//���� 3�� ���� ���� ��ȯ
	public Pattern_3 get_Boss_1_Patter3(){
		return pattern_3;
	}
	
	//���� 3�� ���� ������
	int pattern_3_Delay = 0;
	
	
	
	//���� ���� ��ȯ
	public int get_Boss_pattern(){
		return pattern;
	}
	
	
	//������ ���� ����
	public boolean get_Boss_Move_Direction(){
		return boss_Move_Direction;
	}
	
	//������ �����̴� ���� ��ȯ
	public boolean get_Boss_Move_Direction_Flag(){
		return boss_Move_Direction_Flag;
	}
	
	
	//������ ��ǥ ��������
	public Point get_Boss_Point(){
		return boss_Point;
	}
	//���� ���� ��������
	public int get_Boss_Width(){
		return width;
	}
	//���� ���� ��������
	public int get_Boss_Height(){
		return height;
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
	
	
	public void set_Hero_Point(int x, int y){
		//������ ��ǥ�� �����´�. ���� �ϱ� ���ؼ�
		hero_Point.x = x;
		hero_Point.y = y;
	}

	public void run(){
		while(true){
			
			try {
				//���� ������
				Thread.sleep(20);
				
			
				boss_Move();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
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
		
		
		//���� 2�� ����
		
		
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
		
		
		
	}

}


