package character;

import javax.swing.JPanel;

public class Hero extends JPanel{
	//����� �⺻ ��ǥ����
	private int x_Point;
	private int y_Point;
	
	//������� �β�
	private int hero_Height;
	private int hero_Width;
	

	//����� ����(�ε巯�� ������ ǥ��)
	boolean x_Flag_Left;
	boolean y_Flag_Left;
	boolean x_Flag_Right;
	boolean y_Flag_Right;
	
	//������
	boolean jump_Hero;
	//������ �ö󰡴������� ���� �ɴ� �� ���� Ȯ�� true �̸� ������
	boolean jump_Hero_UP_DOWN;
	//���� ���۰� �Բ� �⺻ ĳ������ �������� Y�� üũ�ϱ����� �÷���
	boolean jump_Hero_Stop_Point;
	//���������� y��ǥ�� ������ ����
	int jump_Hero_Stop_Point_Y;
	
	//���ΰ� �ɱ� ���� �÷��� true = ��������
	private boolean set_Hero_Sit_Stand;
	
	//���ΰ� ���¿� ���� �ӵ� ��ȭ ex) �ɱ� / ����
	private int hero_Speed;
	
	//ĳ���Ͱ� �����ִ� ���� �����϶� true ���� �϶� false
	boolean face_Side_LEFT_RIGHT;
	
	//ĳ���� ������ �߷°��ӵ�
	int g;
	int gSum;
	int dgSum;
	
	
	//���� �����ٸ� ���� �����ٸ� �� ������� �����ؾ� �ٽ� ��� �ֵ��� ����
	int jump_Time_Schedule;
	
	
	//���� ���� ���� ��� boolean
	private boolean view_Temp = false;
	//���� ���� �ܰ躯ȭ 9�ܰ� ����
	private int view_Temp_Int=0;
	
	

	
	
	
	//����� �⺻ ������
	public Hero() {
		x_Point = 100;
		y_Point = 1700;
		
		//����� ��
		hero_Width = 30;
		hero_Height = 45;
		
		x_Flag_Left = false;
		x_Flag_Right = false;
		y_Flag_Left = false;
		y_Flag_Right = false;
		
		face_Side_LEFT_RIGHT = false; //ĳ���� ������ ���ÿ� ���� ����
		
		jump_Hero = false;//����Ű ������ ���� �ϴ� ��ɾ� ����
		jump_Hero_UP_DOWN = true; //�ö󰡴���
		jump_Hero_Stop_Point = true; //true �϶� �ѹ� ����
		
		//���� ���ֱ�
		set_Hero_Sit_Stand = true;
		
		g = 1;//�߷°��ӵ�
		gSum = 0;
		dgSum = 0;//���� ���ӵ�
		
		//������ �⺻�ӵ� = 5
		hero_Speed = 5;
		
		//���� �����ٸ� 0 ���� �ʱ�ȭ
		jump_Time_Schedule = 0;
	}
	
	//����� �� ��ȯ
	public int get_Hero_Width(){
		return hero_Width; 
	}
	//����� ���� ��ȯ
	public int get_Hero_Height(){
		return hero_Height;
	}
	
	//����� �˹� ������ ������ �ǰ�������. �������� �˹�
	public void left_Knock_Back(){
		x_Point -= 5;
	}
	//�������� �˹�
	public void right_Knock_Back(){
		x_Point += 5;
	}
	
	public synchronized void move(){  //synchronized �ش� �Լ��� �۵��ϴ� ���� ����ȭ�� �����Ѵ�.
		//�¿� ������ ��Ʈ��
		if(x_Flag_Left){
			x_Point -= hero_Speed;
		}
		if(x_Flag_Right){
			x_Point += hero_Speed;
		}
	}
	
	
	
	//�� �߻� �̹��� ���� ���� ���� hero �� �ִ� ������ �� �߻� �̺�Ʈ�� ĳ���Ͱ� �ϴ� ���̱⶧����
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
	
	
	//���������� ������ 3~4 
	private int right_Walk=0;
	private int right_Walk_Delay = 0;
	private int walk_Image_Temp = 13; //�Ͼ������ �ȴ� ����� 13�� �ɾ������� 6��
	//���������� ����
	public int set_Right_Walk_Plus(){
		if(right_Walk_Delay%2==0){
		right_Walk++;
		right_Walk_Delay = 0;
		}
		right_Walk_Delay++;
		
		if(right_Walk >= walk_Image_Temp){ //�Ͼ� �������� �ɾ������� ������ ������ �ٸ���.
			right_Walk = 1;
		}
		
		return right_Walk;
	}
	
	//���������� �����̴������� üũ�Ѵ�
	public boolean get_X_Flag_Right(){
		return x_Flag_Right;
	}
	
	//�������� ������ 3~4 
		private int left_Walk=0;
		private int left_Walk_Delay = 0;
		//�������� ����
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
		
		//�������� �����̴������� üũ�Ѵ�
		public boolean get_X_Flag_Left(){
			return x_Flag_Left;
		}
	
	
	
	
	//ĳ���� ���� ����
	public void jump_Move(){ //ĳ������ Y�� ���� �����ͼ� Y��� ���� �����ų� �۾��������� ���ҽ�Ű�� Y�� ���� ���� �Ǵ� �߰��� ���� ������ ���� ���� ������ ��ġ��Ų��.
		
		//jump_Time_Schedule �� ���� �� �̻��϶� ���� ����
		jump_Time_Schedule++;
		
		
		if(jump_Hero){
			//���� ���� �����ϱ�
			if(jump_Hero_Stop_Point){
				jump_Hero_Stop_Point = false;
			}
			
			//gSum�� Ư�� ���ڿ� ���������� ���� ����
			 if(gSum == 0){
				 jump_Hero_UP_DOWN = !jump_Hero_UP_DOWN;
			 }
			 
			 //���� ���� ����
			 if(gSum >= 15){
				 gSum = 15;
			 }
			 
			 //�ö󰡴���
			 if(jump_Hero_UP_DOWN){
				 gSum -= g;
				 y_Point -= gSum;
			 }else{ //����������
				 gSum += g;
				 y_Point += gSum;
			 }
			 
		}
	}
	
	
	//���� ���� ��ȯ
	public int get_View_Temp_Int_Plus(){
		if(view_Temp_Int < 5 ){ //1���� ~ 4����
			view_Temp_Int++; 
		}
		return view_Temp_Int;
	}
	//���� ���� ��ȯ
	public int get_View_Temp_Int_Minus(){
		if(view_Temp_Int > 1 ){ //4���� ~ 1����
			view_Temp_Int--;
		}
		return view_Temp_Int;
	}
	
	
	
		
	
	
	
	//������ ���� ������ �����ش�.
	public void jump_Move_Stop(int y_Stop){
		jump_Hero_Stop_Point_Y = y_Stop; //�� ó�� ĳ���Ͱ� ������ ������ Y��ǥ
		//������ �����Ǿ�� �� ���� //������ �ٽ� �����ϵ��� �ʱ�ȭ
		 if(y_Point >= jump_Hero_Stop_Point_Y){			//�ʱⰪ���� ���� y �����Ͱ� ĳ���ͺ��� �Ʒ��̸� ����
			 y_Point = jump_Hero_Stop_Point_Y;		
			 jump_Hero = false;							//Jump_Move �żҵ� ������ �������� �Ѵ�. ���� ����
			 jump_Hero_Stop_Point = true;				//���� ������ �ٽ� ���� Y �����͸� ������ �ֵ��� �ʱ�ȭ
			 jump_Hero_UP_DOWN = true; 	//�߷� ���ӵ� ���� ���� �ϴ� �Ҹ�
			 g = 1;
			 gSum = 15;
		 }
	}
	
	//���� ��� ���� ������ ������ �������� �Ѵ�.
	public void auto_Jump_Down(){
		
		//�����ϴ� ������ �ƴϸ鼭 ĳ���Ͱ� ���� �´������ ������
		if(!jump_Hero){ //jump_Hero = ���� ���϶�
			if(dgSum>=15)dgSum = 15;
			
			 y_Point += dgSum;
			 dgSum += g;
			 //System.out.println(dgSum);
			 jump_Hero_UP_DOWN = false; //���� �����ʰ� �������� �߿��� ������ �� �ֵ��� ��
		}else dgSum = 0;
	}
	
	//�Ӹ��� ���������� �Ʒ��� ����߸���.
	public void auto_Jump_Down_Head(int minus_Num){
		 y_Point += minus_Num+1;
	}
	
	//�������� ���鰡����
	public void stop_Move_Right(int x_Temp){
		//x_Point = x_Temp;
		//���� �� �� �ε����� ���������� ���� �Ұ���
		x_Flag_Right = false;
		//�������� -5 ��ŭ�� �о���� ������ �ȵ���
		x_Point -= 6;
		//y_Point �� �ణ�� ���� ������ �߰��Ͽ���, ������ �ε����� ���� ���� �� �ֱ⶧���� �̼��ϰ� �����̵��� ��
		//y_Point += 5;
		
	}
	
	//�������� ���鰡����
		public void stop_Move_Leftt(int y_Temp){
			//x_Point = x_Temp;
			//���� �� �� �ε����� ���������� ���� �Ұ���
			x_Flag_Left = false;
			//���������� + 5 ��ŭ�� �о���� ������ �ȵ���
			x_Point += 6;
			//y_Point �� �ణ�� ���� ������ �߰��Ͽ���, ������ �ε����� ���� ���� �� �ֱ⶧���� �̼��ϰ� �����̵��� ��
			//y_Point -= 5;
		}
		
	
	//ĳ���Ͱ� ������ �����ʰ� ���������� �߷� ���ӵ��� �ʱ�ȭ �Ѵ�.
	public void set_dgSum_Zero(){
		dgSum=0;
	}
	
	//���ϰ� �浹�� �ٷ� �Ʒ��� ���������� �ϴ� �Լ�
	public void set_Jump_Hero_UP_DOWN(){
		dgSum=0;
		jump_Hero_UP_DOWN = false;
	}
	
	
	//���� Ű�� �������� ���� �� �����ϵ��� ������
	public void set_Hero_Jumping(){
		
		
		if(jump_Time_Schedule >= 15){	
			if(!jump_Hero){ //�������϶��� ������ �Ұ�����
			jump_Hero = true;
			//���� ������ �ʱ�ȭ
			jump_Time_Schedule = 0;
			}		
		}
	}
	
	//ĳ���Ͱ� ���� ������ �ƴ��� ��ȯ�Ѵ�
	public boolean get_Jump_State(){
		return jump_Hero_UP_DOWN; //true = �ö󰡴���
	}
	
	//�������϶�
	public boolean get_Jump_Hero(){ //�������϶� true
		return jump_Hero;
	}
	
	
	//���ΰ� x �� ����
	public int get_Hero_X_Point(){
		return x_Point;
	}
	//���ΰ� y �� ����
	public int get_Hero_Y_Point(){
		return y_Point;
	}
	
	//ĳ���Ͱ� ó�ٺ��� �ִ� ���� ����
	public boolean get_Face_Side_LFET_RIGHT(){
		return face_Side_LEFT_RIGHT;
	}
	
	//���ΰ� ������ �����ϸ� ������� ������Ų��
	public void set_Hero_Y_Point(int set_Y_Point){
		y_Point = set_Y_Point;
	}
	
	
	
	//���ΰ� ����/�ɱ�
	public void set_Hero_Sit(){ //���ΰ� �ɱ�
		if(set_Hero_Sit_Stand){
		hero_Height = 25;
		y_Point += 20;
		set_Hero_Sit_Stand = false;
		hero_Speed = 2; //���� �̼� ����
		walk_Image_Temp = 6; //�ɾ������� ���� 6��
		}
	}
	public void set_Hero_Stand(){ //���ΰ� ����
		set_Hero_Sit_Stand = true;
		y_Point -= 20;
		hero_Height = 45;
		hero_Speed = 5; //���� �̼� ����
		walk_Image_Temp = 13;
	}
	
	//���ΰ� �ɾ������� ���� ��ȯ
	public boolean get_Sit_State(){
		return set_Hero_Sit_Stand;
	}
	
	
	//���ΰ� �����̵�
	public void set_Hero_X_Left(){
		x_Flag_Left = true;
		face_Side_LEFT_RIGHT = true; //���� ����
	}
	
	//���ΰ� ���� �̵�
	public void set_Hero_X_Right(){
		x_Flag_Right = true;
		face_Side_LEFT_RIGHT = false; //���� ����
	}
	//���ΰ� ����
	public void set_Hero_X_Left_Stop(){
		x_Flag_Left = false;
	}
	//���ΰ� ����
	public void set_Hero_X_Right_Stop(){
		x_Flag_Right = false;
	}
	
	
	
}
