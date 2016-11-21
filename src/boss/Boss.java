package boss;

import java.awt.Point;

public class Boss extends Thread{

	//������ �⺻ ��ġ
		protected Point boss_Point;
		protected Point hero_Point;
		
		
		//���� 1�� ũ�� �� 1������ 
		protected int width;
		protected int height;
		
		//���� �� ũ�� ����� 3�� 
		protected int head_Width;
		protected int head_Height;
		int[] head_Info = new int[4]; //0=x ��ǥ, 1=y ��ǥ, 2=����, 3=����
		
		//������ �� ũ�� ����� 2��
		protected int body_Width;
		protected int body_Height;
		int[] body_Info = new int[4]; //0=x ��ǥ, 1=y ��ǥ, 2=����, 3=����
		
		//���� �ٸ� ũ�� ����� 1��
		protected int leg_Width;
		protected int leg_Height;
		int[] leg_Info = new int[4]; //0=x ��ǥ, 1=y ��ǥ, 2=����, 3=����
		
		//���� Hp
		protected int HP;
	
		//������ �޷��� ����
		boolean Direction; //������ ���� ���� ���ʿ� ������ true
		boolean boss_Move_Direction;
		boolean boss_Move_Direction_Flag = true; //�����Ҷ� ���� ��ȭ ���� �ʵ���
		boolean boss_Move_Direction_Pattern_3 = true; //���� ���� �߻��� ����
		
	//�������̵� �Լ�
	public void boss_Move(){
		
	}
	
		//���� ����
		int pattern = 1;
		int pattern_Change_Time = 1;
		
		//���� ���� ��ȯ
		public int get_Boss_pattern(){
			return pattern;
		}
		//������ ���� ����
		public boolean get_Boss_Move_Direction(){
			return boss_Move_Direction;
		}
		
		//������ ������ �ٶ�
		public boolean get_Boss_Hero_Look(){
			return Direction;
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

		//���� �Ӹ� ũ��� ��ġ ��������
		public int[] get_Head_Info(){
			
			return head_Info; 
		}
		//���� ������ ũ��� ��ġ ��������
		public int[] get_Body_Info(){
			
			return body_Info;
		}
		
		//���� �ٸ��� ũ��� ��ġ ��������
		public int[] get_Leg_Info(){
			return leg_Info;
		}
		
	//�Ӹ� ���⶧
	public void set_Hp_Minus_Head(){
		HP-=3; 
	}
	//�Ӹ� ���⶧
	public void set_Hp_Minus_Body(){
		HP-=2; 
	}
	//�ٸ� ���⶧
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
				//���� ������
				Thread.sleep(20);
				
			
				boss_Move();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	
}
