package mainframe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.JPanel;

import character.Hero;
import enemy.Enemy;
import enemy.Stage_1_Boss;
import enemy.Walker;
import enemy.Walker_Dog;
import mapData.Block;
import mapData.Next_Page_Portal;
import mapData.Stage;
import mapData.Trab_Saw_Tooth;
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
	
	
	Image walker_Dog;
	
	//무기이미지
	Image weapon_Png;
	
	//죽음 이미지
	Image death_Png;
	
	//영웅 시야 이미지
	Image Hero_View_Png;
	
	//땅 이미지
	Image ground_Png;
	
	//뒷 배경
	Image background_Img;
	
	//총알이 적군을 적중하면 피튀기는 효과
	Image Enemy_Blood;
	
	//포탈 이미지
	Image portal_Img;
	
	//톱니 이미지
	Image saw_Tooth_Img;
	
	//좌상단에 뜰 무기 이미지
	Image now_Weapone;
	
	//1스테이지 보스 이미지
	Image stage_1Boss_Img;
	
	//1스테이지 보스 레이저 공격 이미지
	Image boss_Attac_Raser;
	
	
	//더블 버퍼링용 이미지
	Image buffImage;
	Graphics buffg;
	
	
	
	
	//스레드 생성
	Thread th;
	
	
	//히어로 생성
	Hero mainCh;
	
	//문 생성
	Next_Page_Portal portal;
	
	//기본 적군 생성
	Enemy enemy;
	
	//스테이지를 생성한다
	Stage stage;
	
	
	
	
	//다음 스테이지로 넘어 갈 것인가.
	boolean end_Stage;
	
	//공격중인가 공격중이 아닌가
	boolean attack;
	
	//총 발사 이미지 변경하기 위해 
	boolean attack_Img_Temp;
	
	//무기 교체 할 때 번호
	int weapon_Number;
	
	//점프를 실행할 것인가 ? true 이면 실행한다.
	boolean jump;
	
	//총소리 사운드 넣기 위함
	Clip clip;
	File sound = new File("sound/pistol/pistol_1.wav");
	
	//1판 보스
	Stage_1_Boss stage_1_Boss;
	
	//공격 기본생성자
	Weapon weapon; 
	Point pistol_Point;
	
	//다수의 권총(알)을 담을 어레이 리스트
	ArrayList bullet_List = new ArrayList<Weapon>(); 
	
	//다수의 적군을 담을 어레이 리스트
	ArrayList enemy_List = new ArrayList<Enemy>();
	
	//함정: 톱니바퀴
	ArrayList trab_Saw_Tooth_List = new ArrayList<Trab_Saw_Tooth>();
	
	
	
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
		
		
		
		//백그라운드 이미지
		background_Img = tk.getImage("img/back_Ground/back_Ground_Stage_1.png");
		
		
		
		//죽음이미지
		death_Png = tk.getImage("img/redBackground_1.png");
				
	
		//영웅 시야 이미지
		Hero_View_Png = tk.getImage("img/Hero_View_1.png");
				
		//주인공 생성
		mainCh = new Hero();
		
		//문생성
		portal = new Next_Page_Portal(0,0);
		
		
		//총 이미지 변경 true 이면 변경시작
		attack_Img_Temp = false;
		
		
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

	//재시작하기 위함
	public void restart(){
		
		
		//메모리 누수 제거
		bullet_List.clear();
		enemy_List.clear();
		stage.reset_Memory();
		
		//이미지 버퍼링용
		Image_Init_Flag = true;
		
		mainCh = new Hero();
		
		
		//재시작 할 때마다 그 맵의 문 위치를 넣어주어야함
		//portal = new Next_Page_Portal(mainCh.getLocation()); //위치만 지정하면된다.
		
		
		stage = new Stage();
		stage.stage_Num(stage_Num); //현재 스테이지를 재성성한다.
		enemy_List.clear(); //현재 생성된 적군을 다 제거하고 새롭게 생성한다.
		enemy_Process(); 	//적군 생성 루틴
		
		System.out.println(bullet_List.size());
		
	}
	
	
	
	//이미지 버퍼링용
	private boolean Image_Init_Flag = true;
	public void paint(Graphics g){
		//더블버퍼링 버퍼 크기를 화면 크기와 같게 설정
		buffImage = createImage(width,height);
		//버퍼의 그래픽 객체 얻기
		buffg = buffImage.getGraphics();
		
		
		buffg.setColor(Color.gray);
		buffg.fillRect(0, 0, width, height);
		
		//배그라운드 이미지
		buffg.drawImage(background_Img, 0, 1000, this);
		
		if(Image_Init_Flag){
		Image_Init(); //이미지 버퍼링
		Image_Init_Flag = false;
		}
		
		update(g);
		

		
	}
	
	
	
	
	
	public void update(Graphics g){
		
		//색깔 입히기	
		buffg.setColor(Color.gray);
		//buffg.fillRect(0, 0, width, height);
		buffg.setColor(Color.black);
		
		
		
		
		//실제로 그려진 그림을 가져온다.
		draw();
		
		
				
		//스테이지를 그린다.
		draw_Stage();
		
		//총알을 그린다.
		draw_Bullet();
		
		//적군을 그린다.
		draw_Enemy();
		
		//영웅 이미지 그리기
		draw_Hero();
		
		
		
		//캐릭터의 횡축을 가져온다
		vertical_View = mainCh.get_Hero_Y_Point();
		
		
		//buffg.drawImage(death_Png, 0,vertical_View - 1000, this); //죽었을때 이벤트
		
		
		//무기 이미지를 그려 넣는다.
		if(weapon_Number == 1){
		now_Weapone = tk.getImage("img/weapone_Right_1/now_Pistol.png");
		}else if(weapon_Number == 2){
			now_Weapone = tk.getImage("img/weapone_Right_2/now_Pistol.png");
		}else if(weapon_Number == 3){
			now_Weapone = tk.getImage("img/weapone_Right_3/now_Pistol.png");
		}
		buffg.drawImage(now_Weapone, 70, vertical_View - 600, this);
		
		
		//화면에 버퍼에 그린 그림을 가져와 그리기
		g.drawImage(buffImage, 0, (height-1300)-vertical_View, this);
	}
	
	public void draw_Hero(){
		
		//프레임에 저장된 .png 이미지를 그려넣습니다. 영웅 그려넣기
		buffg.drawImage(hero_Png, mainCh.get_Hero_X_Point()-7, mainCh.get_Hero_Y_Point()-17, this);
		
		//영웅 피격시 사망
		if(mainCh.get_Hero_Hp() <= 0){
			buffg.drawImage(death_Png, 0,vertical_View - 1000, this); //죽었을때 이벤트
			//죽으면 움직이지 못해야 한다.
			
			
		}
		
		
		
				
		//영웅이 보는 시각에 따라 암전 형태가 변경 좌우 변경
				if(!mainCh.get_Face_Side_LFET_RIGHT()){ //오른쪽 방향 보고 있을때
					//주인공의 기본 이미지 삽입
					hero_Png = tk.getImage("img/Hero_Move_Right/hero_Right_1.png");
					if(!mainCh.get_Sit_State()){ //앉아 있을떄 기본이미지 변경
						hero_Png = tk.getImage("img/Hero_Move_Right_Down/hero_Move_Right_1.png");
					}
					//System.out.println(mainCh.get_View_Temp_Int_Plus()); //1 - 2 - 3 - 4 
					//점프중일때 오른쪽 점프 이미지
					if(mainCh.get_Jump_Hero()){
						//오른쪽으로 점프중일때
						hero_Png = tk.getImage("img/hero_Jump_Right.png"); //
					}
					
					Hero_View_Png = tk.getImage("img/Hero_View_Right/Hero_View_Right_" + mainCh.get_View_Temp_Int_Plus() + ".png");
					//buffg.drawImage(Hero_View_Png, mainCh.get_Hero_X_Point()- 1050,  mainCh.get_Hero_Y_Point() - 990, this); //영웅 암전
					
				}else{//왼쪽 방향 보고 있을때
					//주인공의 기본 이미지 삽입
					hero_Png = tk.getImage("img/Hero_Move_Left/hero_Left_1.png");
					
					if(!mainCh.get_Sit_State()){ //앉아 있을떄 기본이미지 변경
						hero_Png = tk.getImage("img/Hero_Move_Left_Down/hero_Move_Left_1.png");
					}
					//System.out.println(mainCh.get_View_Temp_Int_Minus()); //4 - 3 - 2 - 1
					
					//점프중일때 왼쪽 점프 이미지
					if(mainCh.get_Jump_Hero()){
						//오른쪽으로 점프중일때
						hero_Png = tk.getImage("img/hero_Jump_Left.png"); //
					}
					
					Hero_View_Png = tk.getImage("img/Hero_View_Left/Hero_View_Left_" + mainCh.get_View_Temp_Int_Minus() + ".png");
					//buffg.drawImage(Hero_View_Png, mainCh.get_Hero_X_Point()- 1500,  mainCh.get_Hero_Y_Point() - 990, this); //영웅 암전	
					
				}
				

				//오른쪽으로 걸어갈때마다
				if(mainCh.get_X_Flag_Right()){
				//System.out.println(mainCh.set_Right_Walk_Plus());
					
					if(mainCh.get_Jump_Hero()){
						//오른쪽으로 점프중일때
						hero_Png = tk.getImage("img/hero_Jump_Right.png"); //
					}else if(!mainCh.get_Sit_State()){//오른쪽으로 앉아서 갈때
						hero_Png = tk.getImage("img/Hero_Move_Right_Down/hero_Move_Right_"+mainCh.set_Right_Walk_Plus()+".png"); //오른쪽으로 앉아서 갈때 걸어다니는 이미지
					}else{
					hero_Png = tk.getImage("img/Hero_Move_Right/hero_Right_"+mainCh.set_Right_Walk_Plus()+".png"); //오른쪽으로 걸어다니는 이미지
					
					}
					
					
				}else if(mainCh.get_X_Flag_Left()){
					if(mainCh.get_Jump_Hero()){
						//오른쪽으로 점프중일때
						hero_Png = tk.getImage("img/hero_Jump_Left.png"); //
					}else if(!mainCh.get_Sit_State()){ //왼쪽으로 앉아서 갈때
						hero_Png = tk.getImage("img/Hero_Move_Left_Down/hero_Move_Left_"+mainCh.set_Right_Walk_Plus()+".png");
					}
					else{
					hero_Png = tk.getImage("img/Hero_Move_Left/hero_Left_"+mainCh.set_Right_Walk_Plus()+".png"); //왼쪽으로 걸어다니는 이미지
					}
				}
				//왼쪽으로 걸어갈때
				
				
				
				
				
				
				//1번 기본총이면서 오른쪽을 볼때
				if(weapon_Number == 1 && !mainCh.get_Face_Side_LFET_RIGHT()){
					
					//총이 발사되면 피스톨 1~7까지 반복
					weapon_Png = tk.getImage("img/weapone_Right_1/pistol_Right_1.png");
					
					if(attack_Img_Temp){//공격 버튼 눌림 7까지 반복하면 false 로 변경 해야함
						mainCh.set_Trigger_State(); //트리거를 1씩 증가며 8이 되었을때 다시 1로 만든다 
						weapon_Png = tk.getImage("img/weapone_Right_1/pistol_Right_" + mainCh.get_Trigger_State() + ".png");
						if(mainCh.get_Trigger_State() == 1){ //트리거가 1이 되었을때 공격이 종료된것
							attack_Img_Temp = false;
						}
					}
					
					//총 이미지 버퍼에 그려넣기
					buffg.drawImage(weapon_Png, mainCh.get_Hero_X_Point()-7, mainCh.get_Hero_Y_Point()-17, this); 
					
				}
				
				//1번 기본총이면서 왼쪽을 볼때
				if(weapon_Number == 1 && mainCh.get_Face_Side_LFET_RIGHT()){
					
					//총이 발사되면 피스톨 1~7까지 반복
					weapon_Png = tk.getImage("img/weapone_Left_1/pistol_Left_1.png");
					
					if(attack_Img_Temp){//공격 버튼 눌림 7까지 반복하면 false 로 변경 해야함
						mainCh.set_Trigger_State(); //트리거를 1씩 증가며 8이 되었을때 다시 1로 만든다 
						weapon_Png = tk.getImage("img/weapone_Left_1/pistol_Left_" + mainCh.get_Trigger_State() + ".png");
						if(mainCh.get_Trigger_State() == 1){ //트리거가 1이 되었을때 공격이 종료된것
							attack_Img_Temp = false;
						}
					}
					
					//총 이미지 버퍼에 그려넣기
					buffg.drawImage(weapon_Png, mainCh.get_Hero_X_Point()-41, mainCh.get_Hero_Y_Point()-17, this); 
				}
				
				
				
				//2번 쌍권총이면서 오른쪽을 볼때
				if(weapon_Number == 2 && !mainCh.get_Face_Side_LFET_RIGHT()){
					
					//총이 발사되면 피스톨 1~7까지 반복
					weapon_Png = tk.getImage("img/weapone_Right_2/pistol_Right_1.png");
					
					if(attack_Img_Temp){//공격 버튼 눌림 7까지 반복하면 false 로 변경 해야함
						mainCh.set_Trigger_State(); //트리거를 1씩 증가며 8이 되었을때 다시 1로 만든다 
						weapon_Png = tk.getImage("img/weapone_Right_2/pistol_Right_" + mainCh.get_Trigger_State() + ".png");
						if(mainCh.get_Trigger_State() == 1){ //트리거가 1이 되었을때 공격이 종료된것
							attack_Img_Temp = false;
						}
					}
					
					//총 이미지 버퍼에 그려넣기
					buffg.drawImage(weapon_Png, mainCh.get_Hero_X_Point()-7, mainCh.get_Hero_Y_Point()-17, this); 
					
				}
				
				//2번 기본총이면서 왼쪽을 볼때
				if(weapon_Number == 2 && mainCh.get_Face_Side_LFET_RIGHT()){
					
					//총이 발사되면 피스톨 1~7까지 반복
					weapon_Png = tk.getImage("img/weapone_Left_2/pistol_Left_1.png");
					
					if(attack_Img_Temp){//공격 버튼 눌림 7까지 반복하면 false 로 변경 해야함
						mainCh.set_Trigger_State(); //트리거를 1씩 증가며 8이 되었을때 다시 1로 만든다 
						weapon_Png = tk.getImage("img/weapone_Left_2/pistol_Left_" + mainCh.get_Trigger_State() + ".png");
						if(mainCh.get_Trigger_State() == 1){ //트리거가 1이 되었을때 공격이 종료된것
							attack_Img_Temp = false;
						}
					}
					
					//총 이미지 버퍼에 그려넣기
					buffg.drawImage(weapon_Png, mainCh.get_Hero_X_Point()-41, mainCh.get_Hero_Y_Point()-17, this); 
				}
				
				
				//3번 기관총이면서 오른쪽을 볼때
				if(weapon_Number == 3 && !mainCh.get_Face_Side_LFET_RIGHT()){
					
					//총이 발사되면 피스톨 1~7까지 반복
					weapon_Png = tk.getImage("img/weapone_Right_3/pistol_Right_1.png");
					
					if(attack_Img_Temp){//공격 버튼 눌림 7까지 반복하면 false 로 변경 해야함
						mainCh.set_Trigger_State(); //트리거를 1씩 증가며 8이 되었을때 다시 1로 만든다 
						weapon_Png = tk.getImage("img/weapone_Right_3/pistol_Right_" + mainCh.get_Trigger_State() + ".png");
						if(mainCh.get_Trigger_State() == 1){ //트리거가 1이 되었을때 공격이 종료된것
							attack_Img_Temp = false;
						}
					}
					
					//총 이미지 버퍼에 그려넣기
					buffg.drawImage(weapon_Png, mainCh.get_Hero_X_Point()-7, mainCh.get_Hero_Y_Point()-23, this); 
					
				}
				
				
				//13 기관총이면서 왼쪽을 볼때
				if(weapon_Number == 3 && mainCh.get_Face_Side_LFET_RIGHT()){
					
					//총이 발사되면 피스톨 1~7까지 반복
					weapon_Png = tk.getImage("img/weapone_Left_3/pistol_Left_1.png");
					
					if(attack_Img_Temp){//공격 버튼 눌림 7까지 반복하면 false 로 변경 해야함
						mainCh.set_Trigger_State(); //트리거를 1씩 증가며 8이 되었을때 다시 1로 만든다 
						weapon_Png = tk.getImage("img/weapone_Left_3/pistol_Left_" + mainCh.get_Trigger_State() + ".png");
						if(mainCh.get_Trigger_State() == 1){ //트리거가 1이 되었을때 공격이 종료된것
							attack_Img_Temp = false;
						}
					}
					
					//총 이미지 버퍼에 그려넣기
					buffg.drawImage(weapon_Png, mainCh.get_Hero_X_Point()-41, mainCh.get_Hero_Y_Point()-23, this); 
				}
				
				
				
			
				
				
				
				
				
				
				
				
				
				
	}
	
	
	//실제로 그림들을 그릴 부분
	public void draw(){
		//(0,0) ~ (width,height) 까지 화면을 지웁니
		//buffg.clearRect(0, 0, width, height);
		
		
		
		
		
		
		
		
		
		//프레임에 저장된 .png 이미지를 그려넣습니다.
		//buffg.drawImage(hero_Png, mainCh.get_Hero_X_Point()-15, mainCh.get_Hero_Y_Point()-17, this);
		buffg.drawRect(mainCh.get_Hero_X_Point(),   mainCh.get_Hero_Y_Point(), mainCh.get_Hero_Width(),  mainCh.get_Hero_Height()); //캐릭터 사각형으로 일단 대체
	}
	
	//땅이미지 변경해줌
	int ground_Img_Temp = 1;
	
	
	//배경 움직임
	int background_Rotation = 1;
	int background_Rotation_Delay = 1;
	public int background_Move(){
		
		if(background_Rotation_Delay % 20 == 0){
		background_Rotation++;
		background_Rotation_Delay = 0;
		}
		if(background_Rotation >= 6){
			background_Rotation = 1;
		}
		background_Rotation_Delay++;
		return background_Rotation;
	}
	
	
	//스테이지 맵 을 그림 (블록)
	public void draw_Stage(){
		
		//다음 스테이지로 넘어감
		if(end_Stage){
			stage_Num++; //다음 스테이지로 넘어감 //현 위치 초기화 1스테이지
			//System.out.println();
			//스테이지넘버를 한번 반영해서 스테이지를 만든다.
			//stage_Num = 1;
			stage.map_Stage(stage_Num);

			//기본 적군 워커 생성
			enemy_Process();
			
			end_Stage = false;
			
			//1판 일때 1스테이지 보스 생성
			if(stage_Num == 1){
				stage_1_Boss = new Stage_1_Boss();
			}
			
		}
		
		//생성된 스테이지의 블록을 그려야함 
		int temp = 0;
		
		
		
		//땅 이미지넣기
		
		//땅 이미지 넣기 나누기 x축
		int ground_Temp_X;
		
		
		
		//스테이지 마다 그리기 -> if(end_Stage) 안쪽으로 넣어도됨
		//1스테이지
		if(stage_Num == 1){
			
			
		//background_Img = tk.getImage("img/back_Ground/stage1/back_Ground_Stage_1_"+ background_Move() +".png");  //배경 그리기
			background_Img = tk.getImage("img/back_Ground/back_Ground_Stage_1.png"); //배경 그리기
		ground_Png = tk.getImage("img/ground/stage/stage_1.png"); //바닥 그리기
		buffg.drawImage(ground_Png, 0, 1785, this);
		}
		//2스테이지
		if(stage_Num == 2){
		background_Img = tk.getImage("img/back_Ground/back_Ground_Stage_2.png"); //배경 그리기
		ground_Png = tk.getImage("img/ground/stage/stage_2.png"); //바닥 그리기
		buffg.drawImage(ground_Png, 0, 1485, this);
		}
		
		//3스테이지
		if(stage_Num == 3){
		background_Img = tk.getImage("img/back_Ground/back_Ground_Stage_2.png"); //배경 그리기
		ground_Png = tk.getImage("img/ground/stage/stage_1.png"); //바닥 그리기
		buffg.drawImage(ground_Png, 0, 1785, this);
		}
		
		
		//6스테이지
		if(stage_Num == 6){
			
			ground_Png = tk.getImage("img/ground/stage/stage_1.png"); //바닥 그리기
			buffg.drawImage(ground_Png, 0, 1785, this);
		}
		
		for(int i=0; i<stage.get_Block().size(); i++){
			
			//땅이미지 변경해줌
			ground_Img_Temp = 1;
			
			//생성된 맵의 x측 길이를 통해 그라운드 이미지가 몇개 들어갈것인지 그린다.
			ground_Temp_X = stage.get_Block().get(i).get_Left_Top_Point().x;
			
			
			while(true){ //바닥 그리기
				//높이가 높은 벽일때
				if(stage.get_Block().get(i).get_Height() > 60){
					ground_Png = tk.getImage("img/ground/ground_Img_" + ground_Img_Temp + ".png");
					buffg.drawImage(ground_Png, ground_Temp_X, stage.get_Block().get(i).get_Left_Top_Point().y-25, this);
					
					ground_Temp_X+=20; //10범위마다 하나씩 그림
					if(ground_Temp_X+20 >= (stage.get_Block().get(i).get_Widht())+stage.get_Block().get(i).get_Left_Top_Point().x){ //width 길이를 20으로 나누어서 20단위로 ground 이미지를 넣는다.
						ground_Png = tk.getImage("img/ground/ground_Img_" + (ground_Img_Temp +1)+ ".png");
						//ground_Png = tk.getImage("img/ground/ground_Img_51.png"); //벽 매무새
						buffg.drawImage(ground_Png, ground_Temp_X, stage.get_Block().get(i).get_Left_Top_Point().y-25, this);
						break;
					}
					ground_Img_Temp++;
					if(ground_Img_Temp == 52){//그림보다 사진량이 많아지면 안된다.
						ground_Img_Temp = 1;
					}
				}else{ //높이가 낮은 벽일때
					ground_Png = tk.getImage("img/ground_Img/ground_Img2_" + ground_Img_Temp + ".png");
					buffg.drawImage(ground_Png, ground_Temp_X, stage.get_Block().get(i).get_Left_Top_Point().y, this);
					
					ground_Temp_X+=10; //10범위마다 하나씩 그림
					if(ground_Temp_X+20 >= (stage.get_Block().get(i).get_Widht())+stage.get_Block().get(i).get_Left_Top_Point().x){ //width 길이를 20으로 나누어서 20단위로 ground 이미지를 넣는다.
						ground_Png = tk.getImage("img/ground_Img/ground_Img2_11.png"); //벽 매무새
						buffg.drawImage(ground_Png, ground_Temp_X, stage.get_Block().get(i).get_Left_Top_Point().y, this);
						break;
					}
					ground_Img_Temp++;
					if(ground_Img_Temp == 12){//그림보다 사진량이 많아지면 안된다.
						ground_Img_Temp = 1;
					}
				}
				 //fillRect
				//buffg.fillRect(stage.get_Block().get(i).get_Left_Top_Point().x,
					//	stage.get_Block().get(i).get_Left_Top_Point().y,
						//stage.get_Block().get(i).get_Widht(), 
						//stage.get_Block().get(i).get_Height());
			}
			
			
			
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
		//5@1811@973@77벽임워커포탈904@1741
		}
		
		
		//톱니 바퀴 그리기
		for(int i=0; i<stage.get_Trab_Saw_Tooth().size(); i++){
			//톱니 바퀴 생성위치
			buffg.drawOval(stage.get_Trab_Saw_Tooth().get(i).Get_Trab_Saw_tooth_Point().x - stage.get_Trab_Saw_Tooth().get(i).get_Radius() ,
					stage.get_Trab_Saw_Tooth().get(i).Get_Trab_Saw_tooth_Point().y - stage.get_Trab_Saw_Tooth().get(i).get_Radius(),
					stage.get_Trab_Saw_Tooth().get(i).get_Radius() * 2,
					stage.get_Trab_Saw_Tooth().get(i).get_Radius() * 2);
			
			saw_Tooth_Img = tk.getImage("img/back_Ground/saw_Tooth/saw_Tooth_" + stage.get_Trab_Saw_Tooth().get(i).set_Rotation() + ".png");
			buffg.drawImage(saw_Tooth_Img, stage.get_Trab_Saw_Tooth().get(i).Get_Trab_Saw_tooth_Point().x - stage.get_Trab_Saw_Tooth().get(i).get_Radius() + 4,
					stage.get_Trab_Saw_Tooth().get(i).Get_Trab_Saw_tooth_Point().y - stage.get_Trab_Saw_Tooth().get(i).get_Radius()+2, this);
			
			
			
			//톱니와 캐릭터 충돌판정
			crash_Decide_Hero_Saw_Tooth(mainCh, stage.get_Trab_Saw_Tooth().get(i));
		}
		
		
		
		//문생성 스테이지 생성할때 받아와야한다.
		portal = stage.get_Next_Page_Portal();
		
		//포탈이미지 그리기
		portal_Img = tk.getImage("img/portal/portal_" +portal.set_portal_Img_Cut()+ ".png");
		//portal_Img = tk.getImage("img/portal/portal.png");
		buffg.drawImage(portal_Img, portal.get_Left_Top_Point().x, portal.get_Left_Top_Point().y, this);
		//System.out.println(portal.get_Left_Top_Point().y);
		
		//포탈에 캐릭터가 들어가면
		crash_Decide_Block(mainCh, portal);
		
	}
	
	//톱니 캐릭터 충돌 판정
	int rect_Left_Boundary;
	int rect_Right_Boundary;
	int rect_Top_Boundary;
	int rect_Bottom_Boundary;
	public void crash_Decide_Hero_Saw_Tooth(Hero hero, Trab_Saw_Tooth trab_Saw_Tooth){
		//원 하고 사각형 충돌 판정 = 원의 반지름을 사각형의 좌우상하 에 더해서 원의 가운데 포인트가 접하는지 하지 않는지 체크한다.
		rect_Left_Boundary = hero.get_Hero_X_Point() - trab_Saw_Tooth.get_Radius();
		rect_Right_Boundary = hero.get_Hero_X_Point() + hero.get_Hero_Width() + trab_Saw_Tooth.get_Radius() - 10;
		rect_Top_Boundary = hero.get_Hero_Y_Point() - trab_Saw_Tooth.get_Radius();
		rect_Bottom_Boundary = hero.get_Hero_Y_Point() + hero.get_Hero_Height() + trab_Saw_Tooth.get_Radius();
		
		if(trab_Saw_Tooth.Get_Trab_Saw_tooth_Point().x >= rect_Left_Boundary && trab_Saw_Tooth.Get_Trab_Saw_tooth_Point().x <= rect_Right_Boundary &&
				trab_Saw_Tooth.Get_Trab_Saw_tooth_Point().y >= rect_Top_Boundary && trab_Saw_Tooth.Get_Trab_Saw_tooth_Point().y <= rect_Bottom_Boundary){
			System.out.println("원하고 겹침");
		}
		
		
	}
	
	
	//오른쪽으로 쏠때 약간더 오른쪽에서 공격해야함
	int bullet_Plus_Temp = 35;
	
	//총알을 그리는 함수
	public void draw_Bullet(){
		
		
		
		
		//총알 개수 만큼 반복하며 그린다.
		for(int i=0; i<bullet_List.size(); i++){
			weapon = (Weapon) bullet_List.get(i);
			
			//오른쪽으로 날아갈때
			if(!weapon.get_Bullet_Side_LEFT_RIGHT()){
				bullet_Png = tk.getImage("img/right_Bullet.png");
				bullet_Plus_Temp = 35;
			}else {
				bullet_Png = tk.getImage("img/Left_Bullet.png");
				bullet_Plus_Temp = 0;
			}
			
			if(weapon instanceof Pistol){ //불릿 리스트에 정보가 피스톨로 변형 가능하다면.
				
				buffg.drawImage(bullet_Png, weapon.getPoint().x + bullet_Plus_Temp,  weapon.getPoint().y, this);
				//buffg.drawRect(weapon.getPoint().x,  weapon.getPoint().y, weapon.get_Weapon_Width(),  weapon.get_Weapon_Height()); //사각형으로 일단 대체
				
				//피스톨 총알 제각기 의 방향성을 가지고 날아간다.
				//((Pistol) weapon).pistol_Move( weapon.get_Bullet_Side_LEFT_RIGHT() ); // -> 쓰레드로 변경
				
			}
			
			//총알과 적군의 충돌판정 함수 호출 
			for(int j=0; j<enemy_List.size(); j++){
			enemy = (Enemy) enemy_List.get(j);
			crash_Decide_Enemy(weapon, enemy, enemy.get_Move_Site());
			}
			
			//땅하고 적군하고 충돌판정 땅에 떨어지고있을때만 하면 되긴 한다.
			for(int j=0; j< stage.get_Block().size(); j++ ){			
			//crash_Decide_Enemy_Block(stage.get_Block().get(j), enemy);
			
			//땅하고 총알 하고 충돌판정
			crash_Decide_Weapon_Block(weapon, stage.get_Block().get(j));
			
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
	
	
	//포탈과 캐릭터 중복 확인
	public void crash_Decide_Block(Hero hero, Next_Page_Portal portal){
		if((hero.get_Hero_X_Point()+hero.get_Hero_Width()-30) < (portal.get_Left_Top_Point().x ) || 
				hero.get_Hero_X_Point() > (portal.get_Left_Top_Point().x+50) ||
				(hero.get_Hero_Y_Point()+hero.get_Hero_Height()) < portal.get_Left_Top_Point().y ||
				hero.get_Hero_Y_Point() > (portal.get_Left_Top_Point().y+50)){
			
		}else{
			
			//포탈 탔을때 다음판으로 넘어감
			stage_Num++;
			restart();
			
		}
	}
	
	
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
				if(hero.get_Hero_Y_Point() >= block.get_Left_Top_Point().y + block.get_Height() - 10){
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
					//System.out.println("벽의 왼쪽 면 부딛힘");
					//오른쪽으로 전진 못하도록 막아야함
					mainCh.stop_Move_Right(hero.get_Hero_X_Point());
				}
				//캐릭터 좌측과 벽의 우측이 박았을때
				else if(hero.get_Hero_X_Point() >= block.get_Left_Top_Point().x + block.get_Widht() - 25){
					//System.out.println("벽의 오른쪽 면 부딛힘");
					//왼쪽으로 전진 못하도록 막아야함
					mainCh.stop_Move_Leftt(hero.get_Hero_X_Point());
					
				}else if(hero.get_Hero_Y_Point() >= block.get_Left_Top_Point().y + block.get_Height() - 20){
					//-20은 캐릭터의 위치와 발판의 위치에의해 변경될수 있다. 이쪽 코드는 캐릭터가 앉은 상태에서 일어나지 못하도록 함
					//앉기 기능
					mainCh.set_Hero_Sit();
					
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
					(block.get_Left_Top_Point().y + block.get_Height()) <= enemy.get_enemy_Point().y + enemy.get_Enemy_Height() - 10 ||
					block.get_Left_Top_Point().y-1 >= (enemy.get_enemy_Point().y+enemy.get_Enemy_Height())){
			
			
			//enemy.get_Enemy_Exit_Yoint(1000);
			
		}else{
			//System.out.println("적군 땅에 닿아 있음");
		
			
			//닿아 있을때 발판의 위치를 리턴한다, 
			enemy.get_Enemy_Exit_Yoint(block.get_Left_Top_Point().y  - enemy.get_Enemy_Height() );
			//추적 알고리즘도 같이 떨궈야한다.
			enemy.init_Range_Site(enemy.get_enemy_Point().x, enemy.get_enemy_Point().y);
			//적군을 블록위에 안착시킨다.
			enemy.init_Bound_Site(block.get_Left_Top_Point().x, (block.get_Widht() + block.get_Left_Top_Point().x), block.get_Left_Top_Point().y - block.get_Height());
			
		}
			
		}
		
		//적군 좌우측 벽으로 못들어가게 만듬
		if((block.get_Left_Top_Point().x + block.get_Widht()) <= (enemy.get_enemy_Point().x ) || 
				block.get_Left_Top_Point().x >= (enemy.get_enemy_Point().x+enemy.get_Enemy_Width()) ||
				(block.get_Left_Top_Point().y + block.get_Height()) <= enemy.get_enemy_Point().y + enemy.get_Enemy_Height() - 10 ||
				block.get_Left_Top_Point().y >= (enemy.get_enemy_Point().y+enemy.get_Enemy_Height())){
		
		}else{
			
			//벽에 부딛힐떄 방향전환 해주어야하며, 20정도 밀쳐 내야함
			
			
			//몬스터의 방향을 반대로 꺽어야함
			if(enemy.get_Move_Site()){ 
				enemy.set_Move_Site(false);
				enemy.knockback(true); //벽에서 밀처주어야 안끼인다.
			}else {
				enemy.set_Move_Site(true);
				enemy.knockback(false);
			}
			
			
			
			/*
			//몬스터 오른쪽과 벽의 왼쪽 면 set_Move_Site //flag 가 ture 이면 왼쪽으로 전환
			if((enemy.get_enemy_Point().x+enemy.get_Enemy_Width()) >=  block.get_Left_Top_Point().x){
				enemy.set_Move_Site(true);
				//enemy.knockback(false);//넉백으로 한번 밀처낸다.
			}else { //몬스터 왼쪽과 벽의 오른쪽 면 
				
				enemy.set_Move_Site(false);
				//enemy.knockback(true);//넉백으로 한번 밀처낸다.
			}
			*/
			
		}
		
		
		
		
	}
	
	
	
	
	//충돌체크 
	
	
	
	
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
						
						enemy.set_Find_Hero(mainCh.get_Hero_X_Point()); //캐릭터를 찾았을때
						
						//캐릭터와 적군이 직접 닿았을때. 좌측방향
						if((hero.get_Hero_X_Point() + +hero.get_Hero_Width()) < (enemy.get_enemy_Point().x) || 
								hero.get_Hero_X_Point() > (enemy.get_enemy_Point().x + enemy.get_Enemy_Width()) ||
								(hero.get_Hero_Y_Point()+hero.get_Hero_Height()) < enemy.get_enemy_Point().y ||
								hero.get_Hero_Y_Point() > (enemy.get_enemy_Point().y+enemy.get_Enemy_Height())){
							
						}else{
							
							//좌측방향 넉백
							if(mainCh.get_Hero_Hp() > 0) //영웅이 살아있어야 넉백
							hero.left_Knock_Back();
							//System.out.println("a");
							//여웅 피 떨어짐
							//mainCh.set_Hero_Hp_Minus();
							
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
							if(mainCh.get_Hero_Hp() > 0) //영웅이 살아있어야 넉백
							hero.right_Knock_Back();
							
							//여웅 피 떨어짐
							//mainCh.set_Hero_Hp_Minus();
						}
						
					}
					
				}
	}
	
	
	
	
	//총알과 벽의 충돌검사
	public void crash_Decide_Weapon_Block(Weapon Weapon, Block block){
		if((weapon.getPoint().x+weapon.get_Weapon_Width()) < (block.get_Left_Top_Point().x ) || 
				weapon.getPoint().x > (block.get_Left_Top_Point().x+block.get_Widht()) ||
				(weapon.getPoint().y+weapon.get_Weapon_Height()) < block.get_Left_Top_Point().y ||
				weapon.getPoint().y > (block.get_Left_Top_Point().y+block.get_Height())){
			
			
		}else{ //충돌
			//System.out.println("총 벽 충돌");
			bullet_List.remove(weapon);
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
					//넉백과 동시에 피튀기는효과
						
				}
				
				//피격된 몬스터를 자신의 방향으로 달려오도록 해야한다. 즉 몬스터를 공격 상태로 변경해야한다.
				if(enemy.get_enemy_Point().x <= mainCh.get_Hero_X_Point()){
					enemy.set_Move_Site(false);
					//적군 넉백효과
					enemy.knockback(false);
				
				}
				
				
				//넉백 하다가 발판보다 거리가 넘어가게되면 추락 시작
				//if(enemy.get_enemy_Point().x >= enemy.get_Right_Bound_Site() ||
				//		enemy.get_enemy_Point().x + 30 <= enemy.get_Left_Bound_Site()){ //우측으로 떨어지고 좌측으로 떨어지고
				//	enemy.set_Down_Start_True();
				//}
				
				//총알이 적군을 피격함, 출혈 이벤트 시작
				enemy.set_Blood_Event_Flag();
				
				weapon.set_Remove_Bullet_Choice(); //충돌 되면 총알의 상태를 삭제 상태로
			
		}
		
	}
	
	
	//적군을 그리는 함수
	public void draw_Enemy(){
		
		
		
		//적군의 수를 반복하여 그린다
		for(int i=0; i<enemy_List.size(); i++){
			
			enemy = (Enemy) enemy_List.get(i);
			
			
			//발판보다 거리가 넘어가거나/공중에 있을때 추락 시작
			if(enemy.get_enemy_Point().x >= enemy.get_Right_Bound_Site() ||
					enemy.get_enemy_Point().x + 30 <= enemy.get_Left_Bound_Site()){ //우측으로 떨어지고 좌측으로 떨어지고
				
				enemy.set_Down_Start_True();
				//추적 알고리즘도 함께 움직야야한다.
				//if(enemy instanceof Walker){ 
				enemy.init_Range_Site(stage.get_Enemy().get(i).get_enemy_Point().x, stage.get_Enemy().get(i).get_enemy_Point().y);
				//}
				//System.out.println("a");
				
				//에너미가 벽 너머로 밀린다면 삭제
				if(enemy.get_enemy_Point().x + enemy.get_Enemy_Width() < 0 || enemy.get_enemy_Point().x > width){
					enemy_List.remove(i); //벽너머로 밀린 적군 삭제
				}
				
			}
			
			
			
			if(enemy instanceof Walker){ //에너미중 워커의 객체가 있다면 그려라
				
				
				//walker_Png = tk.getImage("img/walker_Right_Nomal_1/walker_Right_Nomal_1.png");
				
				
				//적군 이미지 적군이 오른쪽으로 이동중일때
				if(!(enemy).get_Right_Flag()){
					
					if(((Walker) enemy).get_Find_Hero()){
 						walker_Png = tk.getImage("img/walker_Right_Attack/walker_Right_Attack_" + ((Walker) enemy).set_Right_Walk_Plus() + ".png"); //공격 범위내에 영웅이 있을때
 					}else {
 						walker_Png = tk.getImage("img/walker_Right_Nomal/walker_Right_Nomal_" + ((Walker) enemy).set_Right_Walk_Plus() + ".png");
  					}
					
					//처맞았을때 피흘리기 시작
					if(enemy.get_Blood_Event_Flag()){
					enemy.set_Blood_Event_Count();
					Enemy_Blood = tk.getImage("img/Right_Blood/walker_Right_Blood_" + enemy.get_Blood_Event_Count() + ".png");
					buffg.drawImage(Enemy_Blood, enemy.get_enemy_Point().x - 5, enemy.get_enemy_Point().y+8, this); 
					}
					
				}else{//적군이 왼쪽으로 이동할때
					if(((Walker) enemy).get_Find_Hero()){
						walker_Png = tk.getImage("img/walker_Left_Attack/walker_Left_Attack_" + ((Walker) enemy).set_Right_Walk_Plus() + ".png");
					}else{
					walker_Png = tk.getImage("img/walker_Left_Nomal/walker_Left_Nomal_" + ((Walker) enemy).set_Right_Walk_Plus() + ".png");
					}
				}
				
				//처맞았을때 피흘리기 시작
				if(enemy.get_Blood_Event_Flag()){
				enemy.set_Blood_Event_Count();
				Enemy_Blood = tk.getImage("img/Left_Blood/walker_Left_Blood_" + enemy.get_Blood_Event_Count() + ".png");
				buffg.drawImage(Enemy_Blood, enemy.get_enemy_Point().x - 25, enemy.get_enemy_Point().y+8, this); 
				}
					
				buffg.drawImage(walker_Png, enemy.get_enemy_Point().x - 5, enemy.get_enemy_Point().y+8, this); //+8을 하는 이유는 안하면 공중부양함
				buffg.drawRect(enemy.get_enemy_Point().x,  enemy.get_enemy_Point().y, ((Walker) enemy).get_Enemy_Width(),  ((Walker) enemy).get_Enemy_Height()); //사각형으로 일단 대체
				
			}
			
			
			//워커독 구현
			if(enemy instanceof Walker_Dog){
				
				
				//워커독 이미지 그리기
				
				if(!(enemy).get_Right_Flag()){//오른쪽 이동중
						if(((Walker_Dog) enemy).get_Find_Hero()){
							walker_Dog = tk.getImage("img/walker_Dog_Right_Attack/walker_Dog_Right_Attack_" + ((Walker_Dog) enemy).set_Walk_Plus() + ".png");//공격 범위내에 영웅이 있을때
						}else {
							walker_Dog = tk.getImage("img/walker_Dog_Right/walker_Dog_Right_" + ((Walker_Dog) enemy).set_Walk_Plus() + ".png");
						}
				}
				//워커 독 이미지 그리기
				
				else{
						if(((Walker_Dog)enemy).get_Find_Hero()){//왼쪽 이동중
							//공격범위 내에 영웅 있을때
							walker_Dog = tk.getImage("img/walker_Dog_Left_Attack/walker_Dog_Left_Attack_" + ((Walker_Dog) enemy).set_Walk_Plus() + ".png"); //공격 범위내에 영웅이 있을때
						}else {
							walker_Dog = tk.getImage("img/walker_Dog_Left/walker_Dog_Left_" + ((Walker_Dog) enemy).set_Walk_Plus() + ".png");
						}
				}
				
				buffg.drawRect(enemy.get_enemy_Point().x, enemy.get_enemy_Point().y, enemy.get_Enemy_Width(), enemy.get_Enemy_Height());
				
				buffg.drawImage(walker_Dog, enemy.get_enemy_Point().x- 20, enemy.get_enemy_Point().y-40, this); //+8을 하는 이유는 안하면 공중부양함
			}
			
			
			
			
			
			
			
			
			
			
			
			//적군을 움직이는 함수 호출 -> 멀티쓰레드로 변경 생성과 동시에 적군 클레스 쓰레드 자동생성
			//enemy.enemy_Move();
			
			//캐릭터와 몬스터 충돌판정 함수 호출
			crash_Decide_Enemy(mainCh, enemy, enemy.get_Move_Site());
			
			
			//땅하고 적군하고 충돌판정 땅에 떨어지고있을때만 하면 되긴 한다.
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
		
		
		//보스 그리기 1판 보스가 존재한다면.
		if(stage_1_Boss != null){
			stage_1_Boss.set_Hero_Point(mainCh.get_Hero_X_Point(), mainCh.get_Hero_Y_Point()); //영웅 위치 정보를 보낸다.
			buffg.drawRect(stage_1_Boss.get_Boss_Point().x, stage_1_Boss.get_Boss_Point().y, stage_1_Boss.get_Boss_Width(), stage_1_Boss.get_Boss_Height());
			
			if(!stage_1_Boss.get_Boss_Move_Direction()){
			stage_1Boss_Img = tk.getImage("img/boss/stage_1_Boss/boss_Right_" + stage_1_Boss.set_Image_Rotation() + ".png");
			}else {
				stage_1Boss_Img = tk.getImage("img/boss/stage_1_Boss/boss_Left_" + stage_1_Boss.set_Image_Rotation() + ".png");
			}
			
			if(stage_1_Boss.get_Boss_Move_Direction() && stage_1_Boss.get_Boss_pattern() == 2)  //2번 패턴 오른쪽으로 돌진할때.
			{
				stage_1Boss_Img = tk.getImage("img/boss/stage_1_Boss/pattern_2/boss_Right_Move_" + stage_1_Boss.set_Image_Rotation_Pattern_2() + ".png");
			}else if(!stage_1_Boss.get_Boss_Move_Direction() && stage_1_Boss.get_Boss_pattern() == 2){
				stage_1Boss_Img = tk.getImage("img/boss/stage_1_Boss/pattern_2/boss_Left_Move_" + stage_1_Boss.set_Image_Rotation_Pattern_2() + ".png");
			}
			
			buffg.drawImage(stage_1Boss_Img, stage_1_Boss.get_Boss_Point().x, stage_1_Boss.get_Boss_Point().y, this);
			
			//보스 3번 패턴공격 레이저 공격
			if(stage_1_Boss.get_Boss_1_Patter3().get_Magic_Direct() == 1 && stage_1_Boss.get_Boss_pattern() == 3){ //오른쪽 공격
				
				if(stage_1_Boss.get_Boss_1_Patter3().set_Image_Rotation_Pattern_3() == 0){ //레이저 스탠바이 이미지
					boss_Attac_Raser = tk.getImage("img/boss/stage_1_Boss/pattern_3/laser_Standby.png");
				}else{
					boss_Attac_Raser = tk.getImage("img/boss/stage_1_Boss/pattern_3/laser_Right_" + stage_1_Boss.get_Boss_1_Patter3().set_Image_Rotation_Pattern_3() + ".png");
				}
				buffg.drawImage(boss_Attac_Raser, stage_1_Boss.get_Boss_Point().x + stage_1_Boss.get_Boss_Width(), stage_1_Boss.get_Boss_Point().y + 25, this);
				System.out.println("오른쪽 공격");
				
				System.out.println(stage_1_Boss.get_Boss_1_Patter3().set_Image_Rotation_Pattern_3());
				
			}else if(stage_1_Boss.get_Boss_1_Patter3().get_Magic_Direct() == 2 && stage_1_Boss.get_Boss_pattern() == 3){ //왼쪽 공격
				
				
				if(stage_1_Boss.get_Boss_1_Patter3().set_Image_Rotation_Pattern_3() == 0){ //레이저 스탠바이 이미지
					boss_Attac_Raser = tk.getImage("img/boss/stage_1_Boss/pattern_3/laser_Standby.png");
				}else{
				boss_Attac_Raser = tk.getImage("img/boss/stage_1_Boss/pattern_3/laser_Left_" + stage_1_Boss.get_Boss_1_Patter3().set_Image_Rotation_Pattern_3() + ".png");
				}
				
				
				
				
				buffg.drawImage(boss_Attac_Raser, stage_1_Boss.get_Boss_Point().x - 1080 + stage_1_Boss.get_Boss_Width(), stage_1_Boss.get_Boss_Point().y + 25, this);
				System.out.println("왼쪽 공격");
				
			}
			
			
		}
		
		
		
	}
	
	
	//스테이지가 시작될때마다 적군 숫자를 파라메터로 받고 생성하고 해야 할 듯 하다.
	public void enemy_Process(){
		
		//enemy = new Walker(stage.get_Block().get(0).get_Left_Top_Point().x - 20,   //-20 인이유는 캐릭터를 블록 바깥 까지 밀어낼수 있도록하기 위함
		//		stage.get_Block().get(0).get_Left_Top_Point().x+stage.get_Block().get(0).get_Widht(), 
		//		stage.get_Block().get(0).get_Left_Top_Point().y - 70); //20, width 는 경계 범위 지정, height 는 몬스터가 밝고 있는 블럭의 높이
		
		//enemy_List.add(enemy);
		
		
		
		//생성된 스테이지의 워커를 그려야함
		for(int i=0; i<stage.get_Enemy().size(); i++){
			//추가된 워커들을 바로밑의 블록에 안착시킨다.
			//stage.get_Walker().get(i).set_Down_Start_True();
			//추적 알고리즘도 같이 떨궈야한다.
			//stage.get_Walker().get(i).init_Range_Site(stage.get_Walker().get(i).get_enemy_Point().x, stage.get_Walker().get(i).get_enemy_Point().y);
			
			//블록과 겹치는 적군이 있다면 블록 위로 올려준다.
			for(int j=0; j<stage.get_Block().size(); j++){
				stage.get_Enemy().get(i).set_Down_Start_True(); //초기 생성시 맞닿은 벽위에 안착 시키기 위해서
			
				crash_Decide_Enemy_Block(stage.get_Block().get(j), stage.get_Enemy().get(i));
			}
			
			enemy_List.add(stage.get_Enemy().get(i));
			
		}
		//워커 독 넣기
		//Walker_Dog a = new Walker_Dog(640, 640, 1604);
		//enemy_List.add(a);
		
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
	
	
	//이미지 버퍼링 미리 가져다 놓기
		public void Image_Init(){
			//이미지 버퍼링
			
					
			
			weapon_Png = tk.getImage("img/hero_Jump_Left.png"); //점프 이미지
			buffg.drawImage(weapon_Png, 0,0, this);
			
			weapon_Png = tk.getImage("img/hero_Jump_Right.png");
			buffg.drawImage(weapon_Png, 0,0, this);
			
			
					for(int i=1; i<=4;i++){//영웅 시야 암전 버퍼링
						weapon_Png = tk.getImage("img/Hero_View_Right/Hero_View_Right"+i+".png");
						buffg.drawImage(weapon_Png, 0,0, this);
						weapon_Png = tk.getImage("img/Hero_View_Left/Hero_View_Left"+i+".png");
						buffg.drawImage(weapon_Png, 0,0, this);
					}
			
					for(int i=1; i<=7;i++){ //무기 이미지 버퍼링
						weapon_Png = tk.getImage("img/weapone_Right_1/pistol_Right_"+i+".png");
						buffg.drawImage(weapon_Png, 0,0, this);
						weapon_Png = tk.getImage("img/weapone_Left_1/pistol_Left_"+i+".png");
						buffg.drawImage(weapon_Png, 0,0, this);
						
						weapon_Png = tk.getImage("img/weapone_Right_2/pistol_Right_"+i+".png");
						buffg.drawImage(weapon_Png, 0,0, this);
						weapon_Png = tk.getImage("img/weapone_Left_2/pistol_Left_"+i+".png");
						buffg.drawImage(weapon_Png, 0,0, this);
						
						weapon_Png = tk.getImage("img/weapone_Right_3/pistol_Right_"+i+".png");
						buffg.drawImage(weapon_Png, 0,0, this);
						weapon_Png = tk.getImage("img/weapone_Left_3/pistol_Left_"+i+".png");
						buffg.drawImage(weapon_Png, 0,0, this);
					}
					
					for(int i=1; i<=12; i++){ //걸어다니는 이미지
						weapon_Png = tk.getImage("img/Hero_Move_Right_Down/hero_Move_Right_"+i+".png"); //앉았을때
						buffg.drawImage(weapon_Png, 0,0, this);
						weapon_Png = tk.getImage("img/Hero_Move_Right/hero_Right_"+i+".png");
						buffg.drawImage(weapon_Png, 0,0, this);
						
						weapon_Png = tk.getImage("img/Hero_Move_Left_Down/hero_Move_Left_"+i+".png");
						buffg.drawImage(weapon_Png, 0,0, this);
						weapon_Png = tk.getImage("img/Hero_Move_Left/hero_Left_"+i+".png");
						buffg.drawImage(weapon_Png, 0,0, this);
					}
					
					
					for(int i=1; i<=9; i++){//워커 공격 시
						weapon_Png = tk.getImage("img/walker_Right_Attack/walker_Right_Attack_"+i+".png"); //앉았을때
						buffg.drawImage(weapon_Png, 0,0, this);
						weapon_Png = tk.getImage("img/walker_Left_Attack/walker_Left_Attack_"+i+".png"); //앉았을때
						buffg.drawImage(weapon_Png, 0,0, this);
					}
					
					
					//암전 버퍼링
					for(int i=1; i<=4; i++){
						weapon_Png = tk.getImage("img/Hero_View_Right/Hero_View_Right_" + i + ".png"); 
						buffg.drawImage(weapon_Png, 0,0, this);
						weapon_Png = tk.getImage("img/Hero_View_Left/Hero_View_Left_" + i + ".png"); 
						buffg.drawImage(weapon_Png, 0,0, this);
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
				
				//기본 총소리
				sound = new File("sound/pistol/pistol_1.wav");
					
				try{
					clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(sound));
				//	clip.start();
				}catch(Exception e1){
					
				}
			}
			
			if(weapon_Number == 2 && weapon_Number2_Flag){
				//공격중일때 총알을 생성한다.
				attack = true;
				weapon_Number2_Flag = false; //눌렀을때만 발사 때었을때 true 재장전 가능
				//쌍 총소리
				sound = new File("sound/pistol/pistol_2.wav");
				
				try{
					clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(sound));
				//	clip.start();
				}catch(Exception e1){
					
				}
			}
			
			if(weapon_Number == 3 && weapon_Number3_Flag){
				//공격중일때 총알을 생성한다.
				attack = true;
				weapon_Number3_Flag = false; //눌렀을때만 발사 때었을때 true 재장전 가능
				//기관총 총소리
				sound = new File("sound/pistol/pistol_3.wav");
				
				try{
					clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(sound));
				//	clip.start();
				}catch(Exception e1){
					
				}
				
			}
			/*
			if(weapon_Number1_Flag || weapon_Number2_Flag || weapon_Number3_Flag){
			 //기본 총소리 사운드
			try{
				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(sound));
				clip.start();
			}catch(Exception e1){
				
			}
			if(weapon_Number == 3){
			sound = new File("sound/pistol/탄피.wav");
			try{
				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(sound));
				clip.start();
			}catch(Exception e1){
				
			}
			}
			
			}*/
			
			
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
			
			attack_Img_Temp = true; //총 발사 이미지 시작함 
			
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
			
			
		case KeyEvent.VK_R : // R 은 그 판 재시작
			
			restart();
			break;
			
		}
		
		
	}

	@Override
	//키보드가 타이핑 될 때 이벤트 처리하는 곳
	public void keyTyped(KeyEvent e) {
	}
	///////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	//총알 쓰레드로 돌리고
	//벽넘어가면 몬스터 없애고 
	//여웅ㅇ이 벽넘어가면 리스타트
	
	
	
	
	
}