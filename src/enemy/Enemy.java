package enemy;

import java.awt.Point;

import javax.swing.RepaintManager;

import character.Hero;

//���� �⺻ Ŭ����
public class Enemy extends Thread{ //�����带 ��ӹ޾Ƽ� ������ ���� �˾Ƽ� �����̵��� �Ѵ�.
	
	
	
	//������ �⺻ ��ǥ����
	protected Point enemy_Point;
	
	//������ ���� �¿� ����
	protected boolean move_Site; //true �̸� ���� false�̸� ����
	protected double randomValue; //���� �� ����
	
	//��� ���� ����
	protected int left_Bound_Site;
	protected int right_Bound_Site;
	protected int bottom_Bound_Site;
	
	
	//���� �߰� ���������� false, �߰��������� true
	protected boolean find_Hero;
	
	//�� ���� Ž�� �簢�� ���� �� ����
	protected int range_Site_Width_Left_Point;
	protected int range_Site_Height_Top_Point;
	
	
	//�� ���� Ž�� �簢�� ������ �� �Ʒ��� ����
	protected int range_Site_Width_Right_Point;
	protected int range_Site_Height_Bottom_Point;
	
	//������ ���� �β� �� ���εβ�
	protected int width;
	protected int height;
	
	//������ �����
	protected int enemy_HP;
	
	
	
	//������ ����
	Thread th;
	
	//������ ������� ��ġ ������ ������ �˾ƾ� �Ѵ�.
	protected Hero hero;
	public void set_Hero_Information(Hero hero){
		this.hero = hero;
	}
	
	public Enemy(int left_Bound_Site, int right_Bound_Site, int bottom_Bound_Site) { //������ �� ��ǥ�� �޾ƿ;���
		
		//��豸�� ���� �ʱ� ����
		init_Bound_Site(left_Bound_Site, right_Bound_Site, bottom_Bound_Site);
		
		//�� ���� Ž�� ���� �Ʒ��� �簢�� ���� ���� 
		range_Site_Width_Right_Point = 300;
		range_Site_Height_Bottom_Point = 50;
		
		//�ϴ� �⺻������ 100,600 �� ����
		enemy_Point = new Point(200, bottom_Bound_Site);
		
		
		//�� ���� Ž�� �簢�� ���� ���� ��ġ�� ����
		range_Site_Width_Left_Point = enemy_Point.x;
		range_Site_Height_Top_Point = enemy_Point.y;
		
		
		randomValue = Math.random();
		if(randomValue < 0.5){
			move_Site = true;	//�������� �̵�
			
		}else {
			move_Site = false; //�������� �̵�
			
		}
		
		find_Hero = false; //�������� �߰������� true �� ����
		
		th = new Thread(this); 	  //������ ����
		th.start(); 		  //������ ����
	}
	
	//���Ͱ� ����� �i�� ���� ���������� Ž�� ������ ����ġ ��Ų��.
	public void init_Range_Site(){
		//range_Site_Width_Right_Point = 300;
		//range_Site_Height_Bottom_Point = 50;
		
		//range_Site_Width_Left_Point = enemy_Point.x;
		//range_Site_Height_Top_Point = enemy_Point.y;
	}
	
	
	public void init_Bound_Site(int left_Bound_Site, int right_Bound_Site, int bottom_Bound_Site){ 
		//���Ͱ� ƨ�� �����ų� ���ΰ� ĳ���� �Ѵٰ� �ٸ� �ٴ����� ������ �ѹ��� �ʱ�ȭ �ؾ��Ѵ�.
		this.left_Bound_Site = left_Bound_Site;
		this.right_Bound_Site = right_Bound_Site;
		this.bottom_Bound_Site = bottom_Bound_Site;
	}
	
	//������ �߰�������
	public void set_Find_Hero(int hero_X_Point){
		find_Hero = true;
	}
	//������ ��������
	public void set_Not_Find_Hero(){
		find_Hero = false;
	}
	
	
	//�⺻ ������ �߰� �������� �¿�� ��� ���� �����̰� �־���� //synchronized �ش� �Լ��� �۵��ϴ� ���� ����ȭ�� �����Ѵ�.
	public synchronized void enemy_Move(){ //���� ��� ������ �ٴ� ������ ���� ���� �����ͼ� ����Ѵ�.
		
		//�������� �߰����� �������� �⺻ ��� ������ ���ƴٴѴ�.
		if(!find_Hero){
				if(move_Site){	//�������� �̵�
					enemy_Point.x -= 5;
				}else {			//�������� �̵�
					enemy_Point.x += 5;
				}
				
				//�¿�� ������ ���� ����
				if((left_Bound_Site+100) >= enemy_Point.x){
					move_Site = false;
					
				}else if((right_Bound_Site-100) <= enemy_Point.x){
					move_Site = true;
					
				}
				
		}else { //�� ������ �߰��ϰ� �Ǹ� �� ������ �߰��Ѵ�.
			
		}
		
		
	}
	
	//��� ���� �簢�� �� ����
	public int get_Range_Site_Width_Right_Point(){
		return range_Site_Width_Right_Point;
	}
	
	//��� ���� �簢�� �� ����
	public int get_range_Site_Height_Bottom_Point(){
		return range_Site_Height_Bottom_Point;
	}
	
	//��� ���� �簢�� �� �� ������Ų��. ������ �߰������� ������ ������Ų��.
	public void set_Range_Site_Width_Right_Point(int range_Site_Width_Right_Point){
		this.range_Site_Width_Right_Point = range_Site_Width_Right_Point;
	}
	
	//��� ���� �簢�� �� �� ������Ų��. ������ �߰������� ������ ������Ų��.
	public void set_Range_Site_Height_Bottom_Point(int range_Site_Height_Bottom_Point){
		this.range_Site_Height_Bottom_Point = range_Site_Height_Bottom_Point;
	}
	
			
				//��� ���� �簢�� ���� ��� �� ��ȯ
				public int get_Range_Site_Width_Left_Point(){
					return range_Site_Width_Left_Point;
				}
				
				//��� ���� �簢�� �� ����
				public int get_range_Site_Height_Top_Point(){
					return range_Site_Height_Top_Point;
				}
				
				//��� ���� �簢�� �� �� ������Ų��. ������ �߰������� ������ ������Ų��.
				public void set_Range_Site_Width_Left_Point(int range_Site_Width_Left_Point){
					this.range_Site_Width_Left_Point = range_Site_Width_Left_Point;
				}
				
				//��� ���� �簢�� �� �� ������Ų��. ������ �߰������� ������ ������Ų��.
				public void set_Range_Site_Height_Up_Point(int range_Site_Top_Height){
					this.range_Site_Height_Top_Point = range_Site_Top_Height;
				}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		
	//���� ���� ���� ��ȯ
	public int get_Enemy_Width(){
		return width;
	}
	
	public int get_Enemy_Height(){
		return height;
	}
	
	//���� ���� �ִ� ���� ���� 
	public boolean get_Move_Site(){ //true = ����
		return move_Site;
	}
	
	
	
	//����� ��������ġ�� �����Ѵ�.
	public Point get_enemy_Point(){
		return enemy_Point;
	}
	
	
	//���� ������ ������
	public void run() {
		try{
			while(true){
				
				//�ڹ� ��Ƽ ������ ������ �������� ���⼭
				enemy_Move();
				Thread.sleep(20); //20milli sec �� ������ ������
				
			}
		}catch (Exception e) {
			
		}
	}
	
	//�������� ���� ���� ��ȯ
	public void set_Move_Site(boolean flag){
		//flag �� ture �̸� �������� ��ȯ
		if(flag){
			move_Site = true;
			
		}else {
			
			move_Site = false;
		}
	}
	
	//�ǰ� ������ �ڷ� �з��� ȿ��
			public void knockback(boolean flag){
				//flag �� ture �̸� �������� ��ȯ
				if(flag){
					enemy_Point.x += 15;
				}else {
					enemy_Point.x -= 15;
				}
			}
	
	//������ �ǰ� �پ��
	public void enemy_HP_Down(int gun_Damage){
		enemy_HP--;
		//System.out.println(enemy_HP);
	}
	
	//���� ������ üũ�ϱ�
	public int get_Enemy_HP(){
		return enemy_HP;
	}
			
			
			
}
