package character;

public class Hero {
	//����� �⺻ ��ǥ����
	private int x_Point;
	private int y_Point;
	
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
	
	
	//ĳ���Ͱ� �����ִ� ���� �����϶� true ���� �϶� false
	boolean face_Side_LEFT_RIGHT;
	
	//ĳ���� ������ �߷°��ӵ�
	int g;
	int gSum;
	
	//����� �⺻ ������
	public Hero() {
		x_Point = 100;
		y_Point = 620;
		
		x_Flag_Left = false;
		x_Flag_Right = false;
		y_Flag_Left = false;
		y_Flag_Right = false;
		
		face_Side_LEFT_RIGHT = false; //ĳ���� ������ ���ÿ� ���� ����
		
		jump_Hero = false;//����Ű ������ ���� �ϴ� ��ɾ� ����
		jump_Hero_UP_DOWN = true; //�ö󰡴���
		jump_Hero_Stop_Point = true; //true �϶� �ѹ� ����
		
		g = 1;//�߷°��ӵ�
		gSum = 15;
	}
	
	public synchronized void move(){  //synchronized �ش� �Լ��� �۵��ϴ� ���� ����ȭ�� �����Ѵ�.
		//�¿� ������ ��Ʈ��
		if(x_Flag_Left){
			x_Point -= 5;
		}
		if(x_Flag_Right){
			x_Point += 5;
		}
	}
	
	//ĳ���� ���� ����
	public void jump_Move(int y_Stop){ //ĳ������ Y�� ���� �����ͼ� Y��� ���� �����ų� �۾��������� ���ҽ�Ű�� Y�� ���� ���� �Ǵ� �߰��� ���� ������ ���� ���� ������ ��ġ��Ų��.
		
		if(jump_Hero){
			//���� ���� �����ϱ�
			if(jump_Hero_Stop_Point){
				jump_Hero_Stop_Point_Y = y_Stop; //�� ó�� ĳ���Ͱ� ������ ������ Y��ǥ
				jump_Hero_Stop_Point = false;
			}
			
			//gSum�� Ư�� ���ڿ� ���������� ���� ����
			 if(gSum == 0){
				 jump_Hero_UP_DOWN = !jump_Hero_UP_DOWN;
			 }
			 
			 //�ö󰡴���
			 if(jump_Hero_UP_DOWN){
				 gSum -= g;
				 y_Point -= gSum;
			 }else{ //����������
				 gSum += g;
				 y_Point += gSum;
			 }
			 
			 //������ �����Ǿ�� �� ���� //������ �ٽ� �����ϵ��� �ʱ�ȭ
			 if(y_Point >= jump_Hero_Stop_Point_Y){			//�ʱⰪ���� ���� y �����Ͱ� ĳ���ͺ��� �Ʒ��̸� ����
				 y_Point = jump_Hero_Stop_Point_Y;		
				 jump_Hero = false;							//Jump_Move �żҵ� ������ �������� �Ѵ�. ���� ����
				 jump_Hero_Stop_Point = true;				//���� ������ �ٽ� ���� Y �����͸� ������ �ֵ��� �ʱ�ȭ
				 jump_Hero_UP_DOWN = !jump_Hero_UP_DOWN; 	//�߷� ���ӵ� ���� ���� �ϴ� �Ҹ�
				 g = 1;
				 gSum = 15;
			 }
			 
		}
	}
	
	//���� Ű�� �������� ���� �� �����ϵ��� ������
	public void set_Hero_Jumping(){
		if(!jump_Hero){ //�������϶��� ������ �Ұ�����
		jump_Hero = true;
		}
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
