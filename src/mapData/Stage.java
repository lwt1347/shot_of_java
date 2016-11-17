package mapData;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import enemy.Enemy;
import enemy.Walker;
import enemy.Walker_Dog;

public class Stage {

	//배열리스트에 블록에 대한 정보를 담는다.
	ArrayList block_Array;

	//적군 배열
	ArrayList enemy_Array;
	
	//톱니바퀴 배열
	ArrayList Trab_Saw_Tooth_Array;
	
	Block temp_Block; 
	
	//워커 탬프
	Walker temp_Walker;
	
	//워커독 탬프
	Walker_Dog temp_Walker_Dog;
	
	//포탈 생성
	Next_Page_Portal portal;
	
	//톱니바퀴 생성
	Trab_Saw_Tooth trab_Saw_Tooth;
	
	//블록의 좌상단 포인트를 반환하기 위한 임시 변수
	Point temp_Block_Left_Top_Point;
	//블록의 넓이, 높이 임시 변수
	int width;
	int height;
	
	//워커 값 대입 임시 변수
	int temp_Left_Site;
	int temp_Right_Site;
	int temp_Bottom_Site;
	
	
	//스테이지마다 블록의 위치를 지정한다.
	public Stage() {
	
	block_Array = new ArrayList<Block>();
	temp_Block_Left_Top_Point = new Point(0, 0); //초기화
	//temp_Block = new Block(temp_Block_Left_Top_Point, 0, 0); //블록 어레이이 넣을 블록
	
	enemy_Array = new ArrayList<Enemy>(); //워커 초기화
	
	//포탈 초기화
	portal = new Next_Page_Portal(0, 0);
	
	//톱니바퀴 초기화
	trab_Saw_Tooth = new Trab_Saw_Tooth(0, 0);
	Trab_Saw_Tooth_Array = new ArrayList<Trab_Saw_Tooth>();
	}
	
	
	//메모리 누수 제거
	public void reset_Memory(){
		block_Array.clear();
		enemy_Array.clear();
		Trab_Saw_Tooth_Array.clear();
	}
	
	
	//1스테이지
	public void map_Stage(int stage_Num){
		
		//스테이지 설정
				
		stage_Num(stage_Num);
				    	
				    	
				
		
	}

	public void stage_Num(int stage_Num){
		
		String line = null;
		//맵 메이커를 통해 만든 맵을 불러온다.
		try {
			
			File file = new File("C:\\Users\\USER\\workspace\\Shot\\bin\\mapData\\stage_" + stage_Num +".txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			
			
			//while ((line = reader.readLine()) != null ) {
			line = reader.readLine();
			//System.out.println(line);
			//}
			reader.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//txt 로 저장된 맵을 파싱해서 해석한다.
		String[] Map = line.split("벽임");
		String[] square_Element = null;
		for(int i=0; i<Map.length-1; i++){ //벽돌 생성
			
			square_Element = Map[i].split("@");
			
			temp_Block_Left_Top_Point.x = Integer.parseInt(square_Element[0]);
			temp_Block_Left_Top_Point.y = Integer.parseInt(square_Element[1]);
			width = Integer.parseInt(square_Element[2]);
			height = Integer.parseInt(square_Element[3]);
			temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
			block_Array.add(temp_Block);
			
		}	
		
		
		
		//워커 생성
		
		Map = line.split("워커");
		String[] walker_Element = null;
		for(int i=1; i<Map.length-1; i++){
		//for(int i=1; i<3; i++){	
			walker_Element = Map[i].split("@");
			//System.out.println(walker_Element.length);
			temp_Left_Site = Integer.parseInt(walker_Element[0]);
			temp_Right_Site = Integer.parseInt(walker_Element[1]);
			temp_Bottom_Site = Integer.parseInt(walker_Element[2]);
			temp_Walker = new Walker(temp_Left_Site - 35, temp_Right_Site, temp_Bottom_Site - 70);
			enemy_Array.add(temp_Walker);
		}
		//System.out.println(Map.length);
		
		//워커독
		Map = line.split("울프");
		String[] walker_Dog_Element = null;
		for(int i=1; i<Map.length-1; i++){
		//for(int i=1; i<3; i++){	
			walker_Dog_Element = Map[i].split("@");
			//System.out.println(walker_Element.length);
			temp_Left_Site = Integer.parseInt(walker_Dog_Element[0]);
			temp_Right_Site = Integer.parseInt(walker_Dog_Element[1]);
			temp_Bottom_Site = Integer.parseInt(walker_Dog_Element[2]);
			temp_Walker_Dog = new Walker_Dog(temp_Left_Site - 35, temp_Right_Site, temp_Bottom_Site - 70);
			enemy_Array.add(temp_Walker_Dog);
		}
		
		//톱니 생성
		Map = line.split("톱니");
		String[] trab_Saw_Tooth_Element = null;
		for(int i=1; i<Map.length-1; i++){
		//for(int i=1; i<3; i++){	
			trab_Saw_Tooth_Element = Map[i].split("@");
			//System.out.println(walker_Element.length);
			int x = Integer.parseInt(trab_Saw_Tooth_Element[0]);
			int y = Integer.parseInt(trab_Saw_Tooth_Element[1]);
			trab_Saw_Tooth = new Trab_Saw_Tooth(x,y);
			Trab_Saw_Tooth_Array.add(trab_Saw_Tooth);
		}
		
		
		
		
		
		//포탈 정보는 *로 나눈어서 제일 마지막에 들어간다.
		Map = line.split("포탈");
		    	String[] portal_Point = null;
		portal_Point = Map[1].split("@");
				//마지막으로파싱해서 포탈의 위치를 알려준다
		portal.set_portal_Point(Integer.parseInt(portal_Point[0]), Integer.parseInt(portal_Point[1]));
				//50@1700
				
		
				
	}
	
	//생성된 블록을 반환해서 맵을 그린다.
	public ArrayList<Block> get_Block(){
		return block_Array;
	}
	
	//생성된 워커를 반환한다.
	public ArrayList<Enemy> get_Enemy(){
		return enemy_Array;
	}
	
	//생성된 포탈 을 반환한다.
	public Next_Page_Portal get_Next_Page_Portal(){
		return portal;
	}
	
	//생성된 톱니바퀴를 반환한다.
	public ArrayList<Trab_Saw_Tooth> get_Trab_Saw_Tooth(){
		return Trab_Saw_Tooth_Array;
	}
	
	
	
}
