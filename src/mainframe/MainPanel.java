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
import javax.swing.JPanel;

import character.Hero;
import enemy.Enemy;
import enemy.Walker;
import mapData.Block;
import mapData.Stage;
import weapon.Pistol;
import weapon.Weapon;

//메인 화면 만들기위한 클래스
class MainPanel extends JPanel implements KeyListener, Runnable{
	
	//화면 크기
	int width = 1000;
	int height = 2000;
	
	//현재 나의 스테이지
	int stage_Num = 0;
	
	//횡으로 이동 시킨다.
	int vertical_View = 0;
	
	
	//총알 딜레이
	protected int delay = 0;
	protected int delay_End; //delay_End 까지 찰때마다 한발씩
	protected boolean weapon_Number1_Flag = true; //1번 무기인 피스톨은 눌렀다 때야 공격가능
	
	protected boolean weapon_Number2_Flag = true; //2번 무기인 쌍 피스톨은 눌렀다 때야 공격가능
	
	protected boolean weapon_Number3_Flag = true; //3번 무기인 다연발 피스톨은 눌렀다 때야 공격가능
	
	//다연발 무기 5발 
	protected int weapon_Number_3_Delay = 0; //3번 무기의 연속 발사 수 5로 제한
	
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
	
	//스테이지를 생성한다
	Stage stage;
	
	
	
	
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
	
	
	
	public MainPanel() {
		
		
		
		init();
		
		//setTitle("Shot");		//프레임의 이름을 설정
		setSize(width, height); //프레임의 크기
		
		//프레임이 윈도우에 표시괼때 위치를 세팅하기 위함.
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		//프레임을 모니터 화면 정중앙에 배치 시키기 위해 좌표 값을 계산.
		int focus_X = (int)(screen.getWidth() / 2 -width / 2);
		int focus_Y = (int)(screen.getHeight() / 2 -height / 2);
		
		setLocation(focus_X, focus_Y); //프레임을 화면에 배치
		//setResizable(false);		   //프레임의 크기를 임의로 변경못하도록 설정
		setVisible(true);			   //프레임을 눈에 보이게 띄움	
		
		
	}
	
	private void init(){
		//프로그렘이 정상적으로 종료하도록 만들어 줍니다.
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//주인공의 기본 이미지 삽입
		hero_Png = tk.getImage("img/hero_1.png");
		bullet_Png = tk.getImage("img/Bullet_img.png");
		
		//적군 이미지
		walker_Png = tk.getImage("img/walker_img.png");
		
		//주인공 생성
		mainCh = new Hero();
		
		
		//스테이지 true 가 되면 다음 스테이지로 넘어감
		end_Stage = true;
		
		addKeyListener(this); //키보드 이벤트 실행
		th = new Thread(this); 	  //스레드 생성
		th.start(); 		  //스레드 시작
		
		
		//스테이지 1 생성
		stage = new Stage();
		
		
		attack = false; //공격 상태 설정 
		weapon_Number = 1;//무기의 상태, 기본 1 피스톨
		//피스톨일때 딜레이 5 이면서 공격 키를 눌렀다 땠을때만 격발
		delay_End = 5;
		
		
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
		
		//스테이지를 그린다.
		draw_Stage();
		
		//캐릭터의 횡축을 가져온다
		vertical_View = mainCh.get_Hero_Y_Point();
		
		//화면에 버퍼에 그린 그림을 가져와 그리기
		g.drawImage(buffImage, 0, (height-1300)-vertical_View, this);
	}
	
	//실제로 그림들을 그릴 부분
	public void draw(){
		//(0,0) ~ (width,height) 까지 화면을 지웁니
		buffg.clearRect(0, 0, width, height);
		//프레임에 저장된 .png 이미지를 그려넣습니다.
		//buffg.drawImage(hero_Png, mainCh.get_Hero_X_Point(), mainCh.get_Hero_Y_Point(), this);
		buffg.drawRect(mainCh.get_Hero_X_Point(),   mainCh.get_Hero_Y_Point(), mainCh.get_Hero_Width(),  mainCh.get_Hero_Height()); //사각형으로 일단 대체
	}
	
	//스테이지 맵 을 그림 (블록)
	public void draw_Stage(){
		
		//다음 스테이지로 넘어감
		if(end_Stage){
			stage_Num++; //다음 스테이지로 넘어감 //현 위치 초기화 1스테이지
			
			//스테이지넘버를 한번 반영해서 스테이지를 만든다.
			stage.map_Stage(stage_Num);

			//기본 적군 워커 생성
			enemy_Process();

			end_Stage = false;
			
		}
		
		//생성된 스테이지의 블록을 그려야함 
		int temp = 0;
		for(int i=0; i<stage.get_Block().size(); i++){
			buffg.drawRect(stage.get_Block().get(i).get_Left_Top_Point().x,
					stage.get_Block().get(i).get_Left_Top_Point().y,
					stage.get_Block().get(i).get_Widht(), 
					stage.get_Block().get(i).get_Height());
			
		
			//충돌 함수 호출 1이면 벽과 캐릭터
			crash_Decide_Block(mainCh, stage.get_Block().get(i));
		
			//캐릭터가 모든 발판을 밟고 있지 않을때 추락중이다.
			if(!stage.get_Block().get(i).get_Set_Contect()){
				temp++;
			}
			//발판 숫자만큼 false 이면 발판을 밝고 있지않음 
			if(temp == stage.get_Block().size()){
				mainCh.auto_Jump_Down();
			}
		
		}
		
	
		
		
	}
	
	
	//총알을 그리는 함수
	public void draw_Bullet(){
		//총알 개수 만큼 반복하며 그린다.
		for(int i=0; i<bullet_List.size(); i++){
			weapon = (Weapon) bullet_List.get(i);
			if(weapon instanceof Pistol){ //불릿 리스트에 정보가 피스톨로 변형 가능하다면.
				//buffg.drawImage(bullet_Png, weapon.getPoint().x,  weapon.getPoint().y, this);
				buffg.drawRect(weapon.getPoint().x,  weapon.getPoint().y, weapon.get_Weapon_Width(),  weapon.get_Weapon_Height()); //사각형으로 일단 대체
				
				//피스톨 총알 제각기 의 방향성을 가지고 날아간다.
				((Pistol) weapon).pistol_Move( weapon.get_Bullet_Side_LEFT_RIGHT() );
				
			}
			
			//총알과 적군의 충돌판정 함수 호출 
			
			for(int j=0; j<enemy_List.size(); j++){
			enemy = (Enemy) enemy_List.get(j);
			crash_Decide_Enemy(weapon, enemy, enemy.get_Move_Site());
			
			
			
			}
			
			
			
			
			//총알 제거 함수 호출
			remove_Bullet(weapon, i);
			
		}
	}
	
	//충돌 판정 함수, 충돌은 2중 바운딩렉트로 구한다. 1차 는 큰 사각형 2차는 조각난 사각형과 대조를한다 일단 1차 바운딩 구조만 생각하도록 한다.
	//private int object_Width; //판정의 범위를 연산하기 위한 템프 변수
	//private int object_Height; //판정의 범위를 연산하기 위한 템프 변수
	
	private boolean jump_Up_Lock_Temp = false; //올라가는 도중에는 벽위에 안착 불가능
	
	private int auto_Jump_Down_Head_Flag = 0; //머리 끼임 현상 방지
	
	//충돌 체크 맵과 메인 캐릭터 캐릭터 x,y 
	public void crash_Decide_Block(Hero hero, Block block){ //what_Object 1 일때 벽과 캐릭터 충돌

		//올라가는중일때 안착 불가
		if(!mainCh.get_Jump_State()){
			jump_Up_Lock_Temp = true;
		}
		
			
			
			if((hero.get_Hero_X_Point()+hero.get_Hero_Width()) < (block.get_Left_Top_Point().x ) || 
					hero.get_Hero_X_Point() > (block.get_Left_Top_Point().x+block.get_Widht()) ||
					(hero.get_Hero_Y_Point()+hero.get_Hero_Height()) < block.get_Left_Top_Point().y ||
					hero.get_Hero_Y_Point() > (block.get_Left_Top_Point().y+block.get_Height())){
				
			
				
				//캐릭터가 땅을 밝지 않으면 false 벽을 밝고 있지 않을때는 옛沮層돈 
				block.set_Contect_F();
				
				
				
			}else {
				//캐릭터가 땅을 밝으면 true
				block.set_Contect_T();
				
				//캐릭터가 점프를 하지않고 떨어졌을때 중력 가속도를 초기화 한다.
				mainCh.set_dgSum_Zero();
				
				//캐릭터의 머리가 벽의 바닥에 닿았을때
				if(hero.get_Hero_Y_Point() >= block.get_Left_Top_Point().y + block.get_Height() - 15){
					System.out.println("머리와 바닥 부딛힘");
					
					//캐릭터가 벽과 부딛히면 바로 아래쪽으로 떨어짐
					mainCh.set_Jump_Hero_UP_DOWN();
					
					
					//점프를 안하고 추락할때 끼었을때 끼지 않도록 해야한다.
					//낀 값의 넓이를 구해서 뺀다.
					auto_Jump_Down_Head_Flag++;
					if(auto_Jump_Down_Head_Flag >= 2){
					mainCh.auto_Jump_Down_Head(block.get_Left_Top_Point().y + block.get_Height() - hero.get_Hero_Y_Point());
					}
					
				}else 
				//캐릭터의 하단이 발판의 상단이 겹쳤을때 멈출 지점 알려준다.
				if(hero.get_Hero_Y_Point()+hero.get_Hero_Height()  <=  block.get_Left_Top_Point().y + 25){
					//System.out.println("위에 밝고 있음");
				
				//끼임이 발생하면 연속적인 동작이 발생함으로 연속적인 동작이 발생하였을때 감산을 해준다.
				auto_Jump_Down_Head_Flag = 0;
					
				//캐릭터가 점프했다가 아래로 내려오는 도중에만 벽 위에 올라 설수 있도록 설정 && 낙하시에 제대로 설수 있도록 설정
				if(jump_Up_Lock_Temp){
					
					//벽위로 띄었을때 벽위에 안착 시키기위한 함수
					mainCh.set_Hero_Y_Point(block.get_Left_Top_Point().y-hero.get_Hero_Height());
					//캐릭터가 다시 위치 해야 할 곳 지정해줌 && 점프 중이 아닐때만 초기화 할수 있도록 해야한다.
					mainCh.jump_Move_Stop(mainCh.get_Hero_Y_Point());
					
					jump_Up_Lock_Temp = false;
					
				}
					
					
					//위에 밝을때까지 떨어지다가 밝고 있음 if 문에 들어오면 점프 중지 로 들어온다.
					
				}//캐릭터 우측과 벽의 좌측이 박았을때
				else if(hero.get_Hero_X_Point() + hero.get_Hero_Width() <= block.get_Left_Top_Point().x + 25){
					System.out.println("벽의 왼쪽 면 부딛힘");
					//오른쪽으로 전진 못하도록 막아야함
					mainCh.stop_Move_Right(hero.get_Hero_X_Point());
				}
				//캐릭터 좌측과 벽의 우측이 박았을때
				else if(hero.get_Hero_X_Point() >= block.get_Left_Top_Point().x + block.get_Widht() - 25){
					System.out.println("벽의 오른쪽 면 부딛힘");
					//왼쪽으로 전진 못하도록 막아야함
					mainCh.stop_Move_Leftt(hero.get_Hero_X_Point());
					
				}
				else {
					block.set_Contect_F();//캐릭터가 땅을 밝지 않으면 false 벽을 밝고 있지 않을때는 옛沮層돈 
				}

				
			}
			
		}
		
	
	//적군과 블록의 충돌 검사 떨어지다가 맞물려야한다.
	public void crash_Decide_Enemy_Block(Block block, Enemy enemy){
		
		//떨어지면 작동
		if(enemy.get_Down_Start()){
		
			//xpoint = 블럭의 x 좌표
			if((block.get_Left_Top_Point().x + block.get_Widht()) <= (enemy.get_enemy_Point().x ) || 
					block.get_Left_Top_Point().x >= (enemy.get_enemy_Point().x+enemy.get_Enemy_Width()) ||
					(block.get_Left_Top_Point().y + block.get_Height()) <= enemy.get_enemy_Point().y ||
					block.get_Left_Top_Point().y-1 >= (enemy.get_enemy_Point().y+enemy.get_Enemy_Height())){
			
			
			//enemy.get_Enemy_Exit_Yoint(1000);
			
		}else{
			//System.out.println("적군 땅에 닿아 있음");
			
			
			
			//닿아 있을때 발판의 위치를 리턴한다, 적군을 블록위에 안착시킨다.
			enemy.get_Enemy_Exit_Yoint(block.get_Left_Top_Point().y  - enemy.get_Enemy_Height() );
			enemy.init_Bound_Site(block.get_Left_Top_Point().x, (block.get_Widht() + block.get_Left_Top_Point().x), block.get_Left_Top_Point().y - block.get_Height());
			
			//추적 알고리즘도 같이 떨궈야한다.
			enemy.init_Range_Site(enemy.get_enemy_Point().x, enemy.get_enemy_Point().y);
			
		}
		
		}
	}
	
	//충돌 체크 함수 캐릭터와 총알 등 
	public void crash_Decide_Enemy(Hero hero, Enemy enemy, boolean get_Site){ //get_Site = 탐지구역이 좌측인지 우측인지
		//what_Object = 1 이면 캐릭터, 2 이면 총알
		
			
				if(get_Site){ //좌측을 탐지할때. 사각형의 범위가 캐릭터의 좌측부터 시작하기 때문에 넉백 계산시에 캐릭터의 넓이 만큼 경계 범위를 x 축의 범위(width)에 더해주어여한다. 
					
					//System.out.println("좌측 이동 캐릭터 위치 : " + enemy.get_enemy_Point().x + ", 캐릭터 좌측 시야 : " + (enemy.get_enemy_Point().x - enemy.get_Range_Site_Width()));
					if((hero.get_Hero_X_Point()+hero.get_Hero_Width()) < (enemy.get_Range_Site_Width_Left_Point() - enemy.get_Range_Site_Width_Right_Point()+hero.get_Hero_Width()) || 
							hero.get_Hero_X_Point() > (enemy.get_Range_Site_Width_Left_Point() - enemy.get_Range_Site_Width_Right_Point()+hero.get_Hero_Width()+enemy.get_Range_Site_Width_Right_Point()) ||
							(hero.get_Hero_Y_Point()+hero.get_Hero_Height()) < enemy.get_range_Site_Height_Top_Point() ||
							hero.get_Hero_Y_Point() > (enemy.get_range_Site_Height_Top_Point()+enemy.get_range_Site_Height_Bottom_Point())){
						
						enemy.set_Not_Find_Hero(); //캐릭터를 찾지 못했을때.
						
						
					}else {
						//System.out.println("충돌 판정");
						enemy.set_Find_Hero(mainCh.get_Hero_X_Point()); //캐릭터를 찾았을때
						
						//캐릭터와 적군이 직접 닿았을때. 좌측방향
						if((hero.get_Hero_X_Point()) < (enemy.get_enemy_Point().x - enemy.get_Enemy_Width()) || 
								hero.get_Hero_X_Point() > (enemy.get_enemy_Point().x - enemy.get_Enemy_Width()+enemy.get_Enemy_Width()) ||
								(hero.get_Hero_Y_Point()+hero.get_Hero_Height()) < enemy.get_enemy_Point().y ||
								hero.get_Hero_Y_Point() > (enemy.get_enemy_Point().y+enemy.get_Enemy_Height())){
							
						}else{
							//좌측방향 넉백
							hero.left_Knock_Back();
							//System.out.println("a");
						}
						
						
					}
					
					
					
				}else { //우측 탐지할때 경계 
					if((hero.get_Hero_X_Point()+hero.get_Hero_Width()) < (enemy.get_Range_Site_Width_Left_Point() ) || 
							hero.get_Hero_X_Point() > (enemy.get_Range_Site_Width_Left_Point()+enemy.get_Range_Site_Width_Right_Point()) ||
							(hero.get_Hero_Y_Point()+hero.get_Hero_Height()) < enemy.get_range_Site_Height_Top_Point() ||
							hero.get_Hero_Y_Point() > (enemy.get_range_Site_Height_Top_Point()+enemy.get_range_Site_Height_Bottom_Point())){
						enemy.set_Not_Find_Hero(); //캐릭터를 찾지 못했을때.
						
						
						
					}else {
						//System.out.println("충돌 판정");
						enemy.set_Find_Hero(mainCh.get_Hero_X_Point()); //캐릭터를 찾았을때
						
						//우측 면과 영웅이 닿았을때.
						if((hero.get_Hero_X_Point()+hero.get_Hero_Width()) < (enemy.get_enemy_Point().x ) || 
								hero.get_Hero_X_Point() > (enemy.get_enemy_Point().x + enemy.get_Enemy_Width()) ||
								(hero.get_Hero_Y_Point()+hero.get_Hero_Height()) < enemy.get_enemy_Point().y ||
								hero.get_Hero_Y_Point() > (enemy.get_enemy_Point().y+enemy.get_Enemy_Height())){
					
						}else {
							//우측방향 넉백
							hero.right_Knock_Back();
						}
						
					}
					
				}
	}
	
	
	
	
	
	
	
	
	
	//충돌 체크 함수 캐릭터와 총알 등 
	public void crash_Decide_Enemy(Weapon weapon, Enemy enemy, boolean get_Site){ //get_Site = 탐지구역이 좌측인지 우측인지

			
			if((weapon.getPoint().x+weapon.get_Weapon_Width()) < (enemy.get_enemy_Point().x ) || 
					weapon.getPoint().x > (enemy.get_enemy_Point().x+enemy.get_Enemy_Width()) ||
					(weapon.getPoint().y+weapon.get_Weapon_Height()) < enemy.get_enemy_Point().y ||
					weapon.getPoint().y > (enemy.get_enemy_Point().y+enemy.get_Enemy_Height())){
				
			}else {
				//System.out.println("충돌 판정");
				
				
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
				
				
				//넉백 하다가 발판보다 거리가 넘어가게되면 추락 시작
				if(enemy.get_enemy_Point().x >= enemy.get_Right_Bound_Site() ||
						enemy.get_enemy_Point().x + 30 <= enemy.get_Left_Bound_Site()){ //우측으로 떨어지고 좌측으로 떨어지고
					enemy.set_Down_Start_True();
				}
				
				
				
				weapon.set_Remove_Bullet_Choice(); //충돌 되면 총알의 상태를 삭제 상태로
			
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
			crash_Decide_Enemy(mainCh, enemy, enemy.get_Move_Site());
			
			
			//땅하고 적군하고 출동판정 땅에 떨어지고있을때만 하면 되긴 한다.
			for(int j=0; j< stage.get_Block().size(); j++ ){
			crash_Decide_Enemy_Block(stage.get_Block().get(j), enemy);
			}
			
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
		
		//enemy = new Walker(stage.get_Block().get(0).get_Left_Top_Point().x - 20,   //-20 인이유는 캐릭터를 블록 바깥 까지 밀어낼수 있도록하기 위함
		//		stage.get_Block().get(0).get_Left_Top_Point().x+stage.get_Block().get(0).get_Widht(), 
		//		stage.get_Block().get(0).get_Left_Top_Point().y - 70); //20, width 는 경계 범위 지정, height 는 몬스터가 밝고 있는 블럭의 높이
		
		//enemy_List.add(enemy);
		
	
		
		//생성된 스테이지의 워커를 그려야함
		for(int i=0; i<stage.get_Walker().size(); i++){
			//추가된 워커들을 바로밑의 블록에 안착시킨다.
			stage.get_Walker().get(i).set_Down_Start_True();
			enemy_List.add(stage.get_Walker().get(i));
			
		}
	
	
		
	}
	
	//총알을 발사중인가 아닌가 총알을 생성하는 함수
	public void bullet_Process(){
		if(attack){ //참일때 총알을 생성한다.
			
			if(weapon_Number == 1 && delay >= delay_End){ //권총일때
				
				pistol_Point = new Point(mainCh.get_Hero_X_Point(),mainCh.get_Hero_Y_Point()); //캐릭터의 좌쵸를 알아와서 위치 정보를 저장
				weapon = new Pistol(pistol_Point, mainCh.get_Face_Side_LFET_RIGHT()); //캐릭터의 좌표 지점에 권총을 생성한다.
				bullet_List.add(weapon); //권총의 공격을 불릿 리스트에 넣는다 블릿 리스z Weapon 클래스로 되어있다.
				
				
				//피스톨 발사시 딜레이 초기화
				delay = 0;
				
				//총알생성을 중지한다.. 한발쓰고 때었을떄 공격 가능
				attack = false;
			}
			
			
			if(weapon_Number == 2 && delay >= delay_End){ //쌍 권총일때
				
				pistol_Point = new Point(mainCh.get_Hero_X_Point(),mainCh.get_Hero_Y_Point()); //캐릭터의 좌쵸를 알아와서 위치 정보를 저장
				weapon = new Pistol(pistol_Point, mainCh.get_Face_Side_LFET_RIGHT()); //캐릭터의 좌표 지점에 권총을 생성한다.
				bullet_List.add(weapon); //권총의 공격을 불릿 리스트에 넣는다 블릿 리스z Weapon 클래스로 되어있다.
				
				pistol_Point = new Point(mainCh.get_Hero_X_Point()-20,mainCh.get_Hero_Y_Point()+5); //캐릭터의 좌쵸를 알아와서 위치 정보를 저장
				weapon = new Pistol(pistol_Point, mainCh.get_Face_Side_LFET_RIGHT()); //캐릭터의 좌표 지점에 권총을 생성한다.
				bullet_List.add(weapon); //권총의 공격을 불릿 리스트에 넣는다 블릿 리스z Weapon 클래스로 되어있다.
				
				//피스톨 발사시 딜레이 초기화
				delay = 0;
				
				//총알생성을 중지한다.. 한발쓰고 때었을떄 공격 가능
				attack = false;
			}
			
			
			if(weapon_Number == 3 && delay >= delay_End-5 && weapon_Number_3_Delay <= 5){ //다연발 총일때 5발씩 쏘고 제자리
				
				pistol_Point = new Point(mainCh.get_Hero_X_Point(),mainCh.get_Hero_Y_Point()); //캐릭터의 좌쵸를 알아와서 위치 정보를 저장
				weapon = new Pistol(pistol_Point, mainCh.get_Face_Side_LFET_RIGHT()); //캐릭터의 좌표 지점에 권총을 생성한다.
				bullet_List.add(weapon); //권총의 공격을 불릿 리스트에 넣는다 블릿 리스z Weapon 클래스로 되어있다.
				
				
				weapon_Number_3_Delay++;
				
				//총알생성을 중지한다.. 한발쓰고 때었을떄 공격 가능
				//attack = false; //5발 발사 하면 되돌려 놓는다.
			}else if(weapon_Number == 3){ //다연발이면서 딜레이를 돌려 놓을때.
				//피스톨 발사시 딜레이 초기화
				delay = 0;
				weapon_Number_3_Delay = 0;
				attack = false;
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
				
				
				//총알 발사 딜레이
				delay++;
				
				
				//점프 실행 매소드
				if(jump){
					
					mainCh.set_Hero_Jumping();
				}
				
				//캐릭터가 밟고있는 발판의 높이가 되어야한다.
				mainCh.jump_Move();
				
				
				//캐릭터의 횡축을 가져온다
				vertical_View = mainCh.get_Hero_Y_Point();
				
				
				repaint(); //화면을 지웠다 다시 그리기
				Thread.sleep(18); //20milli sec 로 스레드 돌리기
				
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
			//앉기 기능
			mainCh.set_Hero_Sit();
			break;
			
		case KeyEvent.VK_LEFT :
			mainCh.set_Hero_X_Left();
			break;
			
		case KeyEvent.VK_RIGHT :
			mainCh.set_Hero_X_Right();
			break;
			
		case KeyEvent.VK_A :
			
			
			
			
			//피스톨 / 기본 공격일때는 눌렀다 때야만 공격가능
			if(weapon_Number == 1 && weapon_Number1_Flag){
				//공격중일때 총알을 생성한다.
				attack = true;
				weapon_Number1_Flag = false; //눌렀을때만 발사 때었을때 true 재장전 가능
			}
			
			if(weapon_Number == 2 && weapon_Number2_Flag){
				//공격중일때 총알을 생성한다.
				attack = true;
				weapon_Number2_Flag = false; //눌렀을때만 발사 때었을때 true 재장전 가능
			}
			
			if(weapon_Number == 3 && weapon_Number3_Flag){
				//공격중일때 총알을 생성한다.
				attack = true;
				weapon_Number3_Flag = false; //눌렀을때만 발사 때었을때 true 재장전 가능
			}
			
			
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
			//놓였을때 서기
			mainCh.set_Hero_Stand();
			break;
			
		case KeyEvent.VK_LEFT :
			mainCh.set_Hero_X_Left_Stop();
			break;
			
		case KeyEvent.VK_RIGHT :
			mainCh.set_Hero_X_Right_Stop();
			break;
		case KeyEvent.VK_A :
			//총알생성을 중지한다..
		 //다연발이 아닐때
			attack = false;
			
			
			
			weapon_Number1_Flag = true; //기본무기재사용 가능
			
			weapon_Number2_Flag = true; //2번 무기 재사용가능
			
			weapon_Number3_Flag = true;
			break;
			
		case KeyEvent.VK_S :
			//점프 종료
			jump = false;
			break;
			
		case KeyEvent.VK_1 : //총 바꾸기 기본총
			weapon_Number = 1;//무기 변경
			break;
			
		case KeyEvent.VK_2 : //총 바꾸기 쌍 피스톨
			weapon_Number = 2;//무기 변경
			break;
		
		case KeyEvent.VK_3 : //총 바꾸기 다연발 총
			weapon_Number = 3;//무기 변경
			break;
		}
		
		
	}

	@Override
	//키보드가 타이핑 될 때 이벤트 처리하는 곳
	public void keyTyped(KeyEvent e) {
	}
	///////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
}