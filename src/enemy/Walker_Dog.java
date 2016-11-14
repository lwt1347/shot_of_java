package enemy;

import character.Hero;

//��Ŀ Ŭ����
public class Walker_Dog extends Enemy{

	//lock_Trans = �¼� ��ȯ�� ���� �Ҹ� ����/��� ��
	private boolean lock_Trans;
	
	
	
	
	public Walker_Dog(int left_Site, int right_Site, int bottom_Site) {
		super(left_Site, right_Site, bottom_Site);
		// TODO Auto-generated constructor stub
		
		//�� ���� Ž�� �簢�� ���� ����
		range_Site_Width_Right_Point = 500;
		range_Site_Height_Bottom_Point = 60;
		
		//��Ŀ �ѷ�
		width = 150;
		height = 120;
		
		lock_Trans = true;
		
		//��Ŀ�� �⺻ ����� 50
		enemy_HP = 300;
		
	}
	
	
	
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@���� ���̵�
		//��� ���� �簢�� �� ����
		public int get_Range_Site_Width_Right_Point(){
			return range_Site_Width_Right_Point;
		}
		
		//��� ���� �簢�� �� ����
		public int get_Range_Site_Height(){
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
		
			//���Ͱ� ����� �i�� ���� ���������� Ž�� ������ ����ġ ��Ų��.
			public void init_Range_Site(){
				range_Site_Width_Right_Point = 500;
				range_Site_Height_Bottom_Point = 60;
				
				range_Site_Width_Left_Point = enemy_Point.x;
				range_Site_Height_Top_Point = enemy_Point.y;
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
						public void set_Range_Site_Height_Top_Point(int range_Site_Top_Height){
							this.range_Site_Height_Top_Point = range_Site_Top_Height;
						}
				

		
		
		//��谪 �ѹ��� ���� ��Ű������ �÷���
		boolean plusOnce = true;
						

		//������ �߰�������
		public void set_Find_Hero(int hero_X_Point){
			find_Hero = true;
			if(hero_X_Point > enemy_Point.x){
				move_Site = false;
			}else {
				move_Site = true;
			}
		}
		
		//������ ��������
		public void set_Not_Find_Hero(){
			find_Hero = false;
		}
		
		//������ ���� ����Ȯ��
		public boolean get_Find_Hero(){
			return find_Hero;
		}
		
		
		
		
					
					
		//�ǰ� ������ �ڷ� �з��� ȿ��
		public void knockback(boolean flag){
			//flag �� ture �̸� �������� ��ȯ
			if(flag){
				
				enemy_Point.x += 15;
				
			}else {
				
				enemy_Point.x -= 15;
				
			}
			//�˹���ġ���� �ٽñ� ��� �簢���� �׸���.
			//range_Site_Width_Left_Point = enemy_Point.x;
			//range_Site_Height_Top_Point = enemy_Point.y;
		}
		
		
		
		//������ 3~4 
		private int Walk=0;
		private int Walk_Delay = 0;
		private int Walk_Delay_Point = 20;//���� �߽߰� ������ ������
		private int walk_Image_Temp = 7; //�ȴ��߿� 6�� ���� ��� 10��
		//���������� ����
		public int set_Walk_Plus(){
			if(Walk_Delay%Walk_Delay_Point==0){
			Walk++;
			Walk_Delay = 0;
			}
			Walk_Delay++;
			
			if(Walk >= walk_Image_Temp){ //�Ͼ� �������� �ɾ������� ������ ������ �ٸ���.
				Walk = 1;
			}
			
			return Walk;
		}
		
		//���������� �����̴������� üũ�Ѵ�
		public boolean get_Right_Flag(){ //false �϶� ������
			return move_Site;
		}
		
		
		
		
		public synchronized void enemy_Move(){ //���� ��� ������ �ٴ� ������ ���� ���� �����ͼ� ����Ѵ�.
			
			//�������� �߰����� �������� �⺻ ��� ������ ���ƴٴѴ�.
			if(!find_Hero){
				Walk_Delay_Point = 20;
				walk_Image_Temp = 7; //�ȴ� ���
				
					if(move_Site){	//�������� �̵�
						enemy_Point.x -= 2;
					}else {			//�������� �̵�
						enemy_Point.x += 2;
					}
					
					//�¿�� ������ ���� ���� ������ ������ �߰� �������� ��� ������ �� ��ü�� ��´�.
					if((left_Bound_Site - 10) >= enemy_Point.x){
						move_Site = false;
					}else if((right_Bound_Site - 20) <= enemy_Point.x){
						move_Site = true;
					}
					
					if(!lock_Trans){
						init_Range_Site(); //�� ���� ���� ���°� �ƴҶ� ���� ����
						lock_Trans = true; //�¼� ��ȯ�� ���� �Ҹ�
					}
					
					//Ž�� ������ ��� �ؼ� ����ȭ ���� �־���Ѵ�.
					
					range_Site_Width_Left_Point = enemy_Point.x + width/2; //+width/2�� ��Ŀ�� �β��� �� = ������� Ȯ�ǽ� �ϱ� ���ؼ�
					//range_Site_Height_Top_Point = enemy_Point.y;
				
					
			}else { //�� ������ �߰��ϰ� �Ǹ� �� ������ �߰��Ѵ�.
				Walk_Delay_Point = 10;
				//������ �߰��ؾ��Ѵ� ���� ��ȯ 
				range_Site_Width_Left_Point = enemy_Point.x + width/2;  //+width/2�� ��Ŀ�� �β��� �� = ������� Ȯ�ǽ� �ϱ� ���ؼ�
				
				walk_Image_Temp = 6; //���ݸ��
				
				
				
				range_Site_Height_Top_Point = enemy_Point.y-100;	//���� 
				range_Site_Height_Bottom_Point = 250; //���� 600- 450  500 - 450 enemy_Point.y - 450
				//System.out.println(range_Site_Height_Bottom_Point);
				//Ž�� ������ ��� �ؼ� ����ȭ ���� �־���Ѵ�.
				//range_Site_Width_Left_Point = enemy_Point.x;
				
				//�¼� ��ȯ�� �ʱ�ȭ �ѹ�
				if(lock_Trans){
					set_Range_Site_Width_Right_Point(700); //���� �¼��϶� ��谪 ������ �þ��.
					
					//set_Range_Site_Height(100); //����
					lock_Trans = false;
				}
					//�̵� �ӵ� ����
					if(move_Site){	//�������� �̵�
						if(hero.get_Hero_X_Point()+30 <= enemy_Point.x){ //�������� ĳ���� ���� ���� ���� ���ϵ��� = ĳ���͸� �Ѿ� ������
						enemy_Point.x -= 10;
						}
					}else {			//�������� �̵�
						if(hero.get_Hero_X_Point()-30 >= enemy_Point.x){
						enemy_Point.x += 10;
						}
					}
					
					//�¿�� ������ ���� ���� ������ ���ݹ��������� ������ ���������
					if((left_Bound_Site) >= enemy_Point.x + 30){
						//move_Site = false; //������
						enemy_Point.x -= 10; //���� �ڵ�� �ϸ� ������ �ٲ��.
					}else if((right_Bound_Site) <= enemy_Point.x){
						//move_Site = true;//����
						enemy_Point.x += 10;
					}
					
				
				
			}
		}
}











