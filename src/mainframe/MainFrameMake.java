package mainframe;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import character.Hero;
import enemy.Enemy;
import enemy.Walker;
import mapData.Block;
import mapData.Stage;
import weapon.Pistol;
import weapon.Weapon;

//���� ȭ�� ��������� Ŭ����
class MainFrameMake extends JFrame implements KeyListener, Runnable{
	
	//ȭ�� ũ��
	int width = 700;
	int height = 700;
	
	//���� ���� ��������
	int stage_Num = 0;
	
	//�̹����� �ҷ����� ���� ��Ŷ
	Toolkit tk = Toolkit.getDefaultToolkit();
	Image hero_Png;
	Image bullet_Png;
	Image walker_Png;
	
	//���� ���۸��� �̹���
	Image buffImage;
	Graphics buffg;
	
	
	//������ ����
	Thread th;
	
	
	//����� ����
	Hero mainCh;
	
	//�⺻ ���� ����
	Enemy enemy;
	
	//���������� �����Ѵ�
	Stage stage;
	
	//���� ���������� �Ѿ� �� ���ΰ�.
	boolean end_Stage;
	
	//�������ΰ� �������� �ƴѰ�
	boolean attack;
	//���� ��ü �� �� ��ȣ
	int weapon_Number;
	
	//������ ������ ���ΰ� ? true �̸� �����Ѵ�.
	boolean jump;
	
	
	//���� �⺻������
	Weapon weapon; 
	Point pistol_Point;
	
	//�ټ��� ����(��)�� ���� ��� ����Ʈ
	ArrayList bullet_List = new ArrayList<Weapon>(); 
	
	//�ټ��� ������ ���� ��� ����Ʈ
	ArrayList enemy_List = new ArrayList<Enemy>();
	
	
	
	public MainFrameMake() {
		
		
		
		init();
		
		setTitle("Shot");		//�������� �̸��� ����
		setSize(width, height); //�������� ũ��
		
		//�������� �����쿡 ǥ�ñ��� ��ġ�� �����ϱ� ����.
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		//�������� ����� ȭ�� ���߾ӿ� ��ġ ��Ű�� ���� ��ǥ ���� ���.
		int focus_X = (int)(screen.getWidth() / 2 -width / 2);
		int focus_Y = (int)(screen.getHeight() / 2 -height / 2);
		
		setLocation(focus_X, focus_Y); //�������� ȭ�鿡 ��ġ
		setResizable(false);		   //�������� ũ�⸦ ���Ƿ� ������ϵ��� ����
		setVisible(true);			   //�������� ���� ���̰� ���	
		
		
	}
	
	private void init(){
		//���α׷��� ���������� �����ϵ��� ����� �ݴϴ�.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//���ΰ��� �⺻ �̹��� ����
		hero_Png = tk.getImage("img/hero_1.png");
		bullet_Png = tk.getImage("img/Bullet_img.png");
		
		//���� �̹���
		walker_Png = tk.getImage("img/walker_img.png");
		
		//���ΰ� ����
		mainCh = new Hero();
		
		
		//�������� true �� �Ǹ� ���� ���������� �Ѿ
		end_Stage = true;
		
		addKeyListener(this); //Ű���� �̺�Ʈ ����
		th = new Thread(this); 	  //������ ����
		th.start(); 		  //������ ����
		
		
		//�������� 1 ����
		stage = new Stage();
		
		
		attack = false; //���� ���� ���� 
		weapon_Number = 1;//������ ����, �⺻ 1 �ǽ���
		
		jump = false; //���� ���¼���
		
	}
	
	
	public void paint(Graphics g){
		//������۸� ���� ũ�⸦ ȭ�� ũ��� ���� ����
		buffImage = createImage(width,height);
		//������ �׷��� ��ü ���
		buffg = buffImage.getGraphics();
		
		update(g);
		
		
	}
	
	public void update(Graphics g){
		//������ �׷��� �׸��� �����´�.
		draw();
		
		//�Ѿ��� �׸���.
		draw_Bullet();
		
		//������ �׸���.
		draw_Enemy();
		
		//���������� �׸���.
		draw_Stage();
		
		//ȭ�鿡 ���ۿ� �׸� �׸��� ������ �׸���
		g.drawImage(buffImage, 0, 0, this);
	}
	
	//������ �׸����� �׸� �κ�
	public void draw(){
		//(0,0) ~ (width,height) ���� ȭ���� �����
		buffg.clearRect(0, 0, width, height);
		//�����ӿ� ����� .png �̹����� �׷��ֽ��ϴ�.
		//buffg.drawImage(hero_Png, mainCh.get_Hero_X_Point(), mainCh.get_Hero_Y_Point(), this);
		buffg.drawRect(mainCh.get_Hero_X_Point(),   mainCh.get_Hero_Y_Point(), 30,  45); //�簢������ �ϴ� ��ü
	}
	
	//�������� �� �� �׸� (���)
	public void draw_Stage(){
		
		//���� ���������� �Ѿ
		if(end_Stage){
			stage_Num++; //���� ���������� �Ѿ //�� ��ġ �ʱ�ȭ 1��������
			
			//���������ѹ��� �ѹ� �ݿ��ؼ� ���������� �����.
			stage.map_Stage(stage_Num);

			//�⺻ ���� ��Ŀ ����
			enemy_Process(stage_Num);

			end_Stage = false;
			
		}
		
		//������ ���������� ����� �׷����� 
		int temp = 0;
		for(int i=0; i<stage.get_Block().size(); i++){
			buffg.drawRect(stage.get_Block().get(i).get_Left_Top_Point().x,
					stage.get_Block().get(i).get_Left_Top_Point().y,
					stage.get_Block().get(i).get_Widht(), 
					stage.get_Block().get(i).get_Height());
			
		
			//�浹 �Լ� ȣ�� 1�̸� ���� ĳ����
			crash_Decide_Block(mainCh, stage.get_Block().get(i));
		
			//ĳ���Ͱ� ��� ������ ��� ���� ������ �߶����̴�.
			if(!stage.get_Block().get(i).get_Set_Contect()){
				temp++;
			}
			//���� ���ڸ�ŭ false �̸� ������ ��� �������� 
			if(temp == stage.get_Block().size()){
				mainCh.auto_Jump_Down();
			}
		
		}
		
	
		
		
	}
	
	
	//�Ѿ��� �׸��� �Լ�
	public void draw_Bullet(){
		//�Ѿ� ���� ��ŭ �ݺ��ϸ� �׸���.
		for(int i=0; i<bullet_List.size(); i++){
			weapon = (Weapon) bullet_List.get(i);
			if(weapon instanceof Pistol){ //�Ҹ� ����Ʈ�� ������ �ǽ���� ���� �����ϴٸ�.
				//buffg.drawImage(bullet_Png, weapon.getPoint().x,  weapon.getPoint().y, this);
				buffg.drawRect(weapon.getPoint().x,  weapon.getPoint().y, 10,  10); //�簢������ �ϴ� ��ü
				
				//�ǽ��� �Ѿ� ������ �� ���⼺�� ������ ���ư���.
				((Pistol) weapon).pistol_Move( weapon.get_Bullet_Side_LEFT_RIGHT() );
				
			}
			
			//�Ѿ˰� ������ �浹���� �Լ� ȣ�� 
			
			for(int j=0; j<enemy_List.size(); j++){
			enemy = (Enemy) enemy_List.get(j);
			crash_Decide_Enemy(weapon, enemy, enemy.get_Move_Site());
			
			
			
			}
			
			
			
			
			//�Ѿ� ���� �Լ� ȣ��
			remove_Bullet(weapon, i);
			
		}
	}
	
	//�浹 ���� �Լ�, �浹�� 2�� �ٿ����Ʈ�� ���Ѵ�. 1�� �� ū �簢�� 2���� ������ �簢���� �������Ѵ� �ϴ� 1�� �ٿ�� ������ �����ϵ��� �Ѵ�.
	//private int object_Width; //������ ������ �����ϱ� ���� ���� ����
	//private int object_Height; //������ ������ �����ϱ� ���� ���� ����
	
	private boolean jump_Up_Lock_Temp = false; //�ö󰡴� ���߿��� ������ ���� �Ұ���
	
	private int auto_Jump_Down_Head_Flag = 0; //�Ӹ� ���� ���� ����
	
	//�浹 üũ �ʰ� ���� ĳ���� ĳ���� x,y
	public void crash_Decide_Block(Hero hero, Block block){ //what_Object 1 �϶� ���� ĳ���� �浹

		//�ö󰡴����϶� ���� �Ұ�
		if(!mainCh.get_Jump_State()){
			jump_Up_Lock_Temp = true;
		}
		
			
			
			if((hero.get_Hero_X_Point()+hero.get_Hero_Width()) < (block.get_Left_Top_Point().x ) || 
					hero.get_Hero_X_Point() > (block.get_Left_Top_Point().x+block.get_Widht()) ||
					(hero.get_Hero_Y_Point()+hero.get_Hero_Height()) < block.get_Left_Top_Point().y ||
					hero.get_Hero_Y_Point() > (block.get_Left_Top_Point().y+block.get_Height())){
				
			
				
				//ĳ���Ͱ� ���� ���� ������ false ���� ��� ���� �������� ���������� 
				block.set_Contect_F();
				
				
				
			}else {
				//ĳ���Ͱ� ���� ������ true
				block.set_Contect_T();
				
				//ĳ���Ͱ� ������ �����ʰ� ���������� �߷� ���ӵ��� �ʱ�ȭ �Ѵ�.
				mainCh.set_dgSum_Zero();
				
				//ĳ������ �Ӹ��� ���� �ٴڿ� �������
				if(hero.get_Hero_Y_Point() >= block.get_Left_Top_Point().y + block.get_Height() - 20){
					System.out.println("�Ӹ��� �ٴ� �ε���");
					
					//ĳ���Ͱ� ���� �ε����� �ٷ� �Ʒ������� ������
					mainCh.set_Jump_Hero_UP_DOWN();
					
					
					//������ ���ϰ� �߶��Ҷ� �������� ���� �ʵ��� �ؾ��Ѵ�.
					//�� ���� ���̸� ���ؼ� ����.
					auto_Jump_Down_Head_Flag++;
					if(auto_Jump_Down_Head_Flag >= 2){
					mainCh.auto_Jump_Down_Head(block.get_Left_Top_Point().y + block.get_Height() - hero.get_Hero_Y_Point());
					}
					
				}else 
				//ĳ������ �ϴ��� ������ ����� �������� ���� ���� �˷��ش�.
				if(hero.get_Hero_Y_Point()+hero.get_Hero_Height()  <=  block.get_Left_Top_Point().y + 20){
					//System.out.println("���� ��� ����");
				
				//������ �߻��ϸ� �������� ������ �߻������� �������� ������ �߻��Ͽ����� ������ ���ش�.
				auto_Jump_Down_Head_Flag = 0;
					
				//ĳ���Ͱ� �����ߴٰ� �Ʒ��� �������� ���߿��� �� ���� �ö� ���� �ֵ��� ���� && ���Ͻÿ� ����� ���� �ֵ��� ����
				if(jump_Up_Lock_Temp){
					
					//������ ������� ������ ���� ��Ű������ �Լ�
					mainCh.set_Hero_Y_Point(block.get_Left_Top_Point().y-hero.get_Hero_Height());
					//ĳ���Ͱ� �ٽ� ��ġ �ؾ� �� �� �������� && ���� ���� �ƴҶ��� �ʱ�ȭ �Ҽ� �ֵ��� �ؾ��Ѵ�.
					mainCh.jump_Move_Stop(mainCh.get_Hero_Y_Point());
					
					jump_Up_Lock_Temp = false;
					
				}
					
					
					//���� ���������� �������ٰ� ��� ���� if ���� ������ ���� ���� �� ���´�.
					
				}//ĳ���� ������ ���� ������ �ھ�����
				else if(hero.get_Hero_X_Point() + hero.get_Hero_Width() <= block.get_Left_Top_Point().x + 25){
					System.out.println("���� ���� �� �ε���");
					//���������� ���� ���ϵ��� ���ƾ���
					mainCh.stop_Move_Right(hero.get_Hero_X_Point());
				}
				//ĳ���� ������ ���� ������ �ھ�����
				else if(hero.get_Hero_X_Point() >= block.get_Left_Top_Point().x + block.get_Widht() - 25){
					System.out.println("���� ������ �� �ε���");
					//�������� ���� ���ϵ��� ���ƾ���
					mainCh.stop_Move_Leftt(hero.get_Hero_X_Point());
					
				}
				else {
					block.set_Contect_F();//ĳ���Ͱ� ���� ���� ������ false ���� ��� ���� �������� ���������� 
				}

				
			}
			
		}
		
	
	//������ ����� �浹 �˻� �������ٰ� �¹������Ѵ�.
	public void crash_Decide_Enemy_Block(Block block, Enemy enemy){
		
		//�������� �۵�
		if(enemy.get_Down_Start()){
		
			//xpoint = ���� x ��ǥ
			if((block.get_Left_Top_Point().x + block.get_Widht()) <= (enemy.get_enemy_Point().x ) || 
					block.get_Left_Top_Point().x >= (enemy.get_enemy_Point().x+enemy.get_Enemy_Width()) ||
					(block.get_Left_Top_Point().y + block.get_Height()) <= enemy.get_enemy_Point().y ||
					block.get_Left_Top_Point().y-1 >= (enemy.get_enemy_Point().y+enemy.get_Enemy_Height())){
			
			
			//enemy.get_Enemy_Exit_Yoint(1000);
			
		}else{
			System.out.println("���� ���� ��� ����");
			
			
			
			//��� ������ ������ ��ġ�� �����Ѵ�, ������ ������� ������Ų��.
			enemy.get_Enemy_Exit_Yoint(block.get_Left_Top_Point().y  - enemy.get_Enemy_Height() );
			enemy.init_Bound_Site(block.get_Left_Top_Point().x, (block.get_Widht() + block.get_Left_Top_Point().x), block.get_Left_Top_Point().y - block.get_Height());
			
			//���� �˰��� ���� ���ž��Ѵ�.
			enemy.init_Range_Site(enemy.get_enemy_Point().x, enemy.get_enemy_Point().y);
			
		}
		
		}
	}
	
	//�浹 üũ �Լ� ĳ���Ϳ� �Ѿ� �� 
	public void crash_Decide_Enemy(Hero hero, Enemy enemy, boolean get_Site){ //get_Site = Ž�������� �������� ��������
		//what_Object = 1 �̸� ĳ����, 2 �̸� �Ѿ�
		
			
				if(get_Site){ //������ Ž���Ҷ�. �簢���� ������ ĳ������ �������� �����ϱ� ������ �˹� ���ÿ� ĳ������ ���� ��ŭ ��� ������ x ���� ����(width)�� �����־�Ѵ�. 
					
					//System.out.println("���� �̵� ĳ���� ��ġ : " + enemy.get_enemy_Point().x + ", ĳ���� ���� �þ� : " + (enemy.get_enemy_Point().x - enemy.get_Range_Site_Width()));
					if((hero.get_Hero_X_Point()+hero.get_Hero_Width()) < (enemy.get_Range_Site_Width_Left_Point() - enemy.get_Range_Site_Width_Right_Point()+hero.get_Hero_Width()) || 
							hero.get_Hero_X_Point() > (enemy.get_Range_Site_Width_Left_Point() - enemy.get_Range_Site_Width_Right_Point()+hero.get_Hero_Width()+enemy.get_Range_Site_Width_Right_Point()) ||
							(hero.get_Hero_Y_Point()+hero.get_Hero_Height()) < enemy.get_range_Site_Height_Top_Point() ||
							hero.get_Hero_Y_Point() > (enemy.get_range_Site_Height_Top_Point()+enemy.get_range_Site_Height_Bottom_Point())){
						
						enemy.set_Not_Find_Hero(); //ĳ���͸� ã�� ��������.
						
						
					}else {
						//System.out.println("�浹 ����");
						enemy.set_Find_Hero(mainCh.get_Hero_X_Point()); //ĳ���͸� ã������
						
						//ĳ���Ϳ� ������ ���� �������. ��������
						if((hero.get_Hero_X_Point()+hero.get_Hero_Width()) < (enemy.get_enemy_Point().x - enemy.get_Enemy_Width()) || 
								hero.get_Hero_X_Point() > (enemy.get_enemy_Point().x - enemy.get_Enemy_Width()+enemy.get_Enemy_Width()) ||
								(hero.get_Hero_Y_Point()+hero.get_Hero_Height()) < enemy.get_enemy_Point().y ||
								hero.get_Hero_Y_Point() > (enemy.get_enemy_Point().y+enemy.get_Enemy_Height())){
							
						}else{
							//�������� �˹�
							hero.left_Knock_Back();
							System.out.println("a");
						}
						
						
					}
					
					
					
				}else { //���� Ž���Ҷ� ��� 
					if((hero.get_Hero_X_Point()+hero.get_Hero_Width()) < (enemy.get_Range_Site_Width_Left_Point() ) || 
							hero.get_Hero_X_Point() > (enemy.get_Range_Site_Width_Left_Point()+enemy.get_Range_Site_Width_Right_Point()) ||
							(hero.get_Hero_Y_Point()+hero.get_Hero_Height()) < enemy.get_range_Site_Height_Top_Point() ||
							hero.get_Hero_Y_Point() > (enemy.get_range_Site_Height_Top_Point()+enemy.get_range_Site_Height_Bottom_Point())){
						enemy.set_Not_Find_Hero(); //ĳ���͸� ã�� ��������.
						
						
						
					}else {
						//System.out.println("�浹 ����");
						enemy.set_Find_Hero(mainCh.get_Hero_X_Point()); //ĳ���͸� ã������
						
						//���� ��� ������ �������.
						if((hero.get_Hero_X_Point()+hero.get_Hero_Width()) < (enemy.get_enemy_Point().x ) || 
								hero.get_Hero_X_Point() > (enemy.get_enemy_Point().x + enemy.get_Enemy_Width()) ||
								(hero.get_Hero_Y_Point()+hero.get_Hero_Height()) < enemy.get_enemy_Point().y ||
								hero.get_Hero_Y_Point() > (enemy.get_enemy_Point().y+enemy.get_Enemy_Height())){
					
						}else {
							//�������� �˹�
							hero.right_Knock_Back();
						}
						
					}
					
					
					
					
					
				}
	}
	
	
	
	
	
	
	
	
	
	//�浹 üũ �Լ� ĳ���Ϳ� �Ѿ� �� 
	public void crash_Decide_Enemy(Weapon weapon, Enemy enemy, boolean get_Site){ //get_Site = Ž�������� �������� ��������

			
			if((weapon.getPoint().x+weapon.get_Weapon_Width()) < (enemy.get_enemy_Point().x ) || 
					weapon.getPoint().x > (enemy.get_enemy_Point().x+enemy.get_Enemy_Width()) ||
					(weapon.getPoint().y+weapon.get_Weapon_Height()) < enemy.get_enemy_Point().y ||
					weapon.getPoint().y > (enemy.get_enemy_Point().y+enemy.get_Enemy_Height())){
				
			}else {
				//System.out.println("�浹 ����");
				
				
				//�ǰݽ� ������ �������� ���ҽ�Ų��.
				enemy.enemy_HP_Down(weapon.get_Bullet_Power());
				
				//�ǰݵ� ���� �������� 0 �� �Ǹ� �����Ѵ�.
				if(enemy.get_Enemy_HP() == 0){
				enemy_List.remove(enemy);
				}
				
				//�ǰݵ� ���͸� �ڽ��� �������� �޷������� �ؾ��Ѵ�. �� ���͸� ���� ���·� �����ؾ��Ѵ�.
				if(enemy.get_enemy_Point().x >= mainCh.get_Hero_X_Point()){
					enemy.set_Move_Site(true);
					//���� �˹�ȿ��
					enemy.knockback(true);
				}
				
				//�ǰݵ� ���͸� �ڽ��� �������� �޷������� �ؾ��Ѵ�. �� ���͸� ���� ���·� �����ؾ��Ѵ�.
				if(enemy.get_enemy_Point().x <= mainCh.get_Hero_X_Point()){
					enemy.set_Move_Site(false);
					//���� �˹�ȿ��
					enemy.knockback(false);
				}
				
				
				//�˹� �ϴٰ� ���Ǻ��� �Ÿ��� �Ѿ�ԵǸ� �߶� ����
				if(enemy.get_enemy_Point().x >= enemy.get_Right_Bound_Site() ||
						enemy.get_enemy_Point().x + 30 <= enemy.get_Left_Bound_Site()){ //�������� �������� �������� ��������
					enemy.set_Down_Start_True();
				}
				
				
				
				weapon.set_Remove_Bullet_Choice(); //�浹 �Ǹ� �Ѿ��� ���¸� ���� ���·�
			
		}
		
	}
	
	
	//������ �׸��� �Լ�
	public void draw_Enemy(){
		//������ ���� �ݺ��Ͽ� �׸���
		for(int i=0; i<enemy_List.size(); i++){
			
			enemy = (Enemy) enemy_List.get(i);
			
			if(enemy instanceof Walker){ //���ʹ��� ��Ŀ�� ��ü�� �ִٸ� �׷���
				//buffg.drawImage(walker_Png, enemy.get_enemy_Point().x, enemy.get_enemy_Point().y, this);
				buffg.drawRect(enemy.get_enemy_Point().x,  enemy.get_enemy_Point().y, ((Walker) enemy).get_Enemy_Width(),  ((Walker) enemy).get_Enemy_Height()); //�簢������ �ϴ� ��ü
			}
			
			//������ �����̴� �Լ� ȣ�� -> ��Ƽ������� ���� ������ ���ÿ� ���� Ŭ���� ������ �ڵ�����
			//enemy.enemy_Move();
			
			//ĳ���Ϳ� ���� �浹���� �Լ� ȣ��
			crash_Decide_Enemy(mainCh, enemy, enemy.get_Move_Site());
			
			
			//���ϰ� �����ϰ� �⵿���� ���� ���������������� �ϸ� �Ǳ� �Ѵ�.
			for(int j=0; j< stage.get_Block().size(); j++ ){
			crash_Decide_Enemy_Block(stage.get_Block().get(j), enemy);
			}
			
			//Ž�� ���� �׸���
			if(enemy.get_Move_Site()){ //�������� ����
					buffg.drawRect(enemy.get_Range_Site_Width_Left_Point() - enemy.get_Range_Site_Width_Right_Point(),  enemy.get_range_Site_Height_Top_Point(),
							enemy.get_Range_Site_Width_Right_Point(), enemy.get_range_Site_Height_Bottom_Point());	
			}else 
			if(!enemy.get_Move_Site()){ //�������� ����
					buffg.drawRect(enemy.get_Range_Site_Width_Left_Point(),  enemy.get_range_Site_Height_Top_Point(),
					enemy.get_Range_Site_Width_Right_Point(), enemy.get_range_Site_Height_Bottom_Point());
			}
			
			enemy.set_Hero_Information(mainCh);
			
		}
	}
	
	
	//���������� ���۵ɶ����� ���� ���ڸ� �Ķ���ͷ� �ް� �����ϰ� �ؾ� �� �� �ϴ�.
	public void enemy_Process(int stage_Num){
		
		
		if(stage_Num == 1){ //1�������� �϶� ���� ��ġ
		enemy = new Walker(stage.get_Block().get(0).get_Left_Top_Point().x, 
				stage.get_Block().get(0).get_Left_Top_Point().x+stage.get_Block().get(0).get_Widht(), 
				stage.get_Block().get(0).get_Left_Top_Point().y - 70); //20, width �� ��� ���� ����, height �� ���Ͱ� ��� �ִ� ���� ����
		
		//enemy_List.add(enemy);
		
		enemy = new Walker(stage.get_Block().get(1).get_Left_Top_Point().x, 
				stage.get_Block().get(1).get_Left_Top_Point().x + stage.get_Block().get(1).get_Widht(), 
				stage.get_Block().get(1).get_Left_Top_Point().y - 70); //20, width �� ��� ���� ����, height �� ���Ͱ� ��� �ִ� ���� ����
		
		enemy_List.add(enemy);
		
		
		enemy = new Walker(stage.get_Block().get(2).get_Left_Top_Point().x, 
				stage.get_Block().get(2).get_Left_Top_Point().x + stage.get_Block().get(2).get_Widht(), 
				stage.get_Block().get(2).get_Left_Top_Point().y - 70); //20, width �� ��� ���� ����, height �� ���Ͱ� ��� �ִ� ���� ����
		
		//enemy_List.add(enemy);
		
		////////////////////////////////////////////////////////�� �ʿ� 1����������  ��Ŀ �κ� �߰�
		
		
		
		}
		System.out.println(stage.get_Block().get(1).get_Left_Top_Point().x + "  " + stage.get_Block().get(1).get_Widht());
		
		
	}
	
	
	
	
	//�Ѿ��� �߻����ΰ� �ƴѰ� �Ѿ��� �����ϴ� �Լ�
	public void bullet_Process(){
		if(attack){ //���϶� �Ѿ��� �����Ѵ�.
			
			if(weapon_Number == 1){ //�����϶�
				pistol_Point = new Point(mainCh.get_Hero_X_Point(),mainCh.get_Hero_Y_Point()); //ĳ������ ���ݸ� �˾ƿͼ� ��ġ ������ ����
				weapon = new Pistol(pistol_Point, mainCh.get_Face_Side_LFET_RIGHT()); //ĳ������ ��ǥ ������ ������ �����Ѵ�.
				bullet_List.add(weapon); //������ ������ �Ҹ� ����Ʈ�� �ִ´� �� �����z Weapon Ŭ������ �Ǿ��ִ�.
			}
		}
	}
	
	//�Ѿ� ���� �Լ�
	public void remove_Bullet(Weapon weapon, int i){
		//x�࿡�� ȭ�� ������ �������� ����
		if(weapon.getPoint().x > width-30 || weapon.getPoint().x < 10){
			bullet_List.remove(i);
		}
		if(weapon.get_Remove_Bullet_Choice()){
			bullet_List.remove(i);
		}
	}
	
	
	//implement Runnable�� ���� ������ ������ 
	@Override
	public void run() {
		try{
			while(true){
				
				
				mainCh.move();//ĳ������ �������� �׻� üũ�Ѵ�.
				
				bullet_Process();//�Ѿ� ���� �Լ� ȣ��
				
				
				
				
				
				//���� ���� �żҵ�
				if(jump){
					
					mainCh.set_Hero_Jumping();
				}
				
				//ĳ���Ͱ� ����ִ� ������ ���̰� �Ǿ���Ѵ�.
				mainCh.jump_Move();
				
				
				
				repaint(); //ȭ���� ������ �ٽ� �׸���
				Thread.sleep(20); //20milli sec �� ������ ������
				
			}
		}catch (Exception e) {
			
		}
	}

	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////
	//Ű �ڵ鷯
	@Override
	//Ű���尡 ���������� �̺�Ʈ ó���ϴ� ��
	public void keyPressed(KeyEvent e) {

		switch(e.getKeyCode()){
		case KeyEvent.VK_UP :
			break;
			
		case KeyEvent.VK_DOWN :
			break;
			
		case KeyEvent.VK_LEFT :
			mainCh.set_Hero_X_Left();
			break;
			
		case KeyEvent.VK_RIGHT :
			mainCh.set_Hero_X_Right();
			break;
			
		case KeyEvent.VK_A :
			//�������϶� �Ѿ��� �����Ѵ�.
			attack = true;
			break;
			
		case KeyEvent.VK_S :
			//���� �����Ѵ�.
			jump = true;
			break;
		}
		
		
	}

	@Override
	//Ű���尡 �������ٰ� ���������� �̺�Ʈ ó���ϴ� ��
	public void keyReleased(KeyEvent e) {
		
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP :
			break;
			
		case KeyEvent.VK_DOWN :
			break;
			
		case KeyEvent.VK_LEFT :
			mainCh.set_Hero_X_Left_Stop();
			break;
			
		case KeyEvent.VK_RIGHT :
			mainCh.set_Hero_X_Right_Stop();
			break;
		case KeyEvent.VK_A :
			//�Ѿ˻����� �����Ѵ�..
			attack = false;
			break;
			
		case KeyEvent.VK_S :
			//���� ����
			jump = false;
			break;
		}
		
	}

	@Override
	//Ű���尡 Ÿ���� �� �� �̺�Ʈ ó���ϴ� ��
	public void keyTyped(KeyEvent e) {
	}
	///////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
}