package mapData;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Stage {

	//배열리스트에 블록에 대한 정보를 담는다.
	ArrayList block_Array;

	Block temp_Block; 
	
	//블록의 좌상단 포인트를 반환하기 위한 임시 변수
	Point temp_Block_Left_Top_Point;
	//블록의 넓이, 높이 임시 변수
	int width;
	int height;
	
	
	//스테이지마다 블록의 위치를 지정한다.
	public Stage() {
	
	block_Array = new ArrayList<Block>();
	temp_Block_Left_Top_Point = new Point(0, 0); //초기화
	//temp_Block = new Block(temp_Block_Left_Top_Point, 0, 0); //블록 어레이이 넣을 블록

	
	}
	
	//1스테이지
	public void map_Stage(int stage_Num){
		
		//스테이지 설정
				switch(stage_Num){
					//1스테이지 일때
				    case 1:
				    	stage_Num_1();
				    	
				    	
				    	
					break;
					
					
				}
		
	}

	public void stage_Num_1(){
		
		String line = null;
		//맵 메이커를 통해 만든 맵을 불러온다.
		try {
			
			File file = new File("C:\\Users\\USER\\workspace\\Shot\\bin\\mapData\\stage_1.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			
			
			//while ((line = reader.readLine()) != null ) {
			line = reader.readLine();
			System.out.println(line);
			//}
			reader.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//txt 로 저장된 맵을 파싱해서 해석한다.
		String[] Map = line.split("#");
		String[] square_Element = null;
		for(int i=0; i<Map.length; i++){
			
			square_Element = Map[i].split("@");
			
			temp_Block_Left_Top_Point.x = Integer.parseInt(square_Element[0]);
			temp_Block_Left_Top_Point.y = Integer.parseInt(square_Element[1]);
			width = Integer.parseInt(square_Element[2]);
			height = Integer.parseInt(square_Element[3]);
			temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
			block_Array.add(temp_Block);
			
			
		}

		
		
		/*
				temp_Block_Left_Top_Point.x = 0;
				temp_Block_Left_Top_Point.y = 670;
				width = 700;
				height = 500;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//가장 바닥 발판
		
				
				temp_Block_Left_Top_Point.x = 300;
				temp_Block_Left_Top_Point.y = 450;
				width = 300;
				height = 55;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//오른쪽 중간 부분 긴 바닥
				
				
				
				temp_Block_Left_Top_Point.x = 150;
				temp_Block_Left_Top_Point.y = 250;
				width = 270;
				height = 50;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//가운데 윗 부분 살짝 긴 바닥
				
				
				
				//1스테이지의 첫 번째 블록
				temp_Block_Left_Top_Point.x = 100;
				temp_Block_Left_Top_Point.y = 500;
				width = 100;
				height = 50;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				
				
				
				
				temp_Block_Left_Top_Point.x = 500;
				temp_Block_Left_Top_Point.y = 300;
				width = 70;
				height = 60;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//오른쪽 가운데 작은 블록
				
				
				
				
				temp_Block_Left_Top_Point.x = 450;
				temp_Block_Left_Top_Point.y = 600;
				width = 200;
				height = 55;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//가장 오른쪽 아래
				
				
				temp_Block_Left_Top_Point.x = 50;
				temp_Block_Left_Top_Point.y = 450;
				width = 30;
				height = 60;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//좌측 중간 아래의 작은 바닥
				
				
				temp_Block_Left_Top_Point.x = 100;
				temp_Block_Left_Top_Point.y = 350;
				width = 30;
				height = 45;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//좌측 중간 아래 위의 작은 바닥
				
				
				
				
				
				
				
				
				temp_Block_Left_Top_Point.x = 650;
				temp_Block_Left_Top_Point.y = 550;
				width = 30;
				height = 50;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//가장 오른쪽 아래 작은 사각형 발판
				
				
				temp_Block_Left_Top_Point.x = 600;
				temp_Block_Left_Top_Point.y = 200;
				width = 100;
				height = 50;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//가장 오른쪽 위의 사각형
		*/
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
	}
	
	//생성된 블록을 반환해서 맵을 그린다.
	public ArrayList<Block> get_Block(){
		return block_Array;
	}
	
	

}
