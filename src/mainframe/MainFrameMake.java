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

//메인 화면 만들기위한 클래스
class MainFrameMake extends JFrame implements KeyListener, Runnable{
	
	//화면 크기
	int width = 700;
	int height = 700;
	
	//현재 나의 스테이지
	int stage_Num = 1;
	
	//이미지를 불러오기 위한 툴킷
	Toolkit tk = Toolkit.getDefaultToolkit();
	Image hero_Png;
	Image bullet_Png;
	Image walker_Png;
	
	//더블 버퍼링용 이미지
	Image buffImage;
	Graphics buffg;
	
	
	//스레드 생성
	Thread th;
	
	
	//히어로 생성
	Hero mainCh;
	
	//기본 적군 생성
	Enemy enemy;
	
	
	//다음 스테이지로 넘어 갈 것인가.
	boolean end_Stage;
	
	//공격중인가 공격중이 아닌가
	boolean attack;
	//무기 교체 할 때 번호
	int weapon_Number;
	
	//점프를 실행할 것인가 ? true 이면 실행한다.
	boolean jump;
	
	
	//공격 기본생성자
	Weapon weapon; 
	Point pistol_Point;
	
	//다수의 권총(알)을 담을 어레이 리스트
	ArrayList bullet_List = new ArrayList<Weapon>(); 
	
	//다수의 적군을 담을 어레이 리스트
	ArrayList enemy_List = new ArrayList<Enemy>();
	
	
	
	public MainFrameMake() {
		
		
		
		init();
		
		setTitle("Shot");		//프레임의 이름을 설정
		setSize(width, height); //프레임의 크기
		
		//프레임이 윈도우에 표시괼때 위치를 세팅하기 위함.
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		//프레임을 모니터 화면 정중앙에 배치 시키기 위해 좌표 값을 계산.
		int focus_X = (int)(screen.getWidth() / 2 -width / 2);
		int focus_Y = (int)(screen.getHeight() / 2 -height / 2);
		
		setLocation(focus_X, focus_Y); //프레임을 화면에 배치
		setResizable(false);		   //프레임의 크기를 임의로 변경못하도록 설정
		setVisible(true);			   //프레임을 눈에 보이게 띄움	
		
		
	}
	
	private void init(){
		//프로그렘이 정상적으로 종료하도록 만들어 줍니다.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//주인공의 기본 이미지 삽입
		hero_Png = tk.getImage("img/hero_1.png");
		bullet_Png = tk.getImage("img/Bullet_img.png");
		
		//적군 이미지
		walker_Png = tk.getImage("img/walker_img.png");
		
		//주인공 생성
		mainCh = new Hero();
		
		//기본 적군 워커 생성
		enemy_Process();
		
		//스테이지 true 가 되면 다음 스테이지로 넘어감
		end_Stage = false;
		
		addKeyListener(this); //키보드 이벤트 실행
		th = new Thread(this); 	  //스레드 생성
		th.start(); 		  //스레드 시작
		
		
		
		
		
		attack = false; //공격 상태 설정 
		weapon_Number = 1;//무기의 상태, 기본 1 피스톨
		
		jump = false; //점프 상태설정
		
	}
	
	
	public void paint(Graphics g){
		//더블버퍼링 버퍼 크기를 화면 크기와 같게 설정
		buffImage = createImage(width,height);
		//버퍼의 그래픽 객체 얻기
		buffg = buffImage.getGraphics();
		
		update(g);
		
		
	}
	
	public void update(Graphics g){
		//실제로 그려진 그림을 가져온다.
		draw();
		
		//총알을 그린다.
		draw_Bullet();
		
		//적군을 그린다.
		draw_Enemy();
		
		//화면에 버퍼에 그린 그림을 가져와 그리기
		g.drawImage(buffImage, 0, 0, this);
	}
	
	//실제로 그림들을 그릴 부분
	public void draw(){
		//(0,0) ~ (width,height) 까지 화면을 지웁니
		buffg.clearRect(0, 0, width, height);
		//프레임에 저장된 .png 이미지를 그려넣습니다.
		//buffg.drawImage(hero_Png, mainCh.get_Hero_X_Point(), mainCh.get_Hero_Y_Point(), this);
		buffg.drawRect(mainCh.get_Hero_X_Point(),   mainCh.get_Hero_Y_Point(), 30,  45); //사각형으로 일단 대체
	}
	
	//스테이지 맵 을 그림 (블록)
	public void draw_Stage(){
		
		//다음 스테이지로 넘어감
		if(end_Stage){
			
			end_Stage = false;
			stage_Num++; //다음 스테이지로 넘어감
		}
	}
	
	
	//총알을 그리는 함수
	public void draw_Bullet(){
		//총알 개수 만큼 반복하며 그린다.
		for(int i=0; i<bullet_List.size(); i++){
			weapon = (Weapon) bullet_List.get(i);
			if(weapon instanceof Pistol){ //불릿 리스트에 정보가 피스톨로 변형 가능하다면.
				//buffg.drawImage(bullet_Png, weapon.getPoint().x,  weapon.getPoint().y, this);
				buffg.drawRect(weapon.getPoint().x,  weapon.getPoint().y, 10,  10); //사각형으로 일단 대체
				
				//피스톨 총알 제각기 의 방향성을 가지고 날아간다.
				((Pistol) weapon).pistol_Move( weapon.get_Bullet_Side_LEFT_RIGHT() );
				
			}
			
			//총알과 적군의 충돌판정 함수 호출 
			
			for(int j=0; j<enemy_List.size(); j++){
			enemy = (Enemy) enemy_List.get(j);
			carash_Decide(weapon.getPoint().x, weapon.getPoint().y, enemy, enemy.get_Move_Site(), 2);
			
			
			
			}
			
			
			
			
			//총알 제거 함수 호출
			remove_Bullet(weapon, i);
			
		}
	}
	
	//충돌 판정 함수, 충돌은 2중 바운딩렉트로 구한다. 1차 는 큰 사각형 2차는 조각난 사각형과 대조를한다 일단 1차 바운딩 구조만 생각하도록 한다.
	private int object_Width; //판정의 범위를 연산하기 위한 템프 변수
	private int object_Height; //판정의 범위를 연산하기 위한 템프 변수
	
	public void carash_Decide(int x_Point,int y_Point, Enemy enemy, boolean get_Site, int what_Object){ //get_Site = 탐지구역이 좌측인지 우측인지
		//what_Object = 1 이면 캐릭터, 2 이면 총알
		if(what_Object == 1){	//캐릭터의 크기
			object_Width = 30;
			object_Height = 45;
			
				if(get_Site){ //좌측을 탐지할때.
					
					//System.out.println("좌측 이동 캐릭터 위치 : " + enemy.get_enemy_Point().x + ", 캐릭터 좌측 시야 : " + (enemy.get_enemy_Point().x - enemy.get_Range_Site_Width()));
					if((x_Point+object_Width) < (enemy.get_Range_Site_Width_Left_Point() - enemy.get_Range_Site_Width_Right_Point()) || 
							x_Point > (enemy.get_Range_Site_Width_Left_Point() - enemy.get_Range_Site_Width_Right_Point()+enemy.get_Range_Site_Width_Right_Point()) ||
							(y_Point+object_Height) < enemy.get_range_Site_Height_Top_Point() ||
							y_Point > (enemy.get_range_Site_Height_Top_Point()+enemy.get_range_Site_Height_Bottom_Point())){
						
						enemy.set_Not_Find_Hero(); //캐릭터를 찾지 못했을때.
						
						
					}else {
						System.out.println("충돌 판정");
						enemy.set_Find_Hero(mainCh.get_Hero_X_Point()); //캐릭터를 찾았을때
						
					}
					
				}else { //우측 탐지할때
					if((x_Point+object_Width) < (enemy.get_Range_Site_Width_Left_Point() ) || 
							x_Point > (enemy.get_Range_Site_Width_Left_Point()+enemy.get_Range_Site_Width_Right_Point()) ||
							(y_Point+object_Height) < enemy.get_range_Site_Height_Top_Point() ||
							y_Point > (enemy.get_range_Site_Height_Top_Point()+enemy.get_range_Site_Height_Bottom_Point())){
						enemy.set_Not_Find_Hero(); //캐릭터를 찾지 못했을때.
					}else {
						System.out.println("충돌 판정");
						enemy.set_Find_Hero(mainCh.get_Hero_X_Point()); //캐릭터를 찾았을때
						
						
					}
				}
			
		}else if(what_Object == 2){ //총알의 두께
			object_Width = 10;
			object_Height = 10;
			
			if((x_Point+object_Width) < (enemy.get_enemy_Point().x ) || 
					x_Point > (enemy.get_enemy_Point().x+enemy.get_Enemy_Width()) ||
					(y_Point+object_Height) < enemy.get_enemy_Point().y ||
					y_Point > (enemy.get_enemy_Point().y+enemy.get_Enemy_Height())){
				
			}else {
				System.out.println("충돌 판정");
				
				
				//피격시 적군의 에너지를 감소시킨다.
				enemy.enemy_HP_Down(weapon.get_Bullet_Power());
				
				//피격된 적군 에너지가 0 이 되면 삭제한다.
				if(enemy.get_Enemy_HP() == 0){
				enemy_List.remove(enemy);
				}
				
				//피격된 몬스터를 자신의 방향으로 달려오도록 해야한다. 즉 몬스터를 공격 상태로 변경해야한다.
				if(enemy.get_enemy_Point().x >= mainCh.get_Hero_X_Point()){
					enemy.set_Move_Site(true);
					//적군 넉백효과
					enemy.knockback(true);
				}
				
				//피격된 몬스터를 자신의 방향으로 달려오도록 해야한다. 즉 몬스터를 공격 상태로 변경해야한다.
				if(enemy.get_enemy_Point().x <= mainCh.get_Hero_X_Point()){
					enemy.set_Move_Site(false);
					//적군 넉백효과
					enemy.knockback(false);
				}
				
				weapon.set_Remove_Bullet_Choice(); //충돌 되면 총알의 상태를 삭제 상태로
				
				
				
			}
			
			
			
		}
		
		
		
	}
	
	
	//적군을 그리는 함수
	public void draw_Enemy(){
		//적군의 수를 반복하여 그린다
		for(int i=0; i<enemy_List.size(); i++){
			
			enemy = (Enemy) enemy_List.get(i);
			
			if(enemy instanceof Walker){ //에너미중 워커의 객체가 있다면 그려라
				//buffg.drawImage(walker_Png, enemy.get_enemy_Point().x, enemy.get_enemy_Point().y, this);
				buffg.drawRect(enemy.get_enemy_Point().x,  enemy.get_enemy_Point().y, ((Walker) enemy).get_Enemy_Width(),  ((Walker) enemy).get_Enemy_Height()); //사각형으로 일단 대체
			}
			
			//적군을 움직이는 함수 호출 -> 멀티쓰레드로 변경 생성과 동시에 적군 클레스 쓰레드 자동생성
			//enemy.enemy_Move();
			
			//캐릭터와 몬스터 충돌판정 함수 호출
			carash_Decide(mainCh.get_Hero_X_Point(), mainCh.get_Hero_Y_Point(), enemy, enemy.get_Move_Site(), 1);
			
			//탐지 구역 그리기
			if(enemy.get_Move_Site()){ //좌측으로 갈때
					buffg.drawRect(enemy.get_Range_Site_Width_Left_Point() - enemy.get_Range_Site_Width_Right_Point(),  enemy.get_range_Site_Height_Top_Point(),
							enemy.get_Range_Site_Width_Right_Point(), enemy.get_range_Site_Height_Bottom_Point());	
			}else 
			if(!enemy.get_Move_Site()){ //우측으로 갈때
					buffg.drawRect(enemy.get_Range_Site_Width_Left_Point(),  enemy.get_range_Site_Height_Top_Point(),
					enemy.get_Range_Site_Width_Right_Point(), enemy.get_range_Site_Height_Bottom_Point());
			}
			
			enemy.set_Hero_Information(mainCh);
			
		}
	}
	
	
	//스테이지가 시작될때마다 적군 숫자를 파라메터로 받고 생성하고 해야 할 듯 하다.
	public void enemy_Process(){
		enemy = new Walker(20, width, height-100); //20, width 는 경계 범위 지정, height 는 몬스터가 밝고 있는 블럭의 높이
		enemy_List.add(enemy);
		
		enemy = new Walker(20, width, height-200); //20, width 는 경계 범위 지정, height 는 몬스터가 밝고 있는 블럭의 높이
		enemy_List.add(enemy);
		
	}
	
	
	
	
	//총알을 발사중인가 아닌가 총알을 생성하는 함수
	public void bullet_Process(){
		if(attack){ //참일때 총알을 생성한다.
			
			if(weapon_Number == 1){ //권총일때
				pistol_Point = new Point(mainCh.get_Hero_X_Point(),mainCh.get_Hero_Y_Point()); //캐릭터의 좌쵸를 알아와서 위치 정보를 저장
				weapon = new Pistol(pistol_Point, mainCh.get_Face_Side_LFET_RIGHT()); //캐릭터의 좌표 지점에 권총을 생성한다.
				bullet_List.add(weapon); //권총의 공격을 불릿 리스트에 넣는다 블릿 리스틑 Weapon 클래스로 되어있다.
			}
		}
	}
	
	//총알 제거 함수
	public void remove_Bullet(Weapon weapon, int i){
		//x축에서 화면 밖으로 나갔을때 삭제
		if(weapon.getPoint().x > width-30 || weapon.getPoint().x < 10){
			bullet_List.remove(i);
		}
		if(weapon.get_Remove_Bullet_Choice()){
			bullet_List.remove(i);
		}
	}
	
	
	//implement Runnable를 통해 생성된 스레드 
	@Override
	public void run() {
		try{
			while(true){
				
				
				mainCh.move();//캐릭터의 움직임을 항상 체크한다.
				
				bullet_Process();//총알 생성 함수 호출
				
				
				//점프 실행 매소드
				if(jump){
					
					mainCh.set_Hero_Jumping();
				}
				mainCh.jump_Move(mainCh.get_Hero_Y_Point());
				
				
				repaint(); //화면을 지웠다 다시 그리기
				Thread.sleep(20); //20milli sec 로 스레드 돌리기
				
			}
		}catch (Exception e) {
			
		}
	}

	
	//////////////////////////////////////////////////////////////////////////////////////////
	//키 핸들러
	@Override
	//키보드가 눌러졌을떼 이벤트 처리하는 곳
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
			//공격중일때 총알을 생성한다.
			attack = true;
			break;
			
		case KeyEvent.VK_S :
			//점프 실행한다.
			jump = true;
			break;
		}
		
		
	}

	@Override
	//키보드가 눌러졌다가 때어졌을때 이벤트 처리하는 곳
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
			//총알생성을 중지한다..
			attack = false;
			break;
			
		case KeyEvent.VK_S :
			//점프 종료
			jump = false;
			break;
		}
		
	}

	@Override
	//키보드가 타이핑 될 때 이벤트 처리하는 곳
	public void keyTyped(KeyEvent e) {
	}
	///////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
}