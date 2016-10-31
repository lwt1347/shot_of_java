package mapData;

import java.awt.Point;
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
		
				temp_Block_Left_Top_Point.x = 0;
				temp_Block_Left_Top_Point.y = 670;
				width = 700;
				height = 30;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//가장 바닥 발판
		
				
				temp_Block_Left_Top_Point.x = 300;
				temp_Block_Left_Top_Point.y = 450;
				width = 300;
				height = 35;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//오른쪽 중간 부분 긴 바닥
				
				
				
				temp_Block_Left_Top_Point.x = 150;
				temp_Block_Left_Top_Point.y = 250;
				width = 270;
				height = 40;
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
				height = 30;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//오른쪽 가운데 작은 블록
				
				
				
				
				temp_Block_Left_Top_Point.x = 450;
				temp_Block_Left_Top_Point.y = 600;
				width = 200;
				height = 30;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				
				
				temp_Block_Left_Top_Point.x = 50;
				temp_Block_Left_Top_Point.y = 450;
				width = 30;
				height = 30;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//좌측 중간 아래의 작은 바닥
				
				
				temp_Block_Left_Top_Point.x = 100;
				temp_Block_Left_Top_Point.y = 350;
				width = 30;
				height = 20;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//좌측 중간 아래 위의 작은 바닥
				
				
				
				
				
				
				
				
				temp_Block_Left_Top_Point.x = 650;
				temp_Block_Left_Top_Point.y = 550;
				width = 30;
				height = 40;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//가장 오른쪽 아래 작은 사각형 발판
				
				
				temp_Block_Left_Top_Point.x = 600;
				temp_Block_Left_Top_Point.y = 200;
				width = 100;
				height = 40;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//가장 오른쪽 위의 사각형
				
				
				
				
				
	}
	
	//생성된 블록을 반환해서 맵을 그린다.
	public ArrayList<Block> get_Block(){
		return block_Array;
	}
	
	

}
