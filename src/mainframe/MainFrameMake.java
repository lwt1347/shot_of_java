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
import weapon.Pistol;
import weapon.Weapon;

//���� ȭ�� ��������� Ŭ����
class MainFrameMake extends JFrame implements KeyListener, Runnable{
	
	//ȭ�� ũ��
	int width = 700;
	int height = 700;
	
	//���� ���� ��������
	int stage_Num = 1;
	
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
		
		//�⺻ ���� ��Ŀ ����
		enemy_Process();
		
		//�������� true �� �Ǹ� ���� ���������� �Ѿ
		end_Stage = false;
		
		addKeyListener(this); //Ű���� �̺�Ʈ ����
		th = new Thread(this); 	  //������ ����
		th.start(); 		  //������ ����
		
		
		
		
		
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
			
			end_Stage = false;
			stage_Num++; //���� ���������� �Ѿ
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
			carash_Decide(weapon.getPoint().x, weapon.getPoint().y, enemy, enemy.get_Move_Site(), 2);
			
			
			
			}
			
			
			
			
			//�Ѿ� ���� �Լ� ȣ��
			remove_Bullet(weapon, i);
			
		}
	}
	
	//�浹 ���� �Լ�, �浹�� 2�� �ٿ����Ʈ�� ���Ѵ�. 1�� �� ū �簢�� 2���� ������ �簢���� �������Ѵ� �ϴ� 1�� �ٿ�� ������ �����ϵ��� �Ѵ�.
	private int object_Width; //������ ������ �����ϱ� ���� ���� ����
	private int object_Height; //������ ������ �����ϱ� ���� ���� ����
	
	public void carash_Decide(int x_Point,int y_Point, Enemy enemy, boolean get_Site, int what_Object){ //get_Site = Ž�������� �������� ��������
		//what_Object = 1 �̸� ĳ����, 2 �̸� �Ѿ�
		if(what_Object == 1){	//ĳ������ ũ��
			object_Width = 30;
			object_Height = 45;
			
				if(get_Site){ //������ Ž���Ҷ�.
					
					//System.out.println("���� �̵� ĳ���� ��ġ : " + enemy.get_enemy_Point().x + ", ĳ���� ���� �þ� : " + (enemy.get_enemy_Point().x - enemy.get_Range_Site_Width()));
					if((x_Point+object_Width) < (enemy.get_Range_Site_Width_Left_Point() - enemy.get_Range_Site_Width_Right_Point()) || 
							x_Point > (enemy.get_Range_Site_Width_Left_Point() - enemy.get_Range_Site_Width_Right_Point()+enemy.get_Range_Site_Width_Right_Point()) ||
							(y_Point+object_Height) < enemy.get_range_Site_Height_Top_Point() ||
							y_Point > (enemy.get_range_Site_Height_Top_Point()+enemy.get_range_Site_Height_Bottom_Point())){
						
						enemy.set_Not_Find_Hero(); //ĳ���͸� ã�� ��������.
						
						
					}else {
						System.out.println("�浹 ����");
						enemy.set_Find_Hero(mainCh.get_Hero_X_Point()); //ĳ���͸� ã������
						
					}
					
				}else { //���� Ž���Ҷ�
					if((x_Point+object_Width) < (enemy.get_Range_Site_Width_Left_Point() ) || 
							x_Point > (enemy.get_Range_Site_Width_Left_Point()+enemy.get_Range_Site_Width_Right_Point()) ||
							(y_Point+object_Height) < enemy.get_range_Site_Height_Top_Point() ||
							y_Point > (enemy.get_range_Site_Height_Top_Point()+enemy.get_range_Site_Height_Bottom_Point())){
						enemy.set_Not_Find_Hero(); //ĳ���͸� ã�� ��������.
					}else {
						System.out.println("�浹 ����");
						enemy.set_Find_Hero(mainCh.get_Hero_X_Point()); //ĳ���͸� ã������
						
						
					}
				}
			
		}else if(what_Object == 2){ //�Ѿ��� �β�
			object_Width = 10;
			object_Height = 10;
			
			if((x_Point+object_Width) < (enemy.get_enemy_Point().x ) || 
					x_Point > (enemy.get_enemy_Point().x+enemy.get_Enemy_Width()) ||
					(y_Point+object_Height) < enemy.get_enemy_Point().y ||
					y_Point > (enemy.get_enemy_Point().y+enemy.get_Enemy_Height())){
				
			}else {
				System.out.println("�浹 ����");
				
				
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
				
				weapon.set_Remove_Bullet_Choice(); //�浹 �Ǹ� �Ѿ��� ���¸� ���� ���·�
				
				
				
			}
			
			
			
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
			carash_Decide(mainCh.get_Hero_X_Point(), mainCh.get_Hero_Y_Point(), enemy, enemy.get_Move_Site(), 1);
			
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
	public void enemy_Process(){
		enemy = new Walker(20, width, height-100); //20, width �� ��� ���� ����, height �� ���Ͱ� ��� �ִ� ���� ����
		enemy_List.add(enemy);
		
		enemy = new Walker(20, width, height-200); //20, width �� ��� ���� ����, height �� ���Ͱ� ��� �ִ� ���� ����
		enemy_List.add(enemy);
		
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
				mainCh.jump_Move(mainCh.get_Hero_Y_Point());
				
				
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